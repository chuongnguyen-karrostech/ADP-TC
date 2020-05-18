package com.APIMM.etmain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.APIMM.etmain.model.VehicleGpsunitMappings;

public interface VehicleGpsunitMappingRepository extends JpaRepository<VehicleGpsunitMappings, String> {
	
	@Query("select u from VehicleGpsunitMappings u where u.busNumber = ?1 and u.active =1")
	VehicleGpsunitMappings findByBusNumber(String busNumber);
}