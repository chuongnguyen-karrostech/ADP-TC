package com.ADP.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ETDBService {
	public Object[] loadRouteView() {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"etdb/loadrouteview";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	public Object[] loadRouteView(String run) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"etdb/loadrouteview/"+run;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	
	public Object[] loadStopView() {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"etdb/loadstopview";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
	
	public Object[] loadStopView(String run) {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"etdb/loadstopview/"+run;
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
	}
}
