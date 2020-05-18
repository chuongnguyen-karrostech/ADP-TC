package com.ADP.controller;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ADP.service.MMLogsService;
import com.ADP.util.DateFunction;
import com.ADP.util.SearchObject;
import com.ADP.util.Utilities;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value="/issue")
@CrossOrigin(origins = "*")
public class IssueController {
	private final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	MMLogsService mmLogsService;
	
	@GetMapping(value="/punchoffline" )
	@ResponseBody	
	public Object getPunchOffline(Pageable pageable){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get files", "", ""));
		try
		{
			Object r = mmLogsService.getPunchOffline(pageable);			
			logger.info(Utilities.LogReturn(timekey, "get punch log offline issue", "", ""));
			return r;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get punch log offline issue error ", "", "", ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	@PostMapping(value="/punchoffline/search", produces = "application/json" ) // search punch log offline by mdm web
	@ResponseBody	
	public Object searchPunchOffline(@RequestBody List<SearchObject> lstSearch, Pageable pageRequest){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get files", "", ""));
		ObjectMapper mapper = new ObjectMapper();		
		String strObject = "";
		try
		{			
			strObject = mapper.writeValueAsString(lstSearch);
			logger.info(Utilities.LogSave(timekey, "search punch offline logs:", "punch", strObject));
			return mmLogsService.searchPunchOffline(lstSearch, pageRequest);
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get punch offline logs error: ", "", strObject, ex.toString()));
			throw new IllegalArgumentException(ex.toString());			
		}				
	}
	@GetMapping(value="/getnolocation" )
	@ResponseBody	
	public Object getNoLocation(Pageable pageable){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get files", "", ""));
		try
		{
			Object r = mmLogsService.getNoLocation(pageable);			
			logger.info(Utilities.LogReturn(timekey, "get no location issue", "", ""));
			return r;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get no location issue error ", "", "", ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	@PostMapping(value="/getnolocation/search", produces = "application/json" ) // search punch log offline by mdm web
	@ResponseBody	
	public Object searchNoLocation(@RequestBody List<SearchObject> lstSearch, Pageable pageRequest){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get files", "", ""));
		ObjectMapper mapper = new ObjectMapper();		
		String strObject = "";
		try
		{			
			strObject = mapper.writeValueAsString(lstSearch);
			logger.info(Utilities.LogSave(timekey, "search no location issue:", "no location issue", strObject));
			return mmLogsService.searchNoLocation(lstSearch, pageRequest);
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get no location issue error: ", "", strObject, ex.toString()));
			throw new IllegalArgumentException(ex.toString());			
		}				
	}
	@GetMapping(value="/getcontainers" )
	@ResponseBody	
	public Object getContainers(){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get files", "", ""));		
		try
		{
			mmLogsService.resendAthena();
			Object r = mmLogsService.getContainer()	;		
			logger.info(Utilities.LogReturn(timekey, "get no location issue", "", ""));
			return r;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get no location issue error ", "", "", ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
}
