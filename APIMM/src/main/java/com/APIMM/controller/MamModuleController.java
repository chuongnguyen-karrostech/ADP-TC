package com.APIMM.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.APIMM.mam.model.MamDefaultModule;
import com.APIMM.mam.model.MamModule;
import com.APIMM.service.MamDefaultModuleService;
import com.APIMM.service.MamModuleService;
import com.APIMM.util.message.ActionReturn;
import com.APIMM.util.message.CommonMessage;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin("*")
@RequestMapping("/mam")
@Api("mam")
public class MamModuleController {

	@Autowired
	MamModuleService moduleService;
	@Autowired
	MamDefaultModuleService defaultmoduleService;
	

	@GetMapping("/modules")
	public ResponseEntity<?> findAll(){
		try {
			List<MamModule> modules = new ArrayList<>();
			modules = moduleService.findAll();
			return new ResponseEntity<>(modules, HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}
	
	@PostMapping("/modules/update")	
	public ResponseEntity<?> update(@RequestBody MamModule modules){
		try {
			
			Integer Id = modules.getModuleId();
			if(!moduleService.checkExists(Id)) {
				return new ResponseEntity<>("Unable update. Modules not found!", HttpStatus.NOT_FOUND);
			}			
			Exception ex = moduleService.update(modules);
			ActionReturn ar = ActionReturn.Response(CommonMessage.Ok);
			if (ex == null) {
				return new ResponseEntity<>(ar, HttpStatus.OK);
			}else {
				ar = ActionReturn.Response(CommonMessage.False);
				return new ResponseEntity<>(ar, HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}
	@GetMapping("/defaultmodules")
	public ResponseEntity<?> findAllDefaultModule(){
		try {
			List<MamDefaultModule> defaultmodules = new ArrayList<>();
			defaultmodules = defaultmoduleService.findAllDefaultModule();
			return new ResponseEntity<>(defaultmodules.get(0), HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}
	@PostMapping("/defaultmodules/update")	
	public ResponseEntity<?> update(@RequestBody MamDefaultModule defaultmodules){
		try {
			
			Integer Id = defaultmodules.getmoduleId();
			if(!moduleService.checkExists(Id)) {
				return new ResponseEntity<>("Unable update. Modules not found!", HttpStatus.NOT_FOUND);
			}			
			Exception ex = defaultmoduleService.updateDefaultModule(defaultmodules);
			ActionReturn ar = ActionReturn.Response(CommonMessage.Ok);
			if (ex == null) {
				return new ResponseEntity<>(ar, HttpStatus.OK);
			}else {
				ar = ActionReturn.Response(CommonMessage.False);
				return new ResponseEntity<>(ar, HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}
	
}






















