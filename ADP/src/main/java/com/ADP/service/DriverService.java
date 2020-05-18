package com.ADP.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ADP.name.model.Driver;
import com.ADP.name.repository.DriverRepository;

@Service
public class DriverService {
	@Autowired
	DriverRepository server;
	
	public Driver findById(String prsId) {
		return server.findBySysId(prsId);
	}
}
