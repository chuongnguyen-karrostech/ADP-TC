package com.ADP.etext.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ADP.etext.model.DriverPayrollMessage;

@Repository
public interface DriverPayrollMessageRepository extends JpaRepository<DriverPayrollMessage, Long>{
	
	@Query("select u from DriverPayrollMessage u where u.busID =?1 and u.driverID =?2 and u.activityCode =?3 and u.billingID =?4 and u.dateEvent=?5 order by u.id")
	List<DriverPayrollMessage> findByJson(String busID, String driverID, Integer activityCode, String billingID, Date dateEvent);
	
	@Query("select u from DriverPayrollMessage u where u.busID =?1 and u.driverID =?2 order by u.dateEvent desc" )
	List<DriverPayrollMessage> loadByJson(String busID, String driverID);

	@Query("select u from DriverPayrollMessage u where u.driverID =?1 and u.activityCode > 0 order by u.dateAdjusted desc ")
	List<DriverPayrollMessage> loadByEDTAWeb(String driverID);
	
	@Query("select u from DriverPayrollMessage u where u.id =?1")
	DriverPayrollMessage findById(Long id);
}
