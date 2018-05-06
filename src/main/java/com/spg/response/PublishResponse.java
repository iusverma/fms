package com.spg.response;

import java.util.List;

/**
 * PublishResponse
 * @author Ayush Verma
 * Response for message published to subscribers
 */
public class PublishResponse extends Response {
	/** List of receipents */
	private List<String> receipents;

	/**
	 * Constructor with default properties
	 * @param success
	 * @param receipents
	 */
	public PublishResponse(boolean success, List<String> receipents) {
		super(success);
		this.receipents = receipents;
	}

	/** Getter for recipients list */
	public List<String> getReceipents() {
		return receipents;
	}

	/** Setter for recipients list */
	public void setReceipents(List<String> receipents) {
		this.receipents = receipents;
	}

	/** Overriding the toString method */
	public String toString() {
		return "{success: "+super.isSuccess()+", recipients; "+receipents.toString()+"}";
	}
}
