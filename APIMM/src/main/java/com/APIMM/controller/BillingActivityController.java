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

import com.APIMM.mam.model.BillingActivity;
import com.APIMM.service.BillingActivityService;
import com.APIMM.util.message.ActionReturn;
import com.APIMM.util.message.CommonMessage;

@RestController
@RequestMapping("/dp") ///
@CrossOrigin("*")
public class BillingActivityController {

	@Autowired
	BillingActivityService service;

	@GetMapping("/billingactivity")
	public ResponseEntity<?> findAll() {
		try {

			List<BillingActivity> bills = new ArrayList<>();
			bills = service.findAll();
			return new ResponseEntity<>(bills, HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}

	@PostMapping("/billingactivity/update")
	public ResponseEntity<?> update(@RequestBody BillingActivity bill) {
		try {

			Integer Id = bill.getId();
			if (!service.checkExists(Id)) {
				return new ResponseEntity<>("Unable update.", HttpStatus.NOT_FOUND);
			}

			Exception ex = service.update(bill);
			if (ex == null) {
				return new ResponseEntity<>("Update success.", HttpStatus.OK);
			} else {
				return new ResponseEntity<>(ex, HttpStatus.CONFLICT);
			}

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}

	@PostMapping("/billingactivity/create")
	public ResponseEntity<?> create(@RequestBody BillingActivity bill) {
		try {
			Exception ex = service.update(bill);
			ActionReturn ar = ActionReturn.Response(CommonMessage.Ok);
			if (ex == null) {			
				return new ResponseEntity<>(ar, HttpStatus.OK);
			} else {
				ar = ar.Response(CommonMessage.False);
				return new ResponseEntity<>(ar, HttpStatus.CONFLICT);
			}

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}
	
	@PostMapping("/billingactivity/delete")
	public ResponseEntity<?> delete(@RequestBody BillingActivity bill) {
		try {
			Integer Id = bill.getId();
			if (!service.checkExists(Id)) {
				return new ResponseEntity<>("Unable delete.", HttpStatus.NOT_FOUND);
			}
			Exception ex = service.delete(Id);
			if (ex == null) {
				return new ResponseEntity<>("Delete success.", HttpStatus.OK);
			} else {
				return new ResponseEntity<>(ex, HttpStatus.CONFLICT);
			}

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}

}
