package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.APIMM.mam.model.DPRoutes;

import java.util.Date;

//import com.ADP.etdb.model.Run;

import java.util.List;

public interface DPRoutesRepository extends JpaRepository<DPRoutes, Integer> {
	@Query("select e from DPRoutes e where e.routeNumber= ?1")
	List<DPRoutes> findbyRouteNumber(String routeNumber);
	
	@Query("select e from DPRoutes e where e.dev_Id= ?1 and e.routeDate=?2 order by routeNumber, routeId")
	List<DPRoutes> findRoutebyDevDate(Integer devid, Date date);
	
	@Query("select e from DPRoutes e where e.routeDate=?1 order by routeNumber, routeId")
	List<DPRoutes> findRoutebyDate( Date date);
	
	@Query("select distinct e from DPRoutes e inner join e.runs r inner join r.stops s where e.dev_Id is not null and not (s.stopId like '%new%') order by e.routeNumber, e.routeId")
	List<DPRoutes> findAllBuildRoute();
	
	@Query("select distinct e.routeDate from DPRoutes e order by routeDate")
	List<Date> findAllDate();
	
	@Query("select distinct e.dev_Id from DPRoutes e order by dev_Id")
	List<Integer> findAllDevice();
	
	@Query("select e from DPRoutes e where e.routeNumber= ?1 and e.dev_Id=?2 and e.routeDate=?3")
	DPRoutes findbyRouteNumberDevDate(String routeNumber, Integer devId, Date routeDate);
	@Query("select e from DPRoutes e where e.routeNumber= ?1 and e.dev_Id=?2 order by routeId desc")	
	List<DPRoutes> findbyRouteNumberDev(String routeNumber, Integer devId);	
	@Query("select e from DPRoutes e where e.routeId= ?1")	
	DPRoutes findbyRouteId(Integer RouteId);
	@Modifying
	@Transactional
    @Query("delete from DPRoutes e where e.routeId= ?1")
    void deletebyRouteId(Integer routeId);
	
	@Modifying
	@Transactional
    @Query("update DPRoutes r set r.routeDesc = ?2 where r.routeId= ?1")
    void updateRouteDesc(Integer routeId, String desc);
	
	List<DPRoutes> findAll();

}