package com.APIMM.service;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.APIMM.mam.model.MamDeviceMessage;
import com.APIMM.mam.model.NoneMamDeviceMessage;
import com.APIMM.mam.repository.MamDevMessageRepository;
import com.APIMM.mam.repository.MdmDeviceRepository;
import com.APIMM.mam.repository.NoneMamDevMessageRepository;
import com.APIMM.util.DateFunction;
import com.APIMM.util.SearchObject;
import com.APIMM.util.message.CommonMessage;
import com.APIMM.util.message.EventMessage;


@Service
public class MamDevMessageService {	
	@Autowired
	MamDevMessageRepository messageRepository;
	@Autowired
	NoneMamDevMessageRepository None_messageRepository;
	@Autowired
	MdmDeviceRepository deviceRepository;
	
	public String AddMessage(MamDeviceMessage devMessage) {
		String flag = CommonMessage.False;
		try {
			flag = CheckValidate(devMessage);
			if (flag == CommonMessage.Ok) {
				
				Date date = new Date();
				Date oldMessageTime = devMessage.getmessageTime();
				
				Timestamp dateReceived = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));
				devMessage.setlastUpdated(dateReceived);				
				
				MamDeviceMessage addDeviceMessage = messageRepository.save(devMessage);
				
				//boolean bc = StudentSwipeCard(devEvent);
				
				flag = String.valueOf(addDeviceMessage.getid());
			}else{
				throw new IllegalArgumentException(flag);
			}

		} catch (Exception e) {
			flag = e.getMessage();// CommonMessage.False;
			throw new IllegalArgumentException(flag);
		}

		return flag;
	}
	public String CheckValidate(MamDeviceMessage devMessage) {
		String flag = CommonMessage.Ok; 
		if (devMessage.getdevId() <=0)
		{
			flag = EventMessage.DevIdNotExist;
			return flag;
		} else {			
			if (devMessage.getmessageTime() == null)
			{
				flag = EventMessage.EventTimeNull;
				return flag;
			}							
		}
		return flag;
	}
	public Page<NoneMamDeviceMessage> getAllDevMessage(Pageable pageable){
		
		return None_messageRepository.findAll(pageable);
		//return None_messageRepository.findAll(pageable);
		//return pageDeviceMessage ;
	}
	public String findSerialNumberByDevMessId(Integer devId) {
		String SerialNumber = deviceRepository.findByDevID(devId).getDevSerialnumber().toString();
		return SerialNumber;
	}
	public Page<NoneMamDeviceMessage> Search(List<SearchObject> lstsearch, Pageable pageRequest) {
		for (SearchObject search : lstsearch) {
			if(search.column.equals("serialNumber")) {				
				search.column = "mdmDevices.devSerialnumber";				
			}
		}
		return None_messageRepository.findAll(BuildSpecification(lstsearch), pageRequest);
	}
	public List<MamDeviceMessage> getDevMessagebyId(Integer id){
		return messageRepository.findMessagebyDev(id);
	}
	public List<MamDeviceMessage> getDevMessagebyModule(String modulename){
		return messageRepository.findMessagebyModuleName(modulename);
	}
	public List<MamDeviceMessage> getDevMessagebyFunction(String functionname){
		return messageRepository.findMessagebyFunctionName(functionname);
	}
	public List<MamDeviceMessage> getDevMessagebyDate(Date messagetime){
		
		Date messagetime2 = new Date(messagetime.getTime() + (1000*60*60*24));
		return messageRepository.findMessagebyMessageTime(messagetime, messagetime2);
	}
	
	public Specification<NoneMamDeviceMessage> BuildSpecification(List<SearchObject> lstsearch) {

		if (lstsearch.isEmpty())
			return new Specification<NoneMamDeviceMessage>() {
				@Override
				public Predicate toPredicate(Root<NoneMamDeviceMessage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					return null;
				}
			};

		return new Specification<NoneMamDeviceMessage>() {
			@Override
			public Predicate toPredicate(Root<NoneMamDeviceMessage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

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
