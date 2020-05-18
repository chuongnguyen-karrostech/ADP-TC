package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.APIMM.mam.model.MdmDevice;

import java.lang.String;

//import com.ADP.etdb.model.Run;

import java.util.List;

public interface MdmDeviceRepository extends JpaRepository<MdmDevice, Integer> {
	
	MdmDevice findByDevID(int devID);
	
	@Query("SELECT d FROM MdmDevice d WHERE devSerialnumber like %?1%")
	List<MdmDevice> findBySerialNumber(String serialNumber);
	
	@Query("SELECT d FROM MdmDevice d WHERE devSerialnumber = ?1 order by d.devID desc")
	List<MdmDevice> findDeviceBySerialNumber(String serialNumber);
	
	@Query("SELECT d FROM MdmDevice d WHERE devMacaddress = ?1")
	MdmDevice findByMacAddress(String macAddress);
	
	@Query("SELECT COUNT(d) FROM MdmDevice d")
    Long getdevicecount();
}