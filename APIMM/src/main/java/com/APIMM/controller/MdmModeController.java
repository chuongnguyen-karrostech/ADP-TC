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

import com.APIMM.mam.model.MdmMode;
import com.APIMM.service.MdmModeService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/mdm")
@CrossOrigin("*")
@Api("mdm")
public class MdmModeController {

	@Autowired
	MdmModeService modeService;

	@GetMapping("/modes")
	public ResponseEntity<?> findAll() {
		try {
			List<MdmMode> mdmMode = new ArrayList<>();
			mdmMode = modeService.findAll();
			return new ResponseEntity<>(mdmMode, HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}
}
