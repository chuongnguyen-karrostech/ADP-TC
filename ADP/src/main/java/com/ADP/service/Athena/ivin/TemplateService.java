package com.ADP.service.Athena.ivin;

import java.util.ArrayList;
import java.util.List;

import com.ADP.service.Athena.AthenaService;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TemplateService extends AthenaService {

	public Object getTemplateById(Integer id) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ivinUrl + "/template/" + id);
		return getRestTemplate(builder.toUriString());
	}

	public Object getTemplateActiveByStatus(Integer typeid, String status) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ivinUrl + "/template/" + typeid + "/" + status);
		return getRestTemplate(builder.toUriString());
	}

	public Object saveTemplate(Object template) {
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(ivinUrl + "/template");
		
		List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
		list.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(list);	
		
		return postRestTemplate(builder.toUriString(),template);
	}
}
