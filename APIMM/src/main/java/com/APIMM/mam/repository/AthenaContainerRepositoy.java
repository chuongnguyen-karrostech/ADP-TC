package com.APIMM.mam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.APIMM.mam.model.AthenaContainer;

public interface AthenaContainerRepositoy extends JpaRepository<AthenaContainer, Integer>{

	@Query("select a from AthenaContainer a where a.containerType = ?1 order by a.receiveTime asc")
	List<AthenaContainer> findAllContainerByType (String containerType);
	
	@Query("select a from AthenaContainer a order by a.receiveTime asc")
	List<AthenaContainer> findAll();
	
	@Query("select a from AthenaContainer a where a.container = ?1 order by a.receiveTime asc")
	List<AthenaContainer> findAllContainerExisted (String container);
}
