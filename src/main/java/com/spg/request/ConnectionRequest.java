package com.spg.request;

public class ConnectionRequest {
	/*
	private String[] friends;
	public ConnectionRequest(String[] friends) {
		this.friends = friends; 
	}
	public String[] getFriends() {
		return friends;
	}

	public void setFriends(String[] friends) {
		this.friends = friends;
	}
	*/
	
	private String user1;
	private String user2;
	public ConnectionRequest(String user1, String user2) {
		this.user1 = user1;
		this.user2 = user2;
	}
	public String getUser1() {
		return user1;
	}
	public void setUser1(String user1) {
		this.user1 = user1;
	}
	public String getUser2() {
		return user2;
	}
	public void setUser2(String user2) {
		this.user2 = user2;
	}
}
