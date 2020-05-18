package com.APIMM.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.APIMM.mam.model.DPRoutes;
import com.APIMM.mam.model.MdmDevice;
import com.APIMM.service.MdmDeviceService;
import com.APIMM.service.MdmService;
import com.APIMM.util.CommonFunction;
import com.APIMM.util.message.ActionReturn;
import com.APIMM.util.message.CommonMessage;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/mam")
@CrossOrigin(origins = "*")
@Api(value = "mam")
public class MamDeviceController {

	private final Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	MdmDeviceService mdmService;

	@RequestMapping(value = "/devices", method = RequestMethod.GET)
	@ResponseBody
	public List<MdmDevice> findAll() {
		long timekey = com.APIMM.util.DateFunction.TimeKey();

		List<MdmDevice> results = new ArrayList<MdmDevice>();
		results = mdmService.findAll();
		logger.info(CommonFunction.LogSave(timekey, "get all mdm devices", "", ""));
		return results;
	}

	@PostMapping("/device/update")
	public ResponseEntity<?> update(@RequestBody MdmDevice device) {
		try {

			Integer Id = device.getdevID();
			if (!mdmService.checkExists(Id)) {
				return new ResponseEntity<>("Unable update. Device not found", HttpStatus.NOT_FOUND);
			}
			Exception ex = mdmService.update(device);
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

	@PostMapping("/device/create")
	public ResponseEntity<?> save(@RequestBody MdmDevice device) {
		try {
			String macAddress = device.getDevMacaddress();
			String serialNumber = device.getDevSerialnumber();

			Exception ex = mdmService.save(macAddress, serialNumber);
			ActionReturn ar = ActionReturn.Response(CommonMessage.Ok);
			if (ex == null) {
				return new ResponseEntity<>(ar, HttpStatus.CREATED);
			} else {
				ar = ActionReturn.Response(CommonMessage.False);
				return new ResponseEntity<>(ar, HttpStatus.CONFLICT);
			}

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}

	@PostMapping("/device/delete")
	public ResponseEntity<?> delete(@RequestBody MdmDevice device) {
		try {
			Exception ex = mdmService.delete(device);
			ActionReturn ar = ActionReturn.Response(CommonMessage.Ok);
			if (ex == null) {
				return new ResponseEntity<>(ar, HttpStatus.CREATED);
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
