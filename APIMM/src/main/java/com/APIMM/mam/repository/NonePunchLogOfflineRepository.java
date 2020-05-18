package com.APIMM.mam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.APIMM.mam.model.NonePunchLogOffline;

@Repository
public interface NonePunchLogOfflineRepository extends JpaRepository<NonePunchLogOffline, Integer>, JpaSpecificationExecutor<NonePunchLogOffline> {
	
}
