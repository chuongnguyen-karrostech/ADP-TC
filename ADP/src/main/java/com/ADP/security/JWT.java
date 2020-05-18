package com.ADP.security;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


import com.ADP.util.DateFunction;
import com.ADP.util.Utilities;
import com.fasterxml.jackson.databind.JsonNode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JWT {
	public static String tokenType = "";
	public static int expiresIn = -1;
	public static String accessToken = "";
	public static Date createdDatetime = null;
	public static Date expiredDatetime = null;

	public static boolean gettoken(String athena_signin_url, String athena_email, String athena_password, String athena_scope) {
		long timekey = DateFunction.TimeKey();
		Map map = new HashMap();
		map.put("email", athena_email);
		map.put("password", athena_password);
		map.put("scope", athena_scope);

		HttpEntity<Map> entity = new HttpEntity<>(map);
		RestTemplate restTemplate = new RestTemplate();
		Log logger = LogFactory.getLog(JWT.class);

		try {
			String loginJson = restTemplate.postForObject(athena_signin_url, entity, String.class);

			ObjectMapper objMapper = new ObjectMapper();
			JsonNode jsonNode = objMapper.readTree(loginJson);
			String accessToken = jsonNode.get("accessToken").asText(null);
			String tokenType = jsonNode.get("tokenType").asText(null);
			Integer expiresIn = Integer.parseInt(jsonNode.get("expiresIn").asText(null));
			if (accessToken != null && tokenType != null && expiresIn != null) {
				JWT.tokenType = tokenType;
				JWT.expiresIn = expiresIn;
				JWT.accessToken = tokenType + " " + accessToken;
				JWT.createdDatetime = new Date();
				JWT.expiredDatetime = new Date(createdDatetime.getTime() + (expiresIn - 600) * 1000);
				return true;
			} else {
				logger.info(Utilities.LogReceive(timekey, "gettoken", "gettoken", "Can not get token from: " + athena_signin_url + " Response: " + loginJson));
			}
		} catch (Exception eek) {
			logger.error(Utilities.LogError(timekey, "gettoken", "gettoken",
					" signin url: " + athena_signin_url +
							" email: " + athena_email +
							" password: " + athena_password +
							" scope: " + athena_scope, eek.getMessage()));
			System.out.println("** Exception: " + eek.getMessage());
		}
		return false;
	}

	public String gettokenType() {
		return tokenType;
	}
	public void settokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public String getaccessToken() {
		return accessToken;
	}
	public void setaccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public int getexpiresIn() {
		return expiresIn;
	}
	public void setexpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	public Date getcreatedDatetime() {
		return createdDatetime;
	}
	public void setcreatedDatetime(Date createdDatetime) {
		this.createdDatetime = createdDatetime;
	}
	public Date getexpiredDatetime() {
		return expiredDatetime;
	}
	public void setexpiredDatetime(Date expiredDatetime) {
		this.expiredDatetime = expiredDatetime;
	}
}
