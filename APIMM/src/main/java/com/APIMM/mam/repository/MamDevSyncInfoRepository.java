package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.APIMM.mam.model.MamDevSyncInfo;

import java.lang.String;

//import com.ADP.etdb.model.Run;

import java.util.List;

public interface MamDevSyncInfoRepository extends JpaRepository<MamDevSyncInfo, Integer> {	
	
	
	@Query("select ds from MamDevSyncInfo ds where ds.appID = ?1 and ds.devID = ?2")	
	MamDevSyncInfo findbyAppandDevice(int appid,int devid);
	
}