package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.APIMM.mam.model.MamDefaultModule;

import java.util.List;

public interface MamDefaultModuleRepository extends JpaRepository<MamDefaultModule, Integer> {

	@Query("select dm from MamDefaultModule dm where dm.appId = ?1 order by dm.defaultModuleCreated desc ")
	List<MamDefaultModule> findModulebyAppId(int appid);
}