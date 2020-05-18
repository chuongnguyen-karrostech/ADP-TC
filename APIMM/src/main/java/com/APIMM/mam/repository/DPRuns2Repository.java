package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.APIMM.mam.model.DPRuns;
import com.APIMM.mam.model.DPRuns2;

//import com.ADP.etdb.model.Run;

import java.util.List;

public interface DPRuns2Repository extends JpaRepository<DPRuns2, Integer> {
	@Modifying
	@Transactional
    @Query("delete from DPRuns2 e where e.runRoute= ?1")
    void deletebyRunRoute(Integer runRoute);
	
	@Modifying
	@Transactional
    @Query("update DPRuns2 r set r.runDesc = ?2 where r.runRoute= ?1")
    void updateRunDesc(Integer runRoute, String desc);
	

	
	@Query("select e from DPRuns2 e inner join e.stops s where e.runRoute= ?1 order by s.stopTime")
	DPRuns2 findbyRunRoute(Integer runRoute);
	
	List<DPRuns2> findAll();	
		

}