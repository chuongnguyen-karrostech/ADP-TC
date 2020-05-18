package com.APIMM.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APIMM.mam.model.BillingActivity;
import com.APIMM.mam.repository.BillingActivityRepo;

@Service
public class BillingActivityService {

	@Autowired
	BillingActivityRepo repo;
	
	
	public List<BillingActivity> findAll(){
		return repo.findAll();
	}
	
	
	public Exception update(BillingActivity bill) {
		try {
			repo.save(bill);
			return null;
			
		} catch (Exception e) {
			// TODO: handle exception
			return e;
		}
	}
	
	
	public Exception delete(Integer Id) {
		try {
			repo.delete(Id);					
			return null;
			
		} catch (Exception e) {
			// TODO: handle exception
			return e;
		}
	}
	
	public Boolean checkExists(Integer Id) {
		return repo.exists(Id);
	}
	
	
}
