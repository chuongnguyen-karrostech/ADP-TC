package com.APIMM;

import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.APIMM.service.MamEventService;




@SpringBootApplication(scanBasePackages={
		"com.APIMM"})
public class APIMMApplication extends SpringBootServletInitializer {	
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	    return application.sources(APIMMApplication.class);
	}

	public static void main(String[] args) throws Exception {
	    SpringApplication.run(APIMMApplication.class, args);	
	}	
	@Autowired
	MamEventService mmEvent;
	@PostConstruct
	void started() {
	    TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
	    mmEvent.TimerResend();
	}
}


