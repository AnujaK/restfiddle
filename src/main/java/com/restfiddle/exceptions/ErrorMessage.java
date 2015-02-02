package com.restfiddle.exceptions;

/**
 * Created by santoshm1 on 06/06/14.
 */
public class ErrorMessage {

    private String reason;

    public ErrorMessage(String reason) {
	this.reason = reason;
    }

    public ErrorMessage(ApiException ex) {
	this.reason = ex.getMessage();
    }

    public String getReason() {
	return reason;
    }

    public void setReason(String reason) {
	this.reason = reason;
    }

}
