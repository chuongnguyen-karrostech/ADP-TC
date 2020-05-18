package com.APIMM.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.APIMM.mam.model.MamEvents;
import com.APIMM.mam.repository.MamEventsRepository;

public class SignIn {
	public String clientId = com.APIMM.util.CommonFunction.ClientId;
	public String clientSecret = com.APIMM.util.CommonFunction.ClientKey;
	public String grantType = com.APIMM.util.CommonFunction.grantType;
	public String scope = com.APIMM.util.CommonFunction.scope;
	
	public String getclientId() {
		return clientId;
	}	
	public void setclientId(String clientId) {
		this.clientId = clientId;
	}
	public String getclientSecret() {
		return clientSecret;
	}	
	public void setclientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public String getgrantType() {
		return grantType;
	}	
	public void setgrantType(String grantType) {
		this.grantType = grantType;
	}
	public String getscope() {
		return scope;
	}	
	public void setscope(String scope) {
		this.scope = scope;
	}
}