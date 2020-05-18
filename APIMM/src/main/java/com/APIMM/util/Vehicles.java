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

public class Vehicles {
	public String id ;
    public String busNumber ;
    public String vin ;
    public String tenantuuId ;
    public Date createdDatetime ;
    public String description ;
    public Date licenseexpire ;
    public String licensenumber ;
    public String licensestate ;
    public String manufacturer ;
    public String model ;
    public String name ;        
    public String statenumber ;
    public Date statenumberExpire ;        
    public String status ;
    public String code ;
    public String error ;
    public Date updatedDatetime ;
    public String year ;
    
	public String getbusNumber() {
		return busNumber;
	}	
	public void setbusNumber(String busNumber) {
		this.busNumber = busNumber;
	}	
	public String gettenantuuId() {
		return tenantuuId;
	}	
	public void settenantuuId(String tenantuuId) {
		this.tenantuuId = tenantuuId;
	}
	public String getid() {
		return id;
	}	
	public void setid(String id) {
		this.id = id;
	}
	public String getvin() {
		return vin;
	}	
	public void setvin(String vin) {
		this.vin = vin;
	}
	
	public Date getcreatedDatetime() {
		return createdDatetime;
	}	
	public void setcreatedDatetime(Date createdDatetime) {
		this.createdDatetime = createdDatetime;
	}	
	public String getdescription() {
		return description;
	}	
	public void setdescription(String description) {
		this.description = description;
	}
	public Date getlicenseexpire() {
		return licenseexpire;
	}	
	public void setlicenseexpire(Date licenseexpire) {
		this.licenseexpire = licenseexpire;
	}	
	public String getlicensenumber() {
		return licensenumber;
	}	
	public void setlicensenumber(String licensenumber) {
		this.licensenumber = licensenumber;
	}
	public String getlicensestate() {
		return licensestate;
	}	
	public void setlicensestate(String licensestate) {
		this.licensestate = licensestate;
	}
	
	public String getmanufacturer() {
		return manufacturer;
	}	
	public void setmanufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getmodel() {
		return model;
	}	
	public void setmodel(String model) {
		this.model = model;
	}
	public String getname() {
		return name;
	}	
	public void setname(String name) {
		this.name = name;
	}
	public String getstatenumber() {
		return statenumber;
	}	
	public void setstatenumber(String statenumber) {
		this.statenumber = statenumber;
	}
	public Date getstatenumberExpire() {
		return statenumberExpire;
	}	
	public void setstatenumberExpire(Date statenumberExpire) {
		this.statenumberExpire = statenumberExpire;
	}
	public String getstatus() {
		return status;
	}	
	public void setstatus(String status) {
		this.status = status;
	}
	
	public String getcode() {
		return code;
	}	
	public void setcode(String code) {
		this.code = code;
	}
	public String geterror() {
		return error;
	}	
	public void seterror(String error) {
		this.error = error;
	}
	public Date getupdatedDatetime() {
		return updatedDatetime;
	}	
	public void setupdatedDatetime(Date updatedDatetime) {
		this.updatedDatetime = updatedDatetime;
	}
	public String getyear() {
		return year;
	}	
	public void setyear(String year) {
		this.year = year;
	}
}