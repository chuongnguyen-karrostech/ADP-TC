package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.APIMM.mam.model.NoneMamDeviceMessage;

public interface NoneMamDevMessageRepository extends JpaRepository<NoneMamDeviceMessage, Integer>, JpaSpecificationExecutor<NoneMamDeviceMessage> {
	
}
