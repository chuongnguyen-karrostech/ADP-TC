package com.ADP.etext.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class EventCodes {

//	EventCodes_StartID, EventCodes_Description
	
	
	private Integer startId;	
	private String descr;
	private Boolean selected;
	private String vehicleId;
	
	public EventCodes() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the startId
	 */
	public Integer getStartId() {
		return startId;
	}
	/**
	 * @param startId the startId to set
	 */
	public void setStartId(Integer startId) {
		this.startId = startId;
	}
	/**
	 * @return the descr
	 */
	public String getDescr() {
		return descr;
	}
	/**
	 * @param descr the descr to set
	 */
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	public String getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	
}
