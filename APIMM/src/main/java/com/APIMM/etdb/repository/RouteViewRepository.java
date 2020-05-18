package com.APIMM.etdb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.APIMM.etdb.model.Routeview;

@Repository
public interface RouteViewRepository extends JpaRepository<Routeview, String> {
	@Query("select r from Routeview r where UPPER(r.runid) like %?1% or UPPER(r.routenumber) like %?1% or UPPER(r.routedescription) like %?1% order by r.routenumber, r.beginplanned")
	List<Routeview>findAllRouteViewByRun(String run);
}
