package com.spg.response;

public class ErrorResponse extends Response{
	private String message;

	public ErrorResponse(boolean success, String message) {
		super(success);
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
