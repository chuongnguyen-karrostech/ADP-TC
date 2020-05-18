package com.APIMM.mam.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
@Immutable
@Subselect("SELECT id, mam.punch_log.dev_id as devid, mam.mdm_devices.dev_serialnumber as devSerialNumber , activitycode, bus_id, driver_id, billing_id, latitude, longtitude, internal_time, lastsync_time, lastsync_type, lastsync_latitude, lastsync_longtitude, receive_time, punch_time, send_time\r\n" + 
		" FROM mam.punch_log join mam.mdm_devices on mam.mdm_devices.dev_id = mam.punch_log.dev_id where send_time - punch_time > interval '1 hour' order  by 1 desc")
public class NonePunchLogOffline implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "activitycode")
	private Integer activityCode;
	//@Column(name = "dev_id")
	
	private Integer devID;
	
	@Column(name = "bus_id")
	private String busID;
	
	@Column(name = "driver_id")
	private String driverID;
	
	@Column(name = "latitude")
	private Double latitude;
	
	@Column(name = "longtitude")
	private Double longitude;
	
	@Column(name = "billing_id")
	private String billingID;
	
	@Column(name = "internal_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	private Date internalTime;
	
	@Column(name = "lastSync_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	private Date lastSyncTime;	
	
	@Column(name = "lastSync_type")
	private String syncType;
	
	@Column(name = "lastSync_latitude")
	private Double latitudeLastSync;
	@Column(name = "lastSync_longtitude")
	private Double longitudeLastSync;
	
	@Column(name = "receive_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")	
	private Date dateReceived;
	
	@Column(name = "punch_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	private Date dateEvent;
	
	@Column(name = "send_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	private Date sendTime;
	
	private String devSerialNumber;	
	
	public NonePunchLogOffline() {}
	
	public NonePunchLogOffline(Integer id,Integer activityCode, Integer devID, String busID, String driverID,
			Double latitude, Double longtitude, String billingID, Date receiveTime, Date internalTime, Date lastSynceTime, String syncType, Double latitudeLastSync, Double longtitudeLastSync, Date dateEvent,
			Date sendTime, String serialNumber)
	{
		this.id = id;
		this.activityCode = activityCode;
		this.devID = devID;
		this.busID = busID;
		this.driverID = driverID;		
		this.dateReceived = receiveTime;		
		this.latitude = latitude;
		this.longitude = longtitude;		
		this.billingID = billingID;
		this.dateEvent = dateEvent;
		this.latitudeLastSync = latitudeLastSync;
		this.longitudeLastSync = longtitudeLastSync;
		this.internalTime = internalTime;
		this.syncType = syncType;
		this.lastSyncTime = lastSynceTime;
		this.sendTime = sendTime;
		this.devSerialNumber = serialNumber;
	
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

		
	public Integer getDevID() {
		return devID;	
	}

	public void setDevID(Integer devID) {
		this.devID = devID;
	}

	
	public String getBusID() {
		return busID;
	}

	public void setBusID(String busID) {
		this.busID = busID;
	}

	public String getDriverID() {
		return driverID;
	}

	public void setDriverID(String driverID) {
		this.driverID = driverID;
	}

	public Date getInternalTime() {
		return internalTime;
	}

	public void setInternalTime(Date internalTime) {
		this.internalTime = internalTime;
	}

	public Date getLastSyncTime() {
		return lastSyncTime;
	}

	public void setLastSyncTime(Date lastSyncTime) {
		this.lastSyncTime = lastSyncTime;
	}

	public String getSyncType() {
		return syncType;
	}

	public void setSyncType(String syncType) {
		this.syncType = syncType;
	}

	public Double getLatitudeLastSync() {
		return latitudeLastSync;
	}

	public void setLatitudeLastSync(Double latitudeLastSync) {
		this.latitudeLastSync = latitudeLastSync;
	}

	public Double getLongitudeLastSync() {
		return longitudeLastSync;
	}

	public void setLongitudeLastSync(Double longitudeLastSync) {
		this.longitudeLastSync = longitudeLastSync;
	}

	
	public Date getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}

	
	public Integer getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(Integer activityCode) {
		this.activityCode = activityCode;
	}

	
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}	

	
	public String getBillingID() {
		return billingID;
	}

	public void setBillingID(String billingID) {
		this.billingID = billingID;
	}

	public Date getDateEvent() {
		return dateEvent;
	}

	public void setDateEvent(Date dateEvent) {
		this.dateEvent = dateEvent;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getDevSerialNumber() {
		return devSerialNumber;
	}

	public void setDevSerialNumber(String devSerialNumber) {
		this.devSerialNumber = devSerialNumber;
	}

}
