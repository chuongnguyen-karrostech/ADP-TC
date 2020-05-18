package com.APIMM.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.APIMM.mam.model.AthenaContainer;
import com.APIMM.service.AthenaContainerService;
import com.APIMM.util.message.ActionReturn;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/athena/")
public class AthenaContainerController {

	@Autowired
	AthenaContainerService athService;
	
	@GetMapping("/container")
	public ResponseEntity<?> findAll() {
		try {
			List<AthenaContainer> lstAthenaContainer = new ArrayList<>();
			lstAthenaContainer = athService.findAllContainer();
			return new ResponseEntity<>(lstAthenaContainer, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}
	@GetMapping("/container/{containertype}")
	public ResponseEntity<?> findAll(@PathVariable String containertype) {
		try {
			List<AthenaContainer> lstAthenaContainer = new ArrayList<>();
			lstAthenaContainer = athService.findContainer(containertype);
			return new ResponseEntity<>(lstAthenaContainer, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}
	
	@PostMapping(value = "/container/punchtransaction")	
	@ResponseBody
	public ActionReturn AddContainerPunch(@RequestBody Object container) {
		//long timekey = com.APIMM.util.DateFunction.TimeKey();		
		ActionReturn actreturn = new ActionReturn(null);
		String strContainer = objToString(container);
		actreturn = ActionReturn.Response(athService.addContainer(strContainer,"punchtransaction"));		
		return actreturn;
	}
	
	@PostMapping(value = "/container/ivin")
	@ResponseBody
	public ActionReturn AddContainerIvin(@RequestBody Object container) {
		//long timekey = com.APIMM.util.DateFunction.TimeKey();		
		ActionReturn actreturn = new ActionReturn(null);
		String strContainer = objToString(container);
		actreturn = ActionReturn.Response(athService.addContainer(strContainer, "ivin"));		
		return actreturn;
	}
	@PostMapping(value = "/container/transaction")	
	@ResponseBody
	public ActionReturn AddContainerEdta(@RequestBody Object container) {
		//long timekey = com.APIMM.util.DateFunction.TimeKey();		
		ActionReturn actreturn = new ActionReturn(null);
		String strContainer = objToString(container);
		actreturn = ActionReturn.Response(athService.addContainer(strContainer, "transaction"));		
		return actreturn;
	}
	
	@GetMapping(value = "/deletecontainer/{id}")
	@ResponseBody
	public ActionReturn DeleteContainer(@PathVariable Integer id) {
		//long timekey = com.APIMM.util.DateFunction.TimeKey();		
		ActionReturn actreturn = new ActionReturn(null);
		actreturn = ActionReturn.Response(athService.deleteContainer(id));		
		return actreturn;
	}
	
	private String objToString(Object obj)
	{
		ObjectMapper mapper = new ObjectMapper();	
		String strObject = "";
		try
		{			
			strObject = mapper.writeValueAsString(obj);			
			return strObject;
		}
		catch (Exception ex)
		{			
			throw new IllegalArgumentException(ex.toString());			
		}			
	}
}
