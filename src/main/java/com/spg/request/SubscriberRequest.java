package com.spg.request;

/**
 * SubscriberRequest
 * @author Ayush Verma
 * Request for subscriber two users
 */
public class SubscriberRequest {
	/** requestor - this is publisher */
	private String requestor;

	/** target - this is this is subscriber */
	private String target;

	/**
	 * Constructor with default properties
	 * @param requestor
	 * @param target
	 */
	public SubscriberRequest(String requestor, String target) {
		super();
		this.requestor = requestor;
		this.target = target;
	}

	/** Getter for requestor */
	public String getRequestor() {
		return requestor;
	}

	/** Setter for requestor */
	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}

	/** Getter for target */
	public String getTarget() {
		return target;
	}

	/** Setter for target */
	public void setTarget(String target) {
		this.target = target;
	}

	/** overriding toString implementation */
	public String toString() {
		return "{"+requestor+", "+target+"}";
	}
}
