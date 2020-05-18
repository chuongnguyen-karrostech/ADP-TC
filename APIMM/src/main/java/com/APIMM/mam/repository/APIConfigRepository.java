package com.APIMM.mam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.APIMM.mam.model.APIConfig;

public interface APIConfigRepository extends JpaRepository<APIConfig, Integer> {
	List<APIConfig> findAll();
	
	@Query("select s from APIConfig s where s.setting_name = ?1")
	APIConfig findSettingbyName(String setting_name);
}
