package com.APIMM.mam.nonemodel;

//Generated Sep 13, 2017 6:30:10 PM by Hibernate Tools 5.2.3.Final

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

import com.APIMM.mam.model.MdmDevice;
import com.APIMM.mam.model.MdmMode;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class NoneMdmDeviceMode implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer devModeID;
	private MdmDevice mdmDevices;
	private MdmMode mdmModes;
	private int devID;
	private String devBusID;
	private Date devModeUpdated;
	private String modeName;
	private Integer modeID;

	public NoneMdmDeviceMode() {
	}

	public NoneMdmDeviceMode(Integer devModeId, MdmDevice mdmDevices, MdmMode mdmModes) {
		this.devModeID = devModeId;
		this.mdmDevices = mdmDevices;
		this.mdmModes = mdmModes;
	}

	public NoneMdmDeviceMode(Integer devModeId, MdmDevice mdmDevices, MdmMode mdmModes, String devBusId,
			Date devModeUpdated) {
		this.devModeID = devModeId;
		this.mdmDevices = mdmDevices;
		this.mdmModes = mdmModes;
		this.devBusID = devBusId;
		this.devModeUpdated = devModeUpdated;
	}

	public Integer getDevModeID() {
		return this.devModeID;
	}

	public void setDevModeID(Integer devModeId) {
		this.devModeID = devModeId;
	}

	@JsonIgnore
	public MdmDevice getMdmDevices() {
		return this.mdmDevices;
	}

	public void setMdmDevices(MdmDevice mdmDevices) {
		this.mdmDevices = mdmDevices;
	}

	@JsonIgnore
	public MdmMode getMdmModes() {
		return this.mdmModes;
	}

	public void setMdmModes(MdmMode mdmModes) {
		this.mdmModes = mdmModes;
	}

	public int getdevID() {
		if (this.mdmModes != null) {
			return this.mdmDevices.getdevID();
		}
		return -1;
	}

	public void setdevID(int devid) {
		this.devID = devid;
	}

	public String getDevBusID() {
		return this.devBusID;
	}

	public void setDevBusID(String devBusId) {
		this.devBusID = devBusId;
	}

	public Date getDevModeUpdated() {
		return this.devModeUpdated;
	}

	public void setDevModeUpdated(Date devModeUpdated) {
		this.devModeUpdated = devModeUpdated;
	}

	public String getmodeName() {
		if (this.mdmModes != null) {
			return this.mdmModes.getModeName();
		}
		return this.modeName;
	}

	public void setmodeName(String modename) {
		this.modeName = modename;
	}

	public Integer getModeID() {
		if (this.mdmModes != null) {
			return this.mdmModes.getModeId();
		}
		return this.modeID;
	}

	public void setModeID(Integer modeID) {
		this.modeID = modeID;
	}

}
