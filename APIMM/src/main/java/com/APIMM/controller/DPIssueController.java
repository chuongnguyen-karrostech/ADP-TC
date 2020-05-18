package com.APIMM.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.APIMM.mam.model.NoLocationIssue;
import com.APIMM.mam.model.NoneMamDeviceMessage;
import com.APIMM.mam.model.NonePunchLogOffline;
import com.APIMM.service.DPIssueService;
import com.APIMM.util.CommonFunction;
import com.APIMM.util.SearchObject;

@RestController
@CrossOrigin("*")
@RequestMapping("/mam")
public class DPIssueController {
	private final Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	DPIssueService issueSer;
	
	@GetMapping(value="/punchoffline")
	@ResponseBody
	public Page<NonePunchLogOffline> getOfflinePunchLog(Pageable pageable){
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		logger.info(CommonFunction.LogReturn(timekey, "get punch log offline", "punch log offline",""));
		Page<NonePunchLogOffline> pagePunchOffline = issueSer.getAllPunchLogOffline(pageable);
		return pagePunchOffline;
	}
	@PostMapping(value="/punchoffline/search")
	@ResponseBody
	public Page<NonePunchLogOffline> searchOfflinePunchLog(@RequestBody List<SearchObject> search , Pageable pageable){
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		logger.info(CommonFunction.LogReturn(timekey, "search punch log offline", "punch log offline",search.toString()));
		Page<NonePunchLogOffline> pagePunchOffline = issueSer.searchPunchOffline(search, pageable);
		return pagePunchOffline;
	}
	@RequestMapping(path = "/getnolocation", method = RequestMethod.GET)
	@ResponseBody
	public Page<NoLocationIssue> getMessageNoLocation(Pageable pageable){
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		logger.info(CommonFunction.LogReturn(timekey, "get start run with no location", "start no location",""));
		Page<NoLocationIssue> lstMessage = issueSer.getMessageNoLocation(pageable);
		return lstMessage;
	}
	@RequestMapping(path = "/getnolocation/search", method = RequestMethod.POST)
	@ResponseBody
	public Page<NoLocationIssue> searchMessageNoLocation(@RequestBody List<SearchObject> search,Pageable pageable){
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		logger.info(CommonFunction.LogReturn(timekey, "get start run with no location", "start no location",""));
		Page<NoLocationIssue> lstMessage = issueSer.SearchNoLocation(search, pageable);
		return lstMessage;
	}
}
