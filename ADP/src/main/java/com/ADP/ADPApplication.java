package com.ADP;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.ADP.service.MMLogsService;

@SpringBootApplication(scanBasePackages={
		"com.ADP"})

//public class RamapiApplication {
//	public static void main(String[] args) {
//		SpringApplication.run(RamapiApplication.class, args);
//	}
//}
public class ADPApplication extends SpringBootServletInitializer {

//	public static void main(String[] args) {
//		SpringApplication.run(RamapiApplication.class, args);
//	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	    return application.sources(ADPApplication.class);
	}
	
	
	public static void main(String[] args) throws Exception {
	    SpringApplication.run(ADPApplication.class, args);	    
	}
	@Autowired
	MMLogsService mmService;
	@PostConstruct
	void started() {
	   // TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
		mmService.TimerResend();
	}
}


