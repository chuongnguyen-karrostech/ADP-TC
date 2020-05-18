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

import com.APIMM.ename.model.NameSubCategory;
import com.APIMM.service.NameSubCategoryService;

@RestController
@RequestMapping("/ename")
@CrossOrigin("*")
public class NameSubCategoryController {

	
	@Autowired
	NameSubCategoryService service;
	
	@GetMapping("/namesubcategory")
	public ResponseEntity<?> findAll(){
		try {		
			List<NameSubCategory> names = new ArrayList<>();			
			names = service.findAll();
			for(NameSubCategory name : names) {
				name.setDesc(name.getDesc().trim());
			}
			return new ResponseEntity<>(names, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}
	
}
