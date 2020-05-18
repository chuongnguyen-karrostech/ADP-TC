package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.APIMM.mam.model.MamDeviceRunEvent;

//import com.ADP.etdb.model.Run;

import java.util.List;

public interface MamDeviceRunEventRepository extends JpaRepository<MamDeviceRunEvent, Integer>, JpaSpecificationExecutor <MamDeviceRunEvent> {
	
	List<MamDeviceRunEvent> findAll();
	
		

}