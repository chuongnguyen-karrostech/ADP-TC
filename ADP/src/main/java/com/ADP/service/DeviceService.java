package com.ADP.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ADP.etdb.model.MamSyncInfo;
import com.ADP.util.SearchObject;
import com.APIMM.mam.model.NoneMamDeviceEvent;

@Service
public class DeviceService {	
	
	public Object[] getDeviceMode(String devmac) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mdm/devicemode/" + devmac;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	public Object[] getDeviceMode(String devmac,String devserial) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mdm/devicemode/" + devmac+"/"+devserial;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	public Object[] getAppInfo(int appid) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/application/" + appid;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	public Object[] getDeviceModule(int appid,int devid) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/devmodule/"+appid+"/"+devid;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	public Object[] getLastRoute(int devid) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/lastroute/"+devid;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	public Object[] getRecentRoute(int devid) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/recentroute/"+devid;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	public Object[] getRecentRouteByBus(int devid, String busid) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/recentroutebybus/"+devid+"/"+busid;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	
	public Object updateDate(Integer appid, Integer devid, String date_request) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/download/updatedate/"+appid+"/"+devid+"/"+date_request;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object.class);
		
	}
	public Object[] getInfoSync(String date_request) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/download/info/"+date_request;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
		
	}
	public Object addRecentRoute(Object route) {		
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/recentroute/add";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
		list.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(list);
		//return restTemplate.getForObject(builder.toUriString(), Object[].class);	
		return restTemplate.postForObject(builder.toUriString(),route, Object.class);
		
	}
	public Object addEvent(Object event) {		
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/event/add";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
		list.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(list);
		//return restTemplate.getForObject(builder.toUriString(), Object[].class);	
		return restTemplate.postForObject(builder.toUriString(),event, Object.class);		
	}
	public Object addRunEvent(Object event) {		
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/runevent/add";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
		list.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(list);
		//return restTemplate.getForObject(builder.toUriString(), Object[].class);	
		return restTemplate.postForObject(builder.toUriString(),event, Object.class);		
	}
	public Object addStopEvent(Object event) {		
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/stopevent/add";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
		list.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(list);
		//return restTemplate.getForObject(builder.toUriString(), Object[].class);	
		return restTemplate.postForObject(builder.toUriString(),event, Object.class);		
	}
	public Object addStudentEvent(Object event) {		
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/studentevent/add";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
		list.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(list);
		//return restTemplate.getForObject(builder.toUriString(), Object[].class);	
		return restTemplate.postForObject(builder.toUriString(),event, Object.class);		
	}
	public Object addRawEvent(Object rawevent) {		
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/rawevent/add";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
		list.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(list);
		//return restTemplate.getForObject(builder.toUriString(), Object[].class);	
		return restTemplate.postForObject(builder.toUriString(),rawevent, Object.class);
		
	}
	public Object addSwipeEvent(Object swipeevent) {		
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/studentswipecard/add";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
		list.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(list);
		//return restTemplate.getForObject(builder.toUriString(), Object[].class);	
		return restTemplate.postForObject(builder.toUriString(),swipeevent, Object.class);
		
	}
	public Object addRoute(Object route) {		
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"dplive/route/add";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
		list.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(list);
		//return restTemplate.getForObject(builder.toUriString(), Object[].class);	
		return restTemplate.postForObject(builder.toUriString(),route, Object.class);
		
	}
	public Object[] findSettingbyApp(int appid) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/setting/"+appid;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	public Object[] findSettingbySerialNumber(int appid, String serialNumber) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/web/deviceSettings/sn/"+appid+"/"+ serialNumber;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	public Object[] getfiles() {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/files";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	public Object getmessage(Pageable pageRequest) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/message?" + "page=" + pageRequest.getPageNumber() + "&size="+ pageRequest.getPageSize();
		if(pageRequest.getSort()!=null) {		
			String[] arraySort= pageRequest.getSort().toString().split(",");
			String sortRequest = "";
			for(String sort : arraySort) {
				sortRequest = sortRequest + "&sort=" + sort.replace(": ", ",");
			}
			transactionUrl = transactionUrl + sortRequest;
		}		
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object.class);
	}
	public Object getDeviceEvent(Pageable pageRequest) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/deviceevent?" + "page=" + pageRequest.getPageNumber() + "&size="+ pageRequest.getPageSize();
		if(pageRequest.getSort()!=null) {		
			String[] arraySort= pageRequest.getSort().toString().split(",");
			String sortRequest = "";
			for(String sort : arraySort) {
				sortRequest = sortRequest + "&sort=" + sort.replace(": ", ",");
			}
			transactionUrl = transactionUrl + sortRequest;
		}		
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object.class);
	}
	public Object getStopEvent(Pageable pageRequest) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/stopevent?" + "page=" + pageRequest.getPageNumber() + "&size="+ pageRequest.getPageSize();
		if(pageRequest.getSort()!=null) {		
			String[] arraySort= pageRequest.getSort().toString().split(",");
			String sortRequest = "";
			for(String sort : arraySort) {
				sortRequest = sortRequest + "&sort=" + sort.replace(": ", ",");
			}
			transactionUrl = transactionUrl + sortRequest;
		}		
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object.class);
	}
	public Object getRunEvent(Pageable pageRequest) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/runevent?" + "page=" + pageRequest.getPageNumber() + "&size="+ pageRequest.getPageSize();
		if(pageRequest.getSort()!=null) {		
			String[] arraySort= pageRequest.getSort().toString().split(",");
			String sortRequest = "";
			for(String sort : arraySort) {
				sortRequest = sortRequest + "&sort=" + sort.replace(": ", ",");
			}
			transactionUrl = transactionUrl + sortRequest;
		}		
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object.class);
	}
	public Object getLMUInfo() {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/checkhardware";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object.class);
	}
	public Object[] findSettingbyDevice(int appid,int devid) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/setting/"+appid+"/"+devid;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	
	public Object addMessage(Object message) {		
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/message/add";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
		list.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(list);
		//return restTemplate.getForObject(builder.toUriString(), Object[].class);	
		return restTemplate.postForObject(builder.toUriString(),message, Object.class);		
	}
	public String getApiMMVersion() {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/setting/version";
		UriComponentsBuilder builder = UriComponentsBuilder
			    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), String.class);
	}
	
	public Object searchMessage(List<SearchObject> lstSearch, Pageable pageRequest) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/message/search?" + "page=" + pageRequest.getPageNumber() + "&size="+ pageRequest.getPageSize();
		if(pageRequest.getSort()!=null) {		
			String[] arraySort= pageRequest.getSort().toString().split(",");
			String sortRequest = "";
			for(String sort : arraySort) {
				sortRequest = sortRequest + "&sort=" + sort.replace(": ", ",");
			}
			transactionUrl = transactionUrl + sortRequest;
		}		
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);
		HttpEntity<List<SearchObject>> entityRequest = new HttpEntity<>(lstSearch);
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(builder.toUriString(), entityRequest, Object.class);
	}	
	
	public Object searchDeviceEvent(List<SearchObject> lstSearch, Pageable pageRequest) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/deviceevent/search?" + "page=" + pageRequest.getPageNumber() + "&size="+ pageRequest.getPageSize();
		if(pageRequest.getSort()!=null) {		
			String[] arraySort= pageRequest.getSort().toString().split(",");
			String sortRequest = "";
			for(String sort : arraySort) {
				sortRequest = sortRequest + "&sort=" + sort.replace(": ", ",");
			}
			transactionUrl = transactionUrl + sortRequest;
		}		
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);
		HttpEntity<List<SearchObject>> entityRequest = new HttpEntity<>(lstSearch);
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(builder.toUriString(), entityRequest, Object.class);
	}
	
	public Object searchDeviceStopEvent(List<SearchObject> lstSearch, Pageable pageRequest) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/stopevent/search?" + "page=" + pageRequest.getPageNumber() + "&size="+ pageRequest.getPageSize();
		if(pageRequest.getSort()!=null) {		
			String[] arraySort= pageRequest.getSort().toString().split(",");
			String sortRequest = "";
			for(String sort : arraySort) {
				sortRequest = sortRequest + "&sort=" + sort.replace(": ", ",");
			}
			transactionUrl = transactionUrl + sortRequest;
		}		
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);
		HttpEntity<List<SearchObject>> entityRequest = new HttpEntity<>(lstSearch);
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(builder.toUriString(), entityRequest, Object.class);
	}
	public Object searchDeviceRunEvent(List<SearchObject> lstSearch, Pageable pageRequest) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/runevent/search?" + "page=" + pageRequest.getPageNumber() + "&size="+ pageRequest.getPageSize();
		if(pageRequest.getSort()!=null) {		
			String[] arraySort= pageRequest.getSort().toString().split(",");
			String sortRequest = "";
			for(String sort : arraySort) {
				sortRequest = sortRequest + "&sort=" + sort.replace(": ", ",");
			}
			transactionUrl = transactionUrl + sortRequest;
		}		
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);
		HttpEntity<List<SearchObject>> entityRequest = new HttpEntity<>(lstSearch);
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(builder.toUriString(), entityRequest, Object.class);
	}
	public Object getPunchLog(Pageable pageRequest) { 
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/punch/getall?" + "page=" + pageRequest.getPageNumber() + "&size="+ pageRequest.getPageSize();
		if(pageRequest.getSort()!=null) {		
			String[] arraySort= pageRequest.getSort().toString().split(",");
			String sortRequest = "";
			for(String sort : arraySort) {
				sortRequest = sortRequest + "&sort=" + sort.replace(": ", ",");
			}
			transactionUrl = transactionUrl + sortRequest;
		}		
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object.class);		
	}
	public Object searchPunchLog(List<SearchObject> lstSearch, Pageable pageRequest) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/punch/search?" + "page=" + pageRequest.getPageNumber() + "&size="+ pageRequest.getPageSize();
		if(pageRequest.getSort()!=null) {		
			String[] arraySort= pageRequest.getSort().toString().split(",");
			String sortRequest = "";
			for(String sort : arraySort) {
				sortRequest = sortRequest + "&sort=" + sort.replace(": ", ",");
			}
			transactionUrl = transactionUrl + sortRequest;
		}		
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);
		HttpEntity<List<SearchObject>> entityRequest = new HttpEntity<>(lstSearch);
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(builder.toUriString(), entityRequest, Object.class);
	}
	public Object getLmuDiagnostic(Pageable pageRequest) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/checkhardwareresult?" + "page=" + pageRequest.getPageNumber() + "&size="+ pageRequest.getPageSize();
		if(pageRequest.getSort()!=null) {		
			String[] arraySort= pageRequest.getSort().toString().split(",");
			String sortRequest = "";
			for(String sort : arraySort) {
				sortRequest = sortRequest + "&sort=" + sort.replace(": ", ",");
			}
			transactionUrl = transactionUrl + sortRequest;
		}
		UriComponentsBuilder builder = UriComponentsBuilder
			    .fromUriString(transactionUrl);  
			RestTemplate restTemplate = new RestTemplate();
			return restTemplate.getForObject(builder.toUriString(), Object.class);	
	}
	public Object addDriverTheme(Object route) {		
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/drivertheme";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
		list.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(list);
		//return restTemplate.getForObject(builder.toUriString(), Object[].class);	
		return restTemplate.postForObject(builder.toUriString(),route, Object.class);
		
	}
	public Object findDriverTheme(String driverId) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/drivertheme/"+driverId;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object.class);
	}
	public Object searchLmuDiagnostic(List<SearchObject> lstSearch, Pageable pageRequest) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/checkhardwareresult/search?" + "page=" + pageRequest.getPageNumber() + "&size="+ pageRequest.getPageSize();
		if(pageRequest.getSort()!=null) {		
			String[] arraySort= pageRequest.getSort().toString().split(",");
			String sortRequest = "";
			for(String sort : arraySort) {
				sortRequest = sortRequest + "&sort=" + sort.replace(": ", ",");
			}
			transactionUrl = transactionUrl + sortRequest;
		}		
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);
		HttpEntity<List<SearchObject>> entityRequest = new HttpEntity<>(lstSearch);
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(builder.toUriString(), entityRequest, Object.class);
	}
	public Object[] findThemes() {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/getthemes/";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);

	}
}
