package com.APIMM.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APIMM.mam.model.MamApplication;
import com.APIMM.mam.model.MamModule;
import com.APIMM.mam.repository.MamApplicationRepository;
import com.APIMM.mam.repository.MamModuleRepository;

@Service
public class MamModuleService {

	@Autowired
	MamModuleRepository moduleRepo;	
	@Autowired
	MamApplicationRepository appRepo;

	public List<MamModule> findAll() {
		return moduleRepo.findAll();
	}
	
	public Exception update(MamModule mamModule) {
		try {

			MamApplication mamApplication = appRepo.findOne(1);
			mamModule.setMamApplication(mamApplication);
			moduleRepo.save(mamModule);
			return null;

		} catch (Exception e) {
			// TODO: handle exception
			return e;
		}
	}

	public Boolean checkExists(Integer id) {
		return moduleRepo.exists(id);
	}

}
