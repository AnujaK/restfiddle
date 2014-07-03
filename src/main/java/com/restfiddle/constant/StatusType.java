package com.restfiddle.constant;

/**
 * @author abidk
 * 
 */
public enum StatusType {

    ACTIVE("Active"), DELETE("Delete"), NEW("New");

    private StatusType(String status) {
	this.status = status;
    }

    private String status;

    public String getStatus() {
	return status;
    }

    public static StatusType getByStatus(String string) {
	for (StatusType status : StatusType.values()) {
	    if (status.getStatus().equals(string)) {
		return status;
	    }
	}
	return null;
    }
}
