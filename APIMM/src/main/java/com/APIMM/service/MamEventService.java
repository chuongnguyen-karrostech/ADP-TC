package com.APIMM.service;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.APIMM.configurations.APIMMConfig;
import com.APIMM.etgps.model.BusPosition;
import com.APIMM.etgps.model.Student;
import com.APIMM.etgps.model.StudentId;
import com.APIMM.etgps.model.UnknownStudent;
import com.APIMM.etgps.repository.BusPositionRepository;
import com.APIMM.etgps.repository.StudentRepository;
import com.APIMM.etgps.repository.UnknownStudentRepository;
import com.APIMM.etmain.model.Setting;
import com.APIMM.etmain.model.StudentTagIdmappings;
import com.APIMM.etmain.model.VehicleGpsunitMappings;
import com.APIMM.etmain.repository.SettingRepository;
import com.APIMM.etmain.repository.StudentTagIDMappingRepository;
import com.APIMM.etmain.repository.VehicleGpsunitMappingRepository;
import com.APIMM.mam.model.AthenaContainer;
import com.APIMM.mam.model.MamDeviceEvent;
import com.APIMM.mam.model.MamDeviceMessage;
import com.APIMM.mam.model.MamDeviceRawEvent;
import com.APIMM.mam.model.MamDeviceRunEvent;

import com.APIMM.mam.model.MamDeviceStopEvent;
import com.APIMM.mam.model.MamDeviceStudentEvent;
import com.APIMM.mam.model.MamEvents;

import com.APIMM.mam.model.MamSwipeEvent;
import com.APIMM.mam.model.MdmDevice;
import com.APIMM.mam.model.NoneMamDeviceEvent;
import com.APIMM.mam.model.NoneMamDeviceRunEvent;
import com.APIMM.mam.model.NoneMamDeviceStopEvent;
import com.APIMM.mam.repository.APIConfigRepository;
import com.APIMM.mam.repository.AthenaContainerRepositoy;
import com.APIMM.mam.repository.MamDevMessageRepository;
import com.APIMM.mam.repository.MamDeviceEventRepository;
import com.APIMM.mam.repository.MamDeviceRawEventRepository;
import com.APIMM.mam.repository.MamDeviceRunEventRepository;
import com.APIMM.mam.repository.MamDeviceStopEventRepository;
import com.APIMM.mam.repository.MamDeviceStudentEventRepository;
import com.APIMM.mam.repository.MamEventsRepository;
import com.APIMM.mam.repository.MamSettingRepository;
import com.APIMM.mam.repository.MdmDeviceRepository;
import com.APIMM.mam.repository.NoneMamDeviceEventRepository;
import com.APIMM.mam.repository.NoneMamDeviceRunEventRepository;
import com.APIMM.mam.repository.NoneMamDeviceStopEventRepository;
import com.APIMM.util.AppConst;
import com.APIMM.util.CommonFunction;
import com.APIMM.util.DateFunction;
import com.APIMM.util.ManualEmbarkDisembark;
import com.APIMM.util.SearchObject;
import com.APIMM.util.Vehicles;
import com.APIMM.util.message.CommonMessage;
import com.APIMM.util.message.EventMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.google.common.collect.Maps;

@Service
public class MamEventService {
	private final Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	MdmDeviceRepository  MDM_Device_Server;
	@Autowired
	MamDeviceRunEventRepository  MDM_DeviceRunEvent_Server;
	@Autowired
	NoneMamDeviceRunEventRepository  None_MDM_DeviceRunEvent_Server;
	@Autowired
	MamDeviceStopEventRepository  MDM_DeviceStopEvent_Server;	
	@Autowired
	NoneMamDeviceStopEventRepository  None_MDM_DeviceStopEvent_Server;	
	@Autowired
	MamDeviceStudentEventRepository  MDM_DeviceStuEvent_Server;
	@Autowired
	MamDeviceEventRepository  MDM_DeviceEvent_Server;
	@Autowired
	NoneMamDeviceEventRepository  None_MDM_DeviceEvent_Server;
	@Autowired
	MamDeviceRawEventRepository  MDM_DeviceRawEvent_Server;
	@Autowired
	MamEventsRepository  MAM_Event_Server;	
	@Autowired
	BusPositionRepository busPositionRepository;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	UnknownStudentRepository unknownStudentRepository;
	@Autowired
	VehicleGpsunitMappingRepository vehicleGpsunitMappingRepository;
	@Autowired
	StudentTagIDMappingRepository studentTagIDMappingRepository;
	@Autowired
	SettingRepository settingRepository;
	@Autowired
	MamDevMessageRepository messageRepository;
	@Autowired
	APIConfigRepository apiConfigRepository;
	@Autowired
	AthenaContainerService containerService;
	@Autowired
	MamSettingRepository mamSettingRepository;
	@Autowired
    private Environment env;
	
	public List<MamDeviceEvent> findAll(){
		List<MamDeviceEvent> lstEvent =  MDM_DeviceEvent_Server.findAll();
		return lstEvent;
	}
	public Page<NoneMamDeviceEvent> getAllDevEvent(Pageable pageable){
		return None_MDM_DeviceEvent_Server.findAll(pageable);
	}

	public Page<NoneMamDeviceEvent> Search(List<SearchObject> lstsearch, Pageable pageRequest) {
		
		List<SearchObject> lstSearch = FindDevIdBySerialNumber(lstsearch);
		return	  None_MDM_DeviceEvent_Server.findAll(BuildSpecification(lstSearch), pageRequest); 
	}
	 
	public List<MamDeviceStopEvent> findAllStopEvent(){
		List<MamDeviceStopEvent> lstEvent =  MDM_DeviceStopEvent_Server.findAll();
		return lstEvent;
	}
	public Page<NoneMamDeviceStopEvent> getAllStopEvent(Pageable pageable){
		return None_MDM_DeviceStopEvent_Server.findAll(pageable);
	}
	public Page<NoneMamDeviceStopEvent> SearchStopEvent(List<SearchObject> lstsearch, Pageable pageRequest) {
		List<SearchObject> lstSearch = FindDevIdBySerialNumber(lstsearch);
		return	  None_MDM_DeviceStopEvent_Server.findAll(BuildSpecificationStop(lstSearch), pageRequest); 
	}
	public List<MamDeviceRunEvent> findAllRunEvent(){
		List<MamDeviceRunEvent> lstEvent =  MDM_DeviceRunEvent_Server.findAll();
		return lstEvent;
	}
	public Page<NoneMamDeviceRunEvent> getAllRunEvent(Pageable pageable){
		return None_MDM_DeviceRunEvent_Server.findAll(pageable);
	}
	public Page<NoneMamDeviceRunEvent> SearchRunEvent(List<SearchObject> lstsearch, Pageable pageRequest) { 
		List<SearchObject> lstSearch = FindDevIdBySerialNumber(lstsearch);
		return	  None_MDM_DeviceRunEvent_Server.findAll(BuildSpecificationRun(lstSearch), pageRequest); 
	}
	public String AddEvent(MamDeviceEvent devEvent) {
		String flag = CommonMessage.False;
		try {
			flag = CheckValidate(devEvent);
			if (flag == CommonMessage.Ok) {
				
				Date date = new Date();
				Date oldEventTime = devEvent.getEventTime();
				
				Timestamp dateReceived = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));				
				//Timestamp eventTime = DateFunction.AddTimeZone(new Timestamp(devstuEvent.getEventTime().getTime()));
				//Timestamp eventSend = DateFunction.AddTimeZone(new Timestamp(devstuEvent.getEventSend().getTime()));
				Timestamp eventTime = new Timestamp(devEvent.getEventTime().getTime());
				Timestamp eventSend = new Timestamp(devEvent.getEventSend().getTime());
				devEvent.seteventWriteTime(dateReceived);
				devEvent.setEventTime(eventTime);
				devEvent.setEventSend(eventSend);
				
				MamDeviceEvent addDeviceEvent = MDM_DeviceEvent_Server.save(devEvent);
				
				//boolean bc = StudentSwipeCard(devEvent);
				
				flag = String.valueOf(addDeviceEvent.getDeviceEventId());
			}else{
				throw new IllegalArgumentException(flag);
			}

		} catch (Exception e) {
			flag = e.getMessage();// CommonMessage.False;
			throw new IllegalArgumentException(flag);
		}

		return flag;
	}
	public String CheckValidate(MamDeviceEvent devEvent) {
		String flag = CommonMessage.Ok;
		if (AppConst.dictEvent.size()<=0){
			List<MamEvents> lstEvents =MAM_Event_Server.findAll();
			AppConst.initEvent(lstEvents);
		}
		if (devEvent.getEventName()==null)
		{
			flag = EventMessage.EventNull;
			return flag;
		}
		devEvent.setEventName(devEvent.getEventName().toUpperCase());
		Integer eventId = AppConst.getEventId(devEvent.getEventName());
		if (eventId<0)
		{
			flag = EventMessage.EventNotExist;
			return flag;
		}
		devEvent.setEventId(eventId); 
		if (devEvent.getDevId() <=0)
		{
			flag = EventMessage.DevIdNotExist;
			return flag;
		} else {
			MdmDevice md = MDM_Device_Server.findByDevID(devEvent.getDevId());
			if (md==null)		
			{
				flag = EventMessage.DevIdNotExist;
				return flag;
			}
			else if (devEvent.getEventTime() == null)
			{
				flag = EventMessage.EventTimeNull;
				return flag;
			}		
			else if (devEvent.getEventSend() == null)
			{
				flag = EventMessage.SendTimeNull;
				return flag;
			}
			else if (devEvent.getEventSend().before(devEvent.getEventTime()))
			{
				flag = EventMessage.WrongTime;
				return flag;
			}			
		}
		return flag;
	}	
	
	public String AddSwipeEvent(MamSwipeEvent devswipeEvent) {
		String flag = CommonMessage.False;
		try {
			flag = CheckValidateSwipe(devswipeEvent);
			if (flag == CommonMessage.Ok) {
				
				Date date = new Date();				
				Timestamp dateReceived = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));				
				Timestamp eventTime = new Timestamp(devswipeEvent.getEventTime().getTime());
				Timestamp eventSend = new Timestamp(devswipeEvent.getEventSend().getTime());
				devswipeEvent.seteventWriteTime(dateReceived);
				devswipeEvent.setEventTime(eventTime);
				devswipeEvent.setEventSend(eventSend);	
				
				String StuId = StudentSwipeCard(devswipeEvent);
				
				flag = StuId;
			}else{
				throw new IllegalArgumentException(flag);
			}

		} catch (Exception e) {
			flag = e.getMessage();// CommonMessage.False;
			throw new IllegalArgumentException(flag);
		}

		return flag;
	}
	public String AddStudentEvent(MamDeviceStudentEvent devstuEvent) {
		String flag = CommonMessage.False;
		try {
			flag = CheckValidate(devstuEvent);
			if (flag == CommonMessage.Ok) {
				
				Date date = new Date();				
				Timestamp dateReceived = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));				
				//Timestamp eventTime = DateFunction.AddTimeZone(new Timestamp(devstuEvent.getEventTime().getTime()));
				//Timestamp eventSend = DateFunction.AddTimeZone(new Timestamp(devstuEvent.getEventSend().getTime()));
				Timestamp eventTime = new Timestamp(devstuEvent.getEventTime().getTime());
				Timestamp eventSend = new Timestamp(devstuEvent.getEventSend().getTime());
				devstuEvent.seteventWriteTime(dateReceived);
				devstuEvent.setEventTime(eventTime);
				devstuEvent.setEventSend(eventSend);				
				MamDeviceStudentEvent addDeviceStudentEvent = MDM_DeviceStuEvent_Server.save(devstuEvent);
				//if (!com.APIMM.util.CommonFunction.CalAmp_mode.equals("1")){
					//boolean bc = StudentSwipeCard(devstuEvent);
				//}
				//APIConfig apiconfig = apiConfigRepository.findSettingbyName("RFID_TO_ETGPS");
				if(devstuEvent.getswipeCardType() == null || devstuEvent.getswipeCardType() < 1 || devstuEvent.getswipeCardType() > 1|| APIMMConfig.RFID_TO_ETGPS == 1)  // chuong , check swipe type
				{
					SendManualEmbarkDisembark(devstuEvent);
					boolean bc = StudentSwipeCard(devstuEvent);
				}
				flag = String.valueOf(addDeviceStudentEvent.getdeviceStuEventId());
			}else{
				throw new IllegalArgumentException(flag);
			}

		} catch (Exception e) {
			flag = e.getMessage();// CommonMessage.False;
			throw new IllegalArgumentException(flag);
		}

		return flag;
	}
	/*
	 * Save studentSwipeCardData only if event for STUDENT ONBOARD or DISEMBARK
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean StudentSwipeCard(MamDeviceStudentEvent devstuEvent) {
		
		if (devstuEvent.getEventName().contains("STUDENT")&& devstuEvent.getstudentId() !=null) {
			// convert busId to UnitID
			VehicleGpsunitMappings unitIDObject = vehicleGpsunitMappingRepository.findByBusNumber(devstuEvent.getBusId());
			if(unitIDObject!= null && unitIDObject.getUnitId().trim().length() >0) {
				devstuEvent.setBusId(unitIDObject.getUnitId().trim());
			}
			// h convert to UTC Time with Timezone 
			Setting objTimeZone = settingRepository.findByName("TimeZone");
			Date timestampOfSend = devstuEvent.getEventTime();
			if (objTimeZone.getValue().indexOf("GMT") == -1)
			{
				String timezone = "US/" + objTimeZone.getValue();
				timestampOfSend = DateFunction.convertToUTC(devstuEvent.getEventTime(), timezone);
			}
			
			
			StudentId studentId = new StudentId();
			studentId.setUnitId(devstuEvent.getBusId());
			if (com.APIMM.util.CommonFunction.VendorBrand.equalsIgnoreCase("TCM")){
				studentId.setLatitude(devstuEvent.getLatitude() * 10000000);
				studentId.setLongitude(devstuEvent.getLongitude()* 10000000);
			}else{
				studentId.setLatitude(devstuEvent.getLatitude());
				studentId.setLongitude(devstuEvent.getLongitude());
			}
			
			
			studentId.setPositionTimeStamp(timestampOfSend);
			// edit StudentTagIDMappings
			String tagStudentId = devstuEvent.getstudentId();
			if(devstuEvent.getstudentId()!= null && devstuEvent.getstudentId().length() >0) {
				StudentTagIdmappings student = null;
				List<StudentTagIdmappings> studentTags = studentTagIDMappingRepository.findByEdulogStudentId(devstuEvent.getstudentId());				
				if (studentTags.size()>0)
					student = studentTags.get(0);
				if(student!=null && student.getTagId()!= null) {
					tagStudentId = student.getTagId();
				}
			}
			
			studentId.setStudentId(tagStudentId);
			studentId.setStatus("MDT-7P");
			Date date = new Date();
			Timestamp writeTimeStamp = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));
			studentId.setWriteTimeStamp(writeTimeStamp);
			Student student = new Student(studentId);
			studentRepository.save(student);
			// if unknow student, insert UnknownStudent table. with studentname_[id] on field studentname.
			if(devstuEvent.getstatus().toUpperCase().contains("UNKNOWN")){
				UnknownStudent unknownStudent = new UnknownStudent();
				if (com.APIMM.util.CommonFunction.VendorBrand.equalsIgnoreCase("TCM")){
					unknownStudent.setLatitude(devstuEvent.getLatitude() * 10000000);
					unknownStudent.setLongitude(devstuEvent.getLongitude()* 10000000);
				}else{
					unknownStudent.setLatitude(devstuEvent.getLatitude());
					unknownStudent.setLongitude(devstuEvent.getLongitude());
				}				
				
				unknownStudent.setPositionTimeStamp(timestampOfSend);
				unknownStudent.setSource("MDT-7P");
				unknownStudent.setStudentName("studentname_"+devstuEvent.getstudentId());
				unknownStudent.setUnitId(devstuEvent.getBusId());
				unknownStudentRepository.save(unknownStudent);
				
				MamDeviceMessage message = new MamDeviceMessage();
				message.setdevId(devstuEvent.getDevId());
				message.setmoduleName("Swipe RFID");
				message.setfunctionName("Swipe RFID");
				message.setmessage("Unknown TagID: "+devstuEvent.getstudentId());
				message.setmessageTime(timestampOfSend);
				date = new Date();
				Timestamp dateReceived = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));
				message.setlastUpdated(dateReceived);				
				
				MamDeviceMessage addDeviceMessage = messageRepository.save(message);
			}
			BusPosition busPosition = new BusPosition();
			busPosition.setDistrictId(null);
			// busID = UnitID
			busPosition.setUnitId(devstuEvent.getBusId());
			if (com.APIMM.util.CommonFunction.VendorBrand.equalsIgnoreCase("TCM")){
				busPosition.setLatitude(devstuEvent.getLatitude() * 10000000);
				busPosition.setLongitude(devstuEvent.getLongitude()* 10000000);
			}else{
				busPosition.setLatitude(devstuEvent.getLatitude());
				busPosition.setLongitude(devstuEvent.getLongitude());
			}			
			//busPosition.setWriteTimeStamp(devstuEvent.geteventWriteTime());
			busPosition.setPositionTimeStamp(timestampOfSend);
			busPosition.setEventId(60);
			busPosition.setSpeed(0.0);
			busPosition.setRssi(0.0);
			busPosition.setRouteMiles(0.0);
			busPosition.setLogId(null);
			date = new Date();
			Timestamp buswriteTimeStamp = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));
			busPosition.setWriteTimeStamp(buswriteTimeStamp);
			busPositionRepository.save(busPosition);
			
		}
		return true;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public String StudentSwipeCard(MamSwipeEvent devswipeEvent) {
		String TagId = devswipeEvent.gettagId().toString().trim();
		// Decode data on other side, by processing encoded data
		byte[] valueDecoded =  Base64.getDecoder().decode(TagId);
		String newTag = new String(valueDecoded);
		String Studentid= newTag;
		boolean unknown = false;
		//find student from tag
		List<StudentTagIdmappings> studentTags = studentTagIDMappingRepository.findBytagId(newTag);
		StudentTagIdmappings studentTag =null;
		if (studentTags.size()>0)
			studentTag = studentTags.get(0);
		if (studentTag!=null){
			Studentid = studentTag.getEdulogStudentId().trim();
		}else
			unknown=true;
		if (Studentid !=null) {
			// convert busId to UnitID
			VehicleGpsunitMappings unitIDObject = vehicleGpsunitMappingRepository.findByBusNumber(devswipeEvent.getBusId());
			if(unitIDObject!= null && unitIDObject.getUnitId().trim().length() >0) {
				devswipeEvent.setBusId(unitIDObject.getUnitId().trim());
			}
			
			Setting objTimeZone = settingRepository.findByName("TimeZone");
			Date timestampOfSend = devswipeEvent.getEventTime();
			if (objTimeZone.getValue().indexOf("GMT") == -1)
			{
				String timezone = "US/" + objTimeZone.getValue();
				timestampOfSend = DateFunction.convertToUTC(devswipeEvent.getEventTime(), timezone);
			}							
			//insert ETGPS.student 
			StudentId studentId = new StudentId();
			studentId.setUnitId(devswipeEvent.getBusId());
			if (com.APIMM.util.CommonFunction.VendorBrand.equalsIgnoreCase("TCM")){
				studentId.setLatitude(devswipeEvent.getLatitude()*10000000);
				studentId.setLongitude(devswipeEvent.getLongitude()*10000000);
			}else{
				studentId.setLatitude(devswipeEvent.getLatitude());
				studentId.setLongitude(devswipeEvent.getLongitude());
			}
			studentId.setPositionTimeStamp(timestampOfSend);
			// edit StudentTagIDMappings
			String tagStudentId = newTag;
			studentId.setStudentId(tagStudentId);
			studentId.setStatus("MDT-7P");			
			Date date = new Date();
			Timestamp stuwriteTimeStamp = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));
			studentId.setWriteTimeStamp(stuwriteTimeStamp);
			Student student = new Student(studentId);			
			studentRepository.save(student);
			// if unknow student, insert UnknownStudent table. with studentname_[id] on field studentname.
			if(unknown){
				//insert ETGPS.unknownstudent
				UnknownStudent unknownStudent = new UnknownStudent();
				if (com.APIMM.util.CommonFunction.VendorBrand.equalsIgnoreCase("TCM")){
					unknownStudent.setLatitude(devswipeEvent.getLatitude()*10000000);
					unknownStudent.setLongitude(devswipeEvent.getLongitude()*10000000);
				}else{
					unknownStudent.setLatitude(devswipeEvent.getLatitude());
					unknownStudent.setLongitude(devswipeEvent.getLongitude());
				}				
				unknownStudent.setPositionTimeStamp(timestampOfSend);
				unknownStudent.setSource("MDT-7P");
				unknownStudent.setStudentName("studentname_"+Studentid);
				unknownStudent.setUnitId(devswipeEvent.getBusId());				
				unknownStudentRepository.save(unknownStudent);
				
				MamDeviceMessage message = new MamDeviceMessage();
				message.setdevId(devswipeEvent.getDevId());
				message.setmoduleName("Swipe RFID");
				message.setfunctionName("Swipe RFID");
				message.setmessage("Unknown TagID: "+Studentid);
				message.setmessageTime(timestampOfSend);
				date = new Date();
				Timestamp dateReceived = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));
				message.setlastUpdated(dateReceived);			
				
				MamDeviceMessage addDeviceMessage = messageRepository.save(message);
			}
			//bus position
			BusPosition busPosition = new BusPosition();
			busPosition.setDistrictId(null);
			// busID = UnitID
			busPosition.setUnitId(devswipeEvent.getBusId());
			if (com.APIMM.util.CommonFunction.VendorBrand.equalsIgnoreCase("TCM")){
				busPosition.setLatitude(devswipeEvent.getLatitude() * 10000000);
				busPosition.setLongitude(devswipeEvent.getLongitude()* 10000000);
			}else{
				busPosition.setLatitude(devswipeEvent.getLatitude());
				busPosition.setLongitude(devswipeEvent.getLongitude());
			}				
			busPosition.setPositionTimeStamp(timestampOfSend);				
			busPosition.setEventId(60);
			busPosition.setSpeed(0.0);
			busPosition.setRssi(0.0);
			busPosition.setRouteMiles(0.0);
			busPosition.setLogId(null);
			date = new Date();
			Timestamp writeTimeStamp = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));
			busPosition.setWriteTimeStamp(writeTimeStamp);
			busPositionRepository.save(busPosition);
		}
		return Studentid;
	}
	public String CheckValidate(MamDeviceStudentEvent devStuEvent) {
		String flag = CommonMessage.Ok;
		if (AppConst.dictEvent.size()<=0){
			List<MamEvents> lstEvents =MAM_Event_Server.findAll();
			AppConst.initEvent(lstEvents);
		}
		if (devStuEvent.getEventName()==null)
		{
			flag = EventMessage.EventNull;
			return flag;
		}
		devStuEvent.setEventName(devStuEvent.getEventName().toUpperCase());
		if (!devStuEvent.getEventName().contains("STUDENT")){
			flag = "eventName invalid.";
			return flag;
		}
		Integer eventId = AppConst.getEventId(devStuEvent.getEventName());
		if (eventId<0)
		{
			flag = EventMessage.EventNotExist;
			return flag;
		}
		devStuEvent.setEventId(eventId); 
		if (devStuEvent.getDevId() <=0)
		{
			flag = EventMessage.DevIdNotExist;
			return flag;
		} else {
			MdmDevice md = MDM_Device_Server.findByDevID(devStuEvent.getDevId());
			if (md==null)		
			{
				flag = EventMessage.DevIdNotExist;
				return flag;
			}
			else if (devStuEvent.getEventTime() == null)
			{
				flag = EventMessage.EventTimeNull;
				return flag;
			}		
			else if (devStuEvent.getEventSend() == null)
			{
				flag = EventMessage.SendTimeNull;
				return flag;
			}
			else if (devStuEvent.getEventSend().before(devStuEvent.getEventTime()))
			{
				flag = EventMessage.WrongTime;
				return flag;
			}
			else if (devStuEvent.getstudentId()==null)
			{
				flag = EventMessage.MissingStudent;
				return flag;
			}
			else if (devStuEvent.getstopRunId()==null)
			{
				flag = EventMessage.Missingstoprun;
				return flag;
			}
			else if (devStuEvent.getrunId()==null)
			{
				flag = EventMessage.MissingRun;
				return flag;
			}
			else if (devStuEvent.getrunRoute()==null)
			{
				flag = EventMessage.MissingRunRoute;
				return flag;
			}
		}
		return flag;
	}
	public String CheckValidateSwipe(MamSwipeEvent devSwipeEvent) {
		String flag = CommonMessage.Ok;		 
		if (devSwipeEvent.getDevId() <=0)
		{
			flag = EventMessage.DevIdNotExist;
			return flag;
		} else {
			MdmDevice md = MDM_Device_Server.findByDevID(devSwipeEvent.getDevId());
			if (md==null)		
			{
				flag = EventMessage.DevIdNotExist;
				return flag;
			}
			else if (devSwipeEvent.getEventTime() == null)
			{
				flag = EventMessage.EventTimeNull;
				return flag;
			}		
			else if (devSwipeEvent.getEventSend() == null)
			{
				flag = EventMessage.SendTimeNull;
				return flag;
			}
			else if (devSwipeEvent.getEventSend().before(devSwipeEvent.getEventTime()))
			{
				flag = EventMessage.WrongTime;
				return flag;
			}
			else if (devSwipeEvent.gettagId()==null)
			{
				flag = EventMessage.Missingtag;
				return flag;
			}
			
			else if (devSwipeEvent.getrunId()==null)
			{
				flag = EventMessage.MissingRun;
				return flag;
			}
			else if (devSwipeEvent.getrunRoute()==null)
			{
				flag = EventMessage.MissingRunRoute;
				return flag;
			}
		}
		return flag;
	}
	public String AddRawEvent(MamDeviceRawEvent devRawEvent) {
		String flag = CommonMessage.False;
		try {
			flag = CheckValidate(devRawEvent);
			if (flag == CommonMessage.Ok) {
				
				Date date = new Date();
				Date oldEventTime = devRawEvent.getEventTime();
				Timestamp dateReceived = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));				
				//Timestamp eventTime = DateFunction.AddTimeZone(new Timestamp(devstuEvent.getEventTime().getTime()));
				//Timestamp eventSend = DateFunction.AddTimeZone(new Timestamp(devstuEvent.getEventSend().getTime()));
				Timestamp eventTime = new Timestamp(devRawEvent.getEventTime().getTime());
				Timestamp eventSend = new Timestamp(devRawEvent.getEventSend().getTime());				
				devRawEvent.seteventWriteTime(dateReceived);
				devRawEvent.setEventTime(eventTime);
				devRawEvent.setEventSend(eventSend);
				
				MamDeviceRawEvent addDeviceRawEvent = MDM_DeviceRawEvent_Server.save(devRawEvent);
				flag = String.valueOf(addDeviceRawEvent.getDeviceRaweventId());
			}else{
				throw new IllegalArgumentException(flag);
			}

		} catch (Exception e) {
			flag = e.getMessage();// CommonMessage.False;
			throw new IllegalArgumentException(flag);
		}

		return flag;
	}
	
	public String CheckValidate(MamDeviceRawEvent devEvent) {
		String flag = CommonMessage.Ok;
		if (AppConst.dictEvent.size()<=0){
			List<MamEvents> lstEvents =MAM_Event_Server.findAll();
			AppConst.initEvent(lstEvents);
		}
		if (devEvent.getEventName()==null)
		{
			flag = EventMessage.EventNull;
			return flag;
		}
		devEvent.setEventName(devEvent.getEventName().toUpperCase());
		if (!devEvent.getEventName().equalsIgnoreCase("RAW_EVENT")){
			flag = "eventName must be RAW_EVENT.";
			return flag;
		}
		Integer eventId = AppConst.getEventId(devEvent.getEventName());
		if (eventId<0)
		{
			flag = EventMessage.EventNotExist;
			return flag;
		}
		devEvent.setEventId(eventId); 
		if (devEvent.getDevId() <=0)
		{
			flag = EventMessage.DevIdNotExist;
			return flag;
		} else {
			MdmDevice md = MDM_Device_Server.findByDevID(devEvent.getDevId());
			if (md==null)		
		{
			flag = EventMessage.DevIdNotExist;
			return flag;
		}
		else if (devEvent.getEventTime() == null)
		{
			flag = EventMessage.EventTimeNull;
			return flag;
		}		
		else if (devEvent.getEventSend() == null)
		{
			flag = EventMessage.SendTimeNull;
			return flag;
		}
		else if (devEvent.getEventSend().before(devEvent.getEventTime()))
		{
			flag = EventMessage.WrongTime;
			return flag;
		}
		}
		return flag;
	}
	
	public String AddRunEvent(MamDeviceRunEvent devRunEvent) {
		String flag = CommonMessage.False;
		try {
			flag = CheckValidate(devRunEvent);
			if (flag == CommonMessage.Ok) {
				
				Date date = new Date();
				Date oldEventTime = devRunEvent.getEventTime();
				Timestamp dateReceived = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));				
				//Timestamp eventTime = DateFunction.AddTimeZone(new Timestamp(devstuEvent.getEventTime().getTime()));
				//Timestamp eventSend = DateFunction.AddTimeZone(new Timestamp(devstuEvent.getEventSend().getTime()));
				Timestamp eventTime = new Timestamp(devRunEvent.getEventTime().getTime());
				Timestamp eventSend = new Timestamp(devRunEvent.getEventSend().getTime());				
				devRunEvent.seteventWriteTime(dateReceived);
				devRunEvent.setEventTime(eventTime);
				devRunEvent.setEventSend(eventSend);
				
				MamDeviceRunEvent addDeviceRunEvent = MDM_DeviceRunEvent_Server.save(devRunEvent);
				flag = String.valueOf(addDeviceRunEvent.getDeviceRunEventId());
			}else{
				throw new IllegalArgumentException(flag);
			}

		} catch (Exception e) {
			flag = e.getMessage();// CommonMessage.False;
			throw new IllegalArgumentException(flag);
		}

		return flag;
	}
	
	public String CheckValidate(MamDeviceRunEvent devEvent) {
		String flag = CommonMessage.Ok;
		if (AppConst.dictEvent.size()<=0){
			List<MamEvents> lstEvents =MAM_Event_Server.findAll();
			AppConst.initEvent(lstEvents);
		}
		if (devEvent.getEventName()==null)
		{
			flag = EventMessage.EventNull;
			return flag;
		}
		devEvent.setEventName(devEvent.getEventName().toUpperCase());
		if (!devEvent.getEventName().contains("RUN")){
			flag = "eventName invalid.";
			return flag;
		}
		Integer eventId = AppConst.getEventId(devEvent.getEventName());
		if (eventId<0)
		{
			flag = EventMessage.EventNotExist;
			return flag;
		}
		devEvent.setEventId(eventId); 
		if (devEvent.getDevId() <=0)
		{
			flag = EventMessage.DevIdNotExist;
			return flag;
		} else {
			MdmDevice md = MDM_Device_Server.findByDevID(devEvent.getDevId());
			if (md==null)		
			{
				flag = EventMessage.DevIdNotExist;
				return flag;
			}
			else if (devEvent.getEventTime() == null)
			{
				flag = EventMessage.EventTimeNull;
				return flag;
			}		
			else if (devEvent.getEventSend() == null)
			{
				flag = EventMessage.SendTimeNull;
				return flag;
			}
			else if (devEvent.getEventSend().before(devEvent.getEventTime()))
			{
				flag = EventMessage.WrongTime;
				return flag;
			}else if (devEvent.getrunId()==null)
			{
				flag = EventMessage.MissingRun;
				return flag;
			}else if (devEvent.getrunRoute()==null)
			{
				flag = EventMessage.MissingRunRoute;
				return flag;
			}
		}
		return flag;
	}
	
	public String AddStopEvent(MamDeviceStopEvent devStopEvent) {
		String flag = CommonMessage.False;
		try {
			flag = CheckValidate(devStopEvent);
			if (flag == CommonMessage.Ok) {
				
				Date date = new Date();
				Date oldEventTime = devStopEvent.getEventTime();
				Timestamp dateReceived = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));				
				//Timestamp eventTime = DateFunction.AddTimeZone(new Timestamp(devstuEvent.getEventTime().getTime()));
				//Timestamp eventSend = DateFunction.AddTimeZone(new Timestamp(devstuEvent.getEventSend().getTime()));
				Timestamp eventTime = new Timestamp(devStopEvent.getEventTime().getTime());
				Timestamp eventSend = new Timestamp(devStopEvent.getEventSend().getTime());				
				devStopEvent.seteventWriteTime(dateReceived);
				devStopEvent.setEventTime(eventTime);
				devStopEvent.setEventSend(eventSend);
				
				MamDeviceStopEvent addDeviceStopEvent = MDM_DeviceStopEvent_Server.save(devStopEvent);
				flag = String.valueOf(addDeviceStopEvent.getdeviceStopEventId());
			}else{
				throw new IllegalArgumentException(flag);
			}

		} catch (Exception e) {
			flag = e.getMessage();// CommonMessage.False;
			throw new IllegalArgumentException(flag);
		}

		return flag;
	}
	
	public String CheckValidate(MamDeviceStopEvent devEvent) {
		String flag = CommonMessage.Ok;
		if (AppConst.dictEvent.size()<=0){
			List<MamEvents> lstEvents =MAM_Event_Server.findAll();
			AppConst.initEvent(lstEvents);
		}
		if (devEvent.getEventName()==null)
		{
			flag = EventMessage.EventNull;
			return flag;
		}
		devEvent.setEventName(devEvent.getEventName().toUpperCase());
		if (!devEvent.getEventName().contains("STOP")){
			flag = "eventName invalid.";
			return flag;
		}
		Integer eventId = AppConst.getEventId(devEvent.getEventName());
		if (eventId<0)
		{
			flag = EventMessage.EventNotExist;
			return flag;
		}
		devEvent.setEventId(eventId); 
		if (devEvent.getDevId() <=0)
		{
			flag = EventMessage.DevIdNotExist;
			return flag;
		} else {
			MdmDevice md = MDM_Device_Server.findByDevID(devEvent.getDevId());
			if (md==null)		
			{
				flag = EventMessage.DevIdNotExist;
				return flag;
			}
			else if (devEvent.getEventTime() == null)
			{
				flag = EventMessage.EventTimeNull;
				return flag;
			}		
			else if (devEvent.getEventSend() == null)
			{
				flag = EventMessage.SendTimeNull;
				return flag;
			}
			else if (devEvent.getEventSend().before(devEvent.getEventTime()))
			{
				flag = EventMessage.WrongTime;
				return flag;
			}
			else if (devEvent.getstopRunId()==null)
			{
				flag = EventMessage.Missingstoprun;
				return flag;
			}
			else if (devEvent.getrunId()==null)
			{
				flag = EventMessage.MissingRun;
				return flag;
			}
			else if (devEvent.getrunRoute()==null)
			{
				flag = EventMessage.MissingRunRoute;
				return flag;
			}
		}
		return flag;
	}
	public Specification<NoneMamDeviceEvent> BuildSpecification(List<SearchObject> lstsearch) {

		if (lstsearch.isEmpty())
			return new Specification<NoneMamDeviceEvent>() {
				@Override
				public Predicate toPredicate(Root<NoneMamDeviceEvent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					return null;
				}
			};

		return new Specification<NoneMamDeviceEvent>() {
			@Override
			public Predicate toPredicate(Root<NoneMamDeviceEvent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				for (SearchObject sub_lstsearch : lstsearch) {

					List<Predicate> sub_predicates = new ArrayList<Predicate>();
					//for (SearchObject search : sub_lstsearch) {
						Predicate condition = sub_lstsearch.BuildCondition(root, cb);
						if (condition != null)
							sub_predicates.add(condition);
					//}
					predicates.add(cb.and(sub_predicates.toArray(new Predicate[0])));
				}

				return cb.and(predicates.toArray(new Predicate[0]));
			}
		};
	}
	public Specification<NoneMamDeviceStopEvent> BuildSpecificationStop(List<SearchObject> lstsearch) {

		if (lstsearch.isEmpty())
			return new Specification<NoneMamDeviceStopEvent>() {
				@Override
				public Predicate toPredicate(Root<NoneMamDeviceStopEvent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					return null;
				}
			};

		return new Specification<NoneMamDeviceStopEvent>() {
			@Override
			public Predicate toPredicate(Root<NoneMamDeviceStopEvent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				for (SearchObject sub_lstsearch : lstsearch) {

					List<Predicate> sub_predicates = new ArrayList<Predicate>();
					//for (SearchObject search : sub_lstsearch) {
						Predicate condition = sub_lstsearch.BuildCondition(root, cb);
						if (condition != null)
							sub_predicates.add(condition);
					//}
					predicates.add(cb.and(sub_predicates.toArray(new Predicate[0])));
				}

				return cb.and(predicates.toArray(new Predicate[0]));
			}
		};
	}
	public Specification<NoneMamDeviceRunEvent> BuildSpecificationRun(List<SearchObject> lstsearch) {

		if (lstsearch.isEmpty())
			return new Specification<NoneMamDeviceRunEvent>() {
				@Override
				public Predicate toPredicate(Root<NoneMamDeviceRunEvent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					return null;
				}
			};

		return new Specification<NoneMamDeviceRunEvent>() {
			@Override
			public Predicate toPredicate(Root<NoneMamDeviceRunEvent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				for (SearchObject sub_lstsearch : lstsearch) {

					List<Predicate> sub_predicates = new ArrayList<Predicate>();
					//for (SearchObject search : sub_lstsearch) {
						Predicate condition = sub_lstsearch.BuildCondition(root, cb);
						if (condition != null)
							sub_predicates.add(condition);
					//}
					predicates.add(cb.and(sub_predicates.toArray(new Predicate[0])));
				}

				return cb.and(predicates.toArray(new Predicate[0]));
			}
		};
	}
	public List<SearchObject> FindDevIdBySerialNumber(List<SearchObject> lstsearch) {
		for (SearchObject search : lstsearch) {
			if(search.column.equals("serialNumber")) {				
				search.column = "mdmDevices.devSerialnumber";				
			}
		}
		return lstsearch;
	}
	
	public void SendManualEmbarkDisembark(MamDeviceStudentEvent devstuEvent) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		ManualEmbarkDisembark manualBarkDisembark = new ManualEmbarkDisembark();			
		String TagId = devstuEvent.gettagId().toString().trim();
		// Decode data on other side, by processing encoded data
		byte[] valueDecoded =  Base64.getDecoder().decode(TagId);
		String newTag = new String(valueDecoded);
		String StudentDistrictId= newTag;
		Date curent = new Date();
		boolean unknown = false;
		manualBarkDisembark.setEventId(2160);
		// convert to UTC with timezone
		Setting objTimeZone = this.settingRepository.findByName("TimeZone");
        String skey="GMT";
        if (objTimeZone.getValue().indexOf("GMT") == -1) {
            skey = "US/" + objTimeZone.getValue();
        }
		manualBarkDisembark.setEventtime(DateFunction.convertToUTC(devstuEvent.getEventTime(),skey));
		Map<String, Double> location = Maps.newHashMap();
		location.put("longitude", devstuEvent.getLongitude());
		location.put("latitude", devstuEvent.getLatitude());
		manualBarkDisembark.setLocation(location);
		if(devstuEvent.getEventName().contains("DISEMBARK")) {
			manualBarkDisembark.setScanType("DISEMBARK");
		}
		else
		{
			manualBarkDisembark.setScanType("EMBARK");
		}
		//find student from tag
		List<StudentTagIdmappings> studentTags = studentTagIDMappingRepository.findBytagId(newTag);
		StudentTagIdmappings studentTag =null;
		if (studentTags.size()>0)
			studentTag = studentTags.get(0);
		if (studentTag!=null){
			Integer intStudentDistrictId = Integer.parseInt(studentTag.getDistrictId().trim());
			StudentDistrictId = intStudentDistrictId.toString();
		}else
			unknown=true;
		if(StudentDistrictId!=null)
		{			
			manualBarkDisembark.setStudentDistrictId(StudentDistrictId);
			VehicleGpsunitMappings unitIDObject = vehicleGpsunitMappingRepository.findByBusNumber(devstuEvent.getBusId());
			if(unitIDObject!= null && unitIDObject.getUnitId().trim().length() >0) {
				manualBarkDisembark.setUnitid(unitIDObject.getUnitId().trim());
			}
		}
		if (com.APIMM.util.CommonFunction.tenantId != null && !com.APIMM.util.CommonFunction.tenantId.equals("")) {
			
			if (com.APIMM.util.JWT.expiredDatetime == null || com.APIMM.util.JWT.expiredDatetime.before(curent)) {
				com.APIMM.util.JWT.gettoken();
			}
			List<ManualEmbarkDisembark> manualBarkDisembarks = new ArrayList<ManualEmbarkDisembark>();
			String jsonInString="";
			try {
				Vehicles v = (Vehicles) com.APIMM.util.CommonFunction.hVeh.get(devstuEvent.getBusId());
				if( (v!=null))
				{
					manualBarkDisembark.setVin(v.getvin());
				}
				else {
					manualBarkDisembark.setVin("");
				}

				ObjectMapper mapper = new ObjectMapper();				
				manualBarkDisembarks.add(manualBarkDisembark);		

				String transactionUrl = env.getProperty("urlpp");// "api/v1/scan-events";
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.set("ApiKey", com.APIMM.util.CommonFunction.ClientId);
				headers.set("Authorization", com.APIMM.util.JWT.tokenType + " " + com.APIMM.util.JWT.accessToken);					

				jsonInString = mapper.writeValueAsString(manualBarkDisembarks);
				HttpEntity<String> entity = new HttpEntity<String>(jsonInString, headers);
				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<Object> aa = restTemplate.exchange(transactionUrl, HttpMethod.POST, entity,
						Object.class);
				String response= aa.getStatusCode().toString();
				if(response.contains("200")) {
					logger.info(CommonFunction.LogSave(timekey, "manual "+ manualBarkDisembark.getScanType()+ " to Parent Portal successful", "Parent Portal", ""+manualBarkDisembark.getStudentDistrictId()+", event= ["+manualBarkDisembark.getScanType()+"] "));
				}
				else {
					containerService.addContainer(jsonInString, "parentportal");
					TimerResend();					
					logger.info(CommonFunction.LogSave(timekey, "manual "+ manualBarkDisembark.getScanType()+ " to Parent Portal unsuccessful", "Parent Portal", ""+manualBarkDisembark.getStudentDistrictId()+", event= ["+manualBarkDisembark.getScanType()+"] "));
				}
			}
			catch(Exception ex)
			{	
				containerService.addContainer(jsonInString, "parentportal");
				TimerResend();				
				logger.info(CommonFunction.LogError(timekey, "manual "+ manualBarkDisembark.getScanType()+ " to Parent Portal error", "Parent Portal", ""+manualBarkDisembark.getStudentDistrictId()+", event= ["+manualBarkDisembark.getScanType()+"] ",ex.toString()));		
			}
		}	
	}
	public void TimerResend() {
		Integer intervalTime = 60;
		Integer intervalSetting = Interval_Time_Cloud();
		if(intervalSetting >= 0 ) {
			intervalTime = intervalSetting;
		}
		TimerTask task = new TimerTask() {
		      @Override
		      public void run() {
		    	  try {
		    		  Integer intervalTime = Interval_Time_Cloud();
		    		  List<AthenaContainer> objs = containerService.findContainer("parentportal");
		    		  if(objs.size() <= 0 || intervalTime <= 0) {
		    			  System.out.println("Retry to Cloud Parent Portal emtpy !!!");
		    			  cancel();
		    		  }
		    		  else
		    		  {	// task to run goes here
		    			  resendCloudParentPortal(objs);
		    			  System.out.println("Retry send all to Cloud Parent Portal !!!");
		    		  }
		    	  }
		    	  catch(Exception ex) {
		    		  System.out.println("Retry to Container !!!");
		    		  TimerResend();
		    		  cancel();
		    	  }  			  		
		      }
		    };		
		Timer timer = new Timer();
		long delay = 30 * 1000;
		long intervalPeriod = intervalTime * 1000; 
		 // schedules the task to be run in an interval 
		timer.scheduleAtFixedRate(task, delay,
		                                intervalPeriod);		 
	}
	public void resendCloudParentPortal(List<AthenaContainer> lstContainer) {
		Date curent = new Date();
		if (com.APIMM.util.CommonFunction.tenantId != null && !com.APIMM.util.CommonFunction.tenantId.equals("")) {
			if (com.APIMM.util.JWT.expiredDatetime == null || com.APIMM.util.JWT.expiredDatetime.before(curent)) {
				com.APIMM.util.JWT.gettoken();
			}
			try {		
				String transactionUrl = env.getProperty("urlpp");// "api/v1/scan-events";
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.set("ApiKey", com.APIMM.util.CommonFunction.ClientId);
				headers.set("Authorization", com.APIMM.util.JWT.tokenType + " " + com.APIMM.util.JWT.accessToken);
				for (AthenaContainer container : lstContainer){
					String jsonInString = container.getContainer();
					HttpEntity<String> entity = new HttpEntity<String>(jsonInString, headers);
					RestTemplate restTemplate = new RestTemplate();
					ResponseEntity<Object> aa = restTemplate.exchange(transactionUrl, HttpMethod.POST, entity,
							Object.class);
					String response= aa.getStatusCode().toString();
					if(response.contains("200")) {
						containerService.deleteContainer(container.getId());
						logger.info("resend to parent portal cloud successful: "+container.getId());
					}
					else {
						logger.info("resend to parent portal cloud unsuccessful: "+container.getId());
					}
				}
			}
			catch(Exception ex)
			{	
				logger.info("resend to parent portal cloud error : "+ ex.toString());
			}	
		}
	}
	public Integer Interval_Time_Cloud () {			
			Integer ValueSetting =  Integer.valueOf(mamSettingRepository.findSettingbyName("INTERVAL_ATHENA_RETRY"));
			return ValueSetting;
	    }
	public String AddStudentEventDP(MamDeviceStudentEvent devstuEvent) {		
		String flag = CommonMessage.False;
		try {
			Date date = new Date();				
			Timestamp dateReceived = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));		
			Timestamp eventTime = new Timestamp(devstuEvent.getEventTime().getTime());
			Timestamp eventSend = new Timestamp(devstuEvent.getEventSend().getTime());
			devstuEvent.seteventWriteTime(dateReceived);
			devstuEvent.setEventTime(eventTime);
			devstuEvent.setEventSend(eventSend);
									
			String TagId = devstuEvent.gettagId().toString().trim();
			// Decode data on other side, by processing encoded data
			byte[] valueDecoded =  Base64.getDecoder().decode(TagId);
			String newTag = new String(valueDecoded);
			String StudentId = "";			
			//find student from tag
			List<StudentTagIdmappings> studentTags = studentTagIDMappingRepository.findBytagId(newTag);
			StudentTagIdmappings studentTag =null;
			if (studentTags.size()>0)
				studentTag = studentTags.get(0);
			if (studentTag!=null){
				Integer intStudentId = Integer.parseInt(studentTag.getEdulogStudentId().trim());
				StudentId = intStudentId.toString();
			}
			if(StudentId != null ) {
				devstuEvent.setstudentId(StudentId);
			}
			
			flag = CheckValidate(devstuEvent);
			if (flag == CommonMessage.Ok) {							
				MamDeviceStudentEvent addDeviceStudentEvent = MDM_DeviceStuEvent_Server.save(devstuEvent);				
				flag = String.valueOf(addDeviceStudentEvent.getdeviceStuEventId());
			}else{
				throw new IllegalArgumentException(flag);
			}

		} catch (Exception e) {
			flag = e.getMessage();// CommonMessage.False;
			throw new IllegalArgumentException(flag);
		}
		
		return flag;
	}
}
