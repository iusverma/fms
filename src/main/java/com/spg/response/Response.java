package com.spg.response;

/**
 * Response
 * @author Ayush Verma
 * Basic response class
 */
public class Response {
	/** status for operation true/false */
	private boolean success;

	/**
	 * Constructor with default properties
	 * @param success
	 */
	public Response(boolean success) {
		this.success = success;
	}

	/** Getter for success status */
	public boolean isSuccess() {
		return success;
	}

	/** Getter for success status */
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
