package com.APIMM.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.APIMM.mam.model.MamSetting;
import com.APIMM.mam.model.NoneMamSetting;
import com.APIMM.mam.repository.MamSettingRepository;
import com.APIMM.mam.repository.MamSettingWebRepository;
import com.APIMM.util.message.ActionReturn;
import com.APIMM.util.message.CommonMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MamSettingService {

	@Autowired
	MamSettingRepository mamSettingRepo;
	@Autowired
	MamSettingWebRepository mamSettingWebRepo;

	public List<MamSetting> findAll() {
		return mamSettingRepo.findAll();
	}
	
	public List<NoneMamSetting> findAllWeb() {
		return mamSettingWebRepo.findAll();
	}

	public List<MamSetting> findSettingbyApp(int appid) {
		return mamSettingRepo.findSettingbyApp(appid);
	}
	public Page<NoneMamSetting> getAllSettings(Pageable pageable){
		
		return mamSettingWebRepo.findAll(pageable);
		
	}

	public ActionReturn update(List<MamSetting> mamSettings) {
		String flag = CommonMessage.False;
		try {

			Date date = new Date();
			Timestamp dateUpdated = new Timestamp(date.getTime());
			for (MamSetting mamSetting : mamSettings) {
				MamSetting setting = mamSettingRepo.findSetting(1, mamSetting.getSettingName());
				if (setting != null) {
					setting.setSettingUpdated(dateUpdated);
					setting.setSettingValue(mamSetting.getSettingValue());
					setting.setSettingType(mamSetting.getSettingType());
					setting.setSettingDescription(mamSetting.getSettingDescription());
					mamSettingRepo.save(setting);
				}
			}
			flag = String.valueOf("Update Mam_setting successfully !");
			
		} catch (Exception e) {

			return new ActionReturn(CommonMessage.False, e);
		}

		return new ActionReturn(flag);
	}
	
	public ActionReturn updateWeb(List<NoneMamSetting> mamSettings) {
		String flag = CommonMessage.False;
		try {

			Date date = new Date();
			Timestamp dateUpdated = new Timestamp(date.getTime());
			for (NoneMamSetting mamSetting : mamSettings) {
				MamSetting setting = mamSettingRepo.findSetting(1, mamSetting.getSettingName());
				if (setting != null) {
					setting.setSettingUpdated(dateUpdated);
					setting.setSettingValue(mamSetting.getSettingValue());
					setting.setSettingType(mamSetting.getSettingType());
					setting.setSettingDescription(mamSetting.getSettingDescription());
					mamSettingRepo.save(setting);
					flag = String.valueOf("Update "+ setting.getSettingDescription()  +" successfully !");
				}
			}
			//flag = String.valueOf("Update Mam_setting successfully !");
			
		} catch (Exception e) {

			return new ActionReturn(CommonMessage.False, e);
		}

		return new ActionReturn(flag);
	}
	
	public ActionReturn create(List<MamSetting> mamSettings) {
		String flag = CommonMessage.False;
		try {
			Date date = new Date();
			Timestamp dateUpdated = new Timestamp(date.getTime());

			/*
			 * for (int i = 0; i < mamSettings.size(); i++) {
			 * 
			 * @SuppressWarnings("rawtypes") LinkedHashMap lhm = (LinkedHashMap)
			 * mamSettings.get(i); String settingValue = lhm.get("settingValue").toString();
			 * String settingName = lhm.get("settingName").toString(); String settingType =
			 * lhm.get("settingType").toString(); String settingDescription =
			 * lhm.get("settingDescription").toString(); String settingCondtition =
			 * lhm.get("condSetting").toString(); String settingDefault =
			 * lhm.get("defaultValue").toString();
			 * 
			 * MamSetting setting = new MamSetting(); setting.setappID(1);
			 * setting.setSettingName(settingName); setting.setSettingValue(settingValue);
			 * setting.setSettingType(settingType);
			 * setting.setSettingDescription(settingDescription);
			 * setting.setCondSetting(mamSettings.); setting.setSettingUpdated(dateUpdated);
			 * mamSettingRepo.save(setting);
			 * 
			 * }
			 */
			for(MamSetting mamSetting : mamSettings) {
				mamSetting.setappID(1);
				mamSetting.setSettingUpdated(dateUpdated);
				mamSettingRepo.save(mamSetting);
			}
			flag = String.valueOf("Create Mam_setting success !");
		} catch (Exception e) {
			// TODO: handle exception
			return new ActionReturn(CommonMessage.False, e);
		}
		return new ActionReturn(flag);
	}
	
	public ActionReturn createWeb(List<NoneMamSetting> mamSettings) {
		String flag = CommonMessage.False;
		try {
			Date date = new Date();
			Timestamp dateUpdated = new Timestamp(date.getTime());

			/*
			 * for (int i = 0; i < mamSettings.size(); i++) {
			 * 
			 * @SuppressWarnings("rawtypes") LinkedHashMap lhm = (LinkedHashMap)
			 * mamSettings.get(i); String settingValue = lhm.get("settingValue").toString();
			 * String settingName = lhm.get("settingName").toString(); String settingType =
			 * lhm.get("settingType").toString(); String settingDescription =
			 * lhm.get("settingDescription").toString(); String settingCondtition =
			 * lhm.get("condSetting").toString(); String settingDefault =
			 * lhm.get("defaultValue").toString();
			 * 
			 * MamSetting setting = new MamSetting(); setting.setappID(1);
			 * setting.setSettingName(settingName); setting.setSettingValue(settingValue);
			 * setting.setSettingType(settingType);
			 * setting.setSettingDescription(settingDescription);
			 * setting.setCondSetting(mamSettings.); setting.setSettingUpdated(dateUpdated);
			 * mamSettingRepo.save(setting);
			 * 
			 * }
			 */
			for(NoneMamSetting mamSetting : mamSettings) {
				mamSetting.setappID(1);
				mamSetting.setSettingUpdated(dateUpdated);
				mamSetting.setCondId(mamSetting.getCondSetting().getCondId());
				mamSettingWebRepo.save(mamSetting);
			}
			flag = String.valueOf("Create Web Mam_setting success !");
		} catch (Exception e) {
			// TODO: handle exception
			return new ActionReturn(CommonMessage.False, e);
		}
		return new ActionReturn(flag);
	}
	

	public Boolean checkExists(Integer id) {
		return mamSettingRepo.exists(id);
	}

}
