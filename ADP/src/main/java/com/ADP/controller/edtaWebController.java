package com.ADP.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ADP.name.model.User;
import com.ADP.service.DriverService;
import com.ADP.service.PrsTypeService;
import com.ADP.service.UserService;
import com.ADP.etext.model.DriverPayrollMessage;
import com.ADP.etext.model.EventCodes;
import com.ADP.name.model.NameSubCategory;
import com.ADP.security.edtaWeb;
import com.ADP.service.DriverPayrollMessageService;
import com.ADP.service.edtaWebService;
import com.ADP.util.DateFunction;
import com.ADP.util.Utilities;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/edtaweb")
@CrossOrigin(origins = "*")
@Api(value="edtaweb", description="EdtaWeb")
public class edtaWebController {

	private final Log logger = LogFactory.getLog(this.getClass());	
	@Autowired
	edtaWebService edtaWebServ;
	@Autowired
	DriverPayrollMessageService driverPaySer;
	@Autowired
	private UserService userService;
	@Autowired
	private PrsTypeService prsTypeService;
	
	@GetMapping(value="/geteventcode/{driverid}")
	@ApiOperation(value = "Get event code eDTA web", response = Iterable.class)
	@ResponseBody
	public List<EventCodes> getRecentRoute(@PathVariable String driverid){
		long timekey = DateFunction.TimeKey();	
		
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			//Object[] objArr = edtaWebServ.getEventCodes();
			String objArrString = edtaWebServ.getEventCodesString();						
			EventCodes[] arrEvent = mapper.readValue(objArrString, EventCodes[].class);
			List<EventCodes> lstEvent = new LinkedList<EventCodes>(Arrays.asList(arrEvent));
			DriverPayrollMessage driverpayroll = driverPaySer.loadPunchEdtaWeb(driverid);			
			if (driverpayroll != null)
			{
				for(EventCodes obj : lstEvent) {
					if(driverpayroll.getActivityCode().equals(obj.getStartId())) {
						obj.setSelected(true);
						obj.setVehicleId(driverpayroll.getBusID());
					}
				}
			}
			logger.info(Utilities.LogReceive(timekey, "Get event code eDTAWeb by", "dev id", driverid + ""));
			return lstEvent;			
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "Get event code eDTAWeb error by", "dev id", driverid + "", ex.toString()));			
			throw new IllegalArgumentException(ex.toString());
		}
	}
	@PostMapping(value="/in")
	@ApiOperation(value = "Punch In edta Web", response = Iterable.class)
	@ResponseBody
	public ResponseEntity<DriverPayrollMessage> in(@RequestBody DriverPayrollMessage obj){
		long timekey = DateFunction.TimeKey();
		ObjectMapper mapper = new ObjectMapper();		
		String strObject = "";
		//logger.info(Utilities.LogReceive(timekey, "PunchIn", "driver id", obj.getDriverID()));
		try
		{
			strObject = mapper.writeValueAsString(obj);
			edtaWebServ.savePunchIn(obj);
			//driverPayrollMessageService.punchTransaction(obj);
			ResponseEntity<DriverPayrollMessage> res = new ResponseEntity<DriverPayrollMessage>(obj, HttpStatus.CREATED);
			logger.info(Utilities.LogSave(timekey, "PunchIn eDTA Web", "driver id", strObject));
			return res;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "PunchIn eDTA Web", "driver id", strObject, ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}
	}	
	
	@PostMapping(value="/out/{autoout}")
	@ApiOperation(value = "Punch Out eDTA Web", response = Iterable.class)
	@ResponseBody
	public ResponseEntity<DriverPayrollMessage> out(@RequestBody DriverPayrollMessage obj, @PathVariable Integer autoout){
		long timekey = DateFunction.TimeKey();
		ObjectMapper mapper = new ObjectMapper();		
		String strObject = "";
		//logger.info(Utilities.LogReceive(timekey, "PunchOut", "driver id", obj.getDriverID()));
		try
		{	
			strObject = mapper.writeValueAsString(obj);
			edtaWebServ.savePunchOut(obj, autoout);
			ResponseEntity<DriverPayrollMessage> res = new ResponseEntity<DriverPayrollMessage>(obj, HttpStatus.CREATED);
			logger.info(Utilities.LogSave(timekey, "PunchOut eDTA Web", "driver id", strObject));
			return res;			
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "PunchOut eDTA Web", "driver id", strObject, ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}
	}	
	
	@RequestMapping(value = "/auth", method = { RequestMethod.POST, RequestMethod.OPTIONS })
    public ResponseEntity<?> edtaWebAuthentication(@RequestBody edtaWeb authenticationRequest) throws AuthenticationException {
    	long timekey = DateFunction.TimeKey();	
    	logger.info(Utilities.LogReceive(timekey, "Login", "Driver", authenticationRequest.getEmployeeId()));         
        User user = this.userService.findById(authenticationRequest.getEmployeeId());
        
        // Return job class
        String jobClass = "";
        try {
        	NameSubCategory subCategory = prsTypeService.findById(user.getSysId().trim()).getNameSubCategory();
        	jobClass = subCategory.getName().trim();
        } catch (Exception ex) {
        	jobClass = "";
        }
        if(jobClass.isEmpty()) {
        	ResponseEntity<?> result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("false");
        	logger.info(Utilities.LogReturn(timekey, "Login", "edta web error : ", authenticationRequest.getEmployeeId()));
        	return result;
        }
        else
        {
        	ResponseEntity<?> result = ResponseEntity.ok("true");
        	logger.info(Utilities.LogReturn(timekey, "Login", "edta web", authenticationRequest.getEmployeeId()));
        	return result;
        }        
    }
}
