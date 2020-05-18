package com.APIMM.mam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.APIMM.mam.model.NoneMamDeviceSettingWeb;

public interface MamDeviceSettingWebRepository extends JpaRepository<NoneMamDeviceSettingWeb, Integer> , JpaSpecificationExecutor<NoneMamDeviceSettingWeb> {
//		@Query("select s from NoneMamDeviceSettingWeb s where s.appID = ?1 and s.devID = ?2 order by s.mamSettings.settingName")
//		List<NoneMamDeviceSettingWeb> findSettingbyApp(int appID,int devID);	
//		
//		@Query("select s from NoneMamDeviceSettingWeb s where s.appID = ?1 and s.devID = ?2 and s.mamSettings.settingName like ?3 order by s.appID")
//		NoneMamDeviceSettingWeb findDeviceSetting(int appID,int devID,String settingName );	

		@Query("select s from NoneMamDeviceSettingWeb s where s.appID = ?1 and s.mamSettings.settingName like ?2 order by s.appID")
		NoneMamDeviceSettingWeb findDeviceSettingName(int appID,String settingName );
		
		@Query("select s from NoneMamDeviceSettingWeb s where s.appID = ?1 and s.mdmDevices.devSerialnumber like ?2 order by s.appID")
		List<NoneMamDeviceSettingWeb> findDeviceSerialNumber(int appID, String serialNumber );
	
		List<NoneMamDeviceSettingWeb> findAll();
}
