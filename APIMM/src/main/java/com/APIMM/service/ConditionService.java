package com.APIMM.service;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.APIMM.mam.model.Condition;
import com.APIMM.mam.model.MamFiles;
import com.APIMM.mam.repository.ConditionRepository;
import com.APIMM.util.DateFunction;
import com.APIMM.util.message.CommonMessage;

@Service
public class ConditionService {

	@Autowired
	ConditionRepository CondRepository ;
	@Autowired
	private Environment env;
	
	public List<Condition> findAll(){
		List <Condition> conditions = new ArrayList <Condition> ();
		conditions = CondRepository.findAll();		
		return conditions;
		//return CondRepository.findAll();
	}
	
	public String addCondition(Condition cond) {
		String flag = CommonMessage.False;		
		try {
			flag = checkCondition(cond);
			if(flag.equalsIgnoreCase(CommonMessage.Ok)) 
			{
			Date date = new Date();
			Timestamp dateUpdated = new Timestamp(date.getTime());
			String joinValue = String.join(";",cond.getCondValue());
			Condition condit = new Condition();
			condit.setCondOperator(cond.getCondOperator());
			condit.setCondType(cond.getCondType());
			condit.setCondUpdated(dateUpdated);
			condit.setCondValue1(joinValue);
			CondRepository.save(condit);
			flag = CommonMessage.Ok;
			}
			return flag;			

		} catch (Exception e) {
			// TODO: handle exception
			return e.toString();
		}
	}
	
	@Transactional
	public String updateCondition(Condition cond) {
		String flag = CommonMessage.False;
		flag = checkValidated(cond);
		if(flag == CommonMessage.Ok)
		{
			Date date = new Date();
			Timestamp lastUpdated = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));
			cond.setCondUpdated(lastUpdated);
			cond.setCondValue1(String.join(";",cond.getCondValue()));
			Condition addCondition = CondRepository.save(cond);
			flag = String.valueOf(addCondition.getCondId());
		}
		return flag;
	}
	
	public String DeleteStop(Condition cond) {
		String flag = CommonMessage.False;
		try {						
			CondRepository.delete(cond);						
			flag = "Delete Stop by StopRunId = "+ cond.getCondId() +" is Ok";						

		} catch (Exception e) {
			flag = e.getMessage();// CommonMessage.False;
			throw new IllegalArgumentException(flag);
		}

		return flag;
	}
	public String checkValidated(Condition cond) {
		String flag = CommonMessage.Ok;
		if(cond.getCondId()==null || cond.getCondId() == 0)
		{
			flag = "Condition Id is empty";
			return flag;
		}
		return flag;
	}
	public String checkCondition(Condition cond) {
		String flag = CommonMessage.Ok;
		String joinValue = String.join(";",cond.getCondValue());
		List<Condition> lstCond = CondRepository.findCondition(cond.getCondOperator(), cond.getCondType(), joinValue);
		if(!lstCond.isEmpty())
		{
			flag = "Condition is existing!";
			return flag=CommonMessage.False;
		}
		return flag;
	}
	
	 private String base64Decode(String token) {
			// TODO Auto-generated method stub
		 	Base64 base64 = new Base64();
	    	byte[] decodedBytes = base64.decode(token.getBytes());    	
	    	String decodeString =  new String(decodedBytes, Charset.forName("UTF-8"));		
	    	int index1 = decodeString.indexOf("3du");
	    	int index2 = decodeString.indexOf("l0g");
	    	int lengthChar = decodeString.length();
//	    	if(lengthChar %4 != 0) {
//	    		return "";
//	    	}
	    	if((index1 < 3) & (index2 >=3))
	    	{
	    		lengthChar = lengthChar - 3;
	    	}
	    	if(lengthChar > 0) {
	    		decodeString = decodeString.substring(3, lengthChar);
	    	}    	
	    	return decodeString;
		}
public Boolean loginWeb (String userName, String token) {
	    	String encodeResult = base64Decode(token);
	    	String strUser = env.getProperty("web.user");
	    	String strPass = env.getProperty("web.pass");
	    	if((userName.equalsIgnoreCase(strUser)) & encodeResult.equals(strPass)) {
	    		return true;
	    	}
	    	return false;
	    }
}
