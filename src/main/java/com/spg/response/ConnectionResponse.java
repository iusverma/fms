package com.spg.response;

import java.util.List;

/**
 * ConnectionResponse
 * @author Ayush Verma
 * Response for common connection
 */
public class ConnectionResponse extends Response {
	/** List of friends/connections */
	private List<String> friends;

	/** count of common friends/connections */
	private int count;

	/**
	 * Constructor with default properties
	 * @param success
	 * @param friends
	 */
	public ConnectionResponse(boolean success, List<String> friends) {
		super(success);
		this.friends = friends;
		this.count = friends.size();
	}

	/** Getter for friends list */
	public List<String> getFriends() {
		return friends;
	}

	/** Setter for friends list */
	public void setFriends(List<String> friends) {
		this.friends = friends;
	}

	/** Getter for friends count */
	public int getCount() {
		return count;
	}

	/** Setter for friends count */
	public void setCount(int count) {
		this.count = count;
	}
}
