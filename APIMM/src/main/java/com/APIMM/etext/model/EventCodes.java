package com.APIMM.etext.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "EventCodes")
public class EventCodes {

//	EventCodes_StartID, EventCodes_Description
	
	@Id
	@Column(name = "EventCodes_StartID")
	private Integer startId;
	@Column(name = "EventCodes_Description")
	private String descr;
	@Column(name = "EventCodes_eDTAweb")
	private Integer eDTAweb;
	
	@JsonIgnore
	public Integer geteDTAweb() {
		return eDTAweb;
	}
	public void seteDTAweb(Integer eDTAweb) {
		this.eDTAweb = eDTAweb;
	}
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
	
	
	
}
