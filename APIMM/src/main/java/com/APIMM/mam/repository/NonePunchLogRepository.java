package com.APIMM.mam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.APIMM.mam.model.NonePunchLog;

@Repository
public interface NonePunchLogRepository extends JpaRepository<NonePunchLog, Integer>, JpaSpecificationExecutor<NonePunchLog> {

	
}
