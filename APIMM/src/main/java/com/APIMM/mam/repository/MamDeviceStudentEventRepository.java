package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.APIMM.mam.model.MamDeviceStudentEvent;

//import com.ADP.etdb.model.Run;

import java.util.List;

public interface MamDeviceStudentEventRepository extends JpaRepository<MamDeviceStudentEvent, Integer> {
	
	List<MamDeviceStudentEvent> findAll();
	
		

}