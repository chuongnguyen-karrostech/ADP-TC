package com.ADP.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.ADP.service.OHAService;
import com.ADP.util.DateFunction;
import com.ADP.util.Utilities;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/oha")
@CrossOrigin(origins = "*")
@Api(value="oha", description="oha")
public class OHAController {
	private final Log logger = LogFactory.getLog(this.getClass());	
	
	@Autowired
	OHAService service;	
	@GetMapping(value="/date/all")
	@ApiOperation(value = "get All Date", response = Iterable.class)
	@ResponseBody
	public Object[] getAllDate(){	
		long timekey = DateFunction.TimeKey();
		try
		{
			Object[] objArr = service.getAllDate();
			logger.info(Utilities.LogReturn(timekey, "All Date", "", ""));
			return objArr;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "All Date", "", "", ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	@GetMapping(value="/device/all")
	@ApiOperation(value = "get All Device", response = Iterable.class)
	@ResponseBody
	public Object[] getAllDevice(){	
		long timekey = DateFunction.TimeKey();
		try
		{
			Object[] objArr = service.getAllDevice();
			logger.info(Utilities.LogReturn(timekey, "getAllDevice", "", ""));
			return objArr;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "getAllDevice", "", "", ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}	
	@GetMapping(value="/bus/{daterequest}")
	@ApiOperation(value = "get bus", response = Iterable.class)
	@ResponseBody
	public Object[] getBusbyDate(@PathVariable String daterequest){	
		long timekey = DateFunction.TimeKey();
		try
		{
			Object[] objArr = service.getBusbyDate(daterequest);
			logger.info(Utilities.LogReturn(timekey, "getBus", "date", daterequest));
			return objArr;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "getBus", "date", daterequest, ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	@GetMapping(value="/bus/all")
	@ApiOperation(value = "get bus", response = Iterable.class)
	@ResponseBody
	public Object[] getAllBus(){	
		long timekey = DateFunction.TimeKey();
		try
		{
			Object[] objArr = service.getAllBus();
			logger.info(Utilities.LogReturn(timekey, "getAllBus", "", ""));
			return objArr;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "getAllBus", "", "", ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	@GetMapping(value="/run/all")
	@ApiOperation(value = "get All Route", response = Iterable.class)
	@ResponseBody
	public Object[] getAllRun(){	
		long timekey = DateFunction.TimeKey();
		try
		{
			Object[] objArr = service.getAllRun();
			logger.info(Utilities.LogReturn(timekey, "All Run", "", ""));
			return objArr;
		}
		catch (HttpServerErrorException ex)
		{
			String jsonString = ex.getResponseBodyAsString();
			try{
			   JSONObject jsonObject = new JSONObject(jsonString);
				String message = jsonObject.getString("message");
				logger.info(Utilities.LogError(timekey, "get All Run", "", "", message));	
				throw new IllegalArgumentException(message);
			}catch (Exception e){
				logger.info(Utilities.LogError(timekey, "get All Run", "", "", e.getMessage()));		
				throw new IllegalArgumentException(e.getMessage());
			}
		}	
	}	
	@GetMapping(value="/run/{daterequest}")
	@ApiOperation(value = "get bus", response = Iterable.class)
	@ResponseBody
	public Object[] getRunbyDate(@PathVariable String daterequest){	
		long timekey = DateFunction.TimeKey();
		try
		{
			Object[] objArr = service.getRunbyDate(daterequest);
			logger.info(Utilities.LogReturn(timekey, "getRun", "date", daterequest));
			return objArr;
		}
		catch (HttpServerErrorException ex)
		{
			String jsonString = ex.getResponseBodyAsString();
			try{
			   JSONObject jsonObject = new JSONObject(jsonString);
				String message = jsonObject.getString("message");
				logger.info(Utilities.LogError(timekey, "getRun", "date", daterequest, message));	
				throw new IllegalArgumentException(message);
			}catch (Exception e){
				logger.info(Utilities.LogError(timekey, "getRun", "date", daterequest, e.getMessage()));		
				throw new IllegalArgumentException(e.getMessage());
			}
		}			
	}
	@GetMapping(value="/route/all")
	@ApiOperation(value = "get All Route", response = Iterable.class)
	@ResponseBody
	public Object[] getAllRoute(){	
		long timekey = DateFunction.TimeKey();
		try
		{
			Object[] objArr = service.getAllRoute();
			logger.info(Utilities.LogReturn(timekey, "All Route", "", ""));
			return objArr;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "All Route", "", "", ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	
	@GetMapping(value="/route/{routenumber:.+}")
	@ApiOperation(value = "Get Route", response = Iterable.class)
	@ResponseBody
	public Object[] getRoute(@PathVariable String routenumber){	
		long timekey = DateFunction.TimeKey();	 
		try
		{
			Object[] objArr = service.getRoute(routenumber);
			logger.info(Utilities.LogReturn(timekey, "get route ", "Route Number", routenumber));
			return objArr;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get route ", "Route Number", routenumber, ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	@GetMapping(value="/route/{devid}/{daterequest}")
	@ApiOperation(value = "Get Route", response = Iterable.class)
	@ResponseBody
	public Object[] getRoutebyDevDate(@PathVariable Integer devid, @PathVariable String daterequest){	
		long timekey = DateFunction.TimeKey();	 
		try
		{
			Object[] objArr = service.getRoutebyDevDate(devid,daterequest);
			logger.info(Utilities.LogReturn(timekey, "get route ", "Dev id", ""+devid));
			return objArr;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get route ", "Dev id", ""+devid, ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	
	@GetMapping(value="/avlbydevice/{devid}/{runstarttime}/{runendtime}")
	@ApiOperation(value = "Get run dir", response = Iterable.class)
	@ResponseBody
	public Object[] getRundir(@PathVariable int devid,@PathVariable String runstarttime,@PathVariable String runendtime){	
		long timekey = DateFunction.TimeKey();	 
		try
		{
			Object[] objArr = service.getRundir(devid, runstarttime, runendtime);
			logger.info(Utilities.LogReturn(timekey, "get rundir ", "devid", ""+devid));
			return objArr;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get rundir ", "devid", ""+devid, ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	@GetMapping(value="/avlbybus/{busnumber}/{runstarttime}/{runendtime}")
	@ApiOperation(value = "Get run dir", response = Iterable.class)
	@ResponseBody
	public Object[] getRundir(@PathVariable String busnumber,@PathVariable String runstarttime,@PathVariable String runendtime){	
		long timekey = DateFunction.TimeKey();	 
		try
		{
			Object[] objArr = service.getRundirbybus(busnumber, runstarttime, runendtime);
			logger.info(Utilities.LogReturn(timekey, "get rundir by bus", "bus", ""+busnumber));
			return objArr;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get rundir by bus", "bus", ""+busnumber, ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	
	@GetMapping(value="/avlbydevicebus/{devid}/{busnumber}/{runstarttime}/{runendtime}")
	@ApiOperation(value = "Get run dir", response = Iterable.class)
	@ResponseBody
	public Object[] getRundir(@PathVariable int devid,@PathVariable String busnumber,@PathVariable String runstarttime,@PathVariable String runendtime){	
		long timekey = DateFunction.TimeKey();	 
		try
		{
			Object[] objArr = service.getRundirbydevicebus(devid,busnumber, runstarttime, runendtime);
			logger.info(Utilities.LogReturn(timekey, "get rundir by device bus", "dev/bus", ""+devid+"/"+busnumber));
			return objArr;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get rundir by bus", "dev/bus", ""+devid+"/"+busnumber, ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	@RequestMapping(value = "/route/delete/{routeid}", method = RequestMethod.GET)
	@ApiOperation(value = "delete run", response = Iterable.class)
	@ResponseBody	
	public Object DeleteRoute(@PathVariable Integer routeid) {
		long timekey = DateFunction.TimeKey();	 
		try
		{
			Object objArr = service.deleteRoute(routeid);
			logger.info(Utilities.LogReturn(timekey, "delete route", "routeid", ""+routeid));
			return objArr;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "delete route", "routeid", ""+routeid, ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}
	}
	@RequestMapping(value = "/run/delete/{runroute}", method = RequestMethod.GET)
	@ApiOperation(value = "delete run", response = Iterable.class)
	@ResponseBody	
	public Object DeleteRun(@PathVariable Integer runroute) {
		long timekey = DateFunction.TimeKey();	 
		try
		{
			Object objArr = service.deleteRun(runroute);
			logger.info(Utilities.LogReturn(timekey, "delete run", "runroute", ""+runroute));
			return objArr;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "delete run", "runroute", ""+runroute, ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}
	}
	@RequestMapping(value = "/stop/delete/{stoprunid}", method = RequestMethod.GET)
	@ApiOperation(value = "delete route", response = Iterable.class)
	@ResponseBody	
	public Object DeleteStop(@PathVariable Integer stoprunid) {
		long timekey = DateFunction.TimeKey();	 
		try
		{
			Object objArr = service.deleteStop(stoprunid);
			logger.info(Utilities.LogReturn(timekey, "delete stop", "stoprunid", ""+stoprunid));
			return objArr;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "delete stop", "stoprunid", ""+stoprunid, ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}
	}
	
	@RequestMapping(value = "/route/updatedesc/{routeid}/{desc}", method = RequestMethod.GET)
	@ApiOperation(value = "update route", response = Iterable.class)
	@ResponseBody	
	public Object DeleteRoute(@PathVariable Integer routeid,@PathVariable String desc) {
		long timekey = DateFunction.TimeKey();	 
		try
		{
			Object objArr = service.updateRouteDesc(routeid,desc);
			logger.info(Utilities.LogReturn(timekey, "update route", "routeid", ""+routeid));
			return objArr;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "delete route", "routeid", ""+routeid, ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}
	}
	@RequestMapping(value = "/run/updatedesc/{runroute}/{desc}", method = RequestMethod.GET)
	@ApiOperation(value = "update run", response = Iterable.class)
	@ResponseBody	
	public Object DeleteRun(@PathVariable Integer runroute,@PathVariable String desc) {
		long timekey = DateFunction.TimeKey();	 
		try
		{
			Object objArr = service.updateRunDesc(runroute,desc);
			logger.info(Utilities.LogReturn(timekey, "update run", "runroute", ""+runroute));
			return objArr;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "delete run", "runroute", ""+runroute, ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}
	}
	
	@PostMapping(value="/route/edit")
	@ApiOperation(value = "Edit Route", response = Iterable.class)	
	@ResponseBody
	public Object EditRoute(@RequestBody Object route) {		
		long timekey = DateFunction.TimeKey();		
		try
		{
			Object obj = service.editRoute(route);
			logger.info(Utilities.LogSave(timekey, "Edit Route", "route", route.toString()));
			return obj;
		}
		catch (HttpServerErrorException ex)
		{
			String jsonString = ex.getResponseBodyAsString();
			try{
			   JSONObject jsonObject = new JSONObject(jsonString);
				String message = jsonObject.getString("message");
				logger.info(Utilities.LogError(timekey, "Edit Route", "route", route.toString(), message));	
				throw new IllegalArgumentException(message);
			}catch (Exception e){
				logger.info(Utilities.LogError(timekey, "Edit Route", "route", route.toString(), e.getMessage()));		
				throw new IllegalArgumentException(e.getMessage());
			}
		}		
	}
	
	@PostMapping(value="/run/edit")
	@ApiOperation(value = "Edit Run", response = Iterable.class)	
	@ResponseBody
	public Object EditRun(@RequestBody Object run) {		
		long timekey = DateFunction.TimeKey();		
		try
		{
			Object obj = service.editRun(run);
			logger.info(Utilities.LogSave(timekey, "Edit Run", "run", run.toString()));
			return obj;
		}
		catch (HttpServerErrorException ex)
		{
			String jsonString = ex.getResponseBodyAsString();
			try{
			   JSONObject jsonObject = new JSONObject(jsonString);
				String message = jsonObject.getString("message");
				logger.info(Utilities.LogError(timekey, "Edit Run", "run", run.toString(), message));	
				throw new IllegalArgumentException(message);
			}catch (Exception e){
				logger.info(Utilities.LogError(timekey, "Edit run", "run", run.toString(), e.getMessage()));		
				throw new IllegalArgumentException(e.getMessage());
			}
		}		
	}
	
	@PostMapping(value="/route/add")
	@ApiOperation(value = "Edit Route", response = Iterable.class)	
	@ResponseBody
	public Object AddRoute(@RequestBody Object route) {		
		long timekey = DateFunction.TimeKey();		
		try
		{
			Object obj = service.addRoute(route);
			logger.info(Utilities.LogSave(timekey, "OHA Add Route", "route", route.toString()));
			return obj;
		}
		catch (HttpServerErrorException ex)
		{
			String jsonString = ex.getResponseBodyAsString();
			try{
			   JSONObject jsonObject = new JSONObject(jsonString);
				String message = jsonObject.getString("message");
				logger.info(Utilities.LogError(timekey, "OHA Add Route", "route", route.toString(), message));	
				throw new IllegalArgumentException(message);
			}catch (Exception e){
				logger.info(Utilities.LogError(timekey, "OHA Add Route", "route", route.toString(), e.getMessage()));		
				throw new IllegalArgumentException(e.getMessage());
			}
		}		
	}
	
	@PostMapping(value="/rundirs/save/{routeid}/{runid:.+}")
	@ApiOperation(value = "save run dirs", response = Iterable.class)	
	@ResponseBody
	public Object SaveRunDirs(@PathVariable int routeid, @PathVariable String runid, @RequestBody Object[] rundirs) 
	{		
		long timekey = DateFunction.TimeKey();		
		try
		{
			Object obj = service.SaveRunDirs(routeid,runid,rundirs);
			logger.info(Utilities.LogSave(timekey, "Save RunDirs ", "Rundirs", ""+rundirs.length));
			return obj;
		}
		catch (HttpServerErrorException ex)
		{
			String jsonString = ex.getResponseBodyAsString();
			try{
			   JSONObject jsonObject = new JSONObject(jsonString);
				String message = jsonObject.getString("message");
				logger.info(Utilities.LogError(timekey, "Save RunDirs ", "Rundirs", ""+rundirs.length, message));	
				throw new IllegalArgumentException(message);
			}catch (Exception e){
				logger.info(Utilities.LogError(timekey, "Save RunDirs ", "Rundirs", ""+rundirs.length, e.getMessage()));		
				throw new IllegalArgumentException(e.getMessage());
			}
		}		
	}
	@GetMapping(value="/avlbyrun/{runid:.+}/{runroute}/{devid}")
	@ApiOperation(value = "Get run dir", response = Iterable.class)
	@ResponseBody
	public Object[] getRundirbyRun(@PathVariable String runid, @PathVariable int runroute, @PathVariable int devid){	
		long timekey = DateFunction.TimeKey();	 
		try
		{
			Object[] objArr = service.getRundirbyrun(runid,runroute,devid);
			logger.info(Utilities.LogReturn(timekey, "get rundir by run", "run", runid));
			return objArr;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get rundir by run", "run", runid, ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	@GetMapping(value="/getruninfo/{runroute}")
	@ApiOperation(value = "Get run dir", response = Iterable.class)
	@ResponseBody
	public Object getRuninfo(@PathVariable int runroute){	
		long timekey = DateFunction.TimeKey();	 
		try
		{
			Object objArr = service.getRuninfo(runroute);
			logger.info(Utilities.LogReturn(timekey, "get run info", "runroute", ""+runroute));
			return objArr;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "get run info", "runroute", ""+runroute, ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
}
