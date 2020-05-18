package com.APIMM.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.APIMM.mam.model.MamDeviceSetting;
import com.APIMM.mam.model.NoneMamDeviceSettingWeb;
import com.APIMM.mam.nonemodel.NoneMamDeviceSetting;
import com.APIMM.service.MamDeviceSettingService;
import com.APIMM.util.CommonFunction;
import com.APIMM.util.Mapping;
import com.APIMM.util.message.ActionReturn;
import com.APIMM.util.message.CommonMessage;

@RestController
@RequestMapping("/mam")
@CrossOrigin("*")
public class MamDeviceSettingController {

	private final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	MamDeviceSettingService service;

	@GetMapping("/deviceSettings")
	public ResponseEntity<?> findAll()
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		try {
			long timekey = com.APIMM.util.DateFunction.TimeKey();

			NoneMamDeviceSetting destFields = new NoneMamDeviceSetting();
			List <MamDeviceSetting> results = new ArrayList<MamDeviceSetting>();
			results = service.findAll();

			Mapping mapping = new Mapping();
			Object lstReturn = mapping.FieldMapping(results, destFields);

			logger.info(CommonFunction.LogReturn(timekey, "get all Device settings", "", ""));
			return new ResponseEntity<>(lstReturn, HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}

	}
	
	@GetMapping(value = "/setting/{appid}/{devid}")
	@ResponseBody
	public List<MamDeviceSetting> findbyAppandDevice(@PathVariable int appid, @PathVariable int devid) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		// logger.info("[" + timekey + "] get settings by application ["+appid+"] and
		// device ["+devid+"].");
		List<MamDeviceSetting> lstReturn = new ArrayList<MamDeviceSetting>();
		lstReturn = service.findSettingbyApp(appid, devid);
		logger.info(
				CommonFunction.LogReturn(timekey, "get settings", "app", "" + appid + ", device= [" + devid + "] "));
		return lstReturn;
	}

	@PostMapping("deviceSettings/update")
	public ResponseEntity<?> update(@RequestBody List<NoneMamDeviceSetting> devSetting) {
		try {
			Exception ex = service.update(devSetting);
			ActionReturn ar = ActionReturn.Response(CommonMessage.Ok);
			if (ex == null) {
				return new ResponseEntity<>(ar, HttpStatus.OK);
			} else {
				ar = ActionReturn.Response(CommonMessage.False);
				return new ResponseEntity<>(ar, HttpStatus.CONFLICT);
			}

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}
	@PutMapping("deviceSettings/updatebyname")
	public ResponseEntity<?> updatebyname(@RequestBody List<NoneMamDeviceSetting> devSetting) {
		try {
			Exception ex = service.update(devSetting);
			ActionReturn ar = ActionReturn.Response(CommonMessage.Ok);
			if (ex == null) {
				return new ResponseEntity<>(ar, HttpStatus.OK);
			} else {
				ar = ActionReturn.Response(CommonMessage.False);
				return new ResponseEntity<>(ar, HttpStatus.CONFLICT);
			}

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>("", HttpStatus.CONFLICT);
		}
	}
}
