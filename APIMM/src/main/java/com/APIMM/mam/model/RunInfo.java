package com.APIMM.mam.model;
// Generated Nov 13, 2017 11:32:06 AM by Hibernate Tools 4.3.5.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * MamDeviceEvent generated by hbm2java
 */
@Entity
public class RunInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private Integer id;
	private Integer dev_Id;
	private Integer routeId;
	private String routeNumber;	
	private Date routeDate;
	private String runId;	
	private Integer runRoute;
	
	public RunInfo() {
	}

	public RunInfo( Integer id, Integer dev_Id,Integer routeId,String routeNumber, Date routeDate,String runId,Integer runRoute) {
		super();	
		this.id=id;
		this.dev_Id = dev_Id;
		this.routeId = routeId;
		this.routeNumber = routeNumber;
		this.routeDate = routeDate;
		this.runId = runId;
		this.runRoute = runRoute;
	}

	@Id
	@JsonIgnore
	@GeneratedValue(strategy = IDENTITY)
	
	public Integer getid() {
		return this.id;
	}
	public void setid(Integer id) {
		this.id = id;
	}   
	
	public Integer getdev_Id() {
		return this.dev_Id;
	}

	public void setdev_Id(Integer dev_Id) {
		this.dev_Id = dev_Id;
	}
	public Integer getrouteId() {
		return this.routeId;
	}

	public void setrouteId(Integer routeId) {
		this.routeId = routeId;
	}
	public String getrouteNumber() {
		return this.routeNumber;
	}

	public void setrouteNumber(String routeNumber) {
		this.routeNumber = routeNumber;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	public Date getrouteDate() {
		return this.routeDate;
	}

	public void setrouteDate(Date routeDate) {
		this.routeDate = routeDate;
	}		
	public String getrunId() {
		return this.runId;
	}

	public void setrunId(String runId) {
		this.runId = runId;
	}
	public Integer getrunRoute() {
		return this.runRoute;
	}

	public void setrunRoute(Integer runRoute) {
		this.runRoute = runRoute;
	}
}
