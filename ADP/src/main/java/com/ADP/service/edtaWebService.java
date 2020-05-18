package com.ADP.service;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ADP.etext.model.DriverPayrollMessage;
import com.ADP.etext.model.EventCodes;
import com.ADP.etext.repository.DriverPayrollMessageRepository;
import com.ADP.etmain.model.Setting;
import com.ADP.util.DateFunction;

@Service
public class edtaWebService {
	@Autowired
	DriverPayrollMessageRepository server;
	@Autowired
	SettingService settingService;
	
	public Object[] getEventCodes() {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"etext/eventcode";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), Object[].class);
		
	}	
	public String getEventCodesString() {
		String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"etext/eventcodeedtaweb";
		UriComponentsBuilder builder = UriComponentsBuilder
		    .fromUriString(transactionUrl);  
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(builder.toUriString(), String.class);
		
	}
	@SuppressWarnings("deprecation")
	public DriverPayrollMessage savePunchIn(DriverPayrollMessage entity) {
		
		//handling data before save
		/*
		+ ID : autonumber : DONE
		+ [DateReceived] = Get current datetime of client's server 
		+ [EventID] = Activity Code (Punch In or Punch Out respective)
		+ [DriverType] = 1 is Driver and [DriverType] = 2 is Non-Driver
		=> default [DriverType] = 1 (Tho will be confirming this later)
		+ [Latitude] : provided
		+ [Longitude] : provided
		+ [SourceType] : 10
		+ Date Adjust = Date Event = client parameter
	      DateReceived = client's server 
		  Source type =10
		*/
		entity.setId(null);
		Date date = new Date();
		//Date oldEvent = entity.getDateEvent();
		Timestamp dateReceived = new Timestamp(date.getTime());
		String vehTime = getVehicleTime();
		Date dateClient = DateFunction.StringToDate(vehTime, "yyyy-MM-dd HH:mm:ss.SSS");
		//Timestamp dateClient = DateFunction.AddTimeZone(new Timestamp(entity.getDateEvent().getTime()));
		//Timestamp dateClient = DateFunction.AddTimeZone(dateReceived);
		entity.setDateReceived(dateReceived);
		entity.setDateAdjusted(dateClient);
		entity.setDateEvent(dateClient);
		
		entity.setDateAdjusted(entity.getDateEvent());
		//hard code
		entity.setDriverType(null);
		entity.setSourceType("0");
		entity.setDriverPayrollMessage_phone("eDTAweb");
		//save data
		DriverPayrollMessage oldEntity = server.save(entity);		
		//logger.info(Utilities.LogSave(timekey, "PunchIn", "driver id", obj.getDriverID()));
		oldEntity.setDateEvent(dateReceived);
		return oldEntity;		
	}
	
	public DriverPayrollMessage savePunchOut(DriverPayrollMessage entity, Integer autoout) {
		//handling data before save
		entity.setId(null);
		Date date = new Date();
		//Date oldEvent = entity.getDateEvent();
		Timestamp dateReceived;
		Timestamp dateClient;
		String vehTime = getVehicleTime();
		Date dateVeh = DateFunction.StringToDate(vehTime, "yyyy-MM-dd HH:mm:ss.SSS");
		if(autoout != null && autoout == 1)
		{			
			dateReceived = new Timestamp(date.getTime() - 1000);
			dateClient = new Timestamp(dateVeh.getTime() - 1000);
		}
		else
		{
			dateReceived = new Timestamp(date.getTime());
			dateClient = new Timestamp(dateVeh.getTime());
		}
		//Timestamp dateClient = DateFunction.AddTimeZone(new Timestamp(entity.getDateEvent().getTime()));
		//Timestamp dateClient = DateFunction.AddTimeZone(dateReceived);
		entity.setDateReceived(dateReceived);
		entity.setDateAdjusted(dateClient);
		entity.setDateEvent(dateClient);
		//punch out = punch in +1;
		entity.setActivityCode(entity.getActivityCode()+1);	
		//hard code
		entity.setDriverType(null);
		entity.setSourceType("0");
		entity.setDriverPayrollMessage_phone("eDTAweb");
		//save data
		DriverPayrollMessage oldEntity = server.save(entity);		
		//oldEntity.setDateEvent(oldEvent);
		oldEntity.setDateEvent(dateReceived);
		return oldEntity;
	}
	public String getVehicleTime() {
		Setting obj = settingService.findById("TimeZone");
		if (obj.getValueSetting().indexOf("GMT") == -1)
		{
			String setTime = "US/" + obj.getValueSetting();				
			//obj.setValueSetting(setTime);			    
			Date date = new Date();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

			// Use Madrid's time zone to format the date in
			df.setTimeZone(TimeZone.getTimeZone(setTime));
			
			//System.out.println("Vehicle Date and time in: " + df.format(date));
		    String vehTime = df.format(date);
		    return vehTime;
		}
		return "";
	}
}
