package com.APIMM.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APIMM.etext.model.EventCodes;
import com.APIMM.etext.repository.EventCodesRepo;

@Service
public class EventCodesService {

	@Autowired
	EventCodesRepo eventRepo;
	
	public List<EventCodes> findAll(){
		return eventRepo.findAll();
	}
	
	public List<EventCodes> findAllEdtaWeb(){
		return eventRepo.findAllEventCodeEdtaWeb();
	}
}
