package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.APIMM.configurations.APIMMConfig;
import com.APIMM.mam.model.MamSetting;

import java.lang.String;

//import com.ADP.etdb.model.Run;

import java.util.List;

public interface MamSettingRepository extends JpaRepository<MamSetting, Integer> {
	@Query("select s from MamSetting s where s.settingType = 'All' and s.settingName not in ("+APIMMConfig.localsetting+","+APIMMConfig.devicesetting+") and s.appID = ?1 order by s.settingName")
	List<MamSetting> findSettingbyApp(int appID);
	
	@Query("select s from MamSetting s where (s.settingType <> 'All' or s.settingName in ("+APIMMConfig.devicesetting+")) and s.appID = ?1 order by s.settingName")
	List<MamSetting> findDeviceSettingbyApp(int appID);
	
	@Query("select s from MamModuleSetting ms inner join ms.mamSettings s where ms.mamModules.moduleId = ?1 order by s.settingName")
	List<MamSetting> findSettingbyModule(int moduleID);
	
	@Query("select ms.settingValue from MamSetting ms where ms.settingName = ?1")
	String findSettingbyName(String settingName);
	
	@Query("select s from MamSetting s where s.appID = ?1 and s.settingName = ?2 order by s.settingName")
	MamSetting findSetting(Integer appID, String settingName);
	
	
}