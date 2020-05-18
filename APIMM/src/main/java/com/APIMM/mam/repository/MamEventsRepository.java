package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.APIMM.mam.model.MamEvents;


//import com.ADP.etdb.model.Run;

import java.util.List;

public interface MamEventsRepository extends JpaRepository<MamEvents, Integer>  {
	
	List<MamEvents> findAll();
	
	@Query("select e from MamEvents e order by e.eventName")
	List<MamEvents> findOrderbyEventName();	

}