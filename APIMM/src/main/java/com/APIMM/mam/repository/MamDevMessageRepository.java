package com.APIMM.mam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.APIMM.mam.model.MamDeviceMessage;

import java.lang.String;
import java.util.Date;

//import com.ADP.etdb.model.Run;

import java.util.List;

public interface MamDevMessageRepository extends JpaRepository<MamDeviceMessage, Integer>, JpaSpecificationExecutor<MamDeviceMessage> {
		
	@Query("select s from MamDeviceMessage s where s.devId = ?1 order by s.messageTime")
	List<MamDeviceMessage> findMessagebyDev(int devID);	
	
	@Query("select s from MamDeviceMessage s where s.moduleName = ?1 order by s.messageTime desc")
	List<MamDeviceMessage> findMessagebyModuleName(String modulename);	
	
	@Query("select s from MamDeviceMessage s where s.functionName like ?1 order by s.messageTime desc")
	List<MamDeviceMessage> findMessagebyFunctionName(String functionname);	
	
	@Query("select s from MamDeviceMessage s where s.messageTime between ?1 and ?2 order by s.messageTime desc")
	List<MamDeviceMessage> findMessagebyMessageTime(Date messagetime, Date messagetime2);
	
	@Query("select s from MamDeviceMessage s where s.moduleName = ?1  and s.functionName = ?2 order by s.messageTime desc")
	List<MamDeviceMessage> findMessagebyModuleAndFunction(String modulename, String functionname);
	

}