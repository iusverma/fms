package com.spg.app;

import java.util.Iterator;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.spg.enums.ConnectionType;
import com.spg.enums.Status;
import com.spg.model.Connection;
import com.spg.request.ConnectionRequest;
import com.spg.request.MessageUpdateRequset;
import com.spg.request.Request;
import com.spg.request.SubscriberRequest;
import com.spg.response.PublishResponse;

public class FMSControllerTest {
	private static final String USER1 = "user1@example.com";
	private static final String USER2 = "user2@example.com";
	private static final String FRIEND1 = "friend1@example.com";
	private static final String FRIEND2 = "friend2@example.com";
	private static final String FRIEND3 = "friend3@example.com";
	private static final String FRIEND4 = "friend4@example.com";
	private static final String SUBSCRIBER1 = "subscriber1@example.com";

	@Test(priority=1)
	public void addFriendTest() {
		FMSController fmsController = new FMSController();
		Assert.assertTrue(fmsController.friends().isEmpty());
		Assert.assertTrue(fmsController.friend(createConnectionRequest(USER1,FRIEND1)).isSuccess());
		Assert.assertTrue(fmsController.friend(createConnectionRequest(USER1,FRIEND2)).isSuccess());
		Assert.assertTrue(fmsController.friend(createConnectionRequest(USER2,FRIEND3)).isSuccess());
		Assert.assertTrue(fmsController.friend(createConnectionRequest(USER2,FRIEND4)).isSuccess());
		Assert.assertEquals(fmsController.friends().size(), 4);
		Iterator <Connection> iter = fmsController.friends().iterator();
		while(iter.hasNext()) {
			Connection conn = iter.next();
			Assert.assertEquals(conn.getConnectionType(), ConnectionType.FRIEND);
			Assert.assertEquals(conn.getStatus(), Status.SUBSCRIBERD);
		}
		
	}

	@Test(priority=2)
	public void commonFriendTest() {
		FMSController fmsController = new FMSController();
		Assert.assertEquals(fmsController.friends().size(), 4);
		Assert.assertTrue(fmsController.friend(createConnectionRequest(USER1,FRIEND3)).isSuccess());
		Assert.assertTrue(fmsController.friend(createConnectionRequest(USER2,FRIEND1)).isSuccess());
		ConnectionRequest commonFriendRequest = new ConnectionRequest(USER1, USER2);
		Assert.assertEquals(fmsController.commonFriends(commonFriendRequest).getCount(),2);
		Assert.assertEquals(fmsController.commonFriends(commonFriendRequest).isSuccess(),true);
	}

	@Test(priority=3)
	public void getFreindsForUser() {
		FMSController fmsController = new FMSController();
		Assert.assertEquals(fmsController.friends().size(), 6);
		Assert.assertEquals(fmsController.friends(new Request(USER1, "")).getCount(),3);
		Assert.assertEquals(fmsController.friends(new Request(USER2, "")).getCount(),3);
	}
	
	@Test(priority=4)
	public void addSubscriberTest() {
		FMSController fmsController = new FMSController();
		Assert.assertEquals(fmsController.friends().size(), 6);
		Assert.assertTrue(fmsController.addSubscriber(createSubscriberRequest(USER1, SUBSCRIBER1)).isSuccess());
		Assert.assertTrue(fmsController.addSubscriber(createSubscriberRequest(USER2, SUBSCRIBER1)).isSuccess());
		Assert.assertEquals(fmsController.friends().size(), 8);
		Iterator <Connection> iter = fmsController.friends().iterator();
		while(iter.hasNext()) {
			Connection conn = iter.next();
			if(conn.getSecondary().equals(SUBSCRIBER1) || conn.getSecondary().equals(SUBSCRIBER1)) {
				Assert.assertEquals(conn.getConnectionType(), ConnectionType.SUBSCRIBER);
				Assert.assertEquals(conn.getStatus(), Status.SUBSCRIBERD);
			}
		}
	}
	
	@Test(priority=5)
	public void deleteSubscriberTest() {
		FMSController fmsController = new FMSController();
		Assert.assertEquals(fmsController.friends().size(), 8);
		Assert.assertTrue(fmsController.deleteSubscriber(createSubscriberRequest(USER2, SUBSCRIBER1)).isSuccess());
		Assert.assertEquals(fmsController.friends().size(), 8);
		Iterator <Connection> iter = fmsController.friends().iterator();
		while(iter.hasNext()) {
			Connection conn = iter.next();
			if(conn.getPrimary().equals(USER2) && conn.getSecondary().equals(SUBSCRIBER1)) {
				Assert.assertEquals(conn.getConnectionType(), ConnectionType.SUBSCRIBER);
				Assert.assertEquals(conn.getStatus(), Status.UNSUBSCRIBED);
			}
		}
		
		Assert.assertTrue(fmsController.deleteSubscriber(createSubscriberRequest(USER2, FRIEND1)).isSuccess());
		Assert.assertEquals(fmsController.friends().size(), 8);
		Iterator <Connection> iter1 = fmsController.friends().iterator();
		while(iter1.hasNext()) {
			Connection conn = iter1.next();
			if(conn.getPrimary().equals(USER2) && conn.getSecondary().equals(FRIEND1)) {
				Assert.assertEquals(conn.getConnectionType(), ConnectionType.FRIEND);
				Assert.assertEquals(conn.getStatus(), Status.UNSUBSCRIBED);
			}
		}
	}
	
	@Test(priority=6)
	public void sendUpdateTest() {
		FMSController fmsController = new FMSController();
		Assert.assertEquals(fmsController.friends().size(), 8);
		MessageUpdateRequset message = new MessageUpdateRequset(USER2, "I am back");
		PublishResponse response = fmsController.publishMessage(message);
		Assert.assertTrue(response.isSuccess());
		Assert.assertEquals(response.getReceipents().size(),2);
		System.out.println(response.toString());
	}

	private ConnectionRequest createConnectionRequest(String primaryEmail, String secondaryEmail) {
		return new ConnectionRequest(primaryEmail, secondaryEmail);
	}
	
	private SubscriberRequest createSubscriberRequest(String requestor, String target) {
		return new SubscriberRequest(requestor, target);
	}
}
