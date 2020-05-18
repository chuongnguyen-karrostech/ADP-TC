package com.ADP.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ADP.etmain.model.Setting;
import com.ADP.etmain.repository.SettingRepository;

@Service
public class SettingService {
	@Autowired
	SettingRepository server;
	
	public Setting findById(String nameId) {
		return server.findByNameId(nameId);
	}
}
