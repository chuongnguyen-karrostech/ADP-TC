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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.APIMM.mam.model.NoneMamDeviceMessage;
import com.APIMM.mam.model.NonePunchLog;
import com.APIMM.mam.model.PunchLog;
import com.APIMM.mam.repository.NonePunchLogRepository;
import com.APIMM.mam.repository.PunchLogRepository;
import com.APIMM.util.DateFunction;
import com.APIMM.util.SearchObject;
import com.APIMM.util.message.CommonMessage;

@Service
public class PunchLogService {

	@Autowired
	PunchLogRepository punchLogRep;
	@Autowired
	NonePunchLogRepository nonepunchLogRep;
	
	public String addPunchLog(PunchLog punch) {
		String flag = CommonMessage.False;
		try {
			Date date = new Date();
			Timestamp dateUpdated = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));
			//Timestamp dateReceived = DateFunction.ChangeTimewithZone(new Timestamp(punch.getDateReceived().getTime()));
			punch.setDateReceived(dateUpdated);			
			punchLogRep.save(punch);
			flag = CommonMessage.Ok;
			return flag;
		}
		catch(Exception e) {
			return e.toString();
		}
	}
	public String checkPunchLog(PunchLog punch) {
		String flag = CommonMessage.False;
		try {
			Date date = new Date();
			Timestamp dateUpdated = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));
			//Timestamp dateReceived = DateFunction.ChangeTimewithZone(new Timestamp(punch.getDateReceived().getTime()));
			List<PunchLog> result = punchLogRep.findByJson(punch.getBusID(), punch.getDriverID(),punch.getActivityCode(), punch.getBillingID(),punch.getDateEvent());
			if (result.size()>0)			
				flag = CommonMessage.Ok;
			else 
				flag = CommonMessage.False;
			return flag;
		}
		catch(Exception e) {
			return e.toString();
		}
	}
	public List<NonePunchLog> getAll() {
		Sort sort = orderBy("dateReceived");
		return nonepunchLogRep.findAll(sort);
	}
	public Page<NonePunchLog> getAllPunchLog(Pageable pageable) {
		//Sort sort = orderBy("dateReceived");
		return nonepunchLogRep.findAll(pageable);
	}
	private Sort orderBy(String name) {
 
		return new Sort(Sort.Direction.DESC,name);
    }
	
	public Page<NonePunchLog> Search(List<SearchObject> lstsearch, Pageable pageRequest) {
		for (SearchObject search : lstsearch) {
			if(search.column.equals("serialNumber")) {				
				search.column = "mdmDevices.devSerialnumber";				
			}
		}
		return nonepunchLogRep.findAll(BuildSpecification(lstsearch), pageRequest);
	}
	
	public Specification<NonePunchLog> BuildSpecification(List<SearchObject> lstsearch) {

		if (lstsearch.isEmpty())
			return new Specification<NonePunchLog>() {
				@Override
				public Predicate toPredicate(Root<NonePunchLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					return null;
				}
			};

		return new Specification<NonePunchLog>() {
			@Override
			public Predicate toPredicate(Root<NonePunchLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

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
