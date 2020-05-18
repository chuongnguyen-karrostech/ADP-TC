package com.APIMM.service;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.APIMM.mam.model.CheckHardware;
import com.APIMM.mam.model.CheckHardwareResult;
import com.APIMM.mam.repository.CheckHardwareRepository;
import com.APIMM.mam.repository.CheckHardwareResultRepository;
import com.APIMM.mam.repository.MamDevMessageRepository;
import com.APIMM.mam.repository.MdmDeviceRepository;
import com.APIMM.util.SearchObject;

@Service
public class CheckHardwareService {
	@Autowired
	CheckHardwareRepository checkHardware;
	@Autowired
	MamDevMessageRepository devMessage;
	@Autowired
	MdmDeviceRepository devicesRep;
	@Autowired
	CheckHardwareResultRepository checkHardwareResultRep;
	
	public List<CheckHardware> FindAll()
	{
		List<CheckHardware> lstHardware = new ArrayList<>();
		Sort sortOrder = new Sort(Sort.Direction.DESC,"Critical")
				.and(new Sort(Sort.Direction.ASC,"name"));
		lstHardware = checkHardware.findAll(sortOrder);		
		if(lstHardware.get(0).getOrder()== null)
		{
			int i = 1;
			for(CheckHardware hardware : lstHardware)
			{				
				hardware.setOrder(i++);
			}
		}
		else
		{
			sortOrder= new Sort(Sort.Direction.ASC,"order");
			lstHardware = checkHardware.findAll(sortOrder);	
		}		
		return  lstHardware;
	}
	public Boolean updateCheckHardwareOrder(List<CheckHardware> lstHardware) 
	{	
		if(lstHardware.size() < 1) 
		{
			return false;
		}
		for(CheckHardware hardware : lstHardware)
		{
			checkHardware.save(hardware);
		}
		return true;
	}
//	public List<CheckHardwareResult> findHardwareResult()
//	{
//		List<CheckHardwareResult> lstMessageLMU = new ArrayList<>();
//		List<MamDeviceMessage> lstMessage = devMessage.findMessagebyModuleAndFunction("LMU DIAGNOSTIC","uploadResults");
//		int numberRecords = lstMessage.size();
//		for ( int i = 0 ; i < numberRecords; i ++ )
//		{
//			CheckHardwareResult result = new CheckHardwareResult();
//			String[] lstItem = lstMessage.get(i).getmessage().split(",");
//			result.setIsCellularOK(false);
//			result.setIsDoorCloseOK(false);
//			result.setIsDoorOpenOK(false);
//			result.setIsLocationOK(false);
//			result.setIsStopArmCloseOK(false);
//			result.setIsStopArmOpenOK(false);
//			result.setIsSwipCardOK(false);
//			result.setIsWcLiftDownOK(false);
//			result.setIsWcLiftUpOK(false);
//			for (String item : lstItem)
//			{					
//				if(item.indexOf("VEHICLE UNIT ID") > 0)
//				{
//					String search = "VEHICLE UNIT ID :";
//					String unit = item.substring(item.indexOf("VEHICLE UNIT ID") + search.length(), item.length());
//					result.setUnitId(unit.trim());
//				}
//				if(item.indexOf("CHECK CELLULAR") > 0 && item.indexOf("passed")> 0)
//				{
//					result.setIsCellularOK(true);
//				}				
//				if(item.indexOf("DOOR CLOSE")>0 && item.indexOf("passed") > 0)
//				{
//					result.setIsDoorCloseOK(true);
//				}					
//				if(item.indexOf("DOOR OPEN") >0 && item.indexOf("passed")>0)
//				{
//					result.setIsDoorOpenOK(true);
//				}					
//				if(item.indexOf("LOCATION")>0 && item.indexOf("passed")>0)
//				{
//					result.setIsLocationOK(true);
//				}				
//				if(item.indexOf("STOP ARM CLOSE") >0 && item.indexOf("passed")>0)
//				{
//					result.setIsStopArmCloseOK(true);
//				}				
//				if(item.indexOf("STOP ARM OPEN") >0 && item.indexOf("passed")>0 )
//				{
//					result.setIsStopArmOpenOK(true);
//				}			
//				if(item.indexOf("SWIPE CARD")>0 && item.indexOf("passed")>0)
//				{
//					result.setIsSwipCardOK(true);
//				}				
//				if(item.indexOf("WC LIFT DOWN")>0 && item.indexOf("passed")>0)
//				{
//					result.setIsWcLiftDownOK(true);
//				}			
//				if(item.indexOf("WC LIFT UP")>0 && item.indexOf("passed")>0)
//				{
//					result.setIsWcLiftUpOK(true);
//				}							
//			}
//			result.setDevSerialNumber(devicesRep.findByDevID(lstMessage.get(i).getdevId()).getDevSerialnumber());
//			result.setMessageDeviceTime(lstMessage.get(i).getmessageTime());
//			result.setServerTime(lstMessage.get(i).getlastUpdated());
//			lstMessageLMU.add(result);
//		}		
//		return lstMessageLMU;
//	}
//	public List<CheckHardwareResult> sortListHardwareResult(List<CheckHardwareResult> listSort, Pageable requestPage)
//	{
//		List<CheckHardwareResult> listSorted = listSort;
//		for (CheckHardwareResult r : listSorted )
//		{
//			if (r.getUnitId() == null)
//			{
//				r.setUnitId("not available");
//			}
//		}
//		Comparator<CheckHardwareResult> compareBySerial = (CheckHardwareResult o1, CheckHardwareResult o2) -> o1.getDevSerialNumber().compareTo( o2.getDevSerialNumber() );
//		Comparator<CheckHardwareResult> compareByUnitId = (CheckHardwareResult o1, CheckHardwareResult o2) -> o1.getUnitId().compareTo( o2.getUnitId());		
//		Comparator<CheckHardwareResult> compareByDeviceTime = (CheckHardwareResult o1, CheckHardwareResult o2) -> o1.getMessageDeviceTime().compareTo( o2.getMessageDeviceTime());
//		Comparator<CheckHardwareResult> compareByServerTime = (CheckHardwareResult o1, CheckHardwareResult o2) -> o1.getServerTime().compareTo( o2.getServerTime());
//		switch(requestPage.getSort().toString())
//		{
//			case "devSerialNumber: ASC": 
//			{
//				Collections.sort(listSorted, compareBySerial);
//				break;
//			}
//			case "devSerialNumber: DESC": 
//			{
//				Collections.sort(listSorted, compareBySerial.reversed());
//				break;
//			}
//			case "unitId: ASC": 
//			{
//				Collections.sort(listSorted, compareByUnitId);
//				break;
//			}
//			case "unitId: DESC": 
//			{
//				Collections.sort(listSorted, compareByUnitId.reversed());
//				break;
//			}
//			case "messageDeviceTime: ASC": 
//			{
//				Collections.sort(listSorted, compareByDeviceTime);
//				break;
//			}
//			case "messageDeviceTime: DESC": 
//			{
//				Collections.sort(listSorted, compareByDeviceTime.reversed());
//				break;
//			}
//			case "serverTime: ASC": 
//			{
//				Collections.sort(listSorted, compareByServerTime);
//				break;
//			}
//			case "serverTime: DESC": 
//			{
//				Collections.sort(listSorted, compareByServerTime.reversed());
//				break;
//			}			
//		}		
//		return listSorted;
//	}
	public Page <CheckHardwareResult> findCheckHardwareResult(Pageable requestPage){
		return checkHardwareResultRep.findAll(requestPage);
	}
	public Page<CheckHardwareResult> Search(List<SearchObject> lstsearch, Pageable pageRequest) {
//		for (SearchObject search : lstsearch) {
//			if(search.column.equals("serialNumber")) {				
//				search.column = "mdmDevices.devSerialnumber";				
//			}
//		}
		for (SearchObject search : lstsearch) {
			if(search.column.equals("mdmDevices.devSerialnumber")) {				
				search.column = "serialNumber";				
			}
			if(search.column.equals("serverTime")) {				
				search.column = "messageTime";				
			}
			if(search.column.equals("messageDeviceTime")) {				
				search.column = "lastupdated";				
			}
		}	
		return checkHardwareResultRep.findAll(BuildSpecification(lstsearch), pageRequest);
	}
	
	public Specification<CheckHardwareResult> BuildSpecification(List<SearchObject> lstsearch) {

		if (lstsearch.isEmpty())
			return new Specification<CheckHardwareResult>() {
				@Override
				public Predicate toPredicate(Root<CheckHardwareResult> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					return null;
				}
			};

		return new Specification<CheckHardwareResult>() {
			@Override
			public Predicate toPredicate(Root<CheckHardwareResult> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				for (SearchObject sub_lstsearch : lstsearch) {
					List<Predicate> sub_predicates = new ArrayList<Predicate>();
					//for (SearchObject search : sub_lstsearch) {
						Predicate condition = sub_lstsearch.BuildCondition(root, cb);
						if (condition != null)
							sub_predicates.add(condition);
					//}
					predicates.add(cb.and(sub_predicates.toArray(new Predicate[0])));
				}

				return cb.and(predicates.toArray(new Predicate[0]));
			}
		};
	}
}
