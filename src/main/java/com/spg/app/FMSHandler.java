package com.spg.app;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.spg.enums.ConnectionType;
import com.spg.enums.Status;
import com.spg.model.Connection;
import com.spg.request.ConnectionRequest;
import com.spg.request.MessageUpdateRequset;
import com.spg.response.SuccessResponse;

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

	public List<Connection> getConnections(){
	    return connectionsList;
	}

	public List<Connection> getConnectionsList() {
		return connectionsList;
	}

	public void setConnectionsList(List<Connection> connectionsList) {
		this.connectionsList = connectionsList;
	}
	
	public SuccessResponse addConncetion(ConnectionRequest connectionRequest) {
		LOGGER.info(connectionRequest.toString());
		String primaryUser = connectionRequest.getUser1();
		String secondaryUser = connectionRequest.getUser2();
		//String primaryUser = connectionRequest.getFriends()[0];
		//String secondaryUser = connectionRequest.getFriends()[1];
		Connection friend = new Connection(primaryUser, secondaryUser,
				ConnectionType.FRIEND, Status.SUBSCRIBERD);
		connectionsList.add(friend);
		return new SuccessResponse(true);
	}
	
	public List<Connection> getCommonConnetions(ConnectionRequest connectionRequest) {
		return connectionsList;
	}
	
	public List<Connection> publish(MessageUpdateRequset msgUpdateRequest) {
		LOGGER.info("Updates sent to subscribers");
		return connectionsList;
	}
	
	public SuccessResponse upsertSubscriber(ConnectionRequest connectionRequest, boolean add) {
		String primaryUser = connectionRequest.getUser1();
		String secondaryUser = connectionRequest.getUser2();
		//String primaryUser = connectionRequest.getFriends()[0];
		//String secondaryUser = connectionRequest.getFriends()[1];
		Connection friend = new Connection(primaryUser, secondaryUser,
				ConnectionType.SUBSCRIBER, 
				add==true?Status.SUBSCRIBERD:Status.BLOCKED);
		connectionsList.add(friend);
		return new SuccessResponse(true);
	}
}
