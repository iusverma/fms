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
import com.spg.response.ConnectionResponse;
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
		Connection friend = new Connection(primaryUser, secondaryUser,
				ConnectionType.FRIEND, Status.SUBSCRIBERD);
		connectionsList.add(friend);
		return new Response(true);
	}
	
	public ConnectionResponse findCommonConnetions(ConnectionRequest connectionRequest) {
		return new ConnectionResponse(true, findConnections(connectionRequest.getUser1()));
	}
	
	public PublishResponse publish(MessageUpdateRequset msgUpdateRequest) {
		LOGGER.info("Updates sent to subscribers");
		return new PublishResponse(true, findConnections(msgUpdateRequest.getSender()));
	}
	
	public Response upsertSubscriber(ConnectionRequest connectionRequest, boolean add) {
		String primaryUser = connectionRequest.getUser1();
		String secondaryUser = connectionRequest.getUser2();
		//String primaryUser = connectionRequest.getFriends()[0];
		//String secondaryUser = connectionRequest.getFriends()[1];
		Connection friend = new Connection(primaryUser, secondaryUser,
				ConnectionType.SUBSCRIBER, 
				add==true?Status.SUBSCRIBERD:Status.BLOCKED);
		connectionsList.add(friend);
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
}
