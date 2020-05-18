package com.APIMM.mam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.APIMM.configurations.APIMMConfig;
import com.APIMM.mam.model.NoneMamSetting;

public interface MamSettingWebRepository extends JpaRepository<NoneMamSetting, Integer>  {

	@Query("select s from NoneMamSetting s where s.settingType = 'All' and s.settingName not in ("+APIMMConfig.localsetting+","+APIMMConfig.devicesetting+") and s.appID = ?1 order by s.settingName")
	List<NoneMamSetting> findSettingbyApp(int appID);
	
	@Query("select s from NoneMamSetting s where (s.settingType <> 'All' or s.settingName in ("+APIMMConfig.devicesetting+")) and s.appID = ?1 order by s.settingName")
	List<NoneMamSetting> findDeviceSettingbyApp(int appID);
	
	@Query("select s from MamModuleSetting ms inner join ms.mamSettings s where ms.mamModules.moduleId = ?1 order by s.settingName")
	List<NoneMamSetting> findSettingbyModule(int moduleID);
	
	@Query("select ms.settingValue from MamSetting ms where ms.settingName = ?1")
	String findSettingbyName(String settingName);
	
	@Query("select s from MamSetting s where s.appID = ?1 and s.settingName = ?2 order by s.settingName")
	NoneMamSetting findSetting(Integer appID, String settingName);
	
	List<NoneMamSetting> findAll();
}
