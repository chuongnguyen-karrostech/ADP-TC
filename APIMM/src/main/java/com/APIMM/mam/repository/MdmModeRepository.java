package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.APIMM.mam.model.MdmMode;

import java.lang.String;

//import com.ADP.etdb.model.Run;

import java.util.List;

public interface MdmModeRepository extends JpaRepository<MdmMode, Integer> {	
	
	MdmMode findByModeName(String modeName);
}