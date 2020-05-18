package com.APIMM.etdb.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.APIMM.etdb.model.etdbRunInfo;

@Repository
public interface RunInfoRepository extends JpaRepository<etdbRunInfo, String>{
	@Query("select r from etdbRunInfo r where r.name like ?1")
	etdbRunInfo findRunInfobyRunId(String runId);
	
	@Query("select r from etdbRunInfo r where r.name like ?1 and r.date > ?2")
	etdbRunInfo findByDateGreaterThanEqual (String runId, Date date);
}
