package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.APIMM.mam.model.DPRuns;

//import com.ADP.etdb.model.Run;

import java.util.List;

public interface DPRunsRepository extends JpaRepository<DPRuns, Integer> {
	@Modifying
	@Transactional
    @Query("delete from DPRuns e where e.runRoute= ?1")
    void deletebyRunRoute(Integer runRoute);
	
	@Modifying
	@Transactional
    @Query("update DPRuns r set r.runDesc = ?2 where r.runRoute= ?1")
    void updateRunDesc(Integer runRoute, String desc);
	
	@Query("select distinct e.runRoute from DPRuns e where e.routes.routeId=?1 and e.runId=?2 order by runRoute")
	List<Integer> findbyRouteRun(Integer RouteId, String runId);
	
	@Query("select e from DPRuns e where e.runRoute= ?1")
	DPRuns findbyRunRoute(Integer runRoute);
	
	List<DPRuns> findAll();
	
		

}