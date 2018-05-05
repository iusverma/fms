package com.spg.app;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spg.model.Connection;
import com.spg.request.ConnectionRequest;
import com.spg.request.MessageUpdateRequset;
import com.spg.request.Request;
import com.spg.response.ConnectionResponse;
import com.spg.response.PublishResponse;
import com.spg.response.Response;
import com.spg.utils.ConversionUtils;

@RestController
public class FMSController {
	private static final Logger LOGGER = Logger.getLogger(FMSController.class);

	// Use case 1 for adding friend
	// public SuccessResponse friend(@RequestBody ConnectionRequest
	// connectionRequest) {
	@RequestMapping(value = "/friend", method = RequestMethod.PUT)
	public Response friend(@RequestBody ConnectionRequest connectionRequest) {
		LOGGER.info(connectionRequest.toString());
		FMSHandler fmsHandler = FMSHandler.getInstance();
		return fmsHandler.addConncetion(connectionRequest);
	}

	// Returns all connections
	@RequestMapping(value = "/friends", method = RequestMethod.GET)
	public List<Connection> friend() {
		FMSHandler fmsHandler = FMSHandler.getInstance();
		return fmsHandler.getConnectionsList();
	}

	// Use case 2 for getting friend list
	@RequestMapping(value = "/friends", method = RequestMethod.POST)
	public ConnectionResponse friend(@RequestBody Request request) {
		LOGGER.info("RequestBody: " + request);
		// Request requestObj = ConversionUtils.convertStringToRequestObject(request);
		FMSHandler fmsHandler = FMSHandler.getInstance();
		return fmsHandler.getConnections(request.getEmail());
	}

	// User case 3 for common friends
	@RequestMapping(value = "/friends/common", method = RequestMethod.POST)
	public ConnectionResponse commonFriends(@RequestBody ConnectionRequest connectionRequest) {
		LOGGER.info(connectionRequest.toString());
		FMSHandler fmsHandler = FMSHandler.getInstance();
		return fmsHandler.findCommonConnetions(connectionRequest);
	}

	// Use case 4 for adding subscriber
	@RequestMapping(value = "/subscriber", method = RequestMethod.PUT)
	public Response addSubscriber(@RequestBody ConnectionRequest connectionRequest) {
		FMSHandler fmsHandler = FMSHandler.getInstance();
		fmsHandler.upsertSubscriber(connectionRequest, true);
		return new Response(true);
	}

	// Use case 5 for blocking subscriber
	@RequestMapping(value = "/subscriber", method = RequestMethod.DELETE)
	public Response deleteSubscriber(@RequestBody ConnectionRequest connectionRequest) {
		FMSHandler fmsHandler = FMSHandler.getInstance();
		fmsHandler.upsertSubscriber(connectionRequest, false);
		return new Response(true);
	}

	// Use case 6 for getting list of subscriber who are update with message
	@RequestMapping(value = "/subscribers", method = RequestMethod.POST)
	public PublishResponse subscribers(@RequestBody MessageUpdateRequset msgUpdateRequest) {
		FMSHandler fmsHandler = FMSHandler.getInstance();
		return fmsHandler.publish(msgUpdateRequest);
	}
}
