package com.spg.request;

public class SubscriberRequest {
	private String requestor;
	private String target;
	public SubscriberRequest(String requestor, String target) {
		super();
		this.requestor = requestor;
		this.target = target;
	}
	public String getRequestor() {
		return requestor;
	}
	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String toString() {
		return "{"+requestor+", "+target+"}";
	}
}
