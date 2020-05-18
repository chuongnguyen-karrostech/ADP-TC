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

import com.APIMM.mam.model.DPRoutes;
import com.APIMM.mam.model.DPRunDirs;
import com.APIMM.mam.model.DPRuns;
import com.APIMM.mam.model.DPRuns2;
import com.APIMM.mam.model.MamSyncInfo;
import com.APIMM.mam.model.RunDirs;
import com.APIMM.mam.model.RunInfo;
import com.APIMM.service.DPLiveService;
import com.APIMM.util.CommonFunction;
import com.APIMM.util.message.ActionReturn;
import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/dplive")
@CrossOrigin(origins = "*")
@Api(value = "service")
public class DPController {
	private final Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	DPLiveService DPService;

	@RequestMapping(value = "/date/all", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getAllDate() {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		// logger.info(CommonFunction.LogReceive(timekey, "find route", "route",
		// routenumber));
		List<String> lstReturn = new ArrayList<String>();
		lstReturn = DPService.findAllDate();
		logger.info(CommonFunction.LogReturn(timekey, "find all date", " all date", ""));
		return lstReturn;
	}

	@RequestMapping(value = "/bus/{daterequest}", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getBusbyDate(@PathVariable String daterequest) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		df.setLenient(false);
		Date date = new Date();
		try {
			date = df.parse(daterequest);
		} catch (ParseException e) {
			logger.info(CommonFunction.LogError(timekey, "Get bus", "date", "" + daterequest, e.getMessage()));
			throw new IllegalArgumentException("Date parameter invalid!!!");
		}
		// logger.info(CommonFunction.LogReceive(timekey, "find route", "route",
		// routenumber));
		List<String> lstReturn = new ArrayList<String>();
		lstReturn = DPService.findBusbyDate(date);
		logger.info(CommonFunction.LogReturn(timekey, "get bus", "date", daterequest));
		return lstReturn;
	}

	@RequestMapping(value = "/bus/all", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getAllBus() {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		// logger.info(CommonFunction.LogReceive(timekey, "find route", "route",
		// routenumber));
		List<String> lstReturn = new ArrayList<String>();
		lstReturn = DPService.findAllBus();
		logger.info(CommonFunction.LogReturn(timekey, "get all bus", "", ""));
		return lstReturn;
	}
	
	@RequestMapping(value = "/run/all", method = RequestMethod.GET)
	@ResponseBody
	public List<RunInfo> getAllRun() {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		// logger.info(CommonFunction.LogReceive(timekey, "find route", "route",
		// routenumber));
		List<RunInfo> lstReturn = new ArrayList<RunInfo>();
		lstReturn = DPService.findAllRun();
		logger.info(CommonFunction.LogReturn(timekey, "get all run", "", ""));
		return lstReturn;
	}
	@RequestMapping(value = "/run/{daterequest}", method = RequestMethod.GET)
	@ResponseBody
	public List<RunInfo> getRunbyDate(@PathVariable String daterequest) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		df.setLenient(false);
		Date date = new Date();
		try {
			date = df.parse(daterequest);
		} catch (ParseException e) {
			logger.info(CommonFunction.LogError(timekey, "Get run", "date", "" + daterequest, e.getMessage()));
			throw new IllegalArgumentException("Date parameter invalid!!!");
		}		
		List<RunInfo> lstReturn = new ArrayList<RunInfo>();
		lstReturn = DPService.findRunbyDate(date);
		logger.info(CommonFunction.LogReturn(timekey, "get run", "date", daterequest));
		return lstReturn;
	}
	@RequestMapping(value = "/device/all", method = RequestMethod.GET)
	@ResponseBody
	public List<Integer> getAllDevice() {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		// logger.info(CommonFunction.LogReceive(timekey, "find route", "route",
		// routenumber));
		List<Integer> lstReturn = new ArrayList<Integer>();
		lstReturn = DPService.findAllDevice();
		logger.info(CommonFunction.LogReturn(timekey, "find all device", " all device", ""));
		return lstReturn;
	}

	@RequestMapping(value = "/rundir/{devid}/{starttime}/{endtime}", method = RequestMethod.GET)
	@ResponseBody
	public List<RunDirs> getrundirs(@PathVariable int devid, @PathVariable String starttime,
			@PathVariable String endtime) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		df.setLenient(false);
		Date fromtime = new Date();
		Date totime = new Date();
		try {
			starttime = starttime.replace("%20", " ");
			endtime = endtime.replace("%20", " ");
			fromtime = df.parse(starttime);
			totime = df.parse(endtime);
		} catch (ParseException e) {
			logger.info(CommonFunction.LogError(timekey, "Get rundirs", "device", "" + devid, e.getMessage()));
			throw new IllegalArgumentException("Date parameter invalid!!!");
		}

		List<RunDirs> result = DPService.getRundirs(devid, fromtime, totime);
		logger.info(CommonFunction.LogSave(timekey, "Get rundirs", "devid", "" + devid));
		return result;
	}

	@RequestMapping(value = "/rundirbybus/{busnumber}/{starttime}/{endtime}", method = RequestMethod.GET)
	@ResponseBody
	public List<RunDirs> getrundirs(@PathVariable String busnumber, @PathVariable String starttime,
			@PathVariable String endtime) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		df.setLenient(false);
		Date fromtime = new Date();
		Date totime = new Date();
		try {
			starttime = starttime.replace("%20", " ");
			endtime = endtime.replace("%20", " ");
			fromtime = df.parse(starttime);
			totime = df.parse(endtime);
		} catch (ParseException e) {
			logger.info(CommonFunction.LogError(timekey, "Get rundirs by bus", "bus", "" + busnumber, e.getMessage()));
			throw new IllegalArgumentException("Date parameter invalid!!!");
		}

		List<RunDirs> result = DPService.getRundirsbyBus(busnumber, fromtime, totime);
		logger.info(CommonFunction.LogSave(timekey, "Get rundirs by bus", "bus", "" + busnumber));
		return result;
	}
	
	@RequestMapping(value = "/rundirbyrun/{runid:.+}/{runroute}/{devid}", method = RequestMethod.GET)
	@ResponseBody
	public List<RunDirs> getrundirsbyrun(@PathVariable String runid, @PathVariable int runroute, @PathVariable int devid) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		
		List<RunDirs> result = DPService.getRundirsbyRun(runid,runroute,devid);
		logger.info(CommonFunction.LogSave(timekey, "Get rundirs by run", "run", "" + runid));
		return result;
	}
	@RequestMapping(value = "/runinfo/{runroute}", method = RequestMethod.GET)
	@ResponseBody
	public DPRuns2 getruninfo( @PathVariable int runroute) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		
		DPRuns2 result = DPService.getRuninfo(runroute);
		logger.info(CommonFunction.LogSave(timekey, "Get info", "runroute", "" + runroute));
		return result;
	}
	@RequestMapping(value = "/rundirbydevicebus/{devid}/{busnumber}/{starttime}/{endtime}", method = RequestMethod.GET)
	@ResponseBody
	public List<RunDirs> getrundirs(@PathVariable int devid, @PathVariable String busnumber,
			@PathVariable String starttime, @PathVariable String endtime) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		df.setLenient(false);
		Date fromtime = new Date();
		Date totime = new Date();
		try {
			starttime = starttime.replace("%20", " ");
			endtime = endtime.replace("%20", " ");
			fromtime = df.parse(starttime);
			totime = df.parse(endtime);
		} catch (ParseException e) {
			logger.info(CommonFunction.LogError(timekey, "Get rundirs by bus", "bus", "" + busnumber, e.getMessage()));
			throw new IllegalArgumentException("Date parameter invalid!!!");
		}

		List<RunDirs> result = DPService.getRundirsbyDevBus(devid, busnumber, fromtime, totime);
		logger.info(CommonFunction.LogSave(timekey, "Get rundirs by bus", "bus", "" + busnumber));
		return result;
	}
	@RequestMapping(value = "/rundirs/save/{routeid}/{runid:.+}", method = RequestMethod.POST)
	@ResponseBody
	public ActionReturn SaveRunDirs(@PathVariable int routeid,@PathVariable String runid,@RequestBody DPRunDirs[] rundirs) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		// logger.info(CommonFunction.LogReceive(timekey, "add route", "route",
		// route.getrouteNumber()));
		ActionReturn actreturn = new ActionReturn(null);
		actreturn = ActionReturn.Response(DPService.SaveRunDirs(routeid, runid, rundirs));
		logger.info(CommonFunction.LogSave(timekey, "save rundirs", "route/run", routeid+"/"+runid));
		// logger.info("[" + timekey + "] add route is completed.");
		return actreturn;

	}
	
	@RequestMapping(value = "/route/add", method = RequestMethod.POST)
	@ResponseBody
	public ActionReturn Add(@RequestBody DPRoutes route) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		// logger.info(CommonFunction.LogReceive(timekey, "add route", "route",
		// route.getrouteNumber()));
		ActionReturn actreturn = new ActionReturn(null);
		actreturn = ActionReturn.Response(DPService.AddDPRoute(route));
		logger.info(CommonFunction.LogSave(timekey, "add route", "route", route.getrouteNumber()));
		// logger.info("[" + timekey + "] add route is completed.");
		return actreturn;

	}
	@RequestMapping(value = "/routeoha/add", method = RequestMethod.POST)
	@ResponseBody
	public ActionReturn AddRouteOHA(@RequestBody DPRoutes route) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		// logger.info(CommonFunction.LogReceive(timekey, "add route", "route",
		// route.getrouteNumber()));
		ActionReturn actreturn = new ActionReturn(null);
		actreturn = ActionReturn.Response(DPService.AddDPRouteOHA(route));
		logger.info(CommonFunction.LogSave(timekey, "add route for OHA", "route", route.getrouteNumber()));
		// logger.info("[" + timekey + "] add route is completed.");
		return actreturn;

	}
	@RequestMapping(value = "/route/edit", method = RequestMethod.POST)
	@ResponseBody
	public ActionReturn Edit(@RequestBody DPRoutes route) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		// logger.info(CommonFunction.LogReceive(timekey, "add route", "route",
		// route.getrouteNumber()));
		ActionReturn actreturn = new ActionReturn(null);
		actreturn = ActionReturn.Response(DPService.EditDPRoute(route));
		logger.info(CommonFunction.LogSave(timekey, "add route", "route", route.getrouteNumber()));
		// logger.info("[" + timekey + "] add route is completed.");
		return actreturn;

	}
	@RequestMapping(value = "/run/edit", method = RequestMethod.POST)
	@ResponseBody
	public ActionReturn Edit(@RequestBody DPRuns2 run) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		// logger.info(CommonFunction.LogReceive(timekey, "add route", "route",
		// route.getrouteNumber()));
		ActionReturn actreturn = new ActionReturn(null);
		actreturn = ActionReturn.Response(DPService.EditDPRun2(run));
		logger.info(CommonFunction.LogSave(timekey, "edit run", "run", run.getrunId()));
		// logger.info("[" + timekey + "] add route is completed.");
		return actreturn;

	}
	@RequestMapping(value = "/route/delete/{routeid}", method = RequestMethod.GET)
	@ResponseBody
	public ActionReturn DeleteRoute(@PathVariable Integer routeid) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		// logger.info(CommonFunction.LogReceive(timekey, "add route", "route",
		// route.getrouteNumber()));
		ActionReturn actreturn = new ActionReturn(null);
		actreturn = ActionReturn.Response(DPService.DeleteRoute(routeid));
		logger.info(CommonFunction.LogSave(timekey, "delete route", "route", "" + routeid));
		// logger.info("[" + timekey + "] add route is completed.");
		return actreturn;
	}

	@RequestMapping(value = "/run/delete/{runroute}", method = RequestMethod.GET)
	@ResponseBody
	public ActionReturn DeleteRun(@PathVariable Integer runroute) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		// logger.info(CommonFunction.LogReceive(timekey, "add route", "route",
		// route.getrouteNumber()));
		ActionReturn actreturn = new ActionReturn(null);
		actreturn = ActionReturn.Response(DPService.DeleteRun(runroute));
		logger.info(CommonFunction.LogSave(timekey, "delete run", "runroute", "" + runroute));
		// logger.info("[" + timekey + "] add route is completed.");
		return actreturn;
	}

	@RequestMapping(value = "/route/updatedesc/{routeid}/{desc}", method = RequestMethod.GET)
	@ResponseBody
	public ActionReturn UpdateRouteDesc(@PathVariable Integer routeid, @PathVariable String desc) {
		desc = desc.replace("%20", " ");
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		// logger.info(CommonFunction.LogReceive(timekey, "add route", "route",
		// route.getrouteNumber()));
		ActionReturn actreturn = new ActionReturn(null);
		actreturn = ActionReturn.Response(DPService.UpdateRouteDesc(routeid, desc));
		logger.info(CommonFunction.LogSave(timekey, "update route", "route", "" + routeid));
		// logger.info("[" + timekey + "] add route is completed.");
		return actreturn;
	}

	@RequestMapping(value = "/run/updatedesc/{runroute}/{desc}", method = RequestMethod.GET)
	@ResponseBody
	public ActionReturn UpdateRunDesc(@PathVariable Integer runroute, @PathVariable String desc) {
		desc = desc.replace("%20", " ");
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		// logger.info(CommonFunction.LogReceive(timekey, "add route", "route",
		// route.getrouteNumber()));
		ActionReturn actreturn = new ActionReturn(null);
		actreturn = ActionReturn.Response(DPService.UpdateRunDesc(runroute, desc));
		logger.info(CommonFunction.LogSave(timekey, "update run", "runroute", "" + runroute));
		// logger.info("[" + timekey + "] add route is completed.");
		return actreturn;
	}

	@RequestMapping(value = "/stop/delete/{stoprunid}", method = RequestMethod.GET)
	@ResponseBody
	public ActionReturn DeleteStop(@PathVariable Integer stoprunid) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		// logger.info(CommonFunction.LogReceive(timekey, "add route", "route",
		// route.getrouteNumber()));
		ActionReturn actreturn = new ActionReturn(null);
		actreturn = ActionReturn.Response(DPService.DeleteStop(stoprunid));
		logger.info(CommonFunction.LogSave(timekey, "delete stop", "StopRunid", "" + stoprunid));
		// logger.info("[" + timekey + "] add route is completed.");
		return actreturn;
	}

	@RequestMapping(value = "/route/{routenumber}", method = RequestMethod.GET)
	@ResponseBody
	public List<DPRoutes> findRoutebyRouteNumber(@PathVariable String routenumber) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		// logger.info(CommonFunction.LogReceive(timekey, "find route", "route",
		// routenumber));
		List<DPRoutes> lstReturn = new ArrayList<DPRoutes>();
		lstReturn = DPService.findRoutebyRouteNumber(routenumber);
		logger.info(CommonFunction.LogReturn(timekey, "find route", "route", routenumber));
		return lstReturn;

	}

	@RequestMapping(value = "/route/{devid}/{daterequest}", method = RequestMethod.GET)
	@ResponseBody
	public List<DPRoutes> findRoutebyDate(@PathVariable Integer devid, @PathVariable String daterequest) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		df.setLenient(false);
		Date date = new Date();
		try {
			date = df.parse(daterequest);
		} catch (ParseException e) {
			logger.info(CommonFunction.LogError(timekey, "Get route", "devid", "" + devid, e.getMessage()));
			throw new IllegalArgumentException("Date parameter invalid!!!");
		}
		// logger.info(CommonFunction.LogReceive(timekey, "find route", "route",
		// routenumber));
		List<DPRoutes> lstReturn = new ArrayList<DPRoutes>();
		lstReturn = DPService.findRoutebyDevDate(devid, date);
		logger.info(CommonFunction.LogReturn(timekey, "get route", "devid", "" + devid));
		return lstReturn;

	}

	@RequestMapping(value = "/route/all", method = RequestMethod.GET)
	@ResponseBody
	public List<DPRoutes> findRoute() {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		// logger.info(CommonFunction.LogReceive(timekey, "find route", "route",
		// routenumber));
		List<DPRoutes> lstReturn = new ArrayList<DPRoutes>();
		lstReturn = DPService.findAllRoute();
		logger.info(CommonFunction.LogReturn(timekey, "find all route", " all route", ""));
		return lstReturn;

	}
}