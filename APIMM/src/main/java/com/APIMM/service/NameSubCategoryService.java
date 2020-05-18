package com.APIMM.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APIMM.ename.model.NameSubCategory;
import com.APIMM.ename.repository.NameSubCategoryRepo;

@Service
public class NameSubCategoryService {

	@Autowired
	NameSubCategoryRepo nameRepo;
	
	public List<NameSubCategory> findAll(){
		return nameRepo.findAll();
	}
}
