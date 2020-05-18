package com.APIMM.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

import com.APIMM.mam.model.MamDeviceMessage;
import com.APIMM.mam.model.NoneMamDeviceMessage;
import com.APIMM.service.MamDevMessageService;
import com.APIMM.util.CommonFunction;
import com.APIMM.util.SearchObject;
import com.APIMM.util.message.ActionReturn;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/mam")
@CrossOrigin("*")
@Api("mam")
public class MamMessageController {

	private final Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	MamDevMessageService messageService;

	@PostMapping("/message/add")
	@ResponseBody
	public ActionReturn add(@RequestBody MamDeviceMessage mamDevMess) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		ActionReturn actreturn = new ActionReturn(null);
		actreturn = ActionReturn.Response(messageService.AddMessage(mamDevMess));
		if (mamDevMess.getdevId()!=null)
			logger.info(CommonFunction.LogSave(timekey, "add message", "device", ""+mamDevMess.getdevId()));		
		else
			logger.info(CommonFunction.LogSave(timekey, "add message", "device", ""));
		return actreturn;		
	}
	
	@RequestMapping(path = "/message", method = RequestMethod.GET)
	@ResponseBody
	public Page<NoneMamDeviceMessage> getmessage(Pageable pageable){
		Page<NoneMamDeviceMessage> lstMessage = messageService.getAllDevMessage(pageable);
		return lstMessage;
	}
	
	@RequestMapping(path = "/message/id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<MamDeviceMessage> getDev(@PathVariable Integer id){
		List<MamDeviceMessage> lstMessage = messageService.getDevMessagebyId(id);
		return lstMessage;
	}
	
	@RequestMapping(path = "/message/function/{functionname}", method = RequestMethod.GET)
	@ResponseBody
	public List<MamDeviceMessage> getFunction(@PathVariable String functionname){
		String receive = functionname.trim();
		List<MamDeviceMessage> lstMessage = messageService.getDevMessagebyFunction(receive);
		return lstMessage;
	}
	
	@RequestMapping(path = "/message/module/{modulename}", method = RequestMethod.GET)
	@ResponseBody
	public List<MamDeviceMessage> getModule(@PathVariable String modulename){
		String receive = modulename.trim();
		List<MamDeviceMessage> lstMessage = messageService.getDevMessagebyModule(receive);
		return lstMessage;
	}
	
	@RequestMapping(path = "/message/date/{date_request}", method = RequestMethod.GET)
	@ResponseBody
	public List<MamDeviceMessage> getDate(@PathVariable String date_request){
		String receive = date_request.trim();
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy"); 
		df.setLenient(false);	
		Date date = new Date();
		try {
			date = df.parse(receive);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw new IllegalArgumentException("Date parameter invalid!!!");
		}
		List<MamDeviceMessage> lstMessage = messageService.getDevMessagebyDate(date);
		return lstMessage;
	}
	@RequestMapping(value = "/message/search", method = RequestMethod.POST)
	@ResponseBody
	public Page<NoneMamDeviceMessage> Get(@RequestBody List<SearchObject> search, Pageable pageRequest) {
		Page<NoneMamDeviceMessage> searchResultPage = messageService.Search(search, pageRequest);
		return searchResultPage;
	}
	
}
