package com.ADP.name.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ADP.name.model.Driver;

public interface DriverRepository extends JpaRepository<Driver, String> {
	public Driver findBySysId(String sysId);
}
