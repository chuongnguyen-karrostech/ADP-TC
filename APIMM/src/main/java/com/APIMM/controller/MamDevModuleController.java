package com.APIMM.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.APIMM.mam.model.MamDefaultModule;
import com.APIMM.mam.model.MamDevModule;
import com.APIMM.mam.nonemodel.NoneMamDevModule;
import com.APIMM.service.MamDevModuleService;
import com.APIMM.service.MamService;
import com.APIMM.util.CommonFunction;
import com.APIMM.util.Mapping;
import com.APIMM.util.message.ActionReturn;
import com.APIMM.util.message.CommonMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/mam")
@CrossOrigin(origins = "*")
@Api(value = "mam")

public class MamDevModuleController {
	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	MamDevModuleService mamService;

	@GetMapping(value = "/devmodule/{appid}/{devid}")
	@ResponseBody
	public List<MamDevModule> GetDeviceModulebyAppId(@PathVariable int appid, @PathVariable int devid) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		// logger.info(CommonFunction.LogReceive(timekey, "get device module", "app",
		// ""+appid+", device= ["+devid+"] "));

		List<MamDevModule> lstReturn = new ArrayList<MamDevModule>();
		lstReturn = mamService.findModulebyDeviceMacAddress(appid, devid);
		logger.info(CommonFunction.LogReturn(timekey, "get device module", "app",
				"" + appid + ", device= [" + devid + "] "));
		return lstReturn;
	}

	@GetMapping(value = "/devmodule/")
	@ResponseBody
	public ResponseEntity<?> findAll() {
		try {

			long timekey = com.APIMM.util.DateFunction.TimeKey();
			logger.info("[" + timekey + "] get all devices module ");
			List<MamDevModule> results = new ArrayList<MamDevModule>();
			Mapping mapping = new Mapping();
			NoneMamDevModule destFields = new NoneMamDevModule();
			results = mamService.findAll();

			Object lstReturn = mapping.FieldMapping(results, destFields);

			return new ResponseEntity<>(lstReturn, HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}

	}

	@PostMapping("/devmodule/update")
	public ResponseEntity<?> update(@RequestBody List<NoneMamDevModule> devModule) {
		try {
			Exception ex = mamService.update(devModule);
			ActionReturn ar = new ActionReturn(null);
			if (ex == null) {
				ar = ActionReturn.Response(CommonMessage.Ok);
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
}
