package com.APIMM.util;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SendCloudThread  extends Thread {
	  private int threadId;
	  List<RouteAssignment> rAsAll;
	  List<RouteAssignment> rAsErr;
	  public SendCloudThread(int threadId, List<RouteAssignment> rAsAll, List<RouteAssignment> rAsErr) {
	      this.threadId = threadId;
	      this.rAsAll = rAsAll;
	      this.rAsErr = rAsErr;
	  }
	 
	  @Override
	  public void run() {
		Log logger = LogFactory.getLog(this.getClass());
		logger.info("Start send cloud block "+ this.threadId);
		try {
		String transactionUrl = com.APIMM.util.CommonFunction.EDP_Url + com.APIMM.util.CommonFunction.EDPRouteAssignmentEndpoint;// "v1/routeAssignments";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("ApiKey", com.APIMM.util.CommonFunction.ClientId);
		headers.set("Authorization", com.APIMM.util.JWT.tokenType + " " + com.APIMM.util.JWT.accessToken);
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(rAsAll);
//		logger.info(jsonInString);
		HttpEntity<String> entity = new HttpEntity<String>(jsonInString, headers);
		ResponseEntity<Object> aa = restTemplate.exchange(transactionUrl, HttpMethod.PUT, entity, Object.class);
//			if (aa.getStatusCodeValue()==200){
		if (aa.getStatusCodeValue()<300 && aa.getStatusCodeValue()>=200){
			//success, do something....
			logger.info("send cloud block "+ this.threadId+ " is successfully. total route: "+  this.rAsAll.size() );
		}else{
			//fail
			rAsErr.addAll(rAsAll);
			logger.info("send cloud block "+ this.threadId+ " is fail. Error code = "+aa.getStatusCodeValue());
		}
		}catch (Exception e){
			rAsErr.addAll(rAsAll);
			logger.info("Exception: send cloud block "+ this.threadId+ " is fail. Error: "+e.getMessage());
		}
		logger.info("End send cloud block "+ this.threadId);
	  }	
	}
