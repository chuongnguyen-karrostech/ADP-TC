package com.APIMM.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.APIMM.etdb.model.Routeview;
import com.APIMM.etdb.model.Stopview;
import com.APIMM.service.EtdbService;
import com.APIMM.util.CommonFunction;

@RestController
@RequestMapping("/etdb/")
@CrossOrigin("*")
public class ETDBController {

	private final Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	EtdbService etdbService ;
	@GetMapping(value = "/loadrouteview/{run:.+}")
	@ResponseBody
	public List<Routeview> findAllRouteView(@PathVariable String run) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		List<Routeview> lstReturn = new ArrayList<Routeview>();
		lstReturn = etdbService.findAllRouteViewbyRun(run);
		logger.info(CommonFunction.LogReturn(timekey, "get all route view", "", ""));
		return lstReturn;
	}
	
	@GetMapping(value = "/loadstopview/{run:.+}")
	@ResponseBody
	public List<Stopview> findAllStopView(@PathVariable String run) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		List<Stopview> lstReturn = new ArrayList<Stopview>();
		lstReturn = etdbService.findAllStopViewbyRun(run);
		logger.info(CommonFunction.LogReturn(timekey, "get all route view", "", ""));
		return lstReturn;
	}
	@GetMapping(value = "/loadrouteview")
	@ResponseBody
	public List<Routeview> findAllRouteView() {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		List<Routeview> lstReturn = new ArrayList<Routeview>();
		lstReturn = etdbService.findAllRouteView();
		logger.info(CommonFunction.LogReturn(timekey, "get all route view", "", ""));
		return lstReturn;
	}
	
	@GetMapping(value = "/loadstopview")
	@ResponseBody
	public List<Stopview> findAllStopView() {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		List<Stopview> lstReturn = new ArrayList<Stopview>();
		lstReturn = etdbService.findAllStopView();
		logger.info(CommonFunction.LogReturn(timekey, "get all route view", "", ""));
		return lstReturn;
	}
}
