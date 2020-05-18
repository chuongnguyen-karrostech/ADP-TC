package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.APIMM.mam.model.DPStops;

//import com.ADP.etdb.model.Run;

import java.util.List;

public interface DPStopsRepository extends JpaRepository<DPStops, Integer> {
	@Modifying
	@Transactional
    @Query("delete from DPStops e where e.stopRunId= ?1")
    void deletebyStopRunId(Integer stopRunId);
	@Query("delete from DPStops e where e.runs.runRoute= ?1")
    void deletebyRunroute(Integer runRoute);
	List<DPStops> findAll();
	
		

}