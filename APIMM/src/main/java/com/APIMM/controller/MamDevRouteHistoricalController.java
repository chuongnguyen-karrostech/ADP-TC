package com.APIMM.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.APIMM.mam.model.MamDevRouteHistorical;
import com.APIMM.service.MamService;
import com.APIMM.util.CommonFunction;
import com.APIMM.util.message.ActionReturn;
import io.swagger.annotations.Api;

@RestController
@RequestMapping(value="/mam")
@CrossOrigin(origins = "*")
@Api(value="mam")
public class MamDevRouteHistoricalController {
	private final Log logger = LogFactory.getLog(this.getClass());
	@Autowired
    MamService mamService;
	
	@GetMapping(value="/recentroute/{devid}" )
	@ResponseBody
	public List<MamDevRouteHistorical> FindRecentRoutebyDevice(@PathVariable int devid){
		long timekey = com.APIMM.util.DateFunction.TimeKey();		
		//logger.info(CommonFunction.LogReceive(timekey, "get recent route", "device", ""+devid));
		List<MamDevRouteHistorical> lstReturn = new ArrayList<MamDevRouteHistorical>();
		lstReturn = mamService.findRecentRoutebyDevice(devid,10);
		logger.info(CommonFunction.LogReturn(timekey, "get recent route", "device", ""+devid));		
		return lstReturn;
	}	
	
	@GetMapping(value="/recentroute/{devid}/{numberrecord}" )
	@ResponseBody
	public List<MamDevRouteHistorical> FindTopRecentRoutebyDevice(@PathVariable int devid,@PathVariable int numberrecord){
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		//logger.info(CommonFunction.LogReceive(timekey, "get recent route", "device", ""+devid));
		List<MamDevRouteHistorical> lstReturn = new ArrayList<MamDevRouteHistorical>();
		lstReturn = mamService.findRecentRoutebyDevice(devid,numberrecord);		
		logger.info(CommonFunction.LogReturn(timekey, "get recent route", "device", ""+devid));		
		return lstReturn;
	}	
	@RequestMapping(value = "/recentroute/add", method = RequestMethod.POST)
	@ResponseBody
	public ActionReturn Add(@RequestBody MamDevRouteHistorical routeRecent) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		//logger.info(CommonFunction.LogReceive(timekey, "get recent route", "device", ""+routeRecent.getMdmDevices().getdevID()));
		ActionReturn actreturn = new ActionReturn(null);
		actreturn = ActionReturn.Response(mamService.Add(routeRecent));
		logger.info(CommonFunction.LogSave(timekey, "get recent route", "device", ""+routeRecent.getMdmDevices().getdevID()));		
		return actreturn;
		
	}
	@GetMapping(value="/lastroute/{devid}" )
	@ResponseBody
	public List<MamDevRouteHistorical> FindLastRoutebyDevice(@PathVariable int devid){
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		//logger.info(CommonFunction.LogReceive(timekey, "get last recent route", "device", ""+devid));
		List<MamDevRouteHistorical> lstReturn = new ArrayList<MamDevRouteHistorical>();
		lstReturn = mamService.findLastRoutebyDevice(devid);
		logger.info(CommonFunction.LogReturn(timekey, "get last recent route", "device", ""+devid));
		return lstReturn;		
	}
	@GetMapping(value = "/recentroute/addnotification/{route}/{bus}/{startdate:.+}/{enddate:.+}")
	@ResponseBody
	public ActionReturn AddNotification(@PathVariable String route,@PathVariable String bus,@PathVariable String startdate,@PathVariable String enddate) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		ActionReturn actreturn = new ActionReturn(null);
		boolean bypassGPSP= true; //don't send cloud
		if (!bypassGPSP)
		{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
			df.setLenient(false);	
			Date startDate = new Date();	
			Date endDate = new Date();
			try {
				startDate = df.parse(startdate);		    
			} catch (ParseException e) {
				logger.info(CommonFunction.LogError(timekey, "Get download", "date", startdate,e.getMessage()));
				throw new IllegalArgumentException("Start Date parameter invalid!!!");
			}
			try {
				endDate = df.parse(enddate);		    
			} catch (ParseException e) {
				logger.info(CommonFunction.LogError(timekey, "Get download", "date", enddate,e.getMessage()));
				throw new IllegalArgumentException("End Date parameter invalid!!!");
			}
			actreturn = ActionReturn.Response(mamService.AddNotification(route,bus,startDate,endDate));
		}
		else
			actreturn = ActionReturn.Response("Don't send cloud when route sub from GPSP");
		logger.info(CommonFunction.LogSave(timekey, "push route assignment to cloud", "input", "" +route+","+bus+"," +startdate+","+ enddate +" - output:"+actreturn.result));		
		return actreturn;
		
	}
	
	@RequestMapping(value = "/recentroute/routeassignment", method = RequestMethod.POST)
	@ResponseBody
	public ActionReturn routeassignment(@RequestBody String[] routeassignment) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		logger.info(CommonFunction.LogSave(timekey, "Start push list route assignment to cloud", "parameters", String.join(",", routeassignment)));
		ActionReturn actreturn = new ActionReturn(null);	
		String result=mamService.AddRouteAssign(routeassignment);
		if (result.indexOf("success")>0){
			actreturn = ActionReturn.Response(result);
			logger.info(CommonFunction.LogSave(timekey, "push list route assignment to cloud", "result", actreturn.result));		
			return actreturn;
		}else{
			logger.info(CommonFunction.LogError(timekey, "push list route assignment to cloud", "result", result,""));
			throw new IllegalArgumentException(result);
		}		
		//logger.info(CommonFunction.LogSave(timekey, "push list route assignment to cloud", "result", actreturn.result));		
		//return actreturn;		
	}
	@GetMapping(value="/recentroutebybus/{devid}/{busid}" )
	@ResponseBody
	public List<MamDevRouteHistorical> FindTopRecentRoutebyDeviceAndBus(@PathVariable int devid,@PathVariable String busid){
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		//logger.info(CommonFunction.LogReceive(timekey, "get recent route", "device", ""+devid));
		List<MamDevRouteHistorical> lstReturn = new ArrayList<MamDevRouteHistorical>();
		lstReturn = mamService.findRecentRoutebyDeviceByBus(devid,10,busid);		
		logger.info(CommonFunction.LogReturn(timekey, "get recent route", "device", ""+devid));		
		return lstReturn;
	}	
}