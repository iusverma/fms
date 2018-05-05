package com.spg.response;

import java.util.List;

import com.spg.model.Connection;

public class PublishResponse extends Response {
	private List<String> receipents;
	public PublishResponse(boolean success, List<String> receipents) {
		super(success);
		this.receipents = receipents;
	}
	public List<String> getReceipents() {
		return receipents;
	}
	public void setReceipents(List<String> receipents) {
		this.receipents = receipents;
	}

}
