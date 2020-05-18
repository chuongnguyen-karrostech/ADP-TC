package com.APIMM.mam.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.APIMM.mam.model.NonePunchLog;
import com.APIMM.mam.model.PunchLog;

@Repository
public interface PunchLogRepository extends JpaRepository<PunchLog, Integer>, JpaSpecificationExecutor<PunchLog> {

	@Query("select u from PunchLog u where u.busID =?1 and u.driverID =?2 and u.activityCode =?3 and u.billingID =?4 and u.dateEvent=?5 order by u.id")
	List<PunchLog> findByJson(String busID, String driverID, Integer activityCode, String billingID, Date dateEvent);
}
