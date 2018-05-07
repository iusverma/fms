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
import com.spg.request.SubscriberRequest;
import com.spg.response.ConnectionResponse;
import com.spg.response.PublishResponse;
import com.spg.response.Response;

/**
 * FMSController
 * @author Ayush Verma
 * Controller for application, contains all APIs mapping
 */
@RestController
public class FMSController {
	private static final Logger LOGGER = Logger.getLogger(FMSController.class);

	// Use case 1 for adding friend
	/**
	 * friend, all new add friend connection will come here
	 */
	@RequestMapping(value = "/friend", method = RequestMethod.PUT)
	public Response friend(@RequestBody ConnectionRequest connectionRequest) {
		LOGGER.info(connectionRequest.toString());
		FMSHandler fmsHandler = FMSHandler.getInstance();
		return fmsHandler.addConncetion(connectionRequest);
	}

	/**
	 * deleteFriend, all delete request for a friend will come here
	 */
	@RequestMapping(value = "/friend", method = RequestMethod.DELETE)
	public Response deleteFriend(@RequestBody ConnectionRequest connectionRequest) {
		LOGGER.info(connectionRequest.toString());
		FMSHandler fmsHandler = FMSHandler.getInstance();
		return fmsHandler.removeConncetion(connectionRequest);
	}

	/**
	 * friends, request for list of all connection with full details
	 */
	@RequestMapping(value = "/friends", method = RequestMethod.GET)
	public List<Connection> friends() {
		FMSHandler fmsHandler = FMSHandler.getInstance();
		return fmsHandler.getConnectionsList();
	}

	// Use case 2 for getting friend list
	/**
	 * friends, request for list of all friends for any user will come here
	 */
	@RequestMapping(value = "/friends", method = RequestMethod.POST)
	public ConnectionResponse friends(@RequestBody Request request) {
		LOGGER.info("RequestBody: " + request);
		// Request requestObj = ConversionUtils.convertStringToRequestObject(request);
		FMSHandler fmsHandler = FMSHandler.getInstance();
		return fmsHandler.getConnections(request.getEmail());
	}

	// User case 3 for common friends
	/**
	 * commonFriends, request for common friends/subscriber betweem two user will come here
	 */
	@RequestMapping(value = "/friends/common", method = RequestMethod.POST)
	public ConnectionResponse commonFriends(@RequestBody ConnectionRequest connectionRequest) {
		LOGGER.info(connectionRequest.toString());
		FMSHandler fmsHandler = FMSHandler.getInstance();
		return fmsHandler.findCommonConnetions(connectionRequest);
	}

	// Use case 4 for adding subscriber
	/**
	 * addSubscriber, request for adding a subscriber will come here
	 */
	@RequestMapping(value = "/subscriber", method = RequestMethod.PUT)
	public Response addSubscriber(@RequestBody SubscriberRequest subscriberRequest) {
		LOGGER.info(subscriberRequest.toString());
		FMSHandler fmsHandler = FMSHandler.getInstance();
		Response upsertResponse = fmsHandler.upsertSubscriber(subscriberRequest, true);
		return upsertResponse;
	}

	/**
	 * addSubscriber, request for deleting a subscriber will come here
	 */
	// Use case 5 for blocking subscriber
	@RequestMapping(value = "/subscriber", method = RequestMethod.DELETE)
	public Response deleteSubscriber(@RequestBody SubscriberRequest subscriberRequest) {
		FMSHandler fmsHandler = FMSHandler.getInstance();
		Response upsertResponse = fmsHandler.upsertSubscriber(subscriberRequest, false);
		return upsertResponse;
	}

	/**
	 * addSubscriber, request for publishing message to user's subscriber will come here
	 */
	// Use case 6 for getting list of subscriber who are update with message
	@RequestMapping(value = "/subscribers", method = RequestMethod.POST)
	public PublishResponse publishMessage(@RequestBody MessageUpdateRequset msgUpdateRequest) {
		FMSHandler fmsHandler = FMSHandler.getInstance();
		return fmsHandler.publish(msgUpdateRequest);
	}
}
