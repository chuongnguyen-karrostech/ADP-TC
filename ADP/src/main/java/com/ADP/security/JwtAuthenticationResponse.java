package com.ADP.security;

import java.io.Serializable;

/**
 * Created by stephan on 20.03.16.
 */
public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;
    private final String lastName;
    private final String firstName;
    private final String jobCode;
   
	public JwtAuthenticationResponse(String token, String lastName, String firstName, String jobCode) {
		super();
		this.token = token;
		this.lastName = lastName;
		this.firstName = firstName;
		this.jobCode = jobCode;
	}
    
	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getJobCode() {
		return jobCode;
	}

	public String getToken() {
		return this.token;
	}   
}
