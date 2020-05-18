package com.ADP.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ADP.etext.model.DriverPayrollMessage;
import com.ADP.service.DriverPayrollMessageService;
import com.ADP.util.DateFunction;
import com.ADP.util.SearchObject;
import com.ADP.util.Utilities;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/punch")
@CrossOrigin(origins = "*")
@Api(value="device", description="Punch In/ Punch As")
public class PunchController {
	private final Log logger = LogFactory.getLog(this.getClass());	
	
	@Autowired
	DriverPayrollMessageService driverPayrollMessageService;	
	
	@PostMapping(value="/in")
	@ApiOperation(value = "Punch In", response = Iterable.class)
	@ResponseBody
	public ResponseEntity<DriverPayrollMessage> in(@RequestBody DriverPayrollMessage obj){
		long timekey = DateFunction.TimeKey();
		ObjectMapper mapper = new ObjectMapper();		
		String strObject = "";
		//logger.info(Utilities.LogReceive(timekey, "PunchIn", "driver id", obj.getDriverID()));		
		try
		{
			
			Object objChkPunch = driverPayrollMessageService.findDuplicatepunchLog(obj); 
			strObject = mapper.writeValueAsString(obj);
			if(mapper.writeValueAsString(objChkPunch).contains("true")) {
				//exist
				ResponseEntity<DriverPayrollMessage> res = new ResponseEntity<DriverPayrollMessage>(obj, HttpStatus.CONFLICT);			
				logger.info(Utilities.LogSave(timekey, "PunchIn is duplicated in Punch Log:", "driver id",strObject + " "));
				return res;
			}else {
				//non exist
				logger.info(Utilities.LogSave(timekey, "PunchIn", "driver id",strObject));
				Object objPunch = driverPayrollMessageService.punchLog(obj); // send punch log
				if(mapper.writeValueAsString(objPunch).contains("true")) {
					logger.info(Utilities.LogSave(timekey, "PunchIn Log successfully:", "driver id",strObject + " " + objPunch.toString()));
				}
				else {
					logger.info(Utilities.LogSave(timekey, "PunchIn Log Error:", "driver id",strObject + " " + objPunch.toString()));
				}
			}
//			if(checkDuplicated) // check duplicate punch in sqlserver
//			{
//				ResponseEntity<DriverPayrollMessage> res = new ResponseEntity<DriverPayrollMessage>(obj, HttpStatus.CONFLICT);			
//				logger.info(Utilities.LogSave(timekey, "PunchIn is duplicated in database:", "driver id",strObject + " "));
//				return res;
//			}			
			driverPayrollMessageService.savePunchIn(obj);
			logger.info(Utilities.LogSave(timekey, "PunchIn", "driver id",strObject));
//			Object objPunch = driverPayrollMessageService.punchLog(obj); // send punch log
//			if(mapper.writeValueAsString(objPunch).contains("true")) {
//				logger.info(Utilities.LogSave(timekey, "PunchIn Log successfully:", "driver id",strObject + " " + objPunch.toString()));
//			}
//			else {
//				logger.info(Utilities.LogSave(timekey, "PunchIn Log Error:", "driver id",strObject + " " + objPunch.toString()));
//			}			
			ResponseEntity<DriverPayrollMessage> res = new ResponseEntity<DriverPayrollMessage>(obj, HttpStatus.CREATED);			
			return res;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "PunchIn", "driver id", strObject, ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}
	}	
	
	@PostMapping(value="/out")
	@ApiOperation(value = "Punch Out", response = Iterable.class)
	@ResponseBody
	public ResponseEntity<DriverPayrollMessage> out(@RequestBody DriverPayrollMessage obj){
		long timekey = DateFunction.TimeKey();
		ObjectMapper mapper = new ObjectMapper();		
		String strObject = "";
		//logger.info(Utilities.LogReceive(timekey, "PunchOut", "driver id", obj.getDriverID()));
		try
		{
			strObject = mapper.writeValueAsString(obj);
			//obj.setActivityCode(obj.getActivityCode()+1); // set punch out
			Object objChkPunch = driverPayrollMessageService.findDuplicatepunchLog(obj); 
			strObject = mapper.writeValueAsString(obj);
			if(mapper.writeValueAsString(objChkPunch).contains("true")) {
				//exist
				ResponseEntity<DriverPayrollMessage> res = new ResponseEntity<DriverPayrollMessage>(obj, HttpStatus.CONFLICT);			
				logger.info(Utilities.LogSave(timekey, "PunchOut is duplicated in Punch Log:", "driver id",strObject + " "));
				return res;
			}else {
				//non exist
				logger.info(Utilities.LogSave(timekey, "PunchOut", "driver id",strObject));
				Object objPunch = driverPayrollMessageService.punchLog(obj); // send punch log
				if(mapper.writeValueAsString(objPunch).contains("true")) {
					logger.info(Utilities.LogSave(timekey, "PunchIn Log successfully:", "driver id",strObject + " " + objPunch.toString()));
				}
				else {
					logger.info(Utilities.LogSave(timekey, "PunchIn Log Error:", "driver id",strObject + " " + objPunch.toString()));
				}
			}
//			Boolean checkDuplicated = driverPayrollMessageService.findDuplicate(obj);
//			if(checkDuplicated) // check duplicate punch in sqlserver
//			{
//				ResponseEntity<DriverPayrollMessage> res = new ResponseEntity<DriverPayrollMessage>(obj, HttpStatus.CONFLICT);			
//				logger.info(Utilities.LogSave(timekey, "PunchOut is duplicated in database:", "driver id",strObject + " "));
//				return res;
//			}
//			else {
//				obj.setActivityCode(obj.getActivityCode()-1);
//			}
			driverPayrollMessageService.savePunchOut(obj);
			logger.info(Utilities.LogSave(timekey, "PunchOut", "driver id", strObject));
			
//			Object objPunch = driverPayrollMessageService.punchLog(obj); // send punch log
//			if(mapper.writeValueAsString(objPunch).contains("true")) {
//				logger.info(Utilities.LogSave(timekey, "PunchOut Log successfully:", "driver id",strObject + " " + objPunch.toString()));
//			}
//			else {
//				logger.info(Utilities.LogSave(timekey, "PunchOut Log Error:", "driver id",strObject + " " + objPunch.toString()));
//			}
			ResponseEntity<DriverPayrollMessage> res = new ResponseEntity<DriverPayrollMessage>(obj, HttpStatus.CREATED);			
			return res;			
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "PunchOut", "driver id", strObject, ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}
	}	
	@PutMapping(value="/comment")
	@ApiOperation(value = "Punch Comment", response = Iterable.class)
	@ResponseBody
	public ResponseEntity<DriverPayrollMessage> comment(@RequestBody DriverPayrollMessage obj){
		long timekey = DateFunction.TimeKey();
		try 
		{ 
			driverPayrollMessageService.updatePunchComment(obj);
			ResponseEntity<DriverPayrollMessage> res = new ResponseEntity<DriverPayrollMessage>(obj, HttpStatus.OK);
			logger.info(Utilities.LogSave(timekey, "Punch comment", "driver id",obj.getDriverID())); 
			return res; 
		} 
		catch (Exception ex) 
		{
			logger.info(Utilities.LogError(timekey, "Punch comment", "driver id",obj.getDriverID(), ex.toString())); 
			throw new IllegalArgumentException(ex.toString()); 
		}
		 
	}	
	
	@PostMapping(value="/load", consumes = "application/json", produces = "application/json")
	@ApiOperation(value = "Punch Load", response = Iterable.class)
	@ResponseBody
	public DriverPayrollMessage loadPunch(@RequestBody DriverPayrollMessage obj){
		long timekey = DateFunction.TimeKey();
		try 
		{ 
			
			List<DriverPayrollMessage> res = driverPayrollMessageService.loadPunch(obj);
			logger.info(Utilities.LogSave(timekey, "Punch load", "driver id",obj.getDriverID())); 
			return res.get(0); 
		} 
		catch (Exception ex) 
		{
			logger.info(Utilities.LogError(timekey, "Punch load", "driver id",obj.getDriverID(), ex.toString())); 
			throw new IllegalArgumentException(ex.toString()); 
		}
		 
	}		
	@PutMapping(value="/updatecomment")
	@ApiOperation(value = "Punch Comment", response = Iterable.class)
	@ResponseBody
	public ResponseEntity<DriverPayrollMessage> updatecomment(@RequestBody List<DriverPayrollMessage> obj){
		long timekey = DateFunction.TimeKey();
		try 
		{ 
			if(!obj.isEmpty()) {
				for (DriverPayrollMessage item : obj)
				{
					driverPayrollMessageService.updatePunchComment(item);
					//ResponseEntity<DriverPayrollMessage> res = new ResponseEntity<DriverPayrollMessage>(item, HttpStatus.OK);
					logger.info(Utilities.LogSave(timekey, "Punch comment", "driver id",item.getDriverID())); 
					//return res; 
				}
			}			
			ResponseEntity<DriverPayrollMessage> res = new ResponseEntity<DriverPayrollMessage>(obj.get(0), HttpStatus.OK);
			//logger.info(Utilities.LogSave(timekey, "Punch comment", "driver id",obj.getDriverID())); 
			return res;
		} 
		catch (Exception ex) 
		{
			logger.info(Utilities.LogError(timekey, "Punch comment", "driver id",obj.get(0).getDriverID(), ex.toString())); 
			throw new IllegalArgumentException(ex.toString()); 
		}
		 
	}
	
}
