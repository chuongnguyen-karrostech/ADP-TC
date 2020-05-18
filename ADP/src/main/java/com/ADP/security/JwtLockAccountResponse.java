package com.ADP.security;

import java.io.Serializable;

public class JwtLockAccountResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String status;
	private final String message;
	
	public JwtLockAccountResponse(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}

}
