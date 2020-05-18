package com.APIMM.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APIMM.etgps.model.Message;
import com.APIMM.etgps.repository.MessageRepository;
import com.APIMM.etmain.model.Setting;
import com.APIMM.etmain.repository.SettingRepository;
import com.APIMM.mam.model.MamDefaultModule;
import com.APIMM.mam.model.MamDevModule;
import com.APIMM.mam.model.MamDevRouteHistorical;
import com.APIMM.mam.model.MamDevRunHistorical;
import com.APIMM.mam.model.MamDeviceSetting;
import com.APIMM.mam.repository.MamDefaultModuleRepository;
import com.APIMM.mam.repository.MamDevModuleRepository;
import com.APIMM.mam.repository.MamDevRouteHistoricalRepository;
import com.APIMM.mam.repository.MamDeviceSettingRepository;
import com.APIMM.mam.repository.MamSettingRepository;
import com.APIMM.mam.repository.MdmDeviceRepository;
import com.APIMM.util.CommonFunction;
import com.APIMM.util.DateFunction;
import com.APIMM.util.message.CommonMessage;
import com.APIMM.util.message.RouteHistoricalMessage;

import com.APIMM.util.RouteAssignment;
import com.APIMM.util.SendCloudThread;
import com.APIMM.util.Vehicles;
import com.fasterxml.jackson.databind.ObjectMapper;

//import scala.annotation.meta.setter;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Service
public class MamService {
	@Autowired
	MamDevModuleRepository MAM_Module_Server;
	@Autowired
	MamDevRouteHistoricalRepository MAM_Route_Server;
	@Autowired
	MdmDeviceRepository MDM_Device_Server;
	@Autowired
	MamSettingRepository MAM_Setting_Server;
	@Autowired
	MamDeviceSettingRepository MAM_Dev_Setting_Server;
	@Autowired
	MamDefaultModuleRepository MAM_DefaultModule_Server;
	
	@Autowired
	SettingRepository settingRepository;
	@Autowired
	MessageRepository messageRepository;

	public List<MamDevModule> findModulebyDeviceMacAddress(int appid, int devid) {
		int defaulemoduleid = 0;
		// List<MamDefaultModule>
		// lstdm=MAM_DefaultModule_Server.findModulebyAppId(appid);
		// if (lstdm.size()>0)defaulemoduleid = lstdm.get(0).getmoduleId();
		List<MamDevModule> result = MAM_Module_Server.findModulebyDeviceMacAddress(appid, devid);
		// for (int i=0; i<result.size();i++){
		// MamDevModule devmodule = result.get(i);
		// int moduleid = devmodule.getMamModules().getModuleId();
		// if (moduleid ==defaulemoduleid) devmodule.setisDefault(true);
		// else devmodule.setisDefault(false);
		// }

		return result;
	}

	public List<MamDeviceSetting> findSettingbyApp(int appid, int devid) {
		return MAM_Dev_Setting_Server.findSettingbyApp(appid, devid);
	}

	public List<MamDevRouteHistorical> findRecentRoutebyDevice(int devid, int numberrecord) {
		List<MamDevRouteHistorical> rs = new ArrayList<MamDevRouteHistorical>();
		List<MamDevRouteHistorical> ls = MAM_Route_Server.findRecentRoutebyDevice(devid);
		if (ls.size() > 0) {
			for (int i = 0; i < ls.size() && i < numberrecord; i++) {
				String runid = "";
				for (int j = 0; j < ls.get(i).getmamDevRuns().size(); j++) {
					MamDevRunHistorical run = ls.get(i).getmamDevRuns().get(j);
					if (run.getrunId().equalsIgnoreCase(runid)) {
						ls.get(i).getmamDevRuns().remove(j);
						j--;
					} else
						runid = run.getrunId();
				}
				rs.add(ls.get(i));
			}
		}
		return rs;
	}

	public List<MamDevRouteHistorical> findRecentRoutebyDeviceByBus(int devid, int numberrecord, String busId) {
		List<MamDevRouteHistorical> rs = new ArrayList<MamDevRouteHistorical>();
		List<MamDevRouteHistorical> ls = MAM_Route_Server.findRecentRoutebyDeviceByBus(devid, busId);
		if (ls.size() > 0) {
			for (int i = 0; i < ls.size() && i < numberrecord; i++) {
				String runid = "";
				for (int j = 0; j < ls.get(i).getmamDevRuns().size(); j++) {
					MamDevRunHistorical run = ls.get(i).getmamDevRuns().get(j);
					if (run.getrunId().equalsIgnoreCase(runid)) {
						ls.get(i).getmamDevRuns().remove(j);
						j--;
					} else
						runid = run.getrunId();
				}
				rs.add(ls.get(i));
			}
		}
		return rs;
	}
	
	public List<MamDevRouteHistorical> findLastRoutebyDevice(int devid) {
		List<MamDevRouteHistorical> rs = new ArrayList<MamDevRouteHistorical>();
		List<MamDevRouteHistorical> ls = MAM_Route_Server.findLastRoutebyDevice(devid);
		if (ls.size() > 0) {
			String runid = "";
			for (int j = 0; j < ls.get(0).getmamDevRuns().size(); j++) {
				MamDevRunHistorical run = ls.get(0).getmamDevRuns().get(j);
				if (run.getrunId().equalsIgnoreCase(runid)) {
					ls.get(0).getmamDevRuns().remove(j);
					j--;
				} else
					runid = run.getrunId();
			}
			rs.add(ls.get(0));
		}
		return rs;
	}

	private Date getEndOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		calendar.set(year, month, day, 23, 59, 59);
		return calendar.getTime();
	}

	private Date getStartOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		calendar.set(year, month, day, 0, 0, 0);
		return calendar.getTime();
	}

	public String Add(MamDevRouteHistorical routeHis) {
		Log logger = LogFactory.getLog(this.getClass());
		String flag = CommonMessage.False;

		try {
			// v.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			Date date = new Date();
			Timestamp lastUpdated = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));
			routeHis.setDevRouteCreated(lastUpdated);
			//minh vo route assignment
	         Timestamp tVehTime = lastUpdated;
	         Setting objTimeZone = this.settingRepository.findByName("TimeZone");
	         String skey;
	         if (objTimeZone.getValue().indexOf("GMT") == -1) {
	            skey = "US/" + objTimeZone.getValue();
	            Date tmpTime = DateFunction.convertUTCToZone(date, skey);
	            long time = tmpTime.getTime();
	            tVehTime = new Timestamp(time);	            
	         }

			if (routeHis.getbusId() != null && !routeHis.getbusId().equals("") && routeHis.getrouteNumber() != null
					&& !routeHis.getrouteNumber().equals("") && com.APIMM.util.CommonFunction.tenantId != null
					&& !com.APIMM.util.CommonFunction.tenantId.equals("")) {
				skey = routeHis.getbusId().trim() + "," + routeHis.getrouteNumber().trim();
				boolean bhas_assign = false;
				if (com.APIMM.util.CommonFunction.hRouteAssign.containsKey(skey)) {
					if (((Date) com.APIMM.util.CommonFunction.hRouteAssign.get(skey))
							.compareTo(getStartOfDay(lastUpdated)) < 0) {
						bhas_assign = false;
						com.APIMM.util.CommonFunction.hRouteAssign.put(skey, getStartOfDay(tVehTime));
					} else {
						bhas_assign = true;
					}
				} else {
					bhas_assign = false;
					com.APIMM.util.CommonFunction.hRouteAssign.put(skey, getStartOfDay(tVehTime));
				}
				if (!bhas_assign) {
					String cloudret = EDPRouteAssign(routeHis, lastUpdated, getEndOfDay(tVehTime));
					if (cloudret.indexOf(" is not exist in cloud.") >= 0
							|| cloudret.indexOf("Error put assignment to cloud: ") >= 0
							|| cloudret == "No bus assign") {
						logger.info(CommonFunction.LogReturn(date.getTime(), "put assignment to cloud error", "error",
								"" + cloudret));
					}
				}
			}
			flag = CheckValidate(routeHis);
			if (flag == CommonMessage.Ok) {
				List<MamDevRouteHistorical> rs = MAM_Route_Server.findbyDevRoute(routeHis.getMdmDevices().getdevID(),
						routeHis.getrouteNumber(), routeHis.getbusId());
				if (rs.size() > 0) {
					MamDevRouteHistorical temp = rs.get(0);
					// routeHis.setDevRouteID(temp.getDevRouteID());
					temp.setDevRouteCreated(routeHis.getDevRouteCreated());
					temp.setrouteDescription(routeHis.getrouteDescription());
					temp.setrouteBeginPlan(routeHis.getrouteBeginPlan());
					temp.setrouteEndPlan(routeHis.getrouteEndPlan());
					for (int i = 0; routeHis.getmamDevRuns() != null && i < routeHis.getmamDevRuns().size(); i++) {
						MamDevRunHistorical r = routeHis.getmamDevRuns().get(i);
						r.setmamRoute(temp);
						r.setdevRunCreated(routeHis.getDevRouteCreated());
						temp.getmamDevRuns().add(r);
					}
					MamDevRouteHistorical addRouteRecent = MAM_Route_Server.save(temp);
					flag = String.valueOf(addRouteRecent.getDevRouteID());
				} else {
					for (int i = 0; routeHis.getmamDevRuns() != null && i < routeHis.getmamDevRuns().size(); i++) {
						MamDevRunHistorical r = routeHis.getmamDevRuns().get(i);
						r.setdevRunCreated(routeHis.getDevRouteCreated());
						r.setmamRoute(routeHis);
					}
					MamDevRouteHistorical addRouteRecent = MAM_Route_Server.save(routeHis);
					flag = String.valueOf(addRouteRecent.getDevRouteID());
				}
			}
			// minh vo, insert 1604
			if (!routeHis.getdevRoutePlan()) {
				Timestamp dateReceived = DateFunction.ChangeTimewithZone(new Timestamp(date.getTime()));				
				Date tmpTime = dateReceived;
				if (objTimeZone.getValue().indexOf("GMT") == -1) {
					String timezone = "US/" + objTimeZone.getValue();
					tmpTime = DateFunction.convertToUTC(tmpTime, timezone);
				}	
				Timestamp eventTime = new Timestamp(tmpTime.getTime());				
				Message etgpsMessage = new Message();
				etgpsMessage.setLatitude(null);
				etgpsMessage.setLongitude(null);
				// busID = UnitID
				etgpsMessage.setmessageVehicleId(routeHis.getbusId());
				etgpsMessage.setmessageEventId(1604);
				String messComent = "Event=RSUM;Driver=;Route=" + routeHis.getrouteNumber().trim() + ";Type=Set;";
				etgpsMessage.setmessageComment(messComent);
				etgpsMessage.setmessageDateReceived(dateReceived);
				etgpsMessage.setmessageDateEvent(eventTime);
				etgpsMessage.setmessageDateAdjusted(tVehTime);				
				messageRepository.save(etgpsMessage);
			}
		} catch (Exception e) {
			flag = e.getMessage();// CommonMessage.False;
		}

		return flag;
	}

	public String CheckValidate(MamDevRouteHistorical routeHis) {
		String flag = CommonMessage.Ok;
		if (routeHis.getrouteNumber() == null) {
			flag = RouteHistoricalMessage.RouteNumberNull;
			return flag;
		}
		if (routeHis.getbusId() == null) {
			flag = RouteHistoricalMessage.BusIdNull;
			return flag;
		}
		if (routeHis.getMdmDevices().getdevID() <= 0) {
			flag = RouteHistoricalMessage.DevIdNotExist;
			return flag;
		} else {
			if (MDM_Device_Server.findByDevID(routeHis.getMdmDevices().getdevID()) == null) {
				flag = RouteHistoricalMessage.DevIdNotExist;
				return flag;
			}
		}
		if (routeHis.getrouteDescription() == null) {
			flag = RouteHistoricalMessage.RouteDescriptionNull;
			return flag;
		}
		if (routeHis.getrouteBeginPlan() == null) {
			// flag = RouteHistoricalMessage.BeginPlanedNull;
			// return flag;
		} else {
			if (!routeHis.getrouteBeginPlan().trim().equals("")
					&& DateFunction.CheckValidTime(routeHis.getrouteBeginPlan()) == false) {
				flag = RouteHistoricalMessage.WrongFormatBeginTime;
				return flag;
			}
		}
		if (routeHis.getrouteEndPlan() == null) {
			// flag = RouteHistoricalMessage.EndPlanedNull;
			// return flag;
		} else {
			if (!routeHis.getrouteBeginPlan().trim().equals("")
					&& DateFunction.CheckValidTime(routeHis.getrouteEndPlan()) == false) {
				flag = RouteHistoricalMessage.WrongFormatEndTime;
				return flag;
			}
		}

		return flag;
	}

	public List<MamDefaultModule> findModulebyAppId(int appid) {
		return MAM_DefaultModule_Server.findModulebyAppId(appid);
	}

	private String EDPRouteAssign(MamDevRouteHistorical routeHis, Date startDate, Date endDate) {
		if (routeHis.getbusId() != null && !routeHis.getbusId().equals("")
				&& com.APIMM.util.CommonFunction.tenantId != null
				&& !com.APIMM.util.CommonFunction.tenantId.equals("")) {
			Date curent = new Date();
			if (com.APIMM.util.JWT.expiredDatetime == null || com.APIMM.util.JWT.expiredDatetime.before(curent)) {
				com.APIMM.util.JWT.gettoken();
			}
			if (com.APIMM.util.JWT.accessToken == "")
				return "get cloud token error ";
			try {
				if (com.APIMM.util.CommonFunction.hVeh.containsKey(routeHis.getbusId().trim())) {
					String transactionUrl = com.APIMM.util.CommonFunction.EDP_Url + com.APIMM.util.CommonFunction.EDPRouteAssignmentEndpoint; //"v1/routeAssignments";
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_JSON);
					headers.set("ApiKey", com.APIMM.util.CommonFunction.ClientId);
					headers.set("Authorization", com.APIMM.util.JWT.tokenType + " " + com.APIMM.util.JWT.accessToken);

					RestTemplate restTemplate = new RestTemplate();
					List<RouteAssignment> rAs = new ArrayList<RouteAssignment>();
					RouteAssignment rA = new RouteAssignment();
					rA.busNumber = routeHis.getbusId().trim();
					rA.routeCode = routeHis.getrouteNumber().trim();
					rA.tenantuuId = com.APIMM.util.CommonFunction.tenantId;

					Vehicles v = (Vehicles) com.APIMM.util.CommonFunction.hVeh.get(rA.busNumber);
					rA.vehicleuuId = v.getid();
					rA.vin = v.getvin();
					rA.effectiveStartDate = startDate;// new
														// Timestamp(System.currentTimeMillis());
					rA.effectiveEndDate = endDate;
					rAs.add(rA);
					ObjectMapper mapper = new ObjectMapper();
					String jsonInString = mapper.writeValueAsString(rAs);
					HttpEntity<String> entity = new HttpEntity<String>(jsonInString, headers);
					ResponseEntity<Object> aa = restTemplate.exchange(transactionUrl, HttpMethod.PUT, entity,
							Object.class);
					return aa.getBody().toString();
				} else {
					return "Bus " + routeHis.getbusId().trim() + " is not exist in cloud.";
				}
			} catch (Exception e) {
				return "Error put assignment to cloud: " + e.getMessage();
			}

		}
		return "No bus assign";
	}

	/**
	 * @param lrouteHis
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private String EDPRouteAssign2(List<MamDevRouteHistorical> lrouteHis, Date startDate, Date endDate) {
		Log logger = LogFactory.getLog(this.getClass());
		Date curent = new Date();
		List<RouteAssignment> rAserr = new ArrayList<RouteAssignment>();
		if (com.APIMM.util.CommonFunction.tenantId != null && !com.APIMM.util.CommonFunction.tenantId.equals("")) {

			if (com.APIMM.util.JWT.expiredDatetime == null || com.APIMM.util.JWT.expiredDatetime.before(curent)) {
				com.APIMM.util.JWT.gettoken();
			}
			try {
				String transactionUrl = com.APIMM.util.CommonFunction.EDP_Url + com.APIMM.util.CommonFunction.EDPRouteAssignmentEndpoint;// "v1/routeAssignments";
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.set("ApiKey", com.APIMM.util.CommonFunction.ClientId);
				headers.set("Authorization", com.APIMM.util.JWT.tokenType + " " + com.APIMM.util.JWT.accessToken);
				int j = 0;
				int nass = 0;
				do {
					RestTemplate restTemplate = new RestTemplate();
					List<RouteAssignment> rAs = new ArrayList<RouteAssignment>();
					for (int i = j; i < lrouteHis.size() && i < j + com.APIMM.util.CommonFunction.NumEDPRouteAssign; i++) {
						MamDevRouteHistorical routeHis = lrouteHis.get(i);
						if (routeHis.getbusId() != null && !routeHis.getbusId().equals("")
								&& com.APIMM.util.CommonFunction.hVeh.containsKey(routeHis.getbusId().trim()))

						{
							RouteAssignment rA = new RouteAssignment();
							rA.busNumber = routeHis.getbusId().trim();
							rA.routeCode = routeHis.getrouteNumber().trim();
							rA.tenantuuId = com.APIMM.util.CommonFunction.tenantId;

							Vehicles v = (Vehicles) com.APIMM.util.CommonFunction.hVeh.get(rA.busNumber);
							rA.vehicleuuId = v.getid();
							rA.vin = v.getvin();
							rA.effectiveStartDate = startDate;// new
																// Timestamp(System.currentTimeMillis());
							rA.effectiveEndDate = endDate;
							rAs.add(rA);
							nass++;
						} else {
							logger.info(CommonFunction.LogReturn(curent.getTime(), "Bus is empty or not exist", "Bus",
									routeHis.getbusId() == null ? "null" : routeHis.getbusId().trim()));
							// return "Bus "+ routeHis.getbusId().trim() +" is
							// not exist in cloud.";
						}
					}
					try{
					ObjectMapper mapper = new ObjectMapper();
					String jsonInString = mapper.writeValueAsString(rAs);
					logger.info("Send cloud block "+ (j/com.APIMM.util.CommonFunction.NumEDPRouteAssign)+": "+jsonInString);
					HttpEntity<String> entity = new HttpEntity<String>(jsonInString, headers);
					
					ResponseEntity<Object> aa = restTemplate.exchange(transactionUrl, HttpMethod.PUT, entity,
							Object.class);
//					if (aa.getStatusCodeValue()==HttpStatus.OK.value()){
					if (aa.getStatusCodeValue()<300 && aa.getStatusCodeValue()>=200){
						//success, do something....
						logger.info("send cloud block "+ (j/com.APIMM.util.CommonFunction.NumEDPRouteAssign)+ " is successfully. total route: "+  rAs.size() );
					}else{
						//fail
						rAserr.addAll(rAs);
						logger.info("send cloud block "+ (j/com.APIMM.util.CommonFunction.NumEDPRouteAssign)+ " is fail. Error code = "+aa.getStatusCodeValue());
					}
					}catch (Exception errCloud){
						rAserr.addAll(rAs);
						logger.info("Exception: send cloud block "+ (j/com.APIMM.util.CommonFunction.NumEDPRouteAssign)+ " is fail. Error code = "+errCloud.getMessage());
					}
					j = j + com.APIMM.util.CommonFunction.NumEDPRouteAssign;
				} while (j < lrouteHis.size());				
				if (rAserr.size()>0){
					int ntry=0;
					do {
						List<RouteAssignment> rAsErrRetry = RetryEDPRouteAssign(rAserr);
						rAserr.clear();
						rAserr.addAll(rAsErrRetry);
						ntry++;
					}while (ntry<3 && rAserr.size()>0);
				}
				if (rAserr.size()>0){
					return "Error put assignment to cloud: fail " + rAserr.size()+" Route- Bus pairs.";
				}
				return "Push to clould: success: " + nass + " assignments, fail: " + (lrouteHis.size() - nass);
			}  
			catch (Exception e) {
				return "Error put assignment to cloud: " + e.getMessage();
			}
		}
		return "No bus assign";
	}
	private String EDPRouteAssign3(List<MamDevRouteHistorical> lrouteHis, Date startDate, Date endDate) {
		Log logger = LogFactory.getLog(this.getClass());
		Date curent = new Date();
		List<RouteAssignment> rAserr = new ArrayList<RouteAssignment>();
		if (com.APIMM.util.CommonFunction.tenantId != null && !com.APIMM.util.CommonFunction.tenantId.equals("")) {

			if (com.APIMM.util.JWT.expiredDatetime == null || com.APIMM.util.JWT.expiredDatetime.before(curent)) {
				com.APIMM.util.JWT.gettoken();
			}
			try {
				String transactionUrl = com.APIMM.util.CommonFunction.EDP_Url + com.APIMM.util.CommonFunction.EDPRouteAssignmentEndpoint;// "v1/routeAssignments";
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.set("ApiKey", com.APIMM.util.CommonFunction.ClientId);
				headers.set("Authorization", com.APIMM.util.JWT.tokenType + " " + com.APIMM.util.JWT.accessToken);
				int j = 0;
				int nass = 0;
				//multi thread
				List<SendCloudThread> workers = new ArrayList<SendCloudThread>();
				do {
					RestTemplate restTemplate = new RestTemplate();
					List<RouteAssignment> rAs = new ArrayList<RouteAssignment>();
					for (int i = j; i < lrouteHis.size() && i < j + com.APIMM.util.CommonFunction.NumEDPRouteAssign; i++) {
						MamDevRouteHistorical routeHis = lrouteHis.get(i);
						if (routeHis.getbusId() != null && !routeHis.getbusId().equals("")
								&& com.APIMM.util.CommonFunction.hVeh.containsKey(routeHis.getbusId().trim()))

						{
							RouteAssignment rA = new RouteAssignment();
							rA.busNumber = routeHis.getbusId().trim();
							rA.routeCode = routeHis.getrouteNumber().trim();
							rA.tenantuuId = com.APIMM.util.CommonFunction.tenantId;

							Vehicles v = (Vehicles) com.APIMM.util.CommonFunction.hVeh.get(rA.busNumber);
							rA.vehicleuuId = v.getid();
							rA.vin = v.getvin();
							rA.effectiveStartDate = startDate;// new
																// Timestamp(System.currentTimeMillis());
							rA.effectiveEndDate = endDate;
							rAs.add(rA);
							nass++;
						} else {
							logger.info(CommonFunction.LogReturn(curent.getTime(), "Bus is empty or not exist", "Bus",
									routeHis.getbusId() == null ? "null" : routeHis.getbusId().trim()));
							// return "Bus "+ routeHis.getbusId().trim() +" is
							// not exist in cloud.";
						}
					}
					//multi thread
					SendCloudThread SendThread = new SendCloudThread((j/com.APIMM.util.CommonFunction.NumEDPRouteAssign),rAs,rAserr );
					workers.add(SendThread);
					SendThread.start();		
					Thread.sleep(1000);
					j = j + com.APIMM.util.CommonFunction.NumEDPRouteAssign;
				} while (j < lrouteHis.size());
				//multi thread
				for (int i=0; i< workers.size();i++){
					workers.get(i).join();
				}
				logger.info("Send all block completed, error: "+rAserr.size());
				if (rAserr.size()>0){
					int ntry=0;
					do {
						List<RouteAssignment> rAsErrRetry = RetryEDPRouteAssign(rAserr);
						rAserr.clear();
						rAserr.addAll(rAsErrRetry);
						ntry++;
					}while (ntry<3 && rAserr.size()>0);
				}
				if (rAserr.size()>0){
					return "Error put assignment to cloud: fail " + rAserr.size()+" Route- Bus pairs.";
				}
				return "Push to clould: success: " + nass + " assignments, fail: " + (lrouteHis.size() - nass);
			}  
			catch (Exception e) {
				return "Error put assignment to cloud: " + e.getMessage();
			}
		}
		return "No bus assign";
	}
	private List<RouteAssignment> RetryEDPRouteAssign(List<RouteAssignment> rAsAll) {
		Log logger = LogFactory.getLog(this.getClass());
		Date curent = new Date();
		logger.info("Retry send cloud completed, size: "+rAsAll.size());
		List<RouteAssignment> rAserr = new ArrayList<RouteAssignment>();
		if (com.APIMM.util.CommonFunction.tenantId != null && !com.APIMM.util.CommonFunction.tenantId.equals("")) {

			if (com.APIMM.util.JWT.expiredDatetime == null || com.APIMM.util.JWT.expiredDatetime.before(curent)) {
				com.APIMM.util.JWT.gettoken();
			}
			try {
				String transactionUrl = com.APIMM.util.CommonFunction.EDP_Url + com.APIMM.util.CommonFunction.EDPRouteAssignmentEndpoint;// "v1/routeAssignments";
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.set("ApiKey", com.APIMM.util.CommonFunction.ClientId);
				headers.set("Authorization", com.APIMM.util.JWT.tokenType + " " + com.APIMM.util.JWT.accessToken);
				int j = 0;
				int nass = 0;
				//multi thread
//				List<SendCloudThread> workers = new ArrayList<SendCloudThread>();
				do {
					RestTemplate restTemplate = new RestTemplate();
					List<RouteAssignment> rAs = new ArrayList<RouteAssignment>();
					for (int i = j; i < rAsAll.size() && i < j + com.APIMM.util.CommonFunction.NumEDPRouteAssign; i++) {					
						RouteAssignment rA = rAsAll.get(i);						
						rAs.add(rA);
						nass++;
					}
					try{
					ObjectMapper mapper = new ObjectMapper();
					String jsonInString = mapper.writeValueAsString(rAs);
					HttpEntity<String> entity = new HttpEntity<String>(jsonInString, headers);
					ResponseEntity<Object> aa = restTemplate.exchange(transactionUrl, HttpMethod.PUT, entity,Object.class);
					if (aa.getStatusCodeValue()<300 && aa.getStatusCodeValue()>=200){
						//success, do something....
						logger.info("resend cloud block "+ (j/com.APIMM.util.CommonFunction.NumEDPRouteAssign)+ " is successfully. total route: "+  rAs.size() );
					}else{
						//fail
						rAserr.addAll(rAs);
						logger.info("resend cloud block "+ (j/com.APIMM.util.CommonFunction.NumEDPRouteAssign)+ " is fail. Error code = "+aa.getStatusCodeValue());
					}					
					//multi thread
//					SendCloudThread SendThread = new SendCloudThread((j/com.APIMM.util.CommonFunction.NumEDPRouteAssign),rAs,rAserr );
//					workers.add(SendThread);
//					SendThread.start();
					}catch (Exception errCloud){
						rAserr.addAll(rAs);
						logger.info("Exception: send cloud block "+ (j/com.APIMM.util.CommonFunction.NumEDPRouteAssign)+ " is fail. Error code = "+errCloud.getMessage());
					}
					j = j + com.APIMM.util.CommonFunction.NumEDPRouteAssign;
				} while (j < rAsAll.size());
				//multi thread
//				for (int i=0; i< workers.size();i++){
//					workers.get(i).join();
//				}
				logger.info("retry send completed, error: "+rAserr.size());
				return rAserr;
			} catch (Exception e) {
				return rAsAll;
			}			
		}
		return rAserr;		
	}
	public String AddNotification(String route, String bus, Date startdate, Date endate) {
		MamDevRouteHistorical routeHis = new MamDevRouteHistorical();
		routeHis.setbusId(bus);
		routeHis.setrouteNumber(route);
		return EDPRouteAssign(routeHis, startdate, endate);
		// return "Route Assginment";
	}

	public String AddRouteAssign(String[] routeassignment) {
		List<MamDevRouteHistorical> lRte = new ArrayList<MamDevRouteHistorical>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		df.setLenient(false);
		Date startDate = new Date();
		Date endDate = new Date();
		String rteass = routeassignment[0];
		String arg[] = rteass.split(";");
		String startdate = arg[2];
		String enddate = arg[3];
		try {
			startDate = df.parse(startdate); 
		} catch (ParseException e) {
			throw new IllegalArgumentException("Asignment: ["+rteass+"]. Start Date parameter invalid!!!");
		}
		try {
			endDate = df.parse(enddate);
		} catch (ParseException e) {
			endDate=null;
//			throw new IllegalArgumentException("Asignment: ["+rteass+"]. End Date parameter invalid!!!");
		}
		//endDate=null;
		for (int i = 0; i < routeassignment.length; i++) {
			String irteass = routeassignment[i];
			String iarg[] = irteass.split(";");
			String bus = iarg[0].trim();
			String route = iarg[1].trim();
			MamDevRouteHistorical routeHis = new MamDevRouteHistorical();
			routeHis.setbusId(bus);
			routeHis.setrouteNumber(route);
			lRte.add(routeHis);
		}
		//serialize
		return EDPRouteAssign2(lRte, startDate, endDate);
		//multi thread
//		return EDPRouteAssign3(lRte, startDate, endDate);
		// return "Route Assginment";
	}
}
