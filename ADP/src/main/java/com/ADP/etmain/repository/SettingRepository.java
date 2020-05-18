package com.ADP.etmain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ADP.etmain.model.Setting;

public interface SettingRepository extends JpaRepository<Setting, String> {
	
	public Setting findByNameId(String nameSetting);
	
}