package com.APIMM.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APIMM.mam.model.MamDeviceSetting;
import com.APIMM.mam.model.NoneMamDeviceSettingWeb;
import com.APIMM.mam.nonemodel.NoneMamDeviceSetting;
import com.APIMM.mam.repository.MamDeviceSettingRepository;
import com.APIMM.mam.repository.MamDeviceSettingWebRepository;

@Service
public class MamDeviceSettingService {

	@Autowired
	MamDeviceSettingRepository devSettingRepo;
	@Autowired
	MamDeviceSettingWebRepository devSettingWebRepo;

	public List<MamDeviceSetting> findAll() {
		return devSettingRepo.findAll();
	}
	
	public List<NoneMamDeviceSettingWeb> findAllWeb() {
		return devSettingWebRepo.findAll();
	}
	public List<NoneMamDeviceSettingWeb> findSerialNumberWeb(int appid, String serialNumber) {
		return devSettingWebRepo.findDeviceSerialNumber(appid, serialNumber);
	}


	public List<MamDeviceSetting> findSettingbyApp(int appid, int devid)
	{			
		return devSettingRepo.findSettingbyApp(appid, devid);
	}
	
	@Transactional
	public Exception update(List<NoneMamDeviceSetting> devSettings) {
		try {
			Date date = new Date();
			Timestamp dateUpdated = new Timestamp(date.getTime());
			for (NoneMamDeviceSetting mamDeviceSetting : devSettings) {			
				MamDeviceSetting devSetting = devSettingRepo.findDeviceSetting(1, mamDeviceSetting.getdevID(), mamDeviceSetting.getSettingName());
				if(devSetting != null) {
					devSetting.setSettingValue(mamDeviceSetting.getSettingValue());
					devSetting.setSettingUpdated(dateUpdated);
					devSettingRepo.save(devSetting);	
				}	
			}
			return null;
		} catch (Exception e) {
			return e;
		}
	}

	@Transactional
	public Exception updatebyname(List<NoneMamDeviceSetting> devSettings) {
		try {
			Date date = new Date();
			Timestamp dateUpdated = new Timestamp(date.getTime());
			for (NoneMamDeviceSetting mamDeviceSetting : devSettings) {			
				MamDeviceSetting devSetting = devSettingRepo.findDeviceSetting(1, mamDeviceSetting.getdevID(), mamDeviceSetting.getSettingName());
				if(devSetting != null) {
					devSetting.setSettingValue(mamDeviceSetting.getSettingValue());
					devSetting.setSettingUpdated(dateUpdated);
					devSettingRepo.save(devSetting);	
				}	
			}
			return null;
		} catch (Exception e) {
			return e;
		}
	}

	public Boolean checkExists(Integer settingId) {
		return devSettingRepo.exists(settingId);
	}

}
