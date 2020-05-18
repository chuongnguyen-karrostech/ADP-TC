package com.APIMM.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APIMM.etdb.model.Routeview;
import com.APIMM.etdb.model.etdbRunInfo;
import com.APIMM.etdb.model.Stopview;
import com.APIMM.etdb.repository.RouteViewRepository;
import com.APIMM.etdb.repository.RunInfoRepository;
import com.APIMM.etdb.repository.StopViewRepository;

@Service
public class EtdbService {
	@Autowired
	RouteViewRepository RVRepository;
	@Autowired
	StopViewRepository SVRepository;
	@Autowired
	RunInfoRepository runRepository;
	
	public List<Routeview> findAllRouteView() {
		return RVRepository.findAll();
	}
	public List<Routeview> findAllRouteViewbyRun(String run) {
		return RVRepository.findAllRouteViewByRun(run.toUpperCase());
	}
	public List<Stopview> findAllStopView() {
		return SVRepository.findAll();
	}
	public List<Stopview> findAllStopViewbyRun(String run) {
		return SVRepository.findAllStopViewByRun(run);
	}
	public etdbRunInfo findRunInfobyrunId(String runId) {
		return runRepository.findRunInfobyRunId(runId);
	}
	public etdbRunInfo findRunInfobyrunIdandDate(String runId, Date date_request) {
		return runRepository.findByDateGreaterThanEqual(runId, date_request);
	}
}
