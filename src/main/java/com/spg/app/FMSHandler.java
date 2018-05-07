package com.spg.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.spg.enums.ConnectionType;
import com.spg.enums.Status;
import com.spg.model.Connection;
import com.spg.request.ConnectionRequest;
import com.spg.request.MessageUpdateRequset;
import com.spg.request.SubscriberRequest;
import com.spg.response.ConnectionResponse;
import com.spg.response.ErrorResponse;
import com.spg.response.PublishResponse;
import com.spg.response.Response;

/**
 * FMSHandler stores the connection list for all connections/subscriptions
 * and provides API for work on connections list
 * @author Ayush Verma
 */
public class FMSHandler {
	/** Logger */
	private static final Logger LOGGER = Logger.getLogger(FMSController.class);

	/** Singleton instance for access connection list */
	private static FMSHandler fmsHanlder = new FMSHandler();

	/** Connection list */
	private List<Connection> connectionsList;
	
	private FMSHandler() {
		this.connectionsList = new ArrayList<Connection>();;
	}

	/** Static API to get FMSHandler instance */
	public static FMSHandler getInstance() {
		return fmsHanlder;
	}

	/**
	 * getConnections method
	 * returns all connection for given email
	 */
	public ConnectionResponse getConnections(String email){
		LOGGER.info("getConnections: Primary account: "+email);
		return new ConnectionResponse(true, findConnections(email));
	}

	/**
	 * getConnectionsList method
	 * returns all connections in list with full details
	 */
	public List<Connection> getConnectionsList() {
		return connectionsList;
	}

	/**
	 * setConnectionsList method
	 * Setter for connectionList
	 */
	public void setConnectionsList(List<Connection> connectionsList) {
		this.connectionsList = connectionsList;
	}

	/**
	 * addConnectiod  method
	 * Given a connection request with two user details,
	 * it adds a new connection to connectionsList
	 */
	public Response addConncetion(ConnectionRequest connectionRequest) {
		LOGGER.info(connectionRequest.toString());
		String primaryUser = connectionRequest.getFriends()[0];
		String secondaryUser = connectionRequest.getFriends()[1];

		if(!connectionPresent(primaryUser, secondaryUser)) {
			Connection friend = new Connection(primaryUser, secondaryUser,
					ConnectionType.FRIEND, Status.SUBSCRIBERD);
			connectionsList.add(friend);
			return new Response(true);
		}else {
			return new ErrorResponse(false,"Cannot add connection, connection already present.");
		}
	}

	/**
	 * remvoeConnectiod  method
	 * Given a connection request with two user details,
	 * it remove an existing connection to connectionsList
	 *
	 * returns error when connection is not present
	 */
	public Response removeConncetion(ConnectionRequest connectionRequest) {
		LOGGER.info(connectionRequest.toString());
		String primaryUser = connectionRequest.getFriends()[0];
		String secondaryUser = connectionRequest.getFriends()[1];

		Iterator<Connection> iter = connectionsList.iterator();
		while(iter.hasNext()) {
			Connection conn = iter.next();
			if(conn.getPrimary().equals(primaryUser) && conn.getSecondary().equals(secondaryUser)) {
				iter.remove();
				return new Response(true);
			}
		}
		return new ErrorResponse(false,"Cannot remove connection, no connection present with given details.");
	}

	/**
	 * findCommonConnections method
	 * find list of common connection between two users
	 *
	 * returns empty list if no common connection is present
	 */
	public ConnectionResponse findCommonConnetions(ConnectionRequest connectionRequest) {
		List<String> connectionsOfUser1 = findConnections(connectionRequest.getFriends()[0]);
		List<String> connectionsOfUser2 = findConnections(connectionRequest.getFriends()[1]);
		List<String> commonConnection = new ArrayList<String>();
		Iterator<String>iter = connectionsOfUser1.iterator();
		while(iter.hasNext()) {
			String connectionEmail = iter.next();
			if(connectionsOfUser2.contains(connectionEmail))
				commonConnection.add(connectionEmail);
		}
		return new ConnectionResponse(true, commonConnection);
	}

	/**
	 * publish method
	 * Publish message to all friends and subscriber who are active and subscribed for updates.
	 */
	public PublishResponse publish(MessageUpdateRequset msgUpdateRequest) {
		List<String> subscribersList = new ArrayList<String>();
		Iterator<Connection> iter = connectionsList.iterator();
		while(iter.hasNext()) {
			Connection connection = iter.next();
			if(connection.getPrimary().equals(msgUpdateRequest.getSender())
					&& connection.getStatus()==Status.SUBSCRIBERD) {
				subscribersList.add(connection.getSecondary());
				LOGGER.info("Updates sent to subscriber: "+connection.getSecondary());
			}
		}
		return new PublishResponse(true, subscribersList);
	}

	/**
	 * upsertSubscriber
	 * add a new subscriber
	 * deletes an existing subscriber
	 * Unsubscribe a friend from updates
	 */
	public Response upsertSubscriber(SubscriberRequest subscriberRequest, boolean add) {
		LOGGER.info("upsertSubscriber: "+subscriberRequest.toString());
		LOGGER.info("subcribe: "+add);
		String primaryUser = subscriberRequest.getRequestor();
		String secondaryUser = subscriberRequest.getTarget();
		if(add) {
			/**
			 * Request is for adding a new subscription
			 */
			if(!connectionPresent(primaryUser,secondaryUser)){
				/**
				 * There is not existing connection/subscription. Hence a new connection is added.
				 */
				LOGGER.info("adding new subscriber");
				Connection subscriber = new Connection( primaryUser, secondaryUser,
														ConnectionType.SUBSCRIBER, 
														Status.SUBSCRIBERD);
				connectionsList.add(subscriber);
			}else {
				/**
				 * This is an existing entry with matching details, can't add a new one
				 */
				LOGGER.info("subscriber already present");
				return new ErrorResponse(false,"Cannot add subscriber, subscriber already present.");
			}
		}else {
			/**
			 * Request is for removing a new subscription
			 */
			if(connectionPresent(primaryUser,secondaryUser)){
				/**
				 * Connection present, now need to subscribe
				 */
				LOGGER.info("removing a subscriber");
				Iterator<Connection> iter = connectionsList.iterator();
				while(iter.hasNext()) {
					Connection connection = iter.next();
					if(connection.getPrimary().equals(primaryUser)
							&& connection.getSecondary().equals(secondaryUser)) {
						if(connection.getConnectionType().equals(ConnectionType.FRIEND)) {
							/**
							 * This is a friends connection, should not be removed,
							 * just unsubscribe from updates
							 */
							connection.setStatus(Status.UNSUBSCRIBED);
						}else {
							/**
							 * Removing subscription
							 */
							iter.remove();
						}
						break;
					}
				}
			}else {
				/**
				 * No subscriber with given details, hence can not remove it
				 */
				LOGGER.info("subscriber not present");
				return new ErrorResponse(false,"Cannot not block, connection not present.");
			}
		}
		return new Response(true);
	}

	/**
	 * findConnections method
	 * for given primary email find all connections
	 * and return list of connection's email
	 */
	private List<String> findConnections(String primary){
		LOGGER.info("findConnections: Primary account: "+primary);
		List<String> connections = new ArrayList<String>();
		Iterator<Connection> iter = connectionsList.iterator();
		LOGGER.info("findConnections: connectionsList size: "+connectionsList.size());
		while(iter.hasNext()) {
			Connection connection = iter.next();
			LOGGER.info("findConnections: connection.getPrimary(): "+connection.getPrimary());
			if(connection.getPrimary().equals(primary)) {
				connections.add(connection.getSecondary());
			}
		}
		LOGGER.info("findConnections: Returning common connections: "+connections.size());
		return connections;
	}

	/**
	 * connectionPreset
	 * for given primary user and secondary user
	 * * returns true if there is connection between primary & secondary user
	 * * false otherwise
	 */
	private boolean connectionPresent(String primaryUser, String secondaryUser) {
		if(connectionsList.isEmpty())
			return false;
		Iterator<Connection> iter = connectionsList.iterator();
		while(iter.hasNext()) {
			Connection connection = iter.next();
			if(connection.getPrimary().equals(primaryUser)
					&& connection.getSecondary().equals(secondaryUser)) {
				return true;
			}
		}
		return false;
	}
}
