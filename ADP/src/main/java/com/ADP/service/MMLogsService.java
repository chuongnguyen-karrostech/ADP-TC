package com.ADP.service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ADP.etext.model.DriverPayrollMessage;
import com.ADP.service.Athena.edta.EdtaAthenaService;
import com.ADP.service.Athena.edta.TransactionDTO;
import com.ADP.service.Athena.ivin.InspectionService;
import com.ADP.util.SearchObject;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MMLogsService {
	
	@Autowired
    private Environment env;
	
	@Autowired
    EdtaAthenaService edtaAthenaService;
	
	@Autowired
	InspectionService inspectionService;
	
	@Autowired
	DeviceService deviceService;
	
	private final Log logger = LogFactory.getLog(this.getClass());
	
	public Object getPunchOffline(Pageable pageRequest) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/punchoffline?" + "page=" + pageRequest.getPageNumber() + "&size="+ pageRequest.getPageSize();
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
	public Object searchPunchOffline(List<SearchObject> lstSearch, Pageable pageRequest) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/punchoffline/search?" + "page=" + pageRequest.getPageNumber() + "&size="+ pageRequest.getPageSize();
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
	public Object getNoLocation(Pageable pageRequest) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/getnolocation?" + "page=" + pageRequest.getPageNumber() + "&size="+ pageRequest.getPageSize();
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
	public Object searchNoLocation(List<SearchObject> lstSearch, Pageable pageRequest) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/getnolocation/search?" + "page=" + pageRequest.getPageNumber() + "&size="+ pageRequest.getPageSize();
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
	
	public static Object[] getContainer() {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"athena/container";		
		UriComponentsBuilder builder = UriComponentsBuilder
			    .fromUriString(transactionUrl);  
			RestTemplate restTemplate = new RestTemplate();
			return restTemplate.getForObject(builder.toUriString(), Object[].class);	
	}
	public Object[] getContainer(String type) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"athena/container/"+ type;		
		UriComponentsBuilder builder = UriComponentsBuilder
			    .fromUriString(transactionUrl);  
			RestTemplate restTemplate = new RestTemplate();
			return restTemplate.getForObject(builder.toUriString(), Object[].class);	
	}
	
	public Object addContainer(Object container, String type) {		
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"athena/container/"+ type;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();				
		HttpEntity<Object> entityRequest = new HttpEntity<>(container);
		return restTemplate.postForObject(builder.toUriString(), entityRequest, Object.class);		
	}
	public Object deleteContainer(String id) {		
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"athena/deletecontainer/"+id;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();		
		return restTemplate.getForObject(builder.toUriString(), Object.class);		
	}
	
	public void resendAthena() {		
		try {
			resendPunch();
			resendTransaction();
			resendIvin();
		}
		catch(Exception ex) {}
	}
	
	public void resendPunch() {
		Object[] lstPunch = getContainer("punchtransaction");		
		if(lstPunch.length > 0)
		{
			for (Object a : lstPunch)
			{
				ObjectMapper objectMapper = new ObjectMapper();		
				String punchContainer = ((a != null) ? ((LinkedHashMap) a).get("container").toString() : "");
				String idContainer = ((a != null) ? ((LinkedHashMap) a).get("id").toString() : "");
				try {
					DriverPayrollMessage entity = objectMapper.readValue(punchContainer, DriverPayrollMessage.class);
					Object edtaObject =  edtaAthenaService.punchTransaction(entity);
					Object b = deleteContainer(idContainer);
					logger.info("resend Punch to Athena successfull");
				}
				catch (Exception ex)
				{
					logger.info("resend Punch to Athena unsuccessfull", ex);
				}
				
			}			
		}
	}
	public void resendTransaction() {
		Object[] lstPunch = getContainer("transaction");				
		if(lstPunch.length > 0)
		{
			for (Object a : lstPunch)
			{
				ObjectMapper objectMapper = new ObjectMapper();		
				String punchContainer = ((a != null) ? ((LinkedHashMap) a).get("container").toString() : "");
				String idContainer = ((a != null) ? ((LinkedHashMap) a).get("id").toString() : "");
				try {
					TransactionDTO transactionDTO = objectMapper.readValue(punchContainer, TransactionDTO.class);
					Object edtaObject =  edtaAthenaService.addTransaction(transactionDTO);
					Object b = deleteContainer(idContainer);
					logger.info("resend Transaction to Athena successfull");
				}
				catch (Exception ex)
				{
					logger.info("resend Transaction to Athena unsuccessfull", ex);
				}				
			}			
		}
	}
	public void resendIvin() {
		Object[] lstPunch = getContainer("ivin");				
		if(lstPunch.length > 0)
		{
			for (Object a : lstPunch)
			{
				ObjectMapper objectMapper = new ObjectMapper();		
				String punchContainer = ((a != null) ? ((LinkedHashMap) a).get("container").toString() : "");
				String idContainer = ((a != null) ? ((LinkedHashMap) a).get("id").toString() : "");
				try {
					Object inspection = objectMapper.readValue(punchContainer, Object.class);
					Object obj = inspectionService.saveInspection(inspection);
					Object b = deleteContainer(idContainer);
					logger.info("resend Inspection to Athena successfull");
				}
				catch (Exception ex)
				{
					logger.info("resend Inspection to Athena unsuccessfull", ex);
				}			
			}			
		}	
	}
	public void TimerResend(String type) {
		TimerTask task = new TimerTask() {
		      @Override
		      public void run() {
		    	Object[] objs = getContainer(type);
		    	Integer intervalTime = Interval_Time_Athena();
		  		if(objs.length <= 0 || intervalTime <= 0) {
		  			cancel();
		  		}
		  		else
		  		{	// task to run goes here
		  			resendAthena();
		  			System.out.println("Retry send " + type + " to Athena !!!");
		  		}
		      }
		    };		
		Timer timer = new Timer();
		long delay = 10 * 1000;
		long intevalPeriod = Interval_Time_Athena() * 1000; 
		 // schedules the task to be run in an interval 
		timer.scheduleAtFixedRate(task, delay,
		                                intevalPeriod);		 
	}
	public void TimerResend() {
		Integer intervalTime = 60;
		if (env.containsProperty("interval_athena_retry")) {
    		intervalTime = Integer.parseInt(env.getProperty("interval_athena_retry"));
    	}
		TimerTask task = new TimerTask() {
		      @Override
		      public void run() {
		    	try {
		    	  Object[] objs = getContainer();
		    	  if(objs.length <= 0) {
			  			System.out.println("Retry to Athena emtpy !!!");
			  			cancel();
			  		}
			  		else
			  		{	// task to run goes here
			  			resendAthena();
			  			System.out.println("Retry send all to Athena !!!");
			  		}
		    	}
		    	catch(Exception ex) {
		    		System.out.println("Retry to APIMM !!!");
		    		TimerResend();
		    		cancel();
		    	}  			  		
		      }
		    };		
		Timer timer = new Timer();
		long delay = 30 * 1000;
		long intevalPeriod = intervalTime * 1000; 
		 // schedules the task to be run in an interval 
		timer.scheduleAtFixedRate(task, delay,
		                                intevalPeriod);		 
	}
	public static final String INTERVAL_ATHENA_RETRY = "INTERVAL_ATHENA_RETRY";

    public Integer Interval_Time_Athena () {
       Integer ValueSetting =  Integer.parseInt(getMamSettingbyName(INTERVAL_ATHENA_RETRY));
       return ValueSetting;
    }

    private String getMamSettingbyName(String settingName) {
        Object[] lstSetting = deviceService.findSettingbyApp(1);
        Object a = Arrays.stream(lstSetting).filter(item ->
                ((LinkedHashMap) item).get("settingName").toString().toUpperCase().equals(settingName)
        ).findFirst();    
        return (a != null) ? ((LinkedHashMap) ((Optional)a).get()).get("settingValue").toString() : "1";
    }
}
