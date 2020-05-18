package com.APIMM.mam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.APIMM.mam.model.NoneMamDeviceRunEvent;

public interface NoneMamDeviceRunEventRepository extends JpaRepository<NoneMamDeviceRunEvent, Integer>, JpaSpecificationExecutor <NoneMamDeviceRunEvent> {
	
	List<NoneMamDeviceRunEvent> findAll();
	
		

}