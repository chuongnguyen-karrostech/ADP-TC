package com.ADP.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;


import com.ADP.service.Athena.edta.EdtaAthenaService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ADP.etext.model.DriverPayrollMessage;
import com.ADP.etext.repository.DriverPayrollMessageRepository;
import com.ADP.util.DateFunction;
import com.ADP.util.Utilities;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class DriverPayrollMessageService {
	private final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
    EdtaAthenaService edtaAthenaService;
	@Autowired
	MMLogsService mmlogService;

    @Autowired
    DeviceService deviceService;

    @Autowired
    DriverPayrollMessageRepository server;

    @SuppressWarnings("deprecation")
    public DriverPayrollMessage savePunchIn(DriverPayrollMessage entity) {
      
    long timekey = DateFunction.TimeKey();

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
        Date oldEvent = entity.getDateEvent();
        Timestamp dateReceived = new Timestamp(date.getTime());
        Timestamp dateClient = DateFunction.AddTimeZone(new Timestamp(entity.getDateEvent().getTime()));
        //Timestamp dateClient = new Timestamp(entity.getDateEvent().getTime());
        entity.setDateReceived(dateReceived);
        entity.setDateAdjusted(dateClient);
        entity.setDateEvent(dateClient);

        entity.setDateAdjusted(entity.getDateEvent());
        //hard code
        entity.setDriverType("1");
        entity.setSourceType("10");
        
        if(Is_TimeSheet_Module_On()) {
        	try {        		       		
        		//punch to edta
        		Object edtaObject =  edtaAthenaService.punchTransaction(entity);        		
        	}
        	catch(Exception ex) {
        		//throw new IllegalArgumentException("punch in to EDTAAthena unsuccessfull");        		
        		mmlogService.addContainer(entity, "punchtransaction");
        		mmlogService.TimerResend("punchtransaction");
        		logger.info(Utilities.LogSave(timekey, "PunchIn to EDTA Athena unsuccessfully", "driver id", ex.toString()));
        	}
        }else {
	        //save etext data
	        DriverPayrollMessage oldEntity = server.save(entity);
	        //logger.info(Utilities.LogSave(timekey, "PunchIn", "driver id", obj.getDriverID()));
	        oldEntity.setDateEvent(oldEvent);
	        entity = oldEntity;
        }
        return entity;
    }

    public DriverPayrollMessage savePunchOut(DriverPayrollMessage entity) {
    	long timekey = DateFunction.TimeKey();
    	//handling data before save
        entity.setId(null);
        Date date = new Date();
        Date oldEvent = entity.getDateEvent();
        Timestamp dateReceived = new Timestamp(date.getTime());

        Timestamp dateClient = DateFunction.AddTimeZone(new Timestamp(entity.getDateEvent().getTime()));
        //Timestamp dateClient = new Timestamp(entity.getDateEvent().getTime());
        entity.setDateReceived(dateReceived);
        entity.setDateAdjusted(dateClient);
        entity.setDateEvent(dateClient);
        //punch out = punch in +1;
        entity.setActivityCode(entity.getActivityCode() + 1);
        //hard code
        entity.setDriverType("1");
        entity.setSourceType("10");
        
        if(Is_TimeSheet_Module_On()) {
        	try {
        		
        		//punch to edta
        		Object edtaObject =  edtaAthenaService.punchTransaction(entity);
        	}
        	catch(Exception ex) {
        		//throw new IllegalArgumentException("punch out to EDTAAthena unsuccessfull");
        		mmlogService.addContainer(entity, "punchtransaction");
        		mmlogService.TimerResend("punchtransaction");
        		logger.info(Utilities.LogSave(timekey, "PunchOut to EDTA Athena unsuccessfully", "driver id", ex.toString()));
        	}
        }else {
	        //save etext data
	        DriverPayrollMessage oldEntity = server.save(entity); 
	        oldEntity.setDateEvent(oldEvent);
	        entity = oldEntity;
        }
        return entity;
    }

    public DriverPayrollMessage getById(Serializable id) {
        return server.findOne((Long) id);
    }

    public DriverPayrollMessage updatePunchComment(DriverPayrollMessage entity) {
        //handling data before save
        Date date = new Date();
        Date oldEvent = entity.getDateEvent();
        Timestamp dateReceived = new Timestamp(date.getTime());
        Timestamp dateClient = DateFunction.AddTimeZone(new Timestamp(entity.getDateEvent().getTime()));
        entity.setDateReceived(dateReceived);
        entity.setDateAdjusted(dateClient);
        entity.setDateEvent(dateClient);
        if (entity.getId() != null) {
            DriverPayrollMessage findEntity = server.findById(entity.getId());
            if (entity.getComment() != null) {
                if (!entity.getComment().equals(findEntity.getComment())) {
                    findEntity.setComment(entity.getComment());
                    findEntity.setDateReceived(entity.getDateReceived());
                    DriverPayrollMessage oldEntity = new DriverPayrollMessage();
                    oldEntity = server.save(findEntity);
                    return oldEntity;
                }
            } else {
                if (findEntity.getComment() != null) {
                    findEntity.setComment(entity.getComment());
                    findEntity.setDateReceived(entity.getDateReceived());
                    DriverPayrollMessage oldEntity = new DriverPayrollMessage();
                    oldEntity = server.save(findEntity);
                    return oldEntity;
                }
            }
        } else {
            List<DriverPayrollMessage> lstEntity = server.findByJson(entity.getBusID(), entity.getDriverID(), entity.getActivityCode(), entity.getBillingID(), entity.getDateEvent());
            DriverPayrollMessage oldEntity = new DriverPayrollMessage();
            if (!lstEntity.isEmpty()) {
                for (DriverPayrollMessage driverPayroll : lstEntity) {
                    driverPayroll.setComment(entity.getComment());
                    driverPayroll.setDateReceived(entity.getDateReceived());
                    if (driverPayroll.getDateEvent().getTime() == entity.getDateEvent().getTime()) {
                        oldEntity = server.save(driverPayroll);
                        return oldEntity;
                    }
                }
            } else {
                throw new IllegalArgumentException("update comment unsuccessfull");
            }
        }

        return entity;
    }

    public List<DriverPayrollMessage> loadPunch(DriverPayrollMessage entity) throws ParseException {
        //handling data before save
        //Timestamp timestamp = DateFunction.AddTimeZone(new Timestamp(entity.getTodate().getTime()));
        // Calendar cal = Calendar.getInstance();
        //cal.setTimeInMillis(timestamp.getTime());
        // add 1 day
        // cal.add(Calendar.DAY_OF_MONTH, 1);
        //timestamp = new Timestamp(cal.getTime().getTime());
        //  Timestamp dateFrom = DateFunction.AddTimeZone(new Timestamp(entity.getFromdate().getTime()));
        //Timestamp dateTo = DateFunction.AddTimeZone(new Timestamp(entity.getTodate().getTime()));
        // Timestamp dateTo = timestamp;
        //  entity.setFromdate(dateFrom);
        // entity.setTodate(dateTo);
        //entity.setDateEvent(dateClient);
        //List<DriverPayrollMessage> lstEntity = server.loadByJson(entity.getBusID(), entity.getDriverID(), entity.getFromdate(), entity.getTodate() );
        List<DriverPayrollMessage> lstEntity = server.loadByJson(entity.getBusID(), entity.getDriverID());
        //DriverPayrollMessage oldEntity = new DriverPayrollMessage();
        if (lstEntity != null) {
            for (DriverPayrollMessage item : lstEntity) {
                Timestamp dateClient = DateFunction.MoveTimeZone(new Timestamp(item.getDateEvent().getTime()));
                item.setDateEvent(dateClient);
                item.setDateAdjusted(dateClient);
            }
            return lstEntity;
        } else {
            throw new IllegalArgumentException("load punch unsuccessfully");
        }
        //return lstEntity;
    }

    public DriverPayrollMessage loadPunchEdtaWeb(String entity) throws ParseException {

        List<DriverPayrollMessage> lstEntity = server.loadByEDTAWeb(entity);
        if (lstEntity != null && lstEntity.size() > 0) {
            for (DriverPayrollMessage item : lstEntity) {
                Timestamp dateClient = DateFunction.MoveTimeZone(new Timestamp(item.getDateEvent().getTime()));
                item.setDateEvent(dateClient);
                item.setDateAdjusted(dateClient);
            }
            return lstEntity.get(0);
        } else {
            //throw new 	 IllegalArgumentException("load punch unsuccessfull");
            return null;
        }
        //return lstEntity;
    }

    public Object punchLog(DriverPayrollMessage driverpay) {
        String transactionUrl = com.ADP.util.Utilities.MMAPI_Url + "mam/punch/add"; //add punch log to MAM Punch Log
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(transactionUrl);
        ObjectMapper mapper = new ObjectMapper();
        String punchlog = "";
        Object obj = new Object();
        try {
            Date date = new Date();
            Timestamp dateReceived = new Timestamp(date.getTime());
            driverpay.setDateReceived(dateReceived);
            punchlog = mapper.writeValueAsString(driverpay);
            obj = mapper.readTree(punchlog);
        } catch (Exception e) {
            return e.toString();
        }
        //HttpEntity<String> entityRequest = new HttpEntity<>(punchlog);
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
        list.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(list);
        return restTemplate.postForObject(builder.toUriString(), obj, Object.class);
    }
    public Object findDuplicatepunchLog(DriverPayrollMessage driverpay) {
    	
        String transactionUrl = com.ADP.util.Utilities.MMAPI_Url + "mam/punch/checkexist"; //add punch log to MAM Punch Log
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(transactionUrl);
        ObjectMapper mapper = new ObjectMapper();
        String punchlog = "";
        Object obj = new Object();
        try {
            Date date = new Date();
            Timestamp dateReceived = new Timestamp(date.getTime());
            driverpay.setDateReceived(dateReceived);
            punchlog = mapper.writeValueAsString(driverpay);
            obj = mapper.readTree(punchlog);
        } catch (Exception e) {
            return e.toString();
        }
        //HttpEntity<String> entityRequest = new HttpEntity<>(punchlog);
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
        list.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(list);
        return restTemplate.postForObject(builder.toUriString(), obj, Object.class);
    }
    public Boolean findDuplicate(DriverPayrollMessage entity) {
        Boolean flag;
        Timestamp dateClient = DateFunction.AddTimeZone(new Timestamp(entity.getDateEvent().getTime()));
        List<DriverPayrollMessage> lstEntity = server.findByJson(entity.getBusID(), entity.getDriverID(), entity.getActivityCode(), entity.getBillingID(), dateClient);
        if (!lstEntity.isEmpty()) {
            flag = true;
            entity.setId(lstEntity.get(lstEntity.size() - 1).getId());
        } else {
            flag = false;
        }
        return flag;
    }

    public static final String TIMESHEET_MODULE = "TIMESHEET_MODULE";

    public boolean Is_TimeSheet_Module_On () {
       Boolean bGetNameSetting =  getMamSettingbyName(TIMESHEET_MODULE).equals("ON") ? true : false;
       return bGetNameSetting;
    }

    private String getMamSettingbyName(String settingName) {
        Object[] lstSetting = deviceService.findSettingbyApp(1);
        Object a = Arrays.stream(lstSetting).filter(item ->
                ((LinkedHashMap) item).get("settingName").toString().toUpperCase().equals(settingName)
        ).findFirst();    
        return (a != null) ? ((LinkedHashMap) ((Optional)a).get()).get("settingValue").toString() : "";
    }
}
