package com.APIMM.etext.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.APIMM.etext.model.EventCodes;

@Repository
public interface EventCodesRepo extends JpaRepository<EventCodes, String> {

	@Query("select e from EventCodes e where (e.eDTAweb = 1 or e.startId = 88)")
	List<EventCodes>findAllEventCodeEdtaWeb();
}
