package com.APIMM.mam.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.APIMM.mam.model.MamDeviceEvent;

//import com.ADP.etdb.model.Run;

import java.util.List;

public interface MamDeviceEventRepository extends JpaRepository<MamDeviceEvent, Integer> , JpaSpecificationExecutor<MamDeviceEvent> {
	

}