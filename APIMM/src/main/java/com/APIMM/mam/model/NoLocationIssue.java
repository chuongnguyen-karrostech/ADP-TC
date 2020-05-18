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
@Subselect("SELECT id, mam.devmessage.dev_id,mam.mdm_devices.dev_serialnumber as devSerialNumber, modulename, functionname, message, messagetime, lastupdated\r\n" + 
		"	FROM mam.devmessage join mam.mdm_devices on mam.mdm_devices.dev_id = mam.devmessage.dev_id where functionname like 'addRunEvent' and message like '%Cannot get location' order by lastupdated desc")
public class NoLocationIssue implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	@Column(name="dev_id")
	private Integer devId;
	
	private String moduleName;	
	private String functionName;
	private String message;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	private Date messageTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	private Date lastUpdated;
	
	private String devSerialNumber;
	
	
	public NoLocationIssue() {}
	
	public NoLocationIssue(Integer id, String moduleName, String functionName,String message, 
			Date messageTime, Date lastUpdated, MdmDevice mdmDevices , String serialNumber ) {
		this.id = id;				
		this.moduleName = moduleName;
		this.functionName = functionName;
		this.message = message;
		this.messageTime = messageTime;
		this.lastUpdated = lastUpdated;		
		this.devSerialNumber = serialNumber;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDevId() {
		return devId;
	}

	public void setDevId(Integer devId) {
		this.devId = devId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getMessageTime() {
		return messageTime;
	}

	public void setMessageTime(Date messageTime) {
		this.messageTime = messageTime;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getDevSerialNumber() {
		return devSerialNumber;
	}

	public void setDevSerialNumber(String devSerialNumber) {
		this.devSerialNumber = devSerialNumber;
	}

}
