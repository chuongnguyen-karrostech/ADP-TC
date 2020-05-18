package com.APIMM.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APIMM.mam.model.MamDevModule;
import com.APIMM.mam.nonemodel.NoneMamDevModule;
import com.APIMM.mam.repository.MamDevModuleRepository;

@Service
public class MamDevModuleService {

	@Autowired
	MamDevModuleRepository devModuleRepo;

	public List<MamDevModule> findAll() {
		return devModuleRepo.findAll();
	}

	@Transactional
	public Exception update(List<NoneMamDevModule> devModules) {
		try {
			Date date = new Date();
			Timestamp dtUpdate = new Timestamp(date.getTime());
			for (NoneMamDevModule mamDevModule : devModules) {

				MamDevModule devModule = devModuleRepo.findOne(mamDevModule.getDevModuleId());
				if (devModule != null) {
					devModule.setDevModuleUpdated(dtUpdate);
					devModule.setDevModuleEnable(mamDevModule.getDevModuleEnable());
					devModule.setisDefault(mamDevModule.getisDefault());
					devModuleRepo.save(devModule);
				}
				List<MamDevModule> findDevModules = findModulebyDeviceMacAddress(1, devModule.getDevId());
				for(MamDevModule findDevId : findDevModules) {
					if(findDevId.getDevModuleId() != mamDevModule.getDevModuleId() ) {
						if(mamDevModule.getisDefault()) {
							findDevId.setisDefault(!mamDevModule.getisDefault());
							devModuleRepo.save(findDevId);
						}
					}
				}
			}
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			return e;
		}
	}

	public Boolean checkExists(Integer Id) {
		return devModuleRepo.exists(Id);
	}

	public List<MamDevModule> findModulebyDeviceMacAddress(int appid, int devid) {
		int defaulemoduleid = 0;
		// List<MamDefaultModule>
		// lstdm=MAM_DefaultModule_Server.findModulebyAppId(appid);
		// if (lstdm.size()>0)defaulemoduleid = lstdm.get(0).getmoduleId();
		List<MamDevModule> result = devModuleRepo.findModulebyDeviceMacAddress(appid, devid);
		// for (int i=0; i<result.size();i++){
		// MamDevModule devmodule = result.get(i);
		// int moduleid = devmodule.getMamModules().getModuleId();
		// if (moduleid ==defaulemoduleid) devmodule.setisDefault(true);
		// else devmodule.setisDefault(false);
		// }

		return result;
	}

}
