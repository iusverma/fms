package com.spg.model;

import com.spg.enums.ConnectionType;
import com.spg.enums.Status;

/**
 * Connection class
 * @author Ayush Verma
 * Connection class for the FMS application
 */
public class Connection {
	/** primary email address */
	private String primary;

	/** secondary email address */
	private String secondary;

	/** connection between uses with primary and secondary  */
	private ConnectionType connectionType;

	/** Status of connection */
	private Status status;

	/**
	 * Constructor with properties
	 */
	public Connection(String primary, String secondary,
			ConnectionType connectionType, Status status) {
		this.primary = primary;
		this.secondary = secondary;
		this.connectionType = connectionType;
		this.status = status;
	}

	/**
	 * Getter for primary
	 */
	public String getPrimary() {
		return primary;
	}

	/**
	 * Setter for primary
	 */
	public void setPrimary(String primary) {
		this.primary = primary;
	}

	/**
	 * Getter for secondary
	 */
	public String getSecondary() {
		return secondary;
	}

	/**
	 * Setter for secondary
	 */
	public void setSecondary(String secondary) {
		this.secondary = secondary;
	}

	/**
	 * Getter for connection type
	 */
	public ConnectionType getConnectionType() {
		return connectionType;
	}

	/**
	 * Setter for connection type
	 */
	public void setConnectionType(ConnectionType connectionType) {
		this.connectionType = connectionType;
	}

	/**
	 * Getter for status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Setter for status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * equals
	 * overriding the equals method
	 */
	@Override
	public boolean equals(Object object) {
		if(object instanceof Connection) {
			Connection connection = (Connection)object;
			return (this.primary == connection.getPrimary() &&
					this.secondary == connection.getSecondary() );
		}else {
			return false;
		}
	}

	/**
	 * hashCode,
	 * overriding the hashCode method
	 */
	@Override
	public int hashCode() {
		return ((this.primary.length()+this.secondary.length())/10);
	}
}