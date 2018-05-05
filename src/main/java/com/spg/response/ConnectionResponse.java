package com.spg.response;

import java.util.List;

import com.spg.model.Connection;

public class ConnectionResponse extends Response {
	private List<String> friends;
	private int count;
	public ConnectionResponse(boolean success, List<String> friends) {
		super(success);
		this.friends = friends;
		this.count = friends.size();
	}
	public List<String> getFriends() {
		return friends;
	}
	public void setFriends(List<String> friends) {
		this.friends = friends;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
