package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.APIMM.mam.model.DPRunDirs;
import com.APIMM.mam.model.DPRuns;

//import com.ADP.etdb.model.Run;

import java.util.List;

public interface DPRunDirsRepository extends JpaRepository<DPRunDirs, Integer> {
	@Modifying
	@Transactional
    @Query("delete from DPRunDirs e where e.runRoute= ?1")
    void deletebyRunRoute(Integer runRoute);	
	
	@Query("select e from DPRunDirs e where e.runRoute=?1  order by sequence")
	List<DPRunDirs> findbyRouteRun(Integer runRoute);
	
	List<DPRunDirs> findAll();
	
		

}