package com.ADP.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ADP.etdb.model.MamSyncInfo;

@Service
public class OHAService {	
	
	public Object[] getAllRoute() {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"dplive/route/all";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	public Object[] getAllRun() {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"dplive/run/all";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	public Object[] getRunbyDate(String daterequest) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"dplive/run/"+daterequest;		
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	public Object[] getAllDate() {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"dplive/date/all";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	public Object[] getAllDevice() {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"dplive/device/all";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	public Object[] getBusbyDate(String daterequest) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"dplive/bus/"+daterequest;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	public Object[] getAllBus() {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"dplive/bus/all";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	public Object[] getRoute(String routenumber) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"dplive/route/"+routenumber;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	public Object[] getRoutebyDevDate(Integer devid, String date) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"dplive/route/"+devid+"/"+date;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	
	public Object[] getRundir(int devid,String runstarttime, String runendtime) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"dplive/rundir/"+ devid+"/"+runstarttime+"/"+runendtime;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	
	public Object[] getRundirbybus(String busnumber,String runstarttime, String runendtime) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"dplive/rundirbybus/"+ busnumber+"/"+runstarttime+"/"+runendtime;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	
	public Object[] getRundirbydevicebus(int devid,String busnumber,String runstarttime, String runendtime) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"dplive/rundirbydevicebus/"+ devid+"/"+ busnumber+"/"+runstarttime+"/"+runendtime;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	public Object[] getRundirbyrun(String runid, int runroute,int devid) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"dplive/rundirbyrun/"+ runid+"/"+runroute+"/"+devid;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	public Object getRuninfo(int runroute) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"dplive/runinfo/"+runroute;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object.class);
	}
	public Object deleteRoute(int routeid) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"dplive/route/delete/"+ routeid;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object.class);
	}
	public Object deleteRun(int runroute) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"dplive/run/delete/"+ runroute;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object.class);
	}
	public Object deleteStop(int stoprunid) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"dplive/stop/delete/"+ stoprunid;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object.class);
	}
	public Object updateRouteDesc(int routeid,String desc) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"dplive/route/updatedesc/"+ routeid+"/"+desc;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object.class);
	}
	public Object updateRunDesc(int runroute,String desc) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"dplive/run/updatedesc/"+ runroute+"/"+desc;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object.class);
	}
	public Object editRoute(Object route) {		
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"dplive/route/edit";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
		list.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(list);	
		return restTemplate.postForObject(builder.toUriString(),route, Object.class);		
	}
	public Object editRun(Object run) {		
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"dplive/run/edit";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
		list.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(list);	
		return restTemplate.postForObject(builder.toUriString(),run, Object.class);		
	}
	public Object addRoute(Object route) {		
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"dplive/routeoha/add";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
		list.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(list);	
		return restTemplate.postForObject(builder.toUriString(),route, Object.class);		
	}
	public Object SaveRunDirs(int routeid,String runid, Object[] rundirs) {		
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"dplive/rundirs/save/"+routeid+"/"+runid;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
		list.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(list);	
		return restTemplate.postForObject(builder.toUriString(),rundirs, Object.class);		
	}
}
