package com.APIMM.mam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.APIMM.mam.model.NoneMamDeviceStopEvent;

public interface NoneMamDeviceStopEventRepository extends JpaRepository<NoneMamDeviceStopEvent, Integer>, JpaSpecificationExecutor <NoneMamDeviceStopEvent> {

}