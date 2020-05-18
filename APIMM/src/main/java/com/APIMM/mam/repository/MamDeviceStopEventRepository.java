package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.APIMM.mam.model.MamDeviceStopEvent;

//import com.ADP.etdb.model.Run;

import java.util.List;

public interface MamDeviceStopEventRepository extends JpaRepository<MamDeviceStopEvent, Integer>, JpaSpecificationExecutor <MamDeviceStopEvent> {
	
	List<MamDeviceStopEvent> findAll();
	
		

}