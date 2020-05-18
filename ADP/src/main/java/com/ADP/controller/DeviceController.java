package com.ADP.controller;

import java.util.List;

import javax.persistence.criteria.CommonAbstractCriteria;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.ADP.service.DeviceService;
import com.ADP.service.MMLogsService;
import com.ADP.util.DateFunction;
import com.ADP.util.SearchObject;
import com.ADP.util.Utilities;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/device")
@CrossOrigin(origins = "*")
@Api(value="device", description="Device")
public class DeviceController {
	private final Log logger = LogFactory.getLog(this.getClass());	
	
	@Autowired
	DeviceService deviceService;

	MMLogsService mmLogsService;
	
	@GetMapping(value="/devicemode/{macaddress}")
	@ApiOperation(value = "Check device", response = Iterable.class)
	@ResponseBody
	public Object[] getMacAddress(@PathVariable String macaddress){	
		long timekey = DateFunction.TimeKey();		
		//logger.info(Utilities.LogReceive(timekey, "MacAddress", "address", macaddress));
		try
		{
			Object[] objArr = deviceService.getDeviceMode(macaddress);
			logger.info(Utilities.LogReturn(timekey, "MacAddress", "address", macaddress));
			return objArr;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "MacAddress", "address", macaddress, ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}	
	@GetMapping(value="/devicemode/{macaddress:.+}/{serialNumber:.+}")
	@ApiOperation(value = "Check device", response = Iterable.class)
	@ResponseBody
	public Object[] getDevicebyMacAddressSerial(@PathVariable String macaddress,@PathVariable String serialNumber){	
		long timekey = DateFunction.TimeKey();		
		//logger.info(Utilities.LogReceive(timekey, "device ", "MacAddress", macaddress + ",and Serial =["+ serialNumber + "]")); 
		try
		{
			Object[] objArr = deviceService.getDeviceMode(macaddress,serialNumber);
			logger.info(Utilities.LogReturn(timekey, "device ", "MacAddress", macaddress + ",and Serial =["+ serialNumber + "]"));
			return objArr;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "device ", "MacAddress", macaddress + ",and Serial =["+ serialNumber + "]", ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}	
	@GetMapping(value="/appinfo/{appid}")
	@ApiOperation(value = "Application Info", response = Iterable.class)
	@ResponseBody
	public Object[] getAppInfo(@PathVariable int appid){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "AppInfo", "app info", appid + ""));
		try
		{		
			Object[] objArr = deviceService.getAppInfo(appid);
			logger.info(Utilities.LogReturn(timekey, "AppInfo", "app info", appid + ""));		
			return objArr;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "AppInfo", "app info", appid + "", ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}
	}	
//	@GetMapping(value="/app/{appid}" )
//	@ResponseBody	
//	public Object[] findAppbyID(@PathVariable int appid){
//		long timekey = DateFunction.TimeKey();
//		logger.info(Utilities.LogReceive(timekey, "App", "app id", appid + ""));
//		try
//		{
//			Object[] objArr = deviceService.findSettingbyApp(appid);
//			logger.info(Utilities.LogReturn(timekey, "App", "app id", appid + ""));
//			return objArr;
//		}
//		catch (Exception ex)
//		{
//			logger.info(Utilities.LogError(timekey, "App", "app id", appid + "", ex.toString()));
//			throw new IllegalArgumentException(ex.toString());
//		}		
//	}
	@GetMapping(value="/app/{appid}/{devid}" )
	@ResponseBody	
	public Object[] findSettingbyAppandDevice(@PathVariable int appid,@PathVariable int devid){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get Settings", "App and Device", appid + ", "+devid));
		try
		{
			Object[] objArr = deviceService.findSettingbyApp(appid);
			Object[] objArr2 = deviceService.findSettingbyDevice(appid,devid);
			Object[] r = new Object[objArr.length + objArr2.length];
	        System.arraycopy(objArr, 0, r, 0, objArr.length);
	        System.arraycopy(objArr2, 0, r, objArr.length, objArr2.length);
			logger.info(Utilities.LogReturn(timekey, "get Settings", "App and Device", appid + ", "+devid));
			return r;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get Settings", "App and Device", appid + ", "+devid, ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	@GetMapping(value="/appbysn/{appid}/{serialnumber}" )
	@ResponseBody	
	public Object[] findSettingbyAppandSerialNumber(@PathVariable int appid,@PathVariable String serialnumber){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get Settings", "App and Device", appid + ", "+devid));
		try
		{
			Object[] objArr = deviceService.findSettingbySerialNumber(appid, serialnumber);
			
			logger.info(Utilities.LogReturn(timekey, "get Settings", "App and Device", appid + ", "+ serialnumber));
			return objArr;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get Settings", "App and Device", appid + ", "+serialnumber, ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	@GetMapping(value="/files" )
	@ResponseBody	
	public Object[] getFile(){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get files", "", ""));
		try
		{
			Object[] r = deviceService.getfiles();			
			logger.info(Utilities.LogReturn(timekey, "get files", "", ""));
			return r;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get files", "", "", ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	@GetMapping(value="/messages" )
	@ResponseBody	
	public Object getMessage(Pageable pageable){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get files", "", ""));
		try
		{
			Object r = deviceService.getmessage(pageable);			
			logger.info(Utilities.LogReturn(timekey, "get message", "", ""));
			return r;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get message", "", "", ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	@RequestMapping(value = "/addmessage", method = RequestMethod.POST)
	@ResponseBody
	public Object AddRecentRoute(@RequestBody Object devMessage) {		
		long timekey = DateFunction.TimeKey();
		try
		{
			Object obj = deviceService.addMessage(devMessage);
			logger.info(Utilities.LogSave(timekey, "AddMessage", "message", devMessage.toString()));
			return obj;
		}
		catch (HttpServerErrorException ex)
		{
			String jsonString = ex.getResponseBodyAsString();
			try{
			   JSONObject jsonObject = new JSONObject(jsonString);
				String message = jsonObject.getString("message");
				logger.info(Utilities.LogError(timekey, "AddMessage", "message", devMessage.toString(), message));	
				throw new IllegalArgumentException(message);
			}catch (Exception e){
				logger.info(Utilities.LogError(timekey, "AddMessage", "message", devMessage.toString(), e.getMessage()));		
				throw new IllegalArgumentException(e.getMessage());
			}
		}		
	}
	@GetMapping(value="/deviceevent" )
	@ResponseBody	
	public Object getDeviceEvent(Pageable pageable){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get files", "", ""));
		try
		{
			Object r = deviceService.getDeviceEvent(pageable);			
			logger.info(Utilities.LogReturn(timekey, "get device event", "", ""));
			return r;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get device event", "", "", ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	@GetMapping(value="/runevent" )
	@ResponseBody	
	public Object getRunEvent(Pageable pageable){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get files", "", ""));
		try
		{
			Object r = deviceService.getRunEvent(pageable);			
			logger.info(Utilities.LogReturn(timekey, "get run event", "", ""));
			return r;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get run event", "", "", ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	@GetMapping(value="/stopevent" )
	@ResponseBody	
	public Object getStopEvent(Pageable pageable){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get files", "", ""));
		try
		{
			Object r = deviceService.getStopEvent(pageable);			
			logger.info(Utilities.LogReturn(timekey, "get stop event", "", ""));
			return r;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get stop event", "", "", ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	@PostMapping(value="/messages/search", produces = "application/json" )
	@ResponseBody	
	public Object searchMessage(@RequestBody List<SearchObject> obj, Pageable pageRequest){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get files", "", ""));
		try
		{				
			return deviceService.searchMessage(obj, pageRequest);
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get messages error: ", "", "", ex.toString()));
			throw new IllegalArgumentException(ex.toString());			
		}				
	}
	@PostMapping(value="/deviceevent/search", produces = "application/json" )
	@ResponseBody	
	public Object searchDeviceEvent(@RequestBody List<SearchObject> obj, Pageable pageRequest){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get files", "", ""));
		try
		{				
			return deviceService.searchDeviceEvent(obj, pageRequest);
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get device event error: ", "", "", ex.toString()));
			throw new IllegalArgumentException(ex.toString());			
		}				
	}
	@PostMapping(value="/stopevent/search", produces = "application/json" )
	@ResponseBody	
	public Object searchDeviceStopEvent(@RequestBody List<SearchObject> obj, Pageable pageRequest){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get files", "", ""));
		try
		{				
			return deviceService.searchDeviceStopEvent(obj, pageRequest);
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get files", "", "", ex.toString()));
			throw new IllegalArgumentException(ex.toString());			
		}				
	}
	@PostMapping(value="/runevent/search", produces = "application/json" )
	@ResponseBody	
	public Object searchDeviceRunEvent(@RequestBody List<SearchObject> obj, Pageable pageRequest){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get files", "", ""));
		try
		{				
			return deviceService.searchDeviceRunEvent(obj, pageRequest);
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get files", "", "", ex.toString()));
			throw new IllegalArgumentException(ex.toString());			
		}				
	}
	@GetMapping(value="/lmuinfo" )
	@ResponseBody	
	public Object getLMUInfo(){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get files", "", ""));
		try
		{
			Object r = deviceService.getLMUInfo();			
			logger.info(Utilities.LogReturn(timekey, "get LMU info", "", ""));
			return r;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get LMU info", "", "", ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	@GetMapping(value="/punchlogs" )  // get punch log by mdm web
	@ResponseBody	
	public Object getPunchLog(Pageable pageable){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get files", "", ""));
		try
		{
			Object r = deviceService.getPunchLog(pageable);			
			logger.info(Utilities.LogReturn(timekey, "get punch log", "", ""));
			return r;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get punch log errors:", "", "", ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}

	@PostMapping(value="/punchlogs/search", produces = "application/json" ) // search punch log by mdm web
	@ResponseBody	
	public Object searchPunchLog(@RequestBody List<SearchObject> obj, Pageable pageRequest){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get files", "", ""));
		ObjectMapper mapper = new ObjectMapper();		
		String strObject = "";
		try
		{			
			strObject = mapper.writeValueAsString(obj);
			logger.info(Utilities.LogSave(timekey, "search punch logs:", "punch", strObject));
			return deviceService.searchPunchLog(obj, pageRequest);
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get punch logs error: ", "", strObject, ex.toString()));
			throw new IllegalArgumentException(ex.toString());			
		}				
	}
	@GetMapping(value="/lmudiagnostic" )
	@ResponseBody	
	public Object getLMUDiagnostic(Pageable pageable){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get files", "", ""));
		try
		{
			Object r = deviceService.getLmuDiagnostic(pageable);			
			logger.info(Utilities.LogReturn(timekey, "get lmu diagnostic", "", ""));
			return r;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get lmu diagnostic", "", "", ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	@PostMapping(value="/lmudiagnostic/search", produces = "application/json" ) // search diagnostic logs by mdm web
	@ResponseBody	
	public Object searchLmuDiagnostic(@RequestBody List<SearchObject> obj, Pageable pageRequest){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get files", "", ""));
		ObjectMapper mapper = new ObjectMapper();		
		String strObject = "";
		try
		{			
			strObject = mapper.writeValueAsString(obj);
			logger.info(Utilities.LogSave(timekey, "search diagnostic logs:", "punch", strObject));
			return deviceService.searchLmuDiagnostic(obj, pageRequest);
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get diagnostic logs error: ", "", strObject, ex.toString()));
			throw new IllegalArgumentException(ex.toString());			
		}				
	}
}
