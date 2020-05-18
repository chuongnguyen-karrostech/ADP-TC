package com.ADP.etext.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name= "DriverPayrollMessageBK")
public class DriverPayrollMessagebk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5963539909885927606L;
	private Long id;
	private Date dateEvent;
	private Date dateReceived;
	private Date dateAdjusted;
	private Integer activityCode;
	private String	busID;
	private String driverID;
	private String driverType;
	private float latitude;
	private float longitude;
	private String comment;

	public DriverPayrollMessagebk() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DriverPayrollMessagebk(Long id, Date dateEvent, Date dateReceived, Date dateAdjusted,
			Integer activityCode, String busID, String driverID, String driverType, float latitude, float longitude,
			String sourceType, String comment) {
		super();
		this.id = id;
		this.dateEvent = dateEvent;
		this.dateReceived = dateReceived;
		this.dateAdjusted = dateAdjusted;
		this.activityCode = activityCode;
		this.busID = busID;
		this.driverID = driverID;
		this.driverType = driverType;
		this.latitude = latitude;
		this.longitude = longitude;	
		this.comment = comment; 
		
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
	@Column(name = "DateEvent",nullable= false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")	
	public Date getDateEvent() {		
		return dateEvent;
	}
	public void setDateEvent(Date dateevent) {		
		this.dateEvent = dateevent;
	}
	@Column(name = "DateReceived",nullable= false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	@JsonIgnore
	public Date getDateReceived() {		
		return dateReceived;
	}
	public void setDateReceived(Date datereceived) {				
		this.dateReceived = datereceived;
	}
	@JsonIgnore
	@Column(name = "DateAdjusted")
	@Temporal(TemporalType.TIMESTAMP)	
	public Date getDateAdjusted() {
		return dateAdjusted;
	}
	public void setDateAdjusted(Date dateAdjusted) {
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
	@Column(name = "DriverType")
	@JsonIgnore
	public String getDriverType() {
		return driverType;
	}
	public void setDriverType(String driverType) {
		this.driverType = driverType;
	}
	@Column(name = "Latitude")
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	@Column(name = "Longitude")
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	@Column(name = "comment")
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
