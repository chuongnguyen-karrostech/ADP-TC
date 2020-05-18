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

public class Paging {
	public int totalElements;
    public int totalPages;
    public int currentPage;
    public int elementsInPage;
    public String sort;
	
	public String getsort() {
		return sort;
	}	
	public void setsort(String sort) {
		this.sort = sort;
	}
	public int gettotalElements() {
		return totalElements;
	}	
	public void settotalElements(int totalElements) {
		this.totalElements = totalElements;
	}
	
	public int gettotalPages() {
		return totalPages;
	}	
	public void settotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getcurrentPage() {
		return currentPage;
	}	
	public void setcurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getelementsInPage() {
		return elementsInPage;
	}	
	public void setelementsInPage(int elementsInPage) {
		this.elementsInPage = elementsInPage;
	}
}