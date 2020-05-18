package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.APIMM.configurations.APIMMConfig;
import com.APIMM.mam.model.MamDeviceSetting;

import java.lang.String;

//import com.ADP.etdb.model.Run;

import java.util.List;

public interface MamDeviceSettingRepository extends JpaRepository<MamDeviceSetting, Integer> {
	@Query("select s from MamDeviceSetting s where s.appID = ?1 and s.devID = ?2 order by s.settingName")
	List<MamDeviceSetting> findSettingbyApp(int appID,int devID);	
	
	@Query("select s from MamDeviceSetting s where s.appID = ?1 and s.devID = ?2 and s.settingName like ?3 order by s.appID")
	MamDeviceSetting findDeviceSetting(int appID,int devID,String settingName );	

	@Query("select s from MamDeviceSetting s where s.appID = ?1 and s.settingName like ?2 order by s.appID")
	MamDeviceSetting findDeviceSettingName(int appID,String settingName );
}