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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
@Table(name = "punch_log")
public class NonePunchLog implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer activityCode;
	private Integer devID;
	private String busID;
	private String driverID;
	private Double latitude;
	private Double longitude;
	private String billingID;
	private Date internalTime;
	private Date lastSyncTime;	
	private String syncType;
	private Double latitudeLastSync;
	private Double longitudeLastSync;
	private Date dateReceived;
	private Date dateEvent;
	private Date sendTime;
	private String serialNumber;
	private MdmDevice mdmDevices;

	
	public NonePunchLog() {}
	
	public NonePunchLog(Integer id,Integer activityCode, Integer devID, String busID, String driverID,
			Double latitude, Double longtitude, String billingID, Date receiveTime, Date internalTime, Date lastSynceTime, String syncType, Double latitudeLastSync, Double longtitudeLastSync, Date dateEvent,
			Date sendTime, String serialNumber, MdmDevice mdmDevice)
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
		this.serialNumber = serialNumber;
		this.mdmDevices = mdmDevice;
	
	}

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	//@Column(name = "dev_id")
	@JsonIgnore
	@Transient		
	public Integer getDevID() {
		return devID;	
	}

	public void setDevID(Integer devID) {
		this.devID = devID;
	}

	@Column(name = "bus_id")
	public String getBusID() {
		return busID;
	}

	public void setBusID(String busID) {
		this.busID = busID;
	}

	@Column(name = "driver_id")
	public String getDriverID() {
		return driverID;
	}

	public void setDriverID(String driverID) {
		this.driverID = driverID;
	}

	@Column(name = "internal_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")	
	public Date getInternalTime() {
		return internalTime;
	}

	public void setInternalTime(Date internalTime) {
		this.internalTime = internalTime;
	}

	@Column(name = "lastSync_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")	
	public Date getLastSyncTime() {
		return lastSyncTime;
	}

	public void setLastSyncTime(Date lastSyncTime) {
		this.lastSyncTime = lastSyncTime;
	}

	@Column(name = "lastSync_type")
	public String getSyncType() {
		return syncType;
	}

	public void setSyncType(String syncType) {
		this.syncType = syncType;
	}

	@Column(name = "lastSync_latitude")
	public Double getLatitudeLastSync() {
		return latitudeLastSync;
	}

	public void setLatitudeLastSync(Double latitudeLastSync) {
		this.latitudeLastSync = latitudeLastSync;
	}

	@Column(name = "lastSync_longtitude")
	public Double getLongitudeLastSync() {
		return longitudeLastSync;
	}

	public void setLongitudeLastSync(Double longitudeLastSync) {
		this.longitudeLastSync = longitudeLastSync;
	}

	@Column(name = "receive_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")	
	public Date getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}

	@Column(name = "activitycode")
	public Integer getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(Integer activityCode) {
		this.activityCode = activityCode;
	}

	@Column(name = "latitude")
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "longtitude")
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}	

	@Column(name = "billing_id")
	public String getBillingID() {
		return billingID;
	}

	public void setBillingID(String billingID) {
		this.billingID = billingID;
	}

	@Column(name = "punch_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")	
	public Date getDateEvent() {
		return dateEvent;
	}

	public void setDateEvent(Date dateEvent) {
		this.dateEvent = dateEvent;
	}

	@Column(name = "send_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")	
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)	
	@JoinColumn(name = "dev_id", referencedColumnName = "dev_id", nullable = true )	
	public MdmDevice getMdmDevices() {
		try{
			return mdmDevices;
		}
		catch(Exception e) {
			return null;
		}
	}

	public void setMdmDevices(MdmDevice mdmDevices) {
		this.mdmDevices = mdmDevices;
	}

	@Transient
	public String getSerialNumber() {
		try {
			return this.mdmDevices.getDevSerialnumber();
		}
		catch(Exception e) {
			return "not available";
		}
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}	

}
