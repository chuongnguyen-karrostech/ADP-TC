package com.APIMM.etmain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.APIMM.etmain.model.Setting;

public interface SettingRepository extends JpaRepository<Setting, String> {
	Setting findByName(String name);
}