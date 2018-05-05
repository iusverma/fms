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
import com.spg.response.SuccessResponse;

@RestController
public class FMSController {
	private static final Logger LOGGER = Logger.getLogger(FMSController.class);

	
    // Use case 1 for adding friend
    //public SuccessResponse friend(@RequestBody ConnectionRequest connectionRequest) {
    @RequestMapping("/friend")
    public SuccessResponse friend(@RequestBody ConnectionRequest connectionRequest) {
    	LOGGER.info(connectionRequest.toString());
    	FMSHandler fmsHandler = FMSHandler.getInstance();
    	return fmsHandler.addConncetion(connectionRequest);
    }
    
    // Use case 2 for getting friend list
    @RequestMapping("/friends")
    public List<Connection> friend() {
    	FMSHandler fmsHandler = FMSHandler.getInstance();
    	return fmsHandler.getConnections();
    }
    
    // User case 3 for common friends
    @RequestMapping("/friends/common")
    public List<Connection> commonFriends(@RequestBody ConnectionRequest connectionRequest) {
    	LOGGER.info(connectionRequest.toString());
    	FMSHandler fmsHandler = FMSHandler.getInstance();
    	return fmsHandler.getCommonConnetions(connectionRequest);
    }

    // Use case 4 for subscriber
    @RequestMapping(value="/subscriber", method=RequestMethod.PUT)
    public SuccessResponse addSubscriber(@RequestBody ConnectionRequest connectionRequest) {
    	FMSHandler fmsHandler = FMSHandler.getInstance();
    	fmsHandler.upsertSubscriber(connectionRequest,true);
    	return new SuccessResponse(true);
    }
    
    // Use case 5 for subscriber
    @RequestMapping(value="/subscriber", method=RequestMethod.DELETE)
    public SuccessResponse deleteSubscriber(@RequestBody ConnectionRequest connectionRequest) {
    	FMSHandler fmsHandler = FMSHandler.getInstance();
    	fmsHandler.upsertSubscriber(connectionRequest,false);
    	return new SuccessResponse(true);
    }

    // Use case 6 for getting list of subscriber who are update with message
    @RequestMapping(value="/subscribers",method=RequestMethod.POST)
    public List<Connection> subscribers(@RequestBody MessageUpdateRequset msgUpdateRequest) {
    	FMSHandler fmsHandler = FMSHandler.getInstance();
    	return fmsHandler.publish(msgUpdateRequest);
    }
}
