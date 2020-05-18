package com.APIMM.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.APIMM.mam.model.MamApplication;
import com.APIMM.mam.model.MamFiles;
import com.APIMM.service.MamApplicationService;
import com.APIMM.service.MamService;
import com.APIMM.util.CommonFunction;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(value="/mam")
@CrossOrigin(origins = "*")
@Api(value="mam")
public class MamApplicationController {
	private final Log logger = LogFactory.getLog(this.getClass());
	@Autowired
    MamApplicationService mamService;

	
	@GetMapping(value="/application/{appid}" )
	@ResponseBody	
	public List<MamApplication> findAppbyID(@PathVariable int appid){
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		//logger.info(CommonFunction.LogReceive(timekey, "find application", "id", ""+appid));
		
		List<MamApplication> lstReturn = new ArrayList<MamApplication>();
		lstReturn = mamService.findAppbyID(appid);
		logger.info(CommonFunction.LogReturn(timekey, "find application", "id", ""+appid));
		return lstReturn;		
	}
	
	@GetMapping(value = "/application")
	@ResponseBody
	public List<MamApplication> findAll(){
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		List<MamApplication> lstReturn = new ArrayList<MamApplication>();
		lstReturn = mamService.findAll();
		logger.info(CommonFunction.LogReturn(timekey, "find all application","", ""));
		return lstReturn;		
	}
	
}
