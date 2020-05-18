package com.APIMM.mam.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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


public class NoneMamDeviceStopEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer deviceStopEventId;
	private Integer devId;
	private String busId;
	private Double latitude;
	private Double longitude;
	private Date eventTime;
	private Date eventSend;
	private Date eventWriteTime;
	private Integer eventId;
	private String eventName;
	private String status;
	private String stopRunId;
	private String runId;
	private String runRoute;
	private Date actualArrival;
	private Date actualDeparture;	
	private Integer loadCount;
	private Integer onBoardStudent;
	private Integer disembarkStudent;
	private String serialNumber;	
	private MdmDevice mdmDevices;

	public NoneMamDeviceStopEvent() {
	}

	public NoneMamDeviceStopEvent(Integer devId, String busId, Double latitude, Double longitude, Date eventTime, Date eventSend, Date eventWriteTime, 
			Integer eventId, String eventName, String status, String stopRunId, String runId, String runRoute,
			Date actualArrival, Date actualDeparture, Integer loadCount, Integer onBoardStudent, Integer disembarkStudent,
			String serialNumber, MdmDevice mdmDevices) {
		this.devId = devId;
		this.busId = busId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.eventTime = eventTime;
		this.eventSend = eventSend;
		this.eventWriteTime = eventWriteTime;
		this.eventId = eventId;
		this.eventName = eventName;
		this.status = status;
		this.stopRunId = stopRunId;
		this.runId = runId;
		this.runRoute = runRoute;
		this.actualArrival = actualArrival;
		this.actualDeparture = actualDeparture;			
		this.loadCount = loadCount;
		this.onBoardStudent = onBoardStudent;
		this.disembarkStudent = disembarkStudent;
		this.serialNumber = serialNumber;
		this.mdmDevices = mdmDevices;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "dev_stop_event_id", unique = true, nullable = false)
	public Integer getdeviceStopEventId() {
		return this.deviceStopEventId;
	}

	public void setdeviceStopEventId(Integer deviceStopEventId) {
		this.deviceStopEventId = deviceStopEventId;
	}

	@JsonIgnore
	@Transient
	public Integer getDevId() {
		if(this.mdmDevices != null)
		{
				return this.mdmDevices.getdevID();
			} 
		return null;
	}

	public void setDevId(Integer devId) {
		this.devId = devId;
	}

	@Column(name = "bus_id")
	public String getBusId() {
		return this.busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}

	@Column(name = "latitude", precision = 53, scale = 0)
	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "longitude", precision = 53, scale = 0)
	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")		
	@Column(name = "event_time", length = 23)
	public Date getEventTime() {
		return this.eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "event_send", length = 23)
	public Date getEventSend() {
		return this.eventSend;
	}

	public void setEventSend(Date eventSend) {
		this.eventSend = eventSend;
	}
	@Column(name = "event_write_tstamp",nullable= false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonIgnore
	public Date geteventWriteTime() {		
		return eventWriteTime;
	}
	public void seteventWriteTime(Date eventWriteTime) {				
		this.eventWriteTime = eventWriteTime;
	}
	@Column(name = "event_id")
	@JsonIgnore
	public Integer getEventId() {
		return this.eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	@Column(name = "event_name")
	public String getEventName() {
		return this.eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	@Column(name = "status")
	public String getstatus() {
		return this.status;
	}

	public void setstatus(String status) {
		this.status = status;
	}
	@Column(name = "stoprunid")
	public String getstopRunId() {
		return this.stopRunId;
	}

	public void setstopRunId(String stopRunId) {
		this.stopRunId = stopRunId;
	}
	
	@Column(name = "run_id")
	public String getrunId() {
		return this.runId;
	}

	public void setrunId(String runId) {
		this.runId = runId;
	}
	
	@Column(name = "runroute")
	public String getrunRoute() {
		return this.runRoute;
	}

	public void setrunRoute(String runRoute) {
		this.runRoute = runRoute;
	}
	
	@Column(name = "actualarrival")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonIgnore
	public Date getactualArrival() {		
		return actualArrival;
	}
	public void setactualArrival(Date actualArrival) {				
		this.actualArrival = actualArrival;
	}
	@Column(name = "actualdeparture")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonIgnore
	public Date getactualDeparture() {		
		return actualDeparture;
	}
	public void setactualDeparture(Date actualDeparture) {				
		this.actualDeparture = actualDeparture;
	}	
	
	@Column(name = "loadCount")	
	public Integer getloadCount() {
		return this.loadCount;
	}

	public void setloadCount(Integer loadCount) {
		this.loadCount = loadCount;
	}
	
	@Column(name = "onBoardStudent")	
	public Integer getonBoardStudent() {
		return this.onBoardStudent;
	}

	public void setonBoardStudent(Integer onBoardStudent) {
		this.onBoardStudent = onBoardStudent;
	}
	
	@Column(name = "disembarkStudent")	
	public Integer getdisembarkStudent() {
		return this.disembarkStudent;
	}

	public void setdisembarkStudent(Integer disembarkStudent) {
		this.disembarkStudent = disembarkStudent;
	}

	@JsonIgnoreProperties({ "mamDevModules", "mamDevRouteHistoricals", "mdmDeviceModes" })	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "dev_id", referencedColumnName = "dev_id", nullable = false)
	public MdmDevice getMdmDevices() {
		return mdmDevices;
	}

	public void setMdmDevices(MdmDevice mdmDevices) {
		this.mdmDevices = mdmDevices;
	}

	@Transient
	public String getSerialNumber() {
		if(this.mdmDevices != null) {
			return this.mdmDevices.getDevSerialnumber();		
		}
		return "";
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
}
