package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.APIMM.mam.model.MdmDeviceMode;

import java.lang.String;

//import com.ADP.etdb.model.Run;

import java.util.List;

public interface MdmDeviceModeRepository extends JpaRepository<MdmDeviceMode, Integer> {
//	@Query("select dm from MdmDeviceMode dm inner join dm.mdmModes m inner join dm.mdmDevices d "
//			+"order by d.devSerialnumber")
//	List<MdmDeviceMode> findAll();
	
	@Query("select dm from MdmDeviceMode dm inner join dm.mdmModes m inner join dm.mdmDevices d "
			+"where d.devMacaddress = ?1 order by dm.devModeUpdated desc")	
	List<MdmDeviceMode> findModebyDeviceMacAddress(String macAddress);
}