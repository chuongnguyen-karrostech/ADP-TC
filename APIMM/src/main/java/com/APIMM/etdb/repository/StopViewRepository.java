package com.APIMM.etdb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.APIMM.etdb.model.Stopview;

@Repository
public interface StopViewRepository extends JpaRepository<Stopview,String> {

	@Query("select r from Stopview r where r.runroute like %?1% order by r.runroute , r.stopseq ")
	List<Stopview>findAllStopViewByRun(String run);
}
