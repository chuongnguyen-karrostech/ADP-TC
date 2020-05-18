package com.APIMM.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.APIMM.mam.model.MamDevModule;
import com.APIMM.mam.model.MdmDeviceMode;
import com.APIMM.mam.nonemodel.NoneMdmDeviceMode;
import com.APIMM.service.MdmDevModeService;
import com.APIMM.service.MdmService;
import com.APIMM.util.CommonFunction;
import com.APIMM.util.Mapping;
import com.APIMM.util.message.ActionReturn;
import com.APIMM.util.message.CommonMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/mdm")
@CrossOrigin(origins = "*")
@Api(value = "mdm")
public class MdmDeviceModeController {
	private final Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	MdmDevModeService mdmModeService;

	@GetMapping(value = "/devicemode/{macAddress:.+}")
	@ResponseBody
	public List<MdmDeviceMode> FindRunbyRoutenumber(@PathVariable String macAddress) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		// logger.info("[" + timekey + "] get device mode by macaddress
		// ["+macAddress+"].");
		List<MdmDeviceMode> lstreturn = new ArrayList<MdmDeviceMode>();
		lstreturn = mdmModeService.findModebyDeviceMacAddress(macAddress);
		logger.info(CommonFunction.LogReturn(timekey, "get device mode", "macaddress", macAddress));
		return lstreturn;

	}

	@GetMapping(value = "/devicemode/{macAddress:.+}/{serialNumber:.+}")
	@ResponseBody
	public List<MdmDeviceMode> FindRunbyRoutenumber(@PathVariable String macAddress,
			@PathVariable String serialNumber) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		// logger.info("[" + timekey + "] get device mode by macaddress ["+macAddress+"]
		// and serial ["+serialNumber+"].");
		List<MdmDeviceMode> lstreturn = new ArrayList<MdmDeviceMode>();
		lstreturn = mdmModeService.findModebyDeviceMacAddress(macAddress, serialNumber);
		logger.info(CommonFunction.LogReturn(timekey, "get device mode", "macaddress",
				macAddress + ", serial= [" + serialNumber + "] "));
		return lstreturn;

	}

	@GetMapping("/devicemode")
	public ResponseEntity<?> findAll() {
		try {
			long timekey = com.APIMM.util.DateFunction.TimeKey();
			List<MdmDeviceMode> devModes = new ArrayList<>();
			NoneMdmDeviceMode destFields = new NoneMdmDeviceMode();
			devModes = mdmModeService.findAll();
			
			Mapping mapping = new Mapping();
			Object lstReturn = mapping.FieldMapping(devModes, destFields);
			
			logger.info(CommonFunction.LogReturn(timekey, "Get all device modes", "", ""));
			return new ResponseEntity<>(lstReturn, HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}

	@PostMapping("/devicemode/update")
	public ResponseEntity<?> update(@RequestBody List<NoneMdmDeviceMode> devMode) {
		try {
			Exception ex = mdmModeService.update(devMode);
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

}
