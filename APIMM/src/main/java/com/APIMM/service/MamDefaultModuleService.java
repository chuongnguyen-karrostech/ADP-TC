package com.APIMM.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APIMM.mam.model.MamApplication;
import com.APIMM.mam.model.MamDefaultModule;
import com.APIMM.mam.repository.MamApplicationRepository;
import com.APIMM.mam.repository.MamDefaultModuleRepository;

@Service
public class MamDefaultModuleService {
	@Autowired
	MamDefaultModuleRepository defaultmoduleRepo;
	@Autowired
	MamApplicationRepository appRepo;
	public List<MamDefaultModule> findAllDefaultModule() {
		//return defaultmoduleRepo.findAll();
		return defaultmoduleRepo.findModulebyAppId(1);
	}

	public Exception updateDefaultModule(MamDefaultModule mamDefaultModule) {
		try {
			Date date = new Date();
			Timestamp dateUpdated = new Timestamp(date.getTime());
			MamApplication mamApplication = appRepo.findOne(1);					
			mamDefaultModule.setappId(mamApplication.getappID());
			mamDefaultModule.setDefaultModuleCreated(dateUpdated);
			defaultmoduleRepo.save(mamDefaultModule);
			return null;

		} catch (Exception e) {
			// TODO: handle exception
			return e;
		}
	}
}
