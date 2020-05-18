package com.APIMM.mam.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "MAM_Settings")
public class NoneMamSetting implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer settingId;
	private Integer appID;
	private String settingName;	
	private String settingDescription;
	private String settingValue;
	private String settingType;
	private Date settingUpdated;
	private Set<MamModuleSetting> mamModuleSettings = new HashSet<MamModuleSetting>(0);
	private Set<NoneMamDeviceSettingWeb> mamDeviceSettings = new HashSet<NoneMamDeviceSettingWeb>(0);
	private Integer condId;
	private Condition condSetting;
	private String defaultValue;

	public NoneMamSetting() {
	}

	public NoneMamSetting(Integer settingId) {
		this.settingId = settingId;
	}

	public NoneMamSetting(Integer settingId, Integer appID, String settingName, String settingValue,String settingDescription, 
			String settingType, Date settingUpdated,Set<MamModuleSetting> mamModuleSettings, Condition condSetting , String defauValue) {
		this.settingId = settingId;
		this.appID = appID;
		this.settingName = settingName;
		this.settingValue = settingValue;
		this.settingDescription = settingDescription;
		this.settingType = settingType;
		this.settingUpdated = settingUpdated;
		this.mamModuleSettings = mamModuleSettings;
		this.condSetting = condSetting;
		this.setDefaultValue(defauValue);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Column(name = "setting_id", unique = true, nullable = false)
	public Integer getSettingId() {
		return this.settingId;
	}

	public void setSettingId(Integer settingId) {
		this.settingId = settingId;
	}
	@JsonIgnore
	@Column(name = "app_id")
	public Integer getappID() {
		return this.appID;
	}

	public void setappID(Integer appID) {
		this.appID = appID;
	}
	
	@Column(name = "setting_name")
	public String getSettingName() {
		return this.settingName;
	}

	public void setSettingName(String settingName) {
		this.settingName = settingName;
	}

	@Column(name = "setting_value")
	public String getSettingValue() {
		return this.settingValue;
	}
	
	public void setSettingValue(String settingValue) {
		this.settingValue = settingValue;
	}
	
	@Column(name = "setting_description")
	public String getSettingDescription() {
		return this.settingDescription;
	}

	public void setSettingDescription(String settingDescription) {
		this.settingDescription = settingDescription;
	}
	
	@Column(name = "setting_type")
	public String getSettingType() {
		return this.settingType;
	}

	public void setSettingType(String setting_Type) {
		this.settingType = setting_Type;
	}
	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "setting_updated", length = 23)
	public Date getSettingUpdated() {
		return this.settingUpdated;
	}

	public void setSettingUpdated(Date settingUpdated) {
		this.settingUpdated = settingUpdated;
	}
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mamSettings")
	public Set<MamModuleSetting> getMamModuleSettings() {
		return this.mamModuleSettings;
	}

	public void setMamModuleSettings(Set<MamModuleSetting> mamModuleSettings) {
		this.mamModuleSettings = mamModuleSettings;
	}

	@Column(name = "condition_id")
	public Integer getCondId() {
		return condId;		
	}

	public void setCondId(Integer condId) {
		this.condId = condId;
	}
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name="condition_id", referencedColumnName = "condition_id" ,insertable = false , updatable = false)
	public Condition getCondSetting() {
		try {
			return condSetting;
		}
		catch( Exception e) {
			
		}
		return null;
	}

	public void setCondSetting(Condition condSetting) {
		this.condSetting = condSetting;
	}

	@Column(name = "default_value")
	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mamSettings")
	public Set<NoneMamDeviceSettingWeb> getMamDeviceSettings() {
		return mamDeviceSettings;
	}

	public void setMamDeviceSettings(Set<NoneMamDeviceSettingWeb> mamDeviceSettings) {
		this.mamDeviceSettings = mamDeviceSettings;
	}

	

}
