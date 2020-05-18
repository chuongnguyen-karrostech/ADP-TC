package com.APIMM.controller;

import java.util.List;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.APIMM.mam.model.MamSetting;
import com.APIMM.mam.model.NoneMamDeviceMessage;
import com.APIMM.mam.model.NoneMamSetting;
import com.APIMM.service.MamSettingService;
import com.APIMM.util.CommonFunction;
import com.APIMM.util.message.ActionReturn;
import com.APIMM.util.message.CommonMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(value = "/mam")
@CrossOrigin(origins = "*")
@Api(value = "mam")
public class MamSettingController {
	private final Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	MamSettingService mamService;

	@GetMapping(value = "/setting/{appid}")
	@ResponseBody
	public List<MamSetting> findAppbyID(@PathVariable int appid) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		List<MamSetting> lstReturn = new ArrayList<MamSetting>();
		lstReturn = mamService.findSettingbyApp(appid);
		logger.info(CommonFunction.LogReturn(timekey, "get settings", "app", "" + appid));
		return lstReturn;
	}

	@GetMapping(value = "/settings")
	@ResponseBody
	public List<MamSetting> findAll() {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		List<MamSetting> lstReturn = new ArrayList<MamSetting>();
		lstReturn = mamService.findAll();
		logger.info(CommonFunction.LogReturn(timekey, "get all settings", "", ""));
		return lstReturn;
	}
	

	@PostMapping("/settings/update")
	public ResponseEntity<?> update(@RequestBody List<MamSetting> mamSetting) {
		try {
			//Exception ex = mamService.update(mamSetting);
			ActionReturn ar = mamService.update(mamSetting);			
			if (ar.result.equals(CommonMessage.False)) {
				return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);				
			} else {
				return new ResponseEntity<>(ar, HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}

	@PostMapping("/settings/create")
	public ResponseEntity<?> create(@RequestBody List<MamSetting> mamSettings) {
		try {
			ActionReturn ar = mamService.create(mamSettings);
			if (ar.result.equals(CommonMessage.False)) {
				return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<>(ar, HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}
	@GetMapping(value="/setting/version")
	@ApiOperation(value = "Get version", response = Iterable.class)
	@ResponseBody
	public String getVersion(){				
		long timekey = com.APIMM.util.DateFunction.TimeKey();	
		String result=com.APIMM.util.CommonFunction.version;
	    logger.info(CommonFunction.LogReturn(timekey, "get version", "app", "" + com.APIMM.util.CommonFunction.version));
		return result;			
	}
}
