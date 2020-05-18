package com.APIMM.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.APIMM.configurations.APIMMConfig;
import com.APIMM.mam.model.APIConfig;
import com.APIMM.mam.model.MamDevRouteHistorical;
import com.APIMM.mam.model.MamDeviceEvent;
import com.APIMM.mam.model.MamDeviceMessage;
import com.APIMM.mam.model.MamDeviceRawEvent;
import com.APIMM.mam.model.MamDeviceRunEvent;
import com.APIMM.mam.model.MamDeviceStopEvent;
import com.APIMM.mam.model.MamDeviceStudentEvent;
import com.APIMM.mam.model.MamSwipeEvent;
import com.APIMM.mam.model.MdmDevice;
import com.APIMM.mam.model.NoneMamDeviceEvent;
import com.APIMM.mam.model.NoneMamDeviceRunEvent;
import com.APIMM.mam.model.NoneMamDeviceStopEvent;
import com.APIMM.mam.repository.APIConfigRepository;
import com.APIMM.service.MamEventService;
import com.APIMM.service.MamService;
import com.APIMM.util.CommonFunction;
import com.APIMM.util.SearchObject;
import com.APIMM.util.message.ActionReturn;
import io.swagger.annotations.Api;

@RestController
@RequestMapping(value="/mam")
@CrossOrigin(origins = "*")
@Api(value="mam")
public class MamDeviceEventController {
	private final Log logger = LogFactory.getLog(this.getClass());
	@Autowired
    MamEventService mamEventService;
	@Autowired
	APIConfigRepository apiConfigRepository;
	
	@RequestMapping(value = "/event/add", method = RequestMethod.POST)
	@ResponseBody
	public ActionReturn Add(@RequestBody MamDeviceEvent devEvent) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();		
		//logger.info(CommonFunction.LogReceive(timekey, "add device event", "dev", ""+devEvent.getDevId()+", event= ["+devEvent.getEventName()+"] "));
		ActionReturn actreturn = new ActionReturn(null);
		actreturn = ActionReturn.Response(mamEventService.AddEvent(devEvent));
		logger.info(CommonFunction.LogSave(timekey, "add device event", "dev", ""+devEvent.getDevId()+", event= ["+devEvent.getEventName()+"] "));		
		return actreturn;
		
	}
	
	@RequestMapping(value = "/rawevent/add", method = RequestMethod.POST)
	@ResponseBody
	public ActionReturn AddRaw(@RequestBody MamDeviceRawEvent devEvent) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		//logger.info(CommonFunction.LogReceive(timekey, "add device raw event", "dev", ""+devEvent.getDevId()+", event= ["+devEvent.getEventName()+"] "));		
		ActionReturn actreturn = new ActionReturn(null);
		actreturn = ActionReturn.Response(mamEventService.AddRawEvent(devEvent));
		logger.info(CommonFunction.LogSave(timekey, "add device raw event", "dev", ""+devEvent.getDevId()+", event= ["+devEvent.getEventName()+"] "));		
		return actreturn;		
	}
	@RequestMapping(value = "/runevent/add", method = RequestMethod.POST)
	@ResponseBody
	public ActionReturn AddRun(@RequestBody MamDeviceRunEvent devRunEvent) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		//logger.info(CommonFunction.LogReceive(timekey, "add device raw event", "dev", ""+devRunEvent.getDevId()+", event= ["+devRunEvent.getEventName()+"] "));
		ActionReturn actreturn = new ActionReturn(null);
		actreturn = ActionReturn.Response(mamEventService.AddRunEvent(devRunEvent));
		logger.info(CommonFunction.LogSave(timekey, "add device run event", "dev", ""+devRunEvent.getDevId()+", event= ["+devRunEvent.getEventName()+"] "));		
		return actreturn;		
	}
	@RequestMapping(value = "/stopevent/add", method = RequestMethod.POST)
	@ResponseBody
	public ActionReturn AddStop(@RequestBody MamDeviceStopEvent devStopEvent) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		//logger.info("[" + timekey + "] add device raw event.");
		ActionReturn actreturn = new ActionReturn(null);
		actreturn = ActionReturn.Response(mamEventService.AddStopEvent(devStopEvent));
		logger.info(CommonFunction.LogSave(timekey, "add device stop event", "dev", ""+devStopEvent.getDevId()+", event= ["+devStopEvent.getEventName()+"] "));
		return actreturn;		
	}
	@RequestMapping(value = "/studentevent/add", method = RequestMethod.POST)
	@ResponseBody
	public ActionReturn AddRaw(@RequestBody MamDeviceStudentEvent devstuEvent) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		APIConfig apiconfig = apiConfigRepository.findSettingbyName("RFID_TO_ETGPS");		
		try {
			APIMMConfig.RFID_TO_ETGPS = Integer.parseInt(apiconfig.getSetting_value());	}
		catch( Exception ex) {
			APIMMConfig.RFID_TO_ETGPS = 0;
			logger.info("[" + timekey + "] an error from APIConfig: " + ex.toString());
		}
		
		if (devstuEvent.getstudentId()!=null && !devstuEvent.getstudentId().trim().equals("")){
			//logger.info("[" + timekey + "] add device raw event.");
			ActionReturn actreturn = new ActionReturn(null);
			actreturn = ActionReturn.Response(mamEventService.AddStudentEvent(devstuEvent));
			logger.info(CommonFunction.LogSave(timekey, "add device student event", "dev", ""+devstuEvent.getDevId()+", event= ["+devstuEvent.getEventName()+"] "));
			return actreturn;		
		}else{
			ActionReturn actreturn = new ActionReturn(null);
			MamSwipeEvent devswipeEvent = devstuEvent.toSwipeEvent();
			if(devstuEvent.getswipeCardType() == null || devstuEvent.getswipeCardType() < 1 || devstuEvent.getswipeCardType() > 1 || APIMMConfig.RFID_TO_ETGPS == 1)  // chuong , manual input RFID not save student
			{	
				mamEventService.SendManualEmbarkDisembark(devstuEvent);
				mamEventService.AddStudentEventDP(devstuEvent);				
				actreturn = ActionReturn.Response(mamEventService.AddSwipeEvent(devswipeEvent));				
			}
			logger.info(CommonFunction.LogSave(timekey, "add device student event", "dev", ""+devswipeEvent.getDevId()+", TagID= ["+devswipeEvent.gettagId()+"] "));
			return actreturn;
		}
	}
	@RequestMapping(value = "/studentswipecard/add", method = RequestMethod.POST)
	@ResponseBody
	public ActionReturn AddRaw(@RequestBody MamSwipeEvent devswipeEvent) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		ActionReturn actreturn = new ActionReturn(null);
		actreturn = ActionReturn.Response(mamEventService.AddSwipeEvent(devswipeEvent));
		logger.info(CommonFunction.LogSave(timekey, "add device student event", "dev", ""+devswipeEvent.getDevId()+", TagID= ["+devswipeEvent.gettagId()+"] "));
		return actreturn;		
	}
	@RequestMapping(value = "/deviceevent", method = RequestMethod.GET)
	@ResponseBody
	public Page<NoneMamDeviceEvent> findAll(Pageable pageable) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();

		Page<NoneMamDeviceEvent> results = mamEventService.getAllDevEvent(pageable);
		logger.info(CommonFunction.LogSave(timekey, "get all devices event", "", ""));
		return results;
	}
	
	@RequestMapping(value = "/stopevent", method = RequestMethod.GET)
	@ResponseBody
	public Page<NoneMamDeviceStopEvent> findAllStopEvent(Pageable pageable) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();

		Page<NoneMamDeviceStopEvent> results = mamEventService.getAllStopEvent(pageable);
		logger.info(CommonFunction.LogSave(timekey, "get all devices stop event", "", ""));
		return results;
	}
	@RequestMapping(value = "/runevent", method = RequestMethod.GET)
	@ResponseBody
	public Page<NoneMamDeviceRunEvent> findAllRunEvent(Pageable pageable) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();

		Page<NoneMamDeviceRunEvent> results = mamEventService.getAllRunEvent(pageable);
		logger.info(CommonFunction.LogSave(timekey, "get all devices run event", "", ""));
		return results;
	}
	@RequestMapping(value = "/deviceevent/search", method = RequestMethod.POST)	  
	@ResponseBody public Page<NoneMamDeviceEvent> Get(@RequestBody List<SearchObject> search, Pageable pageRequest) { 
		Page<NoneMamDeviceEvent> searchResultPage =	mamEventService.Search(search, pageRequest); 
		return searchResultPage; 
	}
	@RequestMapping(value = "/stopevent/search", method = RequestMethod.POST)	  
	@ResponseBody public Page<NoneMamDeviceStopEvent> GetStopEvent(@RequestBody List<SearchObject> search, Pageable pageRequest) { 
		Page<NoneMamDeviceStopEvent> searchResultPage =	mamEventService.SearchStopEvent(search, pageRequest); 
		return searchResultPage; 
	}
	
	@RequestMapping(value = "/runevent/search", method = RequestMethod.POST)	  
	@ResponseBody public Page<NoneMamDeviceRunEvent> GetRunEvent(@RequestBody List<SearchObject> search, Pageable pageRequest) { 
		Page<NoneMamDeviceRunEvent> searchResultPage =	mamEventService.SearchRunEvent(search, pageRequest); 
		return searchResultPage; 
	}
}