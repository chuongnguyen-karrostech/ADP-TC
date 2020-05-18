package com.APIMM.controller;

import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.APIMM.mam.model.MamDeviceSetting;
import com.APIMM.mam.model.MamSetting;
import com.APIMM.mam.model.MdmDevice;
import com.APIMM.mam.model.NoneMamDeviceSettingWeb;
import com.APIMM.mam.model.NoneMamSetting;
import com.APIMM.mam.nonemodel.NoneMamDeviceSetting;
import com.APIMM.service.MamDeviceSettingService;
import com.APIMM.service.MamSettingService;
import com.APIMM.service.MamWebService;
import com.APIMM.service.MdmDeviceService;
import com.APIMM.util.CommonFunction;
import com.APIMM.util.message.ActionReturn;
import com.APIMM.util.message.CommonMessage;

@RestController
@RequestMapping("/mam/web")
@CrossOrigin("*")
public class MamWebController {

	private final Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	MamSettingService mamService;
	@Autowired
	MamDeviceSettingService mamDeviceService;
	@Autowired
	MdmDeviceService deviceService;
	@Autowired 
	MamWebService webService;
	
	@GetMapping("/deviceSettings")
	public List<NoneMamDeviceSettingWeb> findAllDeviceSetting() {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		List<NoneMamDeviceSettingWeb> lstReturn = new ArrayList<NoneMamDeviceSettingWeb>();
		lstReturn = mamDeviceService.findAllWeb();
		logger.info(CommonFunction.LogReturn(timekey, "get all device settings", "", ""));
		return lstReturn;
	}
	@GetMapping("/deviceSettings/{appid}/{devId}")
	public Object[] findAllDeviceSettingBySerialNumber(@PathVariable int appid,@PathVariable Integer devId) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		try {
		List<MamSetting> lstSetting = mamService.findSettingbyApp(appid);
		//List<MdmDevice> device = deviceService.findDevicebyserial(serialNumber);
		MdmDevice device = deviceService.findDevicebyid(devId);
		List<MamDeviceSetting> lstDeviceSetting = new ArrayList<>();
		if(device.getdevID() !=null)
		{
			lstDeviceSetting = mamDeviceService.findSettingbyApp(appid, device.getdevID());
		}
			Object[] objArr = lstSetting.toArray();
			Object[] objArr2 = lstDeviceSetting.toArray();
			Object[] r = new Object[objArr.length + objArr2.length];
	        System.arraycopy(objArr, 0, r, 0, objArr.length);
	        System.arraycopy(objArr2, 0, r, objArr.length, objArr2.length);
	        logger.info(CommonFunction.LogReturn(timekey, "get device settings by device ID: ", device.getdevID().toString(), ""));
	        return r;
		}
		catch(Exception e) {
			logger.info(CommonFunction.LogReturn(timekey, "get device settings empty by device ID: ", devId.toString(), ""));
			return null;
		}

	}
	@GetMapping("/deviceSettings/sn/{appid}/{serialNumber}")
	public Object[] findAllDeviceSettingBySerialNumber(@PathVariable int appid,@PathVariable String serialNumber) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		try {
		List<MamSetting> lstSetting = mamService.findSettingbyApp(appid);
		List<MdmDevice> devices = deviceService.findDevicebyserial(serialNumber);
		MdmDevice device = new MdmDevice();
		if(devices.size() > 0)
		{
			device = deviceService.findDevicebyid(devices.get(0).getdevID());
		}
			
		List<MamDeviceSetting> lstDeviceSetting = new ArrayList<>();
		if(device.getdevID() !=null)
		{
			lstDeviceSetting = mamDeviceService.findSettingbyApp(appid, device.getdevID());
		}
			Object[] objArr = lstSetting.toArray();
			Object[] objArr2 = lstDeviceSetting.toArray();
			Object[] r = new Object[objArr.length + objArr2.length];
	        System.arraycopy(objArr, 0, r, 0, objArr.length);
	        System.arraycopy(objArr2, 0, r, objArr.length, objArr2.length);
	        logger.info(CommonFunction.LogReturn(timekey, "get device settings by device ID: ", device.getdevID().toString(), ""));
	        return r;
		}
		catch(Exception e) {
			logger.info(CommonFunction.LogReturn(timekey, "get device settings empty by device ID: ", serialNumber, ""));
			return null;
		}

	}
	
	
	@GetMapping(value = "/settings")
	@ResponseBody
	public List<NoneMamSetting> findAll() {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		List<NoneMamSetting> lstReturn = new ArrayList<NoneMamSetting>();
		lstReturn = mamService.findAllWeb();
		logger.info(CommonFunction.LogReturn(timekey, "get all settings", "", ""));
		return lstReturn;
	}
	
	@PostMapping("/settings/update")
	public ResponseEntity<?> update(@RequestBody List<NoneMamSetting> mamSetting) {
		try {
			//Exception ex = mamService.update(mamSetting);
			ActionReturn ar = mamService.updateWeb(mamSetting);			
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
	public ResponseEntity<?> create(@RequestBody List<NoneMamSetting> mamSettings) {
		try {			
			ActionReturn ar = mamService.createWeb(mamSettings);
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
	@PostMapping("deviceSettings/update")
	public ResponseEntity<?> updateDeviveSetting(@RequestBody List<NoneMamDeviceSetting> devSetting) {
		try {
			Exception ex = mamDeviceService.update(devSetting);
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
	public ResponseEntity<?> updateDeviceSettingByName(@RequestBody List<NoneMamDeviceSetting> devSetting) {
		try {
			Exception ex = mamDeviceService.update(devSetting);
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
