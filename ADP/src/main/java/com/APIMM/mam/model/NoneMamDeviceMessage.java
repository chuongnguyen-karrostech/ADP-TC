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


public class NoneMamDeviceMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;	
	private Integer devId;
	private String moduleName;	
	private String functionName;
	private String message;
	private Date messageTime;
	private Date lastUpdated;
	private String serialNumber;
	private MdmDevice mdmDevices;

	public NoneMamDeviceMessage() {
	}

	public NoneMamDeviceMessage(Integer id) {
		this.id = id;
	}

	public NoneMamDeviceMessage(Integer id, String moduleName, String functionName,String message, 
			Date messageTime, Date lastUpdated, MdmDevice mdmDevices , String serialNumber ) {
		this.id = id;				
		this.moduleName = moduleName;
		this.functionName = functionName;
		this.message = message;
		this.messageTime = messageTime;
		this.lastUpdated = lastUpdated;
		this.mdmDevices = mdmDevices;
		this.serialNumber = serialNumber;
	}
	
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getid() {
		return this.id;
	}

	public void setid(Integer id) {
		this.id = id;
	}
	
	@JsonIgnore
	@Transient
	public Integer getdevId(){ 
		if(this.mdmDevices != null)
		{
				return this.mdmDevices.getdevID();
			} 
		return null;
	}
	  
	public void setdevId(Integer devID) {
		this.devId = devID; 
	}
	 
	
	@Column(name = "modulename")
	public String getmoduleName() {
		return this.moduleName;
	}

	public void setmoduleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Column(name = "functionname")
	public String getfunctionName() {
		return this.functionName;
	}
	
	public void setfunctionName(String functionname) {
		this.functionName = functionname;
	}
	
	@Column(name = "message")
	public String getmessage() {
		return this.message;
	}

	public void setmessage(String message) {
		this.message = message;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "messagetime", length = 23)
	public Date getmessageTime() {
		return this.messageTime;
	}
	public void setmessageTime(Date messageTime) {
		this.messageTime = messageTime;
	}
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastUpdated", length = 23)
	public Date getlastUpdated() {
		return this.lastUpdated;
	}
	public void setlastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
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
		if(this.mdmDevices != null) {
			return this.mdmDevices.getDevSerialnumber();		
		}
		return "";
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}	

}
