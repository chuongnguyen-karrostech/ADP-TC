package com.APIMM.util;

import java.util.Date;
import java.util.Map;

public class ManualEmbarkDisembark {
	public Map <String, Double> location;
	public String vin;
	public Integer eventId;
	public String studentDistrictId;
	public String scanType;
	public Date eventtime;
	public String unitid;
	public String unitvendorid;
	
	public Map<String, Double> getLocation() {
		return location;
	}
	public void setLocation(Map<String, Double> location) {
		this.location = location;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public String getStudentDistrictId() {
		return studentDistrictId;
	}
	public void setStudentDistrictId(String studentDistrictId) {
		this.studentDistrictId = studentDistrictId;
	}
	public String getScanType() {
		return scanType;
	}
	public void setScanType(String scanType) {
		this.scanType = scanType;
	}
	public Date getEventtime() {
		return eventtime;
	}
	public void setEventtime(Date eventtime) {
		this.eventtime = eventtime;
	}
	public String getUnitid() {
		return unitid;
	}
	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}
	public String getUnitvendorid() {
		return unitvendorid;
	}
	public void setUnitvendorid(String unitvendorid) {
		this.unitvendorid = unitvendorid;
	}
	
}

