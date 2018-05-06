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

public class FMSHandler {
	private static final Logger LOGGER = Logger.getLogger(FMSController.class);
	private static FMSHandler fmsHanlder = new FMSHandler();
	private List<Connection> connectionsList;
	
	private FMSHandler() {
		this.connectionsList = new ArrayList<Connection>();;
	}
	
	public static FMSHandler getInstance() {
		return fmsHanlder;
	}

	public ConnectionResponse getConnections(String email){
		LOGGER.info("getConnections: Primary account: "+email);
		return new ConnectionResponse(true, findConnections(email));
	}

	public List<Connection> getConnectionsList() {
		return connectionsList;
	}

	public void setConnectionsList(List<Connection> connectionsList) {
		this.connectionsList = connectionsList;
	}
	
	public Response addConncetion(ConnectionRequest connectionRequest) {
		LOGGER.info(connectionRequest.toString());
		String primaryUser = connectionRequest.getUser1();
		String secondaryUser = connectionRequest.getUser2();
		//String primaryUser = connectionRequest.getFriends()[0];
		//String secondaryUser = connectionRequest.getFriends()[1];

		if(!connectionPresent(primaryUser, secondaryUser)) {
			Connection friend = new Connection(primaryUser, secondaryUser,
					ConnectionType.FRIEND, Status.SUBSCRIBERD);
			connectionsList.add(friend);
			return new Response(true);
		}else {
			return new ErrorResponse(false,"Cannot add connection, connection already present.");
		}
	}

	public Response removeConncetion(ConnectionRequest connectionRequest) {
		LOGGER.info(connectionRequest.toString());
		String primaryUser = connectionRequest.getUser1();
		String secondaryUser = connectionRequest.getUser2();
		//String primaryUser = connectionRequest.getFriends()[0];
		//String secondaryUser = connectionRequest.getFriends()[1];

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

	public ConnectionResponse findCommonConnetions(ConnectionRequest connectionRequest) {
		List<String> connectionsOfUser1 = findConnections(connectionRequest.getUser1());
		List<String> connectionsOfUser2 = findConnections(connectionRequest.getUser2());
		List<String> commonConnection = new ArrayList<String>();
		Iterator<String>iter = connectionsOfUser1.iterator();
		while(iter.hasNext()) {
			String connectionEmail = iter.next();
			if(connectionsOfUser2.contains(connectionEmail))
				commonConnection.add(connectionEmail);
		}
		return new ConnectionResponse(true, commonConnection);
	}
	
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
	
	public Response upsertSubscriber(SubscriberRequest subscriberRequest, boolean add) {
		LOGGER.info("upsertSubscriber: "+subscriberRequest.toString());
		LOGGER.info("subcribe: "+add);
		String primaryUser = subscriberRequest.getRequestor();
		String secondaryUser = subscriberRequest.getTarget();
		//String primaryUser = connectionRequest.getFriends()[0];
		//String secondaryUser = connectionRequest.getFriends()[1];
		if(add) {
			if(!connectionPresent(primaryUser,secondaryUser)){
				LOGGER.info("adding new subscriber");
				Connection subscriber = new Connection( primaryUser, secondaryUser,
														ConnectionType.SUBSCRIBER, 
														add==true?Status.SUBSCRIBERD:Status.UNSUBSCRIBED);
				connectionsList.add(subscriber);
			}else {
				LOGGER.info("subscriber already present");
				return new ErrorResponse(false,"Cannot add connection, connection already present.");
			}
		}else {
			if(connectionPresent(primaryUser,secondaryUser)){
				LOGGER.info("removing a subscriber");
				Iterator<Connection> iter = connectionsList.iterator();
				while(iter.hasNext()) {
					Connection connection = iter.next();
					if(connection.getPrimary().equals(primaryUser) 
							&& connection.getSecondary().equals(secondaryUser)) {
						if(connection.getConnectionType().equals(ConnectionType.FRIEND)) {
							connection.setStatus(Status.UNSUBSCRIBED);
						}else {
							iter.remove();
						}
						break;
					}
				}
			}else {
				LOGGER.info("subscriber not present");
				return new ErrorResponse(false,"Cannot not block, connection not present.");
			}
		}
		return new Response(true);
	}

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
