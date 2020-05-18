package com.APIMM.util;

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

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
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

import com.APIMM.mam.model.MamEvents;
import com.APIMM.mam.repository.MamEventsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWT {
	public static String tokenType = "";
	public static int expiresIn = -1;
	public static String accessToken = "";
	public static Date createdDatetime = null;
	public static Date expiredDatetime = null;
	
	
	public static boolean gettoken(){		
		String transactionUrl = com.APIMM.util.CommonFunction.EDP_Url+com.APIMM.util.CommonFunction.EDPTokenEndpoint; //"v1/signin";		  
		RestTemplate restTemplate = new RestTemplate();	
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("ApiKey", com.APIMM.util.CommonFunction.ClientId);
		try {
		SignIn sign = new SignIn();
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(sign);
		HttpEntity<String> entity = new HttpEntity<String> (jsonInString,headers);
		JWT aa= restTemplate.postForObject(transactionUrl,entity, JWT.class);
		tokenType =aa.tokenType;
		expiresIn = aa.expiresIn;
		accessToken = aa.accessToken;
		createdDatetime = new Date();
		expiredDatetime = new Date(createdDatetime.getTime()+(expiresIn-600)*1000);
		buildVeh();
		}catch (Exception e){
			String error = e.getMessage();
			return false;
		}
		return true;
	}
	private static void buildVeh(){
		com.APIMM.util.CommonFunction.hVeh = new Hashtable();
		int page =0;
		boolean stop = false;
		while (!stop){			
			//String transactionUrl = com.APIMM.util.CommonFunction.EDP_Url+"v2/tenants/"+com.APIMM.util.CommonFunction.tenantId+"/vehicles?size=25&page="+page;
			String transactionUrl = com.APIMM.util.CommonFunction.EDP_Url+com.APIMM.util.CommonFunction.EDPGetVehicles+com.APIMM.util.CommonFunction.tenantId+"/vehicles?size=25&page="+page;
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("ApiKey", com.APIMM.util.CommonFunction.ClientId);
			headers.set("Authorization", tokenType + " " + accessToken);			
			try {				
				HttpEntity<String> entity = new HttpEntity<String> (null,headers);
				//VehicleList vl= restTemplate.getForObject(transactionUrl, VehicleList.class, entity);
						//restTemplate.exchange(transactionUrl, HttpMethod.GET, entity, VehicleList.class);
				//restTemplate.postForObject(transactionUrl,entity, VehicleList.class);
				ResponseEntity<VehicleList> response = restTemplate.exchange(transactionUrl, HttpMethod.GET, entity, VehicleList.class);
				VehicleList vl = response.getBody();
				if (vl==null){
					stop=true;
				}else{
				
					Paging pg = vl.getpaging();
					Vehicles[] vehs = vl.getvehicles();
					for (int i =0; i<vehs.length;i++){
						Vehicles v = vehs[i];
						String bus = v.busNumber;
						if (!com.APIMM.util.CommonFunction.hVeh.containsKey(bus)){
							com.APIMM.util.CommonFunction.hVeh.put(bus, v);
						}else{
							//com.APIMM.util.CommonFunction.hVeh.replace(bus, vehs);
						}
					}
					if (pg.totalPages > pg.currentPage + 1)
	                {
	                    page = page + 1;
	                }
	                else
	                {
	                    stop = true;                                
	                }
				}
			}catch (Exception e){
				
			}
		}		
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
