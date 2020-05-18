package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.APIMM.mam.model.DriverColorTheme;

@Repository
public interface DriverColorThemeRepository extends JpaRepository<DriverColorTheme, Integer> {
	@Query("select a from DriverColorTheme a where a.driverId = ?1")
    DriverColorTheme findDriverColorTheme(String driver_id);
}
