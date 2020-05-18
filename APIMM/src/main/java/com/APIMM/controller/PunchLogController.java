package com.APIMM.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.APIMM.mam.model.NoneMamDeviceMessage;
import com.APIMM.mam.model.NonePunchLog;
import com.APIMM.mam.model.PunchLog;
import com.APIMM.service.PunchLogService;
import com.APIMM.util.CommonFunction;
import com.APIMM.util.SearchObject;
import com.APIMM.util.message.ActionReturn;
import com.APIMM.util.message.CommonMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin("*")
@RequestMapping("/mam")
public class PunchLogController {
	private final Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	PunchLogService punchLogSer;
	
	@RequestMapping(value = "/punch/add", method = RequestMethod.POST)
	@ResponseBody
	public ActionReturn addPunchLog(@RequestBody PunchLog punch) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();		
		// route.getrouteNumber()));
		ObjectMapper mapper = new ObjectMapper();		
		String strObject = "";
		ActionReturn actreturn = new ActionReturn(null);
		try {
			strObject = mapper.writeValueAsString(punch);			
			actreturn = ActionReturn.Response(punchLogSer.addPunchLog(punch));
			if(actreturn.result.equalsIgnoreCase(CommonMessage.Ok))
			{
			
				logger.info(CommonFunction.LogReceive(timekey, "add punch log", "driver id",punch.getDriverID()));
			}
			else {
				logger.info(CommonFunction.LogReceive(timekey, "add punch log error: ", "driver id",strObject+ " " +actreturn.result ));
			}
			
		}		
		catch(Exception e) {
			logger.info(CommonFunction.LogReceive(timekey, "add punch log error: ", strObject+ " " +actreturn.result ,e.toString()));
			
		}
		return actreturn;
	}
	@RequestMapping(value = "/punch/checkexist", method = RequestMethod.POST)
	@ResponseBody
	public ActionReturn checkExistPunchLog(@RequestBody PunchLog punch) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();		
		// route.getrouteNumber()));
		ObjectMapper mapper = new ObjectMapper();		
		String strObject = "";
		ActionReturn actreturn = new ActionReturn(null);
		try {
			strObject = mapper.writeValueAsString(punch);			
			actreturn = ActionReturn.Response(punchLogSer.checkPunchLog(punch));
			if(actreturn.result.equalsIgnoreCase(CommonMessage.Ok))
			{
			
				logger.info(CommonFunction.LogReceive(timekey, "check punch log", "driver id",strObject + " is exist"));
			}
			else {
				logger.info(CommonFunction.LogReceive(timekey, "check punch log error: ", "driver id",strObject+ " isn't exist" ));
			}
			
		}		
		catch(Exception e) {
			logger.info(CommonFunction.LogReceive(timekey, "check punch log error: ", strObject+ " " +actreturn.result ,e.toString()));
			
		}
		return actreturn;
	}
	@RequestMapping(value = "/punch/getall", method = RequestMethod.GET)
	@ResponseBody
	public Page<NonePunchLog> getAllPunchLog(Pageable pageable) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		logger.info(CommonFunction.LogReturn(timekey, "get all punch log", "punch log",""));
		Page<NonePunchLog> pagePunchLog = punchLogSer.getAllPunchLog(pageable);
		return pagePunchLog;
		
	}
	@RequestMapping(value = "/punch/search", method = RequestMethod.POST)
	@ResponseBody
	public Page<NonePunchLog> Get(@RequestBody List<SearchObject> search, Pageable pageRequest) {
		Page<NonePunchLog> searchResultPage = punchLogSer.Search(search, pageRequest);
		return searchResultPage;
	}
	
}
