package com.spg.request;

/**
 * MessageUpdateRequest
 * @author Ayush Verma
 * Request for publishing message to subscribers
 */
public class MessageUpdateRequset {
	/** Sender - publisher */
	private String sender;

	/** text - message to be published */
	private String text;

	/**
	 * Constructor with default properties
	 * @param sender
	 * @param text
	 */
	public MessageUpdateRequset(String sender, String text) {
		this.sender = sender;
		this.text = text;
	}

	/** Getter for sender */
	public String getSender() {
		return sender;
	}

	/** Setter for sender */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/** Getter for text */
	public String getText() {
		return text;
	}

	/** Setter for text */
	public void setText(String text) {
		this.text = text;
	}
}
