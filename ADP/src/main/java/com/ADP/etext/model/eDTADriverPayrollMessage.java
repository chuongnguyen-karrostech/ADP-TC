package com.ADP.etext.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class eDTADriverPayrollMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String dateAdjusted;
	private Integer activityCode;
	private String	busID;
	private String driverID;
	private String billingID;
	private String comment;
	private String status;

	
	public eDTADriverPayrollMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public eDTADriverPayrollMessage(DriverPayrollMessage entity) {
		super();
		this.id = entity.getId();		
		this.dateAdjusted = com.ADP.util.DateFunction.DateTimeToString(entity.getDateAdjusted(),"yyyy-MM-dd HH:mm:ss.SSS");
		this.activityCode = entity.getActivityCode();
		this.busID = entity.getBusID();
		this.driverID = entity.getDriverID();
		this.billingID = entity.getBillingID();
		this.comment = entity.getComment();		
		
	}
	public eDTADriverPayrollMessage(Long id,  String dateAdjusted,Integer activityCode, String busID, String driverID, 
			String billingID, String comment, String status) {
		super();
		this.id = id;

		this.dateAdjusted = dateAdjusted;
		this.activityCode = activityCode;
		this.busID = busID;
		this.driverID = driverID;
		this.comment = comment; 
		this.setStatus(status);
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "Id", nullable= false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
	@Column(name = "DateAdjusted")	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	public String getDateAdjusted() {
		return dateAdjusted;
	}
	public void setDateAdjusted(String dateAdjusted) {
		this.dateAdjusted = dateAdjusted;
	}
	@Column(name = "EventID", nullable = false)
	public Integer getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(Integer activityCode) {
		this.activityCode = activityCode;
	}
	@Column(name = "VehicleID", nullable = false)
	public String getBusID() {
		return busID;
	}
	public void setBusID(String busID) {
		this.busID = busID;
	}
	@Column(name = "DriverID")
	public String getDriverID() {
		return driverID;
	}
	public void setDriverID(String driverID) {
		this.driverID = driverID;
	}
	@Column(name = "BillingID")
	public String getBillingID() {
		return billingID;
	}
	public void setBillingID(String billingID) {
		this.billingID = billingID;
	}
	@Column(name = "comment")
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Column(name = "status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
}
