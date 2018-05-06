package com.spg.request;

/**
 * ConnectionRequest
 * @author Ayush Verma
 * Request for connection two users
 */
public class ConnectionRequest {
	/** list of input users in context */
	private String[] friends;

	/** Getter for friends */
	public String[] getFriends() {
		return friends;
	}

	/** Setter for friends */
	public void setFriends(String[] friends) {
		this.friends = friends;
	}

	/** Overriding default toString methos */
	@Override
	public String toString() {
		return "{"+friends[0]+", "+friends[1]+"}";
	}
}
