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

@Entity
@Table(name = "MAM_Device_Run_Event")
public class NoneMamDeviceRunEvent {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer deviceRunEventId;
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
	private String runId;
	private String runRoute;
	private Date beginActual;
	private Date endActual;
	private Double earlyLate;
	private String serialNumber;
	private MdmDevice mdmDevices;
	
	public NoneMamDeviceRunEvent() {
	}

	public NoneMamDeviceRunEvent(Integer devId, String busId, Double latitude, Double longitude, Date eventTime, Date eventSend, Date eventWriteTime, 
			Integer eventId, String eventName, String status, String runId, String runRoute, Date beginActual, Date endActual, Double earlyLate,
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
		this.runId = runId;
		this.runRoute = runRoute;
		this.beginActual = beginActual;
		this.endActual = endActual;
		this.earlyLate = earlyLate;
		this.serialNumber = serialNumber;
		this.mdmDevices = mdmDevices;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "dev_run_event_id", unique = true, nullable = false)
	public Integer getDeviceRunEventId() {
		return this.deviceRunEventId;
	}

	public void setdeviceRunEventId(Integer deviceRunEventId) {
		this.deviceRunEventId = deviceRunEventId;
	}

	@Transient
	@JsonIgnore
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
	@Column(name = "beginactual")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonIgnore
	public Date getbeginActual() {		
		return beginActual;
	}
	public void setbeginActual(Date beginActual) {				
		this.beginActual = beginActual;
	}
	@Column(name = "endactual")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonIgnore
	public Date getendActual() {		
		return endActual;
	}
	public void setendActual(Date endActual) {				
		this.endActual = endActual;
	}
	
	@Column(name = "earlylate", precision = 53, scale = 0)
	public Double getearlyLate() {
		return this.earlyLate;
	}

	public void setearlyLate(Double earlyLate) {
		this.earlyLate = earlyLate;
	}
	@JsonIgnore	
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
