package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.APIMM.mam.model.NoneMamDeviceEvent;

public interface NoneMamDeviceEventRepository extends JpaRepository<NoneMamDeviceEvent, Integer> , JpaSpecificationExecutor<NoneMamDeviceEvent> {
	

}