package com.ADP.security;

import java.io.Serializable;

public class JwtAuthenticationResponseError implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final long timestamp;
	private final int status;
	private final String exception;
	private final String message;
	private final String path;
	
	public JwtAuthenticationResponseError(long timestamp, int status, String exception, String message, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.exception = exception;
		this.message = message;
		this.path = path;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public int getStatus() {
		return status;
	}
	public String getException() {
		return exception;
	}
	public String getMessage() {
		return message;
	}
	public String getPath() {
		return path;
	}
	

	
	
	/*"timestamp": 1508754261356,
    "status": 401,
    "error": "Unauthorized",
    "exception": "org.springframework.security.authentication.BadCredentialsException",
    "message": "Unauthorized",
    "path": "/api/auth"*/
}
