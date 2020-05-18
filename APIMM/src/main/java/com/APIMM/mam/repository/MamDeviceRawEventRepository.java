package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.APIMM.mam.model.MamDeviceRawEvent;

import java.util.Date;

//import com.ADP.etdb.model.Run;

import java.util.List;

public interface MamDeviceRawEventRepository extends JpaRepository<MamDeviceRawEvent, Integer> {
	
	@Query("select a from MamDeviceRawEvent a where a.devId = ?1 and a.eventTime between ?2 and ?3 order by a.eventTime")
	List<MamDeviceRawEvent> findRunDirs(int devId, Date starttime, Date endTime);
	
	@Query("select a from MamDeviceRawEvent a where a.busId = ?1 and a.eventTime between ?2 and ?3 order by a.devId,a.eventTime")
	List<MamDeviceRawEvent> findRunDirsbyBus(String busnumber, Date starttime, Date endTime);
	
	@Query("select a from MamDeviceRawEvent a where a.devId = ?1 and a.busId = ?2 and a.eventTime between ?3 and ?4 order by a.devId,a.eventTime")
	List<MamDeviceRawEvent> findRunDirsbyDevBus(int devId,String busnumber, Date starttime, Date endTime);
	
	@Query("select distinct a.busId from MamDeviceRawEvent a where a.devId = ?1 and a.eventTime between ?2 and ?3 order by a.busId ")
	List<String> findbus(int devId, Date starttime, Date endTime);
	
	@Query("select distinct a.devId from MamDeviceRawEvent a where a.busId = ?1 and a.eventTime between ?2 and ?3 order by a.busId ")
	List<Integer> finddev(int busId, Date starttime, Date endTime);
	
	List<MamDeviceRawEvent> findAll();
	
		

}