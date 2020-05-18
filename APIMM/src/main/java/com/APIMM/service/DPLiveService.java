package com.APIMM.service;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.jpa.internal.EntityManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.APIMM.mam.model.DPRoutes;
import com.APIMM.mam.model.DPRunDirs;
import com.APIMM.mam.model.DPRuns;
import com.APIMM.mam.model.DPRuns2;
import com.APIMM.mam.model.DPStops;
import com.APIMM.mam.model.DPStops2;
import com.APIMM.mam.model.MamApplication;
import com.APIMM.mam.model.MamDevModule;
import com.APIMM.mam.model.MamDeviceRawEvent;
import com.APIMM.mam.model.MdmDevice;
import com.APIMM.mam.model.RunDirs;
import com.APIMM.mam.model.RunInfo;
import com.APIMM.mam.repository.DPRoutesRepository;
import com.APIMM.mam.repository.DPRunDirsRepository;
import com.APIMM.mam.repository.DPRuns2Repository;
import com.APIMM.mam.repository.DPRunsRepository;
import com.APIMM.mam.repository.DPStopsRepository;
import com.APIMM.mam.repository.MamDeviceRawEventRepository;
import com.APIMM.mam.repository.MdmDeviceRepository;
import com.APIMM.util.AppConst;
import com.APIMM.util.DateFunction;
import com.APIMM.util.message.CommonMessage;
import com.APIMM.util.message.EventMessage;
import com.APIMM.util.message.RouteHistoricalMessage;

@Service
public class DPLiveService {
	@Autowired
	DPRoutesRepository  Server;
	@Autowired
	DPRunsRepository  RunServer;
	@Autowired
	DPRuns2Repository  Run2Server;
	@Autowired
	DPRunDirsRepository  RunDirsServer;
	@Autowired
	DPStopsRepository  StopServer;
	@Autowired
	MdmDeviceRepository  DeviceServer;
	@Autowired
	MamDeviceRawEventRepository  RawEventServer;
	public List<RunDirs> getRundirs(int devid, Date starttime, Date endtime)
	{
		List<MamDeviceRawEvent> raws = RawEventServer.findRunDirs(devid, starttime, endtime);
		List<RunDirs> result = new ArrayList<RunDirs>();
		for (int i = 0; i< raws.size();i++){
			MamDeviceRawEvent r = raws.get(i);			
			RunDirs rundir = new RunDirs();
			rundir.setdevId(r.getDevId());
			rundir.setbusNumber(r.getBusId());
			rundir.settime(r.getEventTime());
			rundir.setsequence(i+1);
			rundir.setLatitude(r.getLatitude());
			rundir.setLongitude(r.getLongitude());
			result.add(rundir);
		}
		return result;
	}
	public List<RunDirs> getRundirsbyBus(String busnumber, Date starttime, Date endtime)
	{
		List<MamDeviceRawEvent> raws = RawEventServer.findRunDirsbyBus(busnumber, starttime, endtime);
		List<RunDirs> result = new ArrayList<RunDirs>();
		for (int i = 0; i< raws.size();i++){
			MamDeviceRawEvent r = raws.get(i);			
			RunDirs rundir = new RunDirs();
			rundir.setdevId(r.getDevId());
			rundir.setbusNumber(r.getBusId());
			rundir.settime(r.getEventTime());
			rundir.setsequence(i+1);
			rundir.setLatitude(r.getLatitude());
			rundir.setLongitude(r.getLongitude());
			result.add(rundir);
		}
		return result;
	}
	public DPRuns2 getRuninfo(int runroute)
	{
		DPRuns2 run = Run2Server.findbyRunRoute(runroute);		
		return run;
	}
	public List<RunDirs> getRundirsbyRun(String runid, int runroute, int devid)
	{
		List<RunDirs> result = new ArrayList<RunDirs>();
		List<DPRunDirs> rundirs = RunDirsServer.findbyRouteRun(runroute);
		if (rundirs.size()>0){
			for (int i = 0; i< rundirs.size();i++){
				DPRunDirs r = rundirs.get(i);			
				RunDirs rundir = new RunDirs();
				rundir.setdevId(r.getdevId());
				rundir.setbusNumber(r.getbusNumber());
				rundir.settime(r.gettime());
				rundir.setsequence(i+1);
				rundir.setLatitude(r.getlatitude());
				rundir.setLongitude(r.getlongitude());
				result.add(rundir);
			}
		}else{		
			DPRuns run = RunServer.findbyRunRoute(runroute);					
			List<MamDeviceRawEvent> raws = RawEventServer.findRunDirs(devid, run.getactualRunStart(), run.getactualRunEnd());		
			for (int i = 0; i< raws.size();i++){
				MamDeviceRawEvent r = raws.get(i);			
				RunDirs rundir = new RunDirs();
				rundir.setdevId(r.getDevId());
				rundir.setbusNumber(r.getBusId());
				rundir.settime(r.getEventTime());
				rundir.setsequence(i+1);
				rundir.setLatitude(r.getLatitude());
				rundir.setLongitude(r.getLongitude());
				result.add(rundir);
			}
		}
		return result;
	}
	public List<RunDirs> getRundirsbyDevBus(int devid,String busnumber, Date starttime, Date endtime)
	{
		List<MamDeviceRawEvent> raws = RawEventServer.findRunDirsbyDevBus(devid,busnumber, starttime, endtime);
		List<RunDirs> result = new ArrayList<RunDirs>();
		for (int i = 0; i< raws.size();i++){
			MamDeviceRawEvent r = raws.get(i);			
			RunDirs rundir = new RunDirs();
			rundir.setdevId(r.getDevId());
			rundir.setbusNumber(r.getBusId());
			rundir.settime(r.getEventTime());
			rundir.setsequence(i+1);
			rundir.setLatitude(r.getLatitude());
			rundir.setLongitude(r.getLongitude());
			result.add(rundir);
		}
		return result;
	}
	@Transactional
	public String SaveRunDirs(int routeId, String runId, DPRunDirs[] rundirs) {
		String flag = CommonMessage.False;
		try {
			List<Integer> runroutes = RunServer.findbyRouteRun(routeId, runId);
			if (runroutes.size()==0){
				throw new IllegalArgumentException("no run have route/run is "+routeId+"/"+runId);
			}
			else if (runroutes.size()>1){
				throw new IllegalArgumentException("more than one run have route/run is "+routeId+"/"+runId);
			}else{
				int runroute = runroutes.get(0);	
				RunDirsServer.deletebyRunRoute(runroute);
				Date date = new Date();
				Timestamp lastUpdated = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));
				for (int i = 0;i<rundirs.length;i++){
					DPRunDirs rundir = rundirs[i];
					rundir.setrunRoute(runroute);
					rundir.setLastUpdated(lastUpdated);
					DPRunDirs retrunDir = RunDirsServer.save(rundir);
				}
			}
		} catch (Exception e) {
			flag = e.getMessage();// CommonMessage.False;
			throw new IllegalArgumentException(flag);
		}

		return CommonMessage.Ok;
	}
	@Transactional
	public String AddDPRoute(DPRoutes route) {
		String flag = CommonMessage.False;
		try {
			flag = CheckValidate(route);
			if (flag == CommonMessage.Ok) {							
				Date date = new Date();
				Timestamp lastUpdated = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));
				route.setLastUpdated(lastUpdated);
//				Date oldEventTime = route.getEventTime();
//				Timestamp dateReceived = new Timestamp(date.getTime());
//				Timestamp eventTime = DateFunction.AddTimeZone(new Timestamp(devEvent.getEventTime().getTime()));
//				Timestamp eventSend = DateFunction.AddTimeZone(new Timestamp(devEvent.getEventSend().getTime()));
//				route.seteventWriteTime(dateReceived);
//				route.setEventTime(eventTime);
//				route.setEventSend(eventSend);
				DPRoutes addroute =null;
				if (route.getrouteNumber()!=null && route.getdev_Id()!=null )
					addroute = Server.findbyRouteNumberDevDate(route.getrouteNumber(),route.getdev_Id(),route.getrouteDate()); 
				if (addroute==null){
					for (int i=0; i<route.getruns().size();i++){					
						DPRuns r = route.getruns().get(i);
						r.setLastUpdated(lastUpdated);
						r.setroutes(route);
						for (int j=0; j<r.getstops().size();j++){
							DPStops s = r.getstops().get(j);
							s.setLastUpdated(lastUpdated);
							s.setruns(r);
						}
					}
					DPRoutes retroute = Server.save(route);
					
					flag = String.valueOf(retroute.getrouteId());
				}else{
					for (int i=0; i<route.getruns().size();i++){					
						DPRuns r = route.getruns().get(i);
						int pos = checkExistRun(r.getrunId(),addroute);
						if (pos<0){
							r.setLastUpdated(lastUpdated);
							addroute.getruns().add(r);
							for (int j=0; j<r.getstops().size();j++){
								DPStops s = r.getstops().get(j);
								s.setLastUpdated(lastUpdated);
								s.setruns(r);
							}
						}else{
							DPRuns addr = addroute.getruns().get(pos);							
							for (int j=0; j<r.getstops().size();j++){
								DPStops s = r.getstops().get(j);
								s.setLastUpdated(lastUpdated);
								addr.getstops().add(s);
								s.setruns(addr);
							}
						}
					}
					DPRoutes retroute = Server.save(addroute);					
					flag = String.valueOf(retroute.getrouteId());
				}
			}else{
				throw new IllegalArgumentException(flag);
			}

		} catch (Exception e) {
			flag = e.getMessage();// CommonMessage.False;
			throw new IllegalArgumentException(flag);
		}

		return flag;
	}
	@Transactional
	public String AddDPRouteOHA(DPRoutes route) {
		String flag = CommonMessage.False;
		try {
			flag = CheckValidateAddfromOHA(route);
			if (flag == CommonMessage.Ok) {							
				Date date = new Date();
				Timestamp lastUpdated = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));
				route.setLastUpdated(lastUpdated);
				DPRoutes addroute =null;				
				if (route.getrouteNumber()!=null && route.getdev_Id()!=null ){
					List<DPRoutes> routes = Server.findbyRouteNumberDev(route.getrouteNumber(),route.getdev_Id());
					if (routes.size()>0){
						addroute=routes.get(0);						
					}
				}
				if (addroute==null){
					for (int i=0; i<route.getruns().size();i++){					
						DPRuns r = route.getruns().get(i);
						r.setLastUpdated(lastUpdated);
						r.setroutes(route);
						for (int j=0; j<r.getstops().size();j++){
							DPStops s = r.getstops().get(j);
							s.setLastUpdated(lastUpdated);
							s.setruns(r);
						}
					}
					DPRoutes retroute = Server.save(route);
					
					flag = String.valueOf(retroute.getrouteId());
				}else{
					for (int i=0; i<route.getruns().size();i++){					
						DPRuns r = route.getruns().get(i);
						int pos = checkExistRun(r.getrunId(),addroute);
						if (pos<0){
							r.setLastUpdated(lastUpdated);
							addroute.getruns().add(r);
							r.setroutes(addroute);							
							for (int j=0; j<r.getstops().size();j++){
								DPStops s = r.getstops().get(j);
								s.setLastUpdated(lastUpdated);
								s.setruns(r);
							}
						}else{
							flag= RouteHistoricalMessage.RunidExistOHARoute;
							throw new IllegalArgumentException(flag);
						}
					}
					DPRoutes retroute = Server.save(addroute);					
					flag = String.valueOf(retroute.getrouteId());
				}
			}else{
				throw new IllegalArgumentException(flag);
			}

		} catch (Exception e) {
			flag = e.getMessage();// CommonMessage.False;
			throw new IllegalArgumentException(flag);
		}

		return flag;
	}
	@Transactional
	public String EditDPRoute(DPRoutes route) {
		String flag = CommonMessage.False;
		try {
			flag = CheckValidate(route);
			if (flag == CommonMessage.Ok) {							
				Date date = new Date();
				Timestamp lastUpdated = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));
				route.setLastUpdated(lastUpdated);
				DPRoutes addroute = Server.findbyRouteNumberDevDate(route.getrouteNumber(),route.getdev_Id(),route.getrouteDate()); 
				if (addroute!=null){
					for (int i=0; i<route.getruns().size();i++){					
						DPRuns r = route.getruns().get(i);						
							r.setLastUpdated(lastUpdated);
							r.setroutes(route);
							//route.getruns().add(r);
							for (int j=0; j<r.getstops().size();j++){
								DPStops s = r.getstops().get(j);
								s.setLastUpdated(lastUpdated);								
								s.setruns(r);
							}
					}
					DPRoutes retroute = Server.save(route);					
					flag = String.valueOf(retroute.getrouteId());
				}
			}else{
				throw new IllegalArgumentException(flag);
			}

		} catch (Exception e) {
			flag = e.getMessage();// CommonMessage.False;
			throw new IllegalArgumentException(flag);
		}

		return flag;
	}
	@Transactional
	public String EditDPRun(DPRuns run) {
		String flag = CommonMessage.False;
		try {
			flag = CheckValidate(run);
			if (flag == CommonMessage.Ok) {							
				Date date = new Date();
				Timestamp lastUpdated = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));
				//run.setLastUpdated(lastUpdated);
				
				DPRuns addrun = RunServer.findbyRunRoute(run.getrunRoute()); 
				if (addrun!=null){	
//					for (int j=0; j<addrun.getstops().size();j++){
//						DPStops s = addrun.getstops().get(j);						
//						addrun.getstops().remove(s);
//						s.setruns(null);
//					}
						
					
					for (int j=0; j<run.getstops().size();j++){
						DPStops s = run.getstops().get(j);
						s.setLastUpdated(lastUpdated);
						//run.getstops().add(s);
						s.setruns(run);									
					}					
					DPRuns retrun = RunServer.save(run);					
					flag = String.valueOf(retrun.getrunRoute());
				}
			}else{
				throw new IllegalArgumentException(flag);
			}

		} catch (Exception e) {
			flag = e.getMessage();// CommonMessage.False;
			throw new IllegalArgumentException(flag);
		}
		return flag;
	}
	@Transactional
	public String EditDPRun2(DPRuns2 run) {
		String flag = CommonMessage.False;
		try {
			flag = CheckValidate(run);
			if (flag == CommonMessage.Ok) {							
				Date date = new Date();
				Timestamp lastUpdated = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));
				//run.setLastUpdated(lastUpdated);
				
				DPRuns2 addrun = Run2Server.findbyRunRoute(run.getrunRoute()); 
				if (addrun!=null){	

						
					
					for (int j=0; j<run.getstops().size();j++){
						DPStops2 s = run.getstops().get(j);
						s.setLastUpdated(lastUpdated);
						//run.getstops().add(s);
						s.setruns(run);									
					}					
					DPRuns2 retrun = Run2Server.save(run);					
					flag = String.valueOf(retrun.getrunRoute());
				}
			}else{
				throw new IllegalArgumentException(flag);
			}

		} catch (Exception e) {
			flag = e.getMessage();// CommonMessage.False;
			throw new IllegalArgumentException(flag);
		}
		return flag;
	}
	@Transactional
	public String DeleteRoute(Integer routeid) {
		String flag = CommonMessage.False;
		try {						
			Server.deletebyRouteId(routeid);						
			flag = "Delete Route by RouteId = "+routeid +" is Ok";
						

		} catch (Exception e) {
			flag = e.getMessage();// CommonMessage.False;
			throw new IllegalArgumentException(flag);
		}

		return flag;
	}
	
	public String DeleteRun(Integer runroute) {
		String flag = CommonMessage.False;
		try {						
			RunServer.deletebyRunRoute(runroute);						
			flag = "Delete Run by RunRoute = "+runroute +" is Ok";
						

		} catch (Exception e) {
			flag = e.getMessage();// CommonMessage.False;
			throw new IllegalArgumentException(flag);
		}

		return flag;
	}
	public String DeleteStop(Integer stoprunid) {
		String flag = CommonMessage.False;
		try {						
			StopServer.deletebyStopRunId(stoprunid);						
			flag = "Delete Stop by StopRunId = "+stoprunid +" is Ok";
						

		} catch (Exception e) {
			flag = e.getMessage();// CommonMessage.False;
			throw new IllegalArgumentException(flag);
		}

		return flag;
	}
	public String UpdateRouteDesc(Integer routeid, String desc) {
		String flag = CommonMessage.False;
		try {						
			Server.updateRouteDesc(routeid,desc);						
			flag = "Update RouteDesc by Routeid = "+routeid +" is Ok";						

		} catch (Exception e) {
			flag = e.getMessage();// CommonMessage.False;
			throw new IllegalArgumentException(flag);
		}

		return flag;
	}
	public String UpdateRunDesc(Integer runroute, String desc) {
		String flag = CommonMessage.False;
		try {						
			RunServer.updateRunDesc(runroute,desc);						
			flag = "Update RunDesc by RunRoute = "+runroute +" is Ok";						

		} catch (Exception e) {
			flag = e.getMessage();// CommonMessage.False;
			throw new IllegalArgumentException(flag);
		}

		return flag;
	}
	private int checkExistRun(String runId, DPRoutes route){
		int result = -1;
		for (int i=0; i<route.getruns().size();i++){					
			DPRuns r = route.getruns().get(i);
			if (r.getrunId().equalsIgnoreCase(runId)) return i;
		}
		return result;
	}
	public String CheckValidateAddfromOHA(DPRoutes route) {
		String flag = CommonMessage.Ok;		
		if (route.getrouteNumber()==null)
		{
			flag = RouteHistoricalMessage.RouteNumberNull;
			return flag;
		}		 
		if (route.getdev_Id()!=99999)
		{
			flag = RouteHistoricalMessage.DevIdNotExist;
			return flag;
		}
		return flag;
	}
	public String CheckValidate(DPRoutes route) {
		String flag = CommonMessage.Ok;		
		if (route.getrouteNumber()==null)
		{
			flag = RouteHistoricalMessage.RouteNumberNull;
			return flag;
		}
		 
		if (route.getdev_Id() <=0)
		{
			flag = EventMessage.DevIdNotExist;
			return flag;
		} else {
			MdmDevice md = DeviceServer.findByDevID(route.getdev_Id());
			if (md==null)		
			{
				flag = EventMessage.DevIdNotExist;
				return flag;
			}					
		}
		return flag;
	}
	public String CheckValidate(DPRuns run) {
		String flag = CommonMessage.Ok;		
		if (run.getrunRoute()==null)
		{
			flag =  RouteHistoricalMessage.RunrouteNull;
			return flag;
		}
		 
		if (run.getrunId()==null)
		{
			flag = RouteHistoricalMessage.RunidNull;
			return flag;
		} 
		
		DPRuns r = RunServer.findbyRunRoute(run.getrunRoute());
		if (r==null)		
		{
			flag = EventMessage.DevIdNotExist;
			return flag;
		}							
		return flag;
	}
	public String CheckValidate(DPRuns2 run) {
		String flag = CommonMessage.Ok;		
		if (run.getrunRoute()==null)
		{
			flag =  RouteHistoricalMessage.RunrouteNull;
			return flag;
		}
		if (run.getrouteId()==null)
		{
			flag =  RouteHistoricalMessage.RouteIdNull;
			return flag;
		}
		if (run.getrunId()==null)
		{
			flag = RouteHistoricalMessage.RunidNull;
			return flag;
		} 
		
		DPRuns r = RunServer.findbyRunRoute(run.getrunRoute());
		if (r==null)		
		{
			flag = RouteHistoricalMessage.RunrouteNotExist;
			return flag;
		}							
		return flag;
	}
	public List<DPRoutes> findRoutebyRouteNumber(String routeNumber) 
	{
		return Server.findbyRouteNumber(routeNumber);
	}
	public List<DPRoutes> findRoutebyDevDate(Integer devid, Date date) 
	{
		return Server.findRoutebyDevDate(devid,date);
	}	
	public List<DPRoutes> findAllRoute() 
	{
		return Server.findAll();
	}
	public List<RunInfo> findAllRun() 
	{
		List<DPRoutes> routes=  Server.findAllBuildRoute();
		List<RunInfo> result = new ArrayList<RunInfo>();
		for (int i = 0; i< routes.size();i++){
			DPRoutes r = routes.get(i);
			
			for (int j = 0; j< r.getruns().size();j++){
				
				RunInfo runinfo = new RunInfo();
				DPRuns run =  r.getruns().get(j);
				runinfo.setdev_Id(r.getdev_Id());
				runinfo.setrouteId(r.getrouteId());
				runinfo.setrouteNumber(r.getrouteNumber());
				runinfo.setrouteDate(r.getrouteDate());
				runinfo.setrunId(run.getrunId());		
				runinfo.setrunRoute(run.getrunRoute());
				result.add(runinfo);
			}
		}
		return result;
		
	}
	public List<RunInfo> findRunbyDate(Date rundate) 
	{
		List<DPRoutes> routes=  Server.findRoutebyDate(rundate);
		List<RunInfo> result = new ArrayList<RunInfo>();
		for (int i = 0; i< routes.size();i++){
			DPRoutes r = routes.get(i);
			
			for (int j = 0; j< r.getruns().size();j++){
				
				RunInfo runinfo = new RunInfo();
				DPRuns run =  r.getruns().get(j);
				runinfo.setdev_Id(r.getdev_Id());
				runinfo.setrouteId(r.getrouteId());
				runinfo.setrouteNumber(r.getrouteNumber());
				runinfo.setrouteDate(r.getrouteDate());
				runinfo.setrunId(run.getrunId());		
				runinfo.setrunRoute(run.getrunRoute());
				result.add(runinfo);
			}
		}
		return result;
		
	}
	public List<String> findAllDate() 
	{
		List<Date> ld = Server.findAllDate();
		List<String> result = new ArrayList<String>();
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy"); 
		for (int i=0; i<ld.size();i++){					
			Date d = ld.get(i);
			String sdate = df.format(d); 
			result.add(sdate);			
		}
		return result;
		//return Server.findAllDate();
	}
	public List<String> findBusbyDate(Date date) 
	{
		List<DPRoutes> routes = Server.findRoutebyDate(date);		
		List<String> result = new ArrayList<String>();
		String lstbus=",";
		for (int i=0; i<routes.size();i++){
			DPRoutes route = routes.get(i);
			int devId = route.getdev_Id();
			for (int j=0; j<route.getruns().size();j++){
				DPRuns run = route.getruns().get(j);
				Date start = run.getactualRunStart();
				Date end = run.getactualRunEnd();				
				List<String> bus = RawEventServer.findbus(devId, start, end);
				for (int b=0; b<bus.size();b++){
					String sBus = bus.get(b);
					if (lstbus.indexOf(","+sBus+",")<0){
						result.add(sBus);
						lstbus = lstbus +sBus+","; 
					}
				}
			}
		}		
		return result;
		
	}
	public List<String> findAllBus() 
	{
		List<DPRoutes> routes = Server.findAll();		
		List<String> result = new ArrayList<String>();
		String lstbus=",";
		for (int i=0; i<routes.size();i++){
			DPRoutes route = routes.get(i);
			int devId = route.getdev_Id();
			for (int j=0; j<route.getruns().size();j++){
				DPRuns run = route.getruns().get(j);
				Date start = run.getactualRunStart();
				Date end = run.getactualRunEnd();				
				List<String> bus = RawEventServer.findbus(devId, start, end);
				for (int b=0; b<bus.size();b++){
					String sBus = bus.get(b);
					if (lstbus.indexOf(","+sBus+",")<0){
						result.add(sBus);
						lstbus = lstbus +sBus+","; 
					}
				}
			}
		}		
		return result;
		
	}
	public List<Integer> findAllDevice() 
	{
		return Server.findAllDevice();
	}
}
