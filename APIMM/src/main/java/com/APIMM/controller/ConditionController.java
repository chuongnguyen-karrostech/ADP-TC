package com.APIMM.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.APIMM.mam.model.CheckHardware;
import com.APIMM.mam.model.CheckHardwareResult;
import com.APIMM.mam.model.Condition;
import com.APIMM.service.CheckHardwareService;
import com.APIMM.service.ConditionService;
import com.APIMM.util.CommonFunction;
import com.APIMM.util.SearchObject;
import com.APIMM.util.message.ActionReturn;
import com.APIMM.util.message.CommonMessage;
import com.APIMM.mam.nonemodel.loginWeb;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/mam")
public class ConditionController {
	@Autowired
	ConditionService CondService;
	@Autowired
	CheckHardwareService checkHardwareService;
	
	@GetMapping("/condition")
	public ResponseEntity<?> findAll() {
		try {

			List<Condition> conditions = new ArrayList<>();
			conditions = CondService.findAll();
			return new ResponseEntity<>(conditions, HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}
	@RequestMapping(value = "/condition/add", method = RequestMethod.POST)
	@ResponseBody
	public ActionReturn AddCondition(@RequestBody Condition condition) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		// logger.info(CommonFunction.LogReceive(timekey, "add route", "route",
		// route.getrouteNumber()));
		ActionReturn actreturn = new ActionReturn(null);
		actreturn = ActionReturn.Response(CondService.addCondition(condition));
		
		return actreturn;
	}
	@RequestMapping(value = "/condition/update", method = RequestMethod.PUT)
	@ResponseBody
	public ActionReturn UpdateCondition(@RequestBody Condition condition) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		// logger.info(CommonFunction.LogReceive(timekey, "add route", "route",
		// route.getrouteNumber()));
		ActionReturn actreturn = new ActionReturn(null);
		actreturn = ActionReturn.Response(CondService.updateCondition(condition));
		
		return actreturn;
	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ActionReturn loginWeb(@RequestBody loginWeb obj) {
		
		// logger.info(CommonFunction.LogReceive(timekey, "add route", "route",
		// route.getrouteNumber()));
		ActionReturn actreturn = new ActionReturn(null);
		Boolean loginWeb = CondService.loginWeb(obj.getUserName(), obj.getUserPass());
		if(loginWeb == true) 
		{
			actreturn = ActionReturn.Response(CommonMessage.Ok);
		}else 
		{
			actreturn = ActionReturn.Response(CommonMessage.False);
		}
		
		return actreturn;
	}
	@RequestMapping(value = "/checkhardware", method = RequestMethod.GET)
	@ResponseBody
	public List<CheckHardware> checkHardware() {
		
		// logger.info(CommonFunction.LogReceive(timekey, "add route", "route",
		// route.getrouteNumber()));
		List<CheckHardware> lsthardware = checkHardwareService.FindAll();
		return lsthardware;
	}

	@RequestMapping(value = "/checkhardwareresult", method = RequestMethod.GET)
	@ResponseBody
	public Page<CheckHardwareResult> checkHardwareResult(Pageable requestPage) {		
//		List<CheckHardwareResult> lsthardwareResult = checkHardwareService.findHardwareResult();
//		List<CheckHardwareResult> lsthardwareResultSorted = checkHardwareService.sortListHardwareResult(lsthardwareResult, requestPage);
//		final Page<CheckHardwareResult> pages = new PageImpl<>(lsthardwareResultSorted, requestPage, lsthardwareResultSorted.size());
		Page<CheckHardwareResult> pages = checkHardwareService.findCheckHardwareResult(requestPage);
		return pages;
	}
	@RequestMapping(value = "/checkhardwareresult/search", method = RequestMethod.POST)
	@ResponseBody
	public Page<CheckHardwareResult> Get(@RequestBody List<SearchObject> search, Pageable pageRequest) {
		Page<CheckHardwareResult> searchResultPage = checkHardwareService.Search(search, pageRequest);
		return searchResultPage;
	}
	@PutMapping(value="/checkhardware")
	public ActionReturn updateCheckHardwareOrder(@RequestBody List<CheckHardware> lstCheckHardware) {
		ActionReturn actreturn = new ActionReturn(null);
		Boolean updated = checkHardwareService.updateCheckHardwareOrder(lstCheckHardware);
		if(updated == true) 
		{
			actreturn = ActionReturn.Response(CommonMessage.Ok);
		}else 
		{
			actreturn = ActionReturn.Response(CommonMessage.False);
		}
		return actreturn;

	}
}
