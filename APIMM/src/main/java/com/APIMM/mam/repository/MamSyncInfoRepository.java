package com.APIMM.mam.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.APIMM.mam.model.MamSyncInfo;

public interface MamSyncInfoRepository extends JpaRepository<MamSyncInfo, Integer> {
	@Query("select m from MamSyncInfo m where m.type like 'Update%' and m.date> ?1")
	public List<MamSyncInfo> findByDateGreaterThanEqual (Date date);
	@Query("select m from MamSyncInfo m where m.type like 'Update%' and m.id= ?1")
	public MamSyncInfo findByid(Integer id);
	@Query("select m from MamSyncInfo m where m.type like 'Update%' and m.date= ?1")
	public MamSyncInfo findByDate(Date date);
}
