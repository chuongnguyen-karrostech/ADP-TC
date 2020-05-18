package com.APIMM.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.APIMM.etext.model.EventCodes;
import com.APIMM.service.EventCodesService;


@RestController
@RequestMapping("/etext")
@CrossOrigin("*")
public class EventCodeController {
	@Autowired
	EventCodesService service;
	
	@GetMapping("/eventcode")
	public ResponseEntity<?> findAll(){
		try {		
			List<EventCodes> names = new ArrayList<>();			
			names = service.findAll();		
			return new ResponseEntity<>(names, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}
	@GetMapping("/eventcodeedtaweb")
	public ResponseEntity<?> findAllEdtaWeb(){
		try {		
			List<EventCodes> names = new ArrayList<>();			
			names = service.findAllEdtaWeb();		
			return new ResponseEntity<>(names, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}
}
