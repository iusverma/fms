package com.spg.response;

/**
 * ErroResponse
 * @author Ayush Verma
 * Error response when flow has some error
 */
public class ErrorResponse extends Response{
	/** Error message */
	private String message;

	/**
	 * Constructor with default properties
	 * @param success
	 * @param message
	 */
	public ErrorResponse(boolean success, String message) {
		super(success);
		this.setMessage(message);
	}

	/** Getter for error message */
	public String getMessage() {
		return message;
	}

	/** Setter for error message */
	public void setMessage(String message) {
		this.message = message;
	}

}
