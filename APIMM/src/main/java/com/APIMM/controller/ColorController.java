package com.APIMM.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.APIMM.mam.model.ColorTheme;
import com.APIMM.mam.model.Condition;
import com.APIMM.mam.model.DriverColorTheme;
import com.APIMM.mam.model.Theme;
import com.APIMM.service.ColorThemeService;
import com.APIMM.util.message.ActionReturn;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/mam")
public class ColorController {
	@Autowired
	ColorThemeService colorService ;
		
	@GetMapping("/getcolortheme")
	public ResponseEntity<?> findAll() {
		try {

			List<ColorTheme> lstcolorThemes = new ArrayList<>();
			lstcolorThemes = colorService.findAllColorTheme();
			return new ResponseEntity<>(lstcolorThemes, HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}
	@GetMapping("/drivertheme/{driver_id}")
	public ResponseEntity<?> findAllDriverColorTheme(@PathVariable String driver_id) {
		try {
			DriverColorTheme driverColorTheme = new DriverColorTheme();
			driverColorTheme = colorService.findDriverColorTheme(driver_id);
			return new ResponseEntity<>(driverColorTheme, HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}
	@GetMapping("/getthemes")
	public ResponseEntity<?> findAllTheme() {
		try {

			List<Theme> lstThemes = new ArrayList<>();
			lstThemes = colorService.findAllTheme();
			return new ResponseEntity<>(lstThemes, HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}	
	@PostMapping(value = "/drivertheme")
	@ResponseBody
	public ResponseEntity<?> findAllDriverColorTheme(@RequestBody DriverColorTheme driverTheme) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();	
		DriverColorTheme driverColorTheme = new DriverColorTheme();
		driverColorTheme = colorService.addDriverTheme(driverTheme);		
		return new ResponseEntity<>(driverColorTheme, HttpStatus.OK);
	}
}
