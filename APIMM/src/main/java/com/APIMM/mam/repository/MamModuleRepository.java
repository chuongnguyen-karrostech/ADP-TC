package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.APIMM.mam.model.MamModule;

import java.lang.String;

//import com.ADP.etdb.model.Run;

import java.util.List;

public interface MamModuleRepository extends JpaRepository<MamModule, Integer> {	
	
	MamModule findByModuleName(String moduleName);
	
	@Query("select m from MamModule m where m.mamApplication.appID = ?1")
	MamModule[] findByApp(Integer appID);
}