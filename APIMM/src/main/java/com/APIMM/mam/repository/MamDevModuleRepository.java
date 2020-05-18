package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.APIMM.mam.model.MamDevModule;

import java.util.List;

public interface MamDevModuleRepository extends JpaRepository<MamDevModule, Integer> {

	@Query("select dm from MamDevModule dm inner join dm.mamModules m "+
			"where dm.mamModules.mamApplication.appID = ?1  and dm.devModuleId in ( select max(dm2.devModuleId) from MamDevModule dm2 where dm2.mdmDevices.devID = ?2 group by dm2.mdmDevices.devID, dm2.mamModules.moduleId)")
	List<MamDevModule> findModulebyDeviceMacAddress(int appid,int devid);
}