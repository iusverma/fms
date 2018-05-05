package com.spg.model;

import com.spg.enums.ConnectionType;
import com.spg.enums.Status;

public class Connection {
	private String primary;
	private String secondary;
	private ConnectionType connectionType;
	private Status status;
	public Connection(String primary, String secondary,
			ConnectionType connectionType, Status status) {
		this.primary = primary;
		this.secondary = secondary;
		this.connectionType = connectionType;
		this.status = status;
	}
	public String getPrimary() {
		return primary;
	}
	public void setPrimary(String primary) {
		this.primary = primary;
	}
	public String getSecondary() {
		return secondary;
	}
	public void setSecondary(String secondary) {
		this.secondary = secondary;
	}
	public ConnectionType getConnectionType() {
		return connectionType;
	}
	public void setConnectionType(ConnectionType connectionType) {
		this.connectionType = connectionType;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
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
	@Override
	public int hashCode() {
		return ((this.primary.length()+this.secondary.length())/10);
	}
}