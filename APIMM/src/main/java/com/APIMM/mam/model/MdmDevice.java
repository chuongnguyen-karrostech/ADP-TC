package com.APIMM.mam.model;
// Generated Sep 13, 2017 6:30:10 PM by Hibernate Tools 5.2.3.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * MdmDevices generated by hbm2java
 */
@Entity
@Table(name = "MDM_Devices")
public class MdmDevice implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer devID;
	private String devModel;
	private String devSerialnumber;
	private String devName;
	private String devMacaddress;
	private String devOs;
	private Date devUpdated;
	private Set<MamDevModule> mamDevModules = new HashSet<MamDevModule>(0);
	private Set<MamDevRouteHistorical> mamDevRouteHistoricals = new HashSet<MamDevRouteHistorical>(0);
	private Set<MdmDeviceMode> mdmDeviceModes = new HashSet<MdmDeviceMode>(0);	
	
	public MdmDevice() {
	}

	public MdmDevice(String devModel, String devSerialnumber, String devName,
			String devMacaddress, String devOs, Date devUpdated, Set<MamDevModule> mamDevModules,
			Set<MamDevRouteHistorical> mamDevRouteHistoricals, Set<MdmDeviceMode> mdmDeviceModes) {
		this.devModel = devModel;
		this.devSerialnumber = devSerialnumber;
		this.devName = devName;
		this.devMacaddress = devMacaddress;
		this.devOs = devOs;
		this.devUpdated = devUpdated;
		this.mamDevModules = mamDevModules;
		this.mamDevRouteHistoricals = mamDevRouteHistoricals;
		this.mdmDeviceModes = mdmDeviceModes;
	
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "dev_id", unique = true, nullable = false)
	public Integer getdevID() {
		return this.devID;
	}

	public void setdevID(Integer devID) {
		this.devID = devID;
	}

	@Column(name = "dev_model")
	public String getDevModel() {
		return this.devModel;
	}

	public void setDevModel(String devModel) {
		this.devModel = devModel;
	}

	@Column(name = "dev_serialnumber")
	public String getDevSerialnumber() {
		return this.devSerialnumber;
	}

	public void setDevSerialnumber(String devSerialnumber) {
		this.devSerialnumber = devSerialnumber;
	}

	@Column(name = "dev_name")
	public String getDevName() {
		return this.devName;
	}

	public void setDevName(String devName) {
		this.devName = devName;
	}

	@Column(name = "dev_macaddress")
	public String getDevMacaddress() {
		return this.devMacaddress;
	}

	public void setDevMacaddress(String devMacaddress) {
		this.devMacaddress = devMacaddress;
	}

	@Column(name = "dev_os")
	public String getDevOs() {
		return this.devOs;
	}

	public void setDevOs(String devOs) {
		this.devOs = devOs;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dev_updated", length = 23)
	public Date getDevUpdated() {
		return this.devUpdated;
	}

	public void setDevUpdated(Date devUpdated) {
		this.devUpdated = devUpdated;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "mdmDevices")
	public Set<MamDevModule> getMamDevModules() {
		return this.mamDevModules;
	}

	public void setMamDevModules(Set<MamDevModule> mamDevModules) {
		this.mamDevModules = mamDevModules;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "mdmDevices")
	public Set<MamDevRouteHistorical> getMamDevRouteHistoricals() {
		return this.mamDevRouteHistoricals;
	}

	public void setMamDevRouteHistoricals(Set<MamDevRouteHistorical> mamDevRouteHistoricals) {
		this.mamDevRouteHistoricals = mamDevRouteHistoricals;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "mdmDevices")
	public Set<MdmDeviceMode> getMdmDeviceModes() {
		return this.mdmDeviceModes;
	}

	public void setMdmDeviceModes(Set<MdmDeviceMode> mdmDeviceModes) {
		this.mdmDeviceModes = mdmDeviceModes;
	}
	
	
}
