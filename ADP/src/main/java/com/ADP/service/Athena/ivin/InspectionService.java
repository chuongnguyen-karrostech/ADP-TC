package com.ADP.service.Athena.ivin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ADP.service.Athena.AthenaService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class InspectionService extends AthenaService {

   public Object getInspectionById(Integer id) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ivinUrl + "/inspection/" + id);
		return getRestTemplate(builder.toUriString());
	}
   
   public Object getLastInspection(Object filter) {
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ivinUrl + "/inspection/last");
		
		List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
		list.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(list);	
		return postRestTemplate(builder.toUriString(), filter);
	}
   
   public Object getDriverName() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ivinUrl + "/inspection/driver");
		RestTemplate restTemplate = new RestTemplate();
		return getRestTemplate(builder.toUriString());
	}
   	
   public Object oversightInspection(Object filter) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ivinUrl + "/inspection/filter");
		List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
		list.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(list);
		return postRestTemplate(builder.toUriString(), filter);
	}
   
   public Object saveInspection(Object inspection) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ivinUrl + "/inspection");
		List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
		list.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(list);	
		return postRestTemplate(builder.toUriString(), inspection);
	}
   
   public Object getReportByInspectionId(Integer id) {
	   List<HttpMessageConverter<?>> converters = new ArrayList<>();
	   ResourceHttpMessageConverter resourceHttpMessageConverter = new ResourceHttpMessageConverter();
	   MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter  = new MappingJackson2HttpMessageConverter();
	   converters.add(resourceHttpMessageConverter);
	   converters.add(mappingJackson2HttpMessageConverter);

	   UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ivinUrl + "/inspection/report/" + id);

	   RestTemplate rest = new RestTemplate();
	   rest.setMessageConverters(converters);

	   HttpHeaders headers = createHttpHeaders();
	   HttpEntity<Object> entity = new HttpEntity<>(headers);

	   return rest.exchange(builder.toUriString(), HttpMethod.GET, entity, Resource.class).getBody();
	}
}
