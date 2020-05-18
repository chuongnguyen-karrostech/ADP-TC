package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.APIMM.mam.model.MamDevRouteHistorical;
import com.APIMM.mam.model.MamSetting;

import java.lang.String;

//import com.ADP.etdb.model.Run;

import java.util.List;

public interface MamDevRouteHistoricalRepository extends JpaRepository<MamDevRouteHistorical, Integer> {
	
	@Query("select distinct dr from MamDevRouteHistorical dr left join dr.mamDevRuns r "+
	"where dr.devRouteID in ( select max(dr2.devRouteID) from MamDevRouteHistorical dr2 where dr2.mdmDevices.devID = ?1 group by dr2.mdmDevices.devID, dr2.routeNumber)"+			
			" order by dr.devRouteCreated desc")	
	List<MamDevRouteHistorical> findRecentRoutebyDevice(int devid);
	
	@Query("select distinct dr from MamDevRouteHistorical dr left join dr.mamDevRuns r "+
			"where dr.devRouteID in ( select max(dr2.devRouteID) from MamDevRouteHistorical dr2 where dr2.mdmDevices.devID = ?1 and dr2.busId = ?2 "+ 
			"group by dr2.mdmDevices.devID, dr2.routeNumber)"+			
					" order by dr.devRouteCreated desc")	
	List<MamDevRouteHistorical> findRecentRoutebyDeviceByBus(int devid, String busId);
	
	@Query("select distinct dr from MamDevRouteHistorical dr left join dr.mamDevRuns r "+
			"where dr.mdmDevices.devID = ?1 order by dr.devRouteCreated desc")	
	List<MamDevRouteHistorical> findLastRoutebyDevice(int devid);	
	
	@Query("select dr from MamDevRouteHistorical dr where  dr.mdmDevices.devID = ?1 and dr.routeNumber = ?2 and dr.busId = ?3 order by dr.routeNumber")
	List<MamDevRouteHistorical> findbyDevRoute(int devId, String routeNumber, String busId);
	
}