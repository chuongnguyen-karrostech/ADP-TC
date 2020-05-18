package com.ADP.service.Athena.edta;

import com.ADP.etext.model.DriverPayrollMessage;
import com.ADP.etext.model.eDTADriverPayrollMessage;
import com.ADP.service.MMLogsService;
import com.ADP.service.Athena.AthenaService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EdtaAthenaService extends AthenaService {
	@Autowired
	MMLogsService mmlogService;
    
	public Object getActivityCode() {
    	logger.info("get Activity Code from Athena");
        return getRestTemplate(athena_middle_url + "/activity");
    }

    public Object  getBillingType() {
    	logger.info("get Billing Type from Athena");
        return getRestTemplate(athena_middle_url + "/billing");
    }

    public Object getTransaction(Object obj) {
    	logger.info("get Transaction from Athena " + this.objToString(obj));
        return postRestTemplate(athena_middle_url + "/transaction/current", obj);
    }

    public Object addTransaction(Object obj) {
    	logger.info("punch Transaction to Athena " + this.objToString(obj));
//        try {
        	return postRestTemplate(athena_middle_url + "/transaction/records", obj);
//        }
//        catch(Exception ex) {
//        	mmlogService.addContainer(obj, "transaction");
//        	logger.error("punch Transaction Error to Athena " + this.objToString(obj), ex);
//        }
//        return null;
    }

    public Object punchTransaction(DriverPayrollMessage obj) {    	
        eDTADriverPayrollMessage eDTAobj = new eDTADriverPayrollMessage(obj);       
        logger.info("punch Transaction to Athena " + this.objToString(eDTAobj));
        return postRestTemplate(athena_middle_url + "/transaction/driverpayrollmessage",  eDTAobj);        
    }

    public Object getTransactionHistory(Long id) {
    	logger.info("get Transaction History Athena ");
        Map map = new HashMap();
        map.put("id", id);
        return postRestTemplate(athena_middle_url + "/transaction/detail/current", map);
    }
    private String objToString(Object obj) {
    	String strReturn = "";
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		strReturn = mapper.writeValueAsString(obj);
    	}
    	catch( Exception ex) {
    		logger.info("punch Transaction to Athena error : " + ex.toString());
    		throw new IllegalArgumentException(ex.toString());
    	}
    	return strReturn;
    }
}
