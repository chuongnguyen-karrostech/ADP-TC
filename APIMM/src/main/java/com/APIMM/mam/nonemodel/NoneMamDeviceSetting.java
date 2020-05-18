package com.APIMM.mam.nonemodel;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.APIMM.mam.model.Condition;
import com.APIMM.mam.model.MamSetting;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class NoneMamDeviceSetting implements java.io.Serializable {

	private Integer devSettingId;
	private Integer appID;
	private Integer devID;
	private String settingName;	
	private String settingDescription;
	private String settingValue;
	private String settingType;
	private Date settingUpdated;	

	public NoneMamDeviceSetting() {
	}

	public NoneMamDeviceSetting(Integer devSettingId) {
		this.devSettingId = devSettingId;
	}

	public NoneMamDeviceSetting(Integer devSettingId, Integer appID, Integer devID, String settingName, String settingValue,String settingDescription, 
			String settingType, Date settingUpdated) {
		this.devSettingId = devSettingId;
		this.appID = appID;
		this.devID = devID;
		this.settingName = settingName;
		this.settingValue = settingValue;
		this.settingDescription = settingDescription;
		this.settingType = settingType;
		this.settingUpdated = settingUpdated;				
	}
	

	public Integer getdevSettingId() {
		return this.devSettingId;
	}

	public void setdevSettingId(Integer devSettingId) {
		this.devSettingId = devSettingId;
	}

	public Integer getappID() {
		return this.appID;
	}

	public void setappID(Integer appID) {
		this.appID = appID;
	}
	

	public Integer getdevID() {
		return this.devID;
	}

	public void setdevID(Integer devID) {
		this.devID = devID;
	}

	public String getSettingName() {
		return this.settingName;
	}

	public void setSettingName(String settingName) {
		this.settingName = settingName;
	}

	public String getSettingValue() {
		return this.settingValue;
	}
	
	public void setSettingValue(String settingValue) {
		this.settingValue = settingValue;
	}
	
	public String getSettingDescription() {
		return this.settingDescription;
	}

	public void setSettingDescription(String settingDescription) {
		this.settingDescription = settingDescription;
	}

	public String getSettingType() {
		return this.settingType;
	}

	public void setSettingType(String setting_Type) {
		this.settingType = setting_Type;
	}
	
	public Date getSettingUpdated() {
		return this.settingUpdated;
	}
	public void setSettingUpdated(Date settingUpdated) {
		this.settingUpdated = settingUpdated;
	}		

}
