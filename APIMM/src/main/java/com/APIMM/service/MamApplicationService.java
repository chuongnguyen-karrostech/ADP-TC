package com.APIMM.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APIMM.mam.model.MamApplication;
import com.APIMM.mam.repository.MamApplicationRepository;

@Service
public class MamApplicationService {

	@Autowired
	MamApplicationRepository MAM_App_Server;
	
	public List<MamApplication> findAppbyID(int appid) {
		return MAM_App_Server.findAppbyID(appid);
	}
	
	public List<MamApplication> findAll() {
		return MAM_App_Server.findAll();
	}
	
}
