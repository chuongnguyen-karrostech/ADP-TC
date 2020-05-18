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

public class RouteAssignment {
	public String busNumber ;
	public String deviceId ;
	public String driveruuId ;
	public Date effectiveEndDate;
	public Date effectiveStartDate;
	public String legacyDriverId;
	public String routeCode;
	public String routeuuId;
	public String tenantuuId;
	public String vehicleuuId;
	public String vin;
	
	public String getbusNumber() {
		return busNumber;
	}	
	public void setbusNumber(String busNumber) {
		this.busNumber = busNumber;
	}
	public String getdeviceId() {
		return deviceId;
	}	
	public void setdeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getdriveruuId() {
		return driveruuId;
	}	
	public void setdriveruuId(String driveruuId) {
		this.driveruuId = driveruuId;
	}
	public Date geteffectiveEndDate() {
		return effectiveEndDate;
	}	
	public void seteffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}
	public Date geteffectiveStartDate() {
		return effectiveStartDate;
	}	
	public void seteffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}
	
	public String getlegacyDriverId() {
		return legacyDriverId;
	}	
	public void setlegacyDriverId(String legacyDriverId) {
		this.legacyDriverId = legacyDriverId;
	}
	public String getrouteCode() {
		return routeCode;
	}	
	public void setrouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	public String getrouteuuId() {
		return routeuuId;
	}	
	public void setrouteuuId(String routeuuId) {
		this.routeuuId = routeuuId;
	}
	public String gettenantuuId() {
		return tenantuuId;
	}	
	public void settenantuuId(String tenantuuId) {
		this.tenantuuId = tenantuuId;
	}
	public String getvehicleuuId() {
		return vehicleuuId;
	}	
	public void setvehicleuuId(String vehicleuuId) {
		this.vehicleuuId = vehicleuuId;
	}
	public String getvin() {
		return vin;
	}	
	public void setvin(String vin) {
		this.vin = vin;
	}
}