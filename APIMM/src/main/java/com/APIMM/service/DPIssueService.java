package com.APIMM.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.APIMM.mam.model.NoLocationIssue;
import com.APIMM.mam.model.NonePunchLogOffline;
import com.APIMM.mam.repository.NoLocationIssueRepository;
import com.APIMM.mam.repository.NonePunchLogOfflineRepository;
import com.APIMM.util.SearchObject;
@Service
public class DPIssueService {
	@Autowired
	NonePunchLogOfflineRepository nonepunchLogOfflineRep;
	@Autowired
	NoLocationIssueRepository noLocationRep;
	
	public Page<NonePunchLogOffline> getAllPunchLogOffline(Pageable pageable) {
		//Sort sort = orderBy("dateReceived");
		return nonepunchLogOfflineRep.findAll(pageable);
	}
	public Page<NonePunchLogOffline> searchPunchOffline(List<SearchObject> lstsearch, Pageable pageRequest) {
//		for (SearchObject search : lstsearch) {
//			if(search.column.equals("serialNumber")) {				
//				search.column = "mdmDevices.devSerialnumber";				
//			}
//		}
		return nonepunchLogOfflineRep.findAll(BuildSpecificationOffline(lstsearch), pageRequest);
	}
	public Specification<NonePunchLogOffline> BuildSpecificationOffline(List<SearchObject> lstsearch) {

		if (lstsearch.isEmpty())
			return new Specification<NonePunchLogOffline>() {
				@Override
				public Predicate toPredicate(Root<NonePunchLogOffline> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					return null;
				}
			};

		return new Specification<NonePunchLogOffline>() {
			@Override
			public Predicate toPredicate(Root<NonePunchLogOffline> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

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
	public Page<NoLocationIssue> getMessageNoLocation(Pageable pageable){
		return noLocationRep.findAll(pageable);		
	}

	public Page<NoLocationIssue> SearchNoLocation(List<SearchObject> lstsearch, Pageable pageRequest) {
//		for (SearchObject search : lstsearch) {
//			if(search.column.equals("serialNumber")) {				
//				search.column = "mdmDevices.devSerialnumber";				
//			}
//		}		
		return noLocationRep.findAll(BuildSpecificationNoLocation(lstsearch), pageRequest);
	}
	public Specification<NoLocationIssue> BuildSpecificationNoLocation(List<SearchObject> lstsearch) {

		if (lstsearch.isEmpty())
			return new Specification<NoLocationIssue>() {
				@Override
				public Predicate toPredicate(Root<NoLocationIssue> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					return null;
				}
			};

		return new Specification<NoLocationIssue>() {
			@Override
			public Predicate toPredicate(Root<NoLocationIssue> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

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
