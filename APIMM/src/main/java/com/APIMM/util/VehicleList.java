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

public class VehicleList {
	public String serviceCode;
	public String serviceMessage;
	public Paging paging ;
	public Vehicles[] vehicles ;
	
	public String getserviceCode() {
		return serviceCode;
	}	
	public void setserviceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getserviceMessage() {
		return serviceMessage;
	}	
	public void setserviceMessage(String serviceMessage) {
		this.serviceMessage = serviceMessage;
	}
	public Vehicles[] getvehicles() {
		return vehicles;
	}	
	public void setvehicles(Vehicles[] vehicles) {
		this.vehicles = vehicles;
	}
	public Paging getpaging() {
		return paging;
	}	
	public void setpaging(Paging paging) {
		this.paging = paging;
	}
}