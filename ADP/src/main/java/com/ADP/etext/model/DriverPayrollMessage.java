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

@Entity
@Table(name= "DriverPayrollMessage")
public class DriverPayrollMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Date dateEvent;
	private Date dateReceived;
	private Date dateAdjusted;
	private Integer activityCode;
	private String	busID;
	private String driverID;
	private String driverType;
	private Float latitude;
	private Float longitude;
	private String sourceType;
	private String billingID;
	private String comment;
	private Date fromdate;
	private Date todate;
	private String driverPayrollMessage_phone;	
	private Integer devID;
	private Date internalTime;
	private Date lastSyncTime;	
	private String syncType;
	private Float latitudeLastSync;
	private Float longitudeLastSync;
	private Date sendTime;
	
	public DriverPayrollMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DriverPayrollMessage(Long id, Date dateEvent, Date dateReceived, Date dateAdjusted,
			Integer activityCode, String busID, String driverID, String driverType, Float latitude, Float longitude,
			String sourceType, String billingID, String comment) {
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
		this.sourceType = sourceType;
		this.billingID = billingID;
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
	
	@Column(name = "DateAdjusted")
	@Temporal(TemporalType.TIMESTAMP)	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
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
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	@Column(name = "Longitude")
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	@Column(name = "SourceType")
	@JsonIgnore
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
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

	@Transient
	public Date getFromdate() {
		return fromdate;
	}
	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}
	
	@Transient
	public Date getTodate() {
		return todate;
	}
	public void setTodate(Date todate) {
		this.todate = todate;
	}
	
	@Column(name = "DriverPayrollMessage_Phone")
	public String getDriverPayrollMessage_phone() {
		return driverPayrollMessage_phone;
	}
	public void setDriverPayrollMessage_phone(String driverPayrollMessage_phone) {
		this.driverPayrollMessage_phone = driverPayrollMessage_phone;
	}
	@Transient
	public Integer getDevID() {
		return devID;
	}
	public void setDevID(Integer devID) {
		this.devID = devID;
	}
	
	@Transient
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	public Date getInternalTime() {
		return internalTime;
	}
	public void setInternalTime(Date internalTime) {
		this.internalTime = internalTime;
	}
	@Transient
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	public Date getLastSyncTime() {
		return lastSyncTime;
	}
	public void setLastSyncTime(Date lastSyncTime) {
		this.lastSyncTime = lastSyncTime;
	}
	@Transient
	public String getSyncType() {
		return syncType;
	}
	public void setSyncType(String syncType) {
		this.syncType = syncType;
	}
	@Transient
	public Float getLatitudeLastSync() {
		return latitudeLastSync;
	}
	public void setLatitudeLastSync(Float latitudeLastSync) {
		this.latitudeLastSync = latitudeLastSync;
	}
	@Transient
	public Float getLongitudeLastSync() {
		return longitudeLastSync;
	}
	public void setLongitudeLastSync(Float longitudeLastSync) {
		this.longitudeLastSync = longitudeLastSync;
	}
	@Transient
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
}
