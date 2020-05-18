package com.ADP.name.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ADP.name.model.PrsType;

public interface PrsTypeRepository extends JpaRepository<PrsType, String>  {
	public PrsType findBySysId(String sysId);
}
