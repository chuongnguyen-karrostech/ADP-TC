package com.ADP.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.ADP.etmain.model.Setting;
import com.ADP.service.DeviceService;
import com.ADP.service.SettingService;
import com.ADP.util.DateFunction;
import com.ADP.util.Utilities;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
//import net.minidev.json.JSONValue;

@RestController
@RequestMapping(value="/setting")
@CrossOrigin(origins = "*")
@Api(value="setting", description="Setting")
public class SettingController {
	private final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	DeviceService deviceService;
	
	@Autowired
	SettingService settingService;	
	
	@GetMapping(value="/devicemodule/{appid}/{devid}")
	@ApiOperation(value = "Device module", response = Iterable.class)
	@ResponseBody
	public Object[] getDevModule(@PathVariable int appid,@PathVariable int devid){
		long timekey = DateFunction.TimeKey();	
		//logger.info(Utilities.LogReceive(timekey, "DevModule", "app id", appid + ", and dev id = [" + devid + "]"));
		try
		{
			Object[] objArr = deviceService.getDeviceModule(appid, devid);
			logger.info(Utilities.LogReturn(timekey, "DevModule", "app id", appid + ", and dev id = [" + devid + "]"));		
			return objArr;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "DevModule", "app id", appid + ", and dev id = [" + devid + "]", ex.toString()));			
			throw new IllegalArgumentException(ex.toString());
		}
	}
	@GetMapping(value="/lastroute/{devid}")
	@ApiOperation(value = "Get Lastest route", response = Iterable.class)
	@ResponseBody
	public Object[] getLastRoute(@PathVariable int devid){		
		long timekey = DateFunction.TimeKey();	
		//logger.info(Utilities.LogReceive(timekey, "LastRoute", "dev id", devid + ""));
		try
		{
			Object[] objArr = deviceService.getLastRoute(devid);
			logger.info(Utilities.LogReturn(timekey, "LastRoute", "dev id", devid + ""));		
			return objArr;			
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "LastRoute", "dev id", devid + "", ex.toString()));			
			throw new IllegalArgumentException(ex.toString());
		}
	}
	@GetMapping(value="/recentroute/{devid}")
	@ApiOperation(value = "Get recent route", response = Iterable.class)
	@ResponseBody
	public Object[] getRecentRoute(@PathVariable int devid){
		long timekey = DateFunction.TimeKey();	
		//logger.info(Utilities.LogReceive(timekey, "RecentRoute", "dev id", devid + ""));
		try
		{
			Object[] objArr = deviceService.getRecentRoute(devid);
			logger.info(Utilities.LogReturn(timekey, "RecentRoute", "dev id", devid + ""));		
			return objArr;			
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "RecentRoute", "dev id", devid + "", ex.toString()));			
			throw new IllegalArgumentException(ex.toString());
		}
	}
	@GetMapping(value="/recentroutebybus/{devid}/{busid}")
	@ApiOperation(value = "Get recent route", response = Iterable.class)
	@ResponseBody
	public Object[] getRecentRoute(@PathVariable int devid, @PathVariable String busid){
		long timekey = DateFunction.TimeKey();	
		//logger.info(Utilities.LogReceive(timekey, "RecentRoute", "dev id", devid + ""));
		try
		{
			Object[] objArr = deviceService.getRecentRouteByBus(devid, busid);
			logger.info(Utilities.LogReturn(timekey, "RecentRoute", "dev id", devid + ""));		
			return objArr;			
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "RecentRoute", "dev id", devid + "", ex.toString()));			
			throw new IllegalArgumentException(ex.toString());
		}
	}
	@RequestMapping(value = "/recentroute/add", method = RequestMethod.POST)
	@ResponseBody
	public Object AddRecentRoute(@RequestBody Object routeRecent) {		
		long timekey = DateFunction.TimeKey();	
		//logger.info(Utilities.LogReceive(timekey, "AddRecentRoute", "route", routeRecent.toString()));
		try
		{
			Object obj = deviceService.addRecentRoute(routeRecent);
			logger.info(Utilities.LogSave(timekey, "AddRecentRoute", "route", routeRecent.toString()));
			return obj;
		}
		catch (HttpServerErrorException ex)
		{
			String jsonString = ex.getResponseBodyAsString();
			try{
			   JSONObject jsonObject = new JSONObject(jsonString);
				String message = jsonObject.getString("message");
				logger.info(Utilities.LogError(timekey, "AddRecentRoute", "route", routeRecent.toString(), message));	
				throw new IllegalArgumentException(message);
			}catch (Exception e){
				logger.info(Utilities.LogError(timekey, "AddRecentRoute", "route", routeRecent.toString(), e.getMessage()));		
				throw new IllegalArgumentException(e.getMessage());
			}
		}		
	}	
	
	@RequestMapping(value = "/event/add", method = RequestMethod.POST)
	@ResponseBody
	public Object AddEvent(@RequestBody Object deviceevent) {		
		long timekey = DateFunction.TimeKey();	
		//logger.info(Utilities.LogReceive(timekey, "AddEvent", "event", deviceevent.toString()));
		try
		{
			Object obj = deviceService.addEvent(deviceevent);
			logger.info(Utilities.LogSave(timekey, "AddEvent", "event", deviceevent.toString()));
			return obj;
		}
		catch (HttpServerErrorException ex)
		{
			String jsonString = ex.getResponseBodyAsString();
			try{
				JSONObject jsonObject = new JSONObject(jsonString);
				String message = jsonObject.getString("message");
				logger.info(Utilities.LogError(timekey, "AddEvent", "event", deviceevent.toString(), message));	
				throw new IllegalArgumentException(message);
			}catch (Exception e){
				logger.info(Utilities.LogError(timekey, "AddEvent", "event", deviceevent.toString(), e.getMessage()));		
				throw new IllegalArgumentException(e.getMessage());
			}
		}
	}	
	
	@RequestMapping(value = "/runevent/add", method = RequestMethod.POST)
	@ResponseBody
	public Object AddRunEvent(@RequestBody Object deviceevent) {		
		long timekey = DateFunction.TimeKey();	
		//logger.info(Utilities.LogReceive(timekey, "AddRunEvent", "event", deviceevent.toString()));
		try
		{
			Object obj = deviceService.addRunEvent(deviceevent);
			logger.info(Utilities.LogSave(timekey, "AddRunEvent", "event", deviceevent.toString()));
			return obj;
		}
		catch (HttpServerErrorException ex)
		{
			String jsonString = ex.getResponseBodyAsString();
			try{				
				JSONObject jsonObject = new JSONObject(jsonString);
				String message = jsonObject.getString("message");
				logger.info(Utilities.LogError(timekey, "AddRunEvent", "event", deviceevent.toString(), message));	
				throw new IllegalArgumentException(message);
			}catch (Exception e){
				logger.info(Utilities.LogError(timekey, "AddRunEvent", "event", deviceevent.toString(), e.getMessage()));		
				throw new IllegalArgumentException(e.getMessage());
			}
		}
	}
	
	@RequestMapping(value = "/stopevent/add", method = RequestMethod.POST)
	@ResponseBody
	public Object AddStopEvent(@RequestBody Object deviceevent) {		
		long timekey = DateFunction.TimeKey();	
		//logger.info(Utilities.LogReceive(timekey, "AddStopEvent", "event", deviceevent.toString()));
		try
		{
			Object obj = deviceService.addStopEvent(deviceevent);
			logger.info(Utilities.LogSave(timekey, "AddStopEvent", "event", deviceevent.toString()));
			return obj;
		}
		catch (HttpServerErrorException ex)
		{
			String jsonString = ex.getResponseBodyAsString();
			try{
				JSONObject jsonObject = new JSONObject(jsonString);
				String message = jsonObject.getString("message");
				logger.info(Utilities.LogError(timekey, "AddStopEvent", "event", deviceevent.toString(), message));	
				throw new IllegalArgumentException(message);
			}catch (Exception e){
				logger.info(Utilities.LogError(timekey, "AddStopEvent", "event", deviceevent.toString(), e.getMessage()));		
				throw new IllegalArgumentException(e.getMessage());
			}
		}
	}
	
	@RequestMapping(value = "/studentevent/add", method = RequestMethod.POST)
	@ResponseBody
	public Object AddStudentEvent(@RequestBody Object deviceevent) {		
		long timekey = DateFunction.TimeKey();	
		//logger.info(Utilities.LogReceive(timekey, "AddStudentEvent", "event", deviceevent.toString()));
		try
		{
			Object obj = deviceService.addStudentEvent(deviceevent);
			logger.info(Utilities.LogSave(timekey, "AddStudentEvent", "event", deviceevent.toString()));
			return obj;
		}
		catch (HttpServerErrorException ex)
		{
			String jsonString = ex.getResponseBodyAsString();
			try{
				JSONObject jsonObject = new JSONObject(jsonString);
				String message = jsonObject.getString("message");
				logger.info(Utilities.LogError(timekey, "AddStudentEvent", "event", deviceevent.toString(), message));	
				throw new IllegalArgumentException(message);
			}catch (Exception e){
				logger.info(Utilities.LogError(timekey, "AddStudentEvent", "event", deviceevent.toString(), e.getMessage()));		
				throw new IllegalArgumentException(e.getMessage());
			}
		}
	}
	
	@RequestMapping(value = "/rawevent/add", method = RequestMethod.POST)
	@ResponseBody
	public Object AddRawEvent(@RequestBody Object devicerawevent) {		
		long timekey = DateFunction.TimeKey();	
		//logger.info(Utilities.LogReceive(timekey, "AddRawEvent", "event", devicerawevent.toString()));
		try
		{
			Object obj = deviceService.addRawEvent(devicerawevent);
			logger.info(Utilities.LogSave(timekey, "AddRawEvent", "event", devicerawevent.toString()));
			return obj;
		}
		catch (HttpServerErrorException ex)
		{
			String jsonString = ex.getResponseBodyAsString();
			try{
				JSONObject jsonObject = new JSONObject(jsonString);
				String message = jsonObject.getString("message");
				logger.info(Utilities.LogError(timekey, "AddRawEvent", "event", devicerawevent.toString(), message));	
				throw new IllegalArgumentException(message);
			}catch (Exception e){
				logger.info(Utilities.LogError(timekey, "AddRawEvent", "event", devicerawevent.toString(), e.getMessage()));		
				throw new IllegalArgumentException(e.getMessage());
			}
		}		
	}	
	@RequestMapping(value = "/swipeevent/add", method = RequestMethod.POST)
	@ResponseBody
	public Object AddSwipeEvent(@RequestBody Object deviceswipeevent) {		
		long timekey = DateFunction.TimeKey();	
		//logger.info(Utilities.LogReceive(timekey, "AddRawEvent", "event", devicerawevent.toString()));
		try
		{
			Object obj = deviceService.addSwipeEvent(deviceswipeevent);
			logger.info(Utilities.LogSave(timekey, "AddSwipeEvent", "event", deviceswipeevent.toString()));
			return obj;
		}
		catch (HttpServerErrorException ex)
		{
			String jsonString = ex.getResponseBodyAsString();
			try{
				JSONObject jsonObject = new JSONObject(jsonString);
				String message = jsonObject.getString("message");
				logger.info(Utilities.LogError(timekey, "AddSwipeEvent", "event", deviceswipeevent.toString(), message));	
				throw new IllegalArgumentException(message);
			}catch (Exception e){
				logger.info(Utilities.LogError(timekey, "AddSwipeEvent", "event", deviceswipeevent.toString(), e.getMessage()));		
				throw new IllegalArgumentException(e.getMessage());
			}
		}		
	}	
	@RequestMapping(value = "/route/add", method = RequestMethod.POST)
	@ResponseBody
	public Object AddRoute(@RequestBody Object deviceroute) {		
		long timekey = DateFunction.TimeKey();	
		//logger.info(Utilities.LogReceive(timekey, "AddRoute", "route", deviceroute.toString()));
		try
		{
			Object obj = deviceService.addRoute(deviceroute);
			logger.info(Utilities.LogSave(timekey, "AddRoute", "route", deviceroute.toString()));
			return obj;
		}
		catch (HttpServerErrorException ex)
		{
			String jsonString = ex.getResponseBodyAsString();
			try{
				JSONObject jsonObject = new JSONObject(jsonString);
				String message = jsonObject.getString("message");
				logger.info(Utilities.LogError(timekey, "AddRoute", "route", deviceroute.toString(), message));
				throw new IllegalArgumentException(message);
			}catch (Exception e){
				logger.info(Utilities.LogError(timekey, "AddRoute", "route", deviceroute.toString(), e.getMessage()));			
				throw new IllegalArgumentException(e.toString());
			}
		}
	}
	@GetMapping(value="/timezone")
	@ApiOperation(value = "Get time zone", response = Iterable.class)
	@ResponseBody
	public Setting getTimeZone(){
		long timekey = DateFunction.TimeKey();	
		//logger.info(Utilities.LogReceive(timekey, "TimeZone", "name setting", "TimeZone"));
		try
		{
			Setting obj = settingService.findById("TimeZone");
			if (obj.getValueSetting().indexOf("GMT") == -1)
			{
				String setTime = "US/" + obj.getValueSetting();
				obj.setValueSetting(setTime);
			}
			
			logger.info(Utilities.LogReturn(timekey, "TimeZone", "name setting", "TimeZone"));
			return obj;			
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "TimeZone", "name setting","TimeZone", ex.toString()));			
			throw new IllegalArgumentException(ex.toString());
		}
	}
	@GetMapping(value="/version")
	@ApiOperation(value = "Get version", response = Iterable.class)
	@ResponseBody
	public List<Setting> getVersion(){
		long timekey = DateFunction.TimeKey();	
		//logger.info(Utilities.LogReceive(timekey, "TimeZone", "name setting", "TimeZone"));
		try
		{	
			List<Setting> result = new ArrayList<Setting>();
			Setting obj = new Setting();
			obj.setNameId("ADP");
		    obj.setValueSetting(com.ADP.util.CommonFunction.version);		    
		    result.add(obj);
		    try {
			    String apimmversion = deviceService.getApiMMVersion();
			    Setting obj2 = new Setting();
				obj2.setNameId("APIMM");
			    obj2.setValueSetting(apimmversion);
			    result.add(obj2);
		    }catch (Exception e){
		    	Setting obj2 = new Setting();
				obj2.setNameId("APIMM");
			    obj2.setValueSetting("");
			    result.add(obj2);
		    }
		    logger.info(Utilities.LogReturn(timekey, "Version", "Adp", com.ADP.util.CommonFunction.version));
			return result;			
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "Version", "Adp", "ADPversion", ex.toString()));			
			throw new IllegalArgumentException(ex.toString());
		}
	}
	@GetMapping(value="/getvehicletime")
	@ApiOperation(value = "Get Vehicle time", response = Iterable.class)
	@ResponseBody
	public Setting getVehTime(){
		long timekey = DateFunction.TimeKey();	
		//logger.info(Utilities.LogReceive(timekey, "TimeZone", "name setting", "TimeZone"));
		try
		{
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
			    obj.setNameId("VehicleTime");
			    obj.setValueSetting(vehTime);
			}
			
			logger.info(Utilities.LogReturn(timekey, "VehicleTime", "", ""));
			return obj;			
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "VehicleTime", "","", ex.toString()));			
			throw new IllegalArgumentException(ex.toString());
		}
	}
	@GetMapping(value="/drivertheme/{driverid}")
	@ApiOperation(value = "Get recent route", response = Iterable.class)
	@ResponseBody
	public Object getDriverTheme(@PathVariable String driverid){
		long timekey = DateFunction.TimeKey();	
		//logger.info(Utilities.LogReceive(timekey, "RecentRoute", "dev id", devid + ""));
		try
		{
			Object objArr = deviceService.findDriverTheme(driverid);
			logger.info(Utilities.LogReturn(timekey, "Driver Theme: ", "driver id", driverid + ""));		
			return objArr;			
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "Driver Theme", "driver id", driverid + "", ex.toString()));			
			throw new IllegalArgumentException(ex.toString());
		}
	}
	@PostMapping(value = "/drivertheme")
	@ResponseBody
	public Object AddDriverTheme(@RequestBody Object driverTheme) {		
		long timekey = DateFunction.TimeKey();
		ObjectMapper mapper = new ObjectMapper();	
		//logger.info(Utilities.LogReceive(timekey, "AddRoute", "route", deviceroute.toString()));
		try
		{
			Object obj = deviceService.addDriverTheme(driverTheme);
			try 
			{
				if(mapper.writeValueAsString(obj).contains("true")) 
				{
					logger.info(Utilities.LogSave(timekey, "Add Driver Theme sucessfully: ", "driver id",obj + " " + obj.toString()));
				}
				else 
				{
					logger.info(Utilities.LogSave(timekey, "Add Driver Theme not completed by theme or driver id: ", "driver id",obj + " " + obj.toString()));
				}
			}
			catch (Exception ex) {
				throw new IllegalArgumentException(ex.toString());
			}
			return obj;
		}
		catch (HttpServerErrorException ex)
		{
			String jsonString = ex.getResponseBodyAsString();
			try{
				JSONObject jsonObject = new JSONObject(jsonString);
				String message = jsonObject.getString("message");
				logger.info(Utilities.LogError(timekey, "AddDriverTheme", "drivertheme", driverTheme.toString(), message));
				throw new IllegalArgumentException(message);
			}catch (Exception e){
				logger.info(Utilities.LogError(timekey, "AddDriverTheme", "drivertheme", driverTheme.toString(), e.getMessage()));			
				throw new IllegalArgumentException(e.toString());
			}
		}
	}
	@GetMapping(value="/getthemes")
	@ApiOperation(value = "Get recent route", response = Iterable.class)
	@ResponseBody
	public Object getAllThemes(){
		long timekey = DateFunction.TimeKey();	
		//logger.info(Utilities.LogReceive(timekey, "RecentRoute", "dev id", devid + ""));
		try
		{
			Object[] objArr = deviceService.findThemes();
			logger.info(Utilities.LogReturn(timekey, "Get all Themes: ", "themes", ""));		
			return objArr;			
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "Get all Themes", "themes", "", ex.toString()));			
			throw new IllegalArgumentException(ex.toString());
		}
	}
}
