package com.ADP.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ADP.name.model.PrsType;
import com.ADP.name.repository.PrsTypeRepository;

@Service
public class PrsTypeService {
	@Autowired
	PrsTypeRepository server;
	
	public PrsType findById(String prsId) {
		return server.findBySysId(prsId);
	}
}
