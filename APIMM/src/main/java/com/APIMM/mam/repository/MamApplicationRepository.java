package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.APIMM.mam.model.MamApplication;

import java.lang.String;

//import com.ADP.etdb.model.Run;

import java.util.List;

public interface MamApplicationRepository extends JpaRepository<MamApplication, Integer> {
	@Query("select a from MamApplication a where a.appID = ?1")
	List<MamApplication> findAppbyID(int appID);

}