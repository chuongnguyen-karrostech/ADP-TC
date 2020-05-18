package com.APIMM.mam.model;
// Generated Sep 13, 2017 6:30:10 PM by Hibernate Tools 5.2.3.Final

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * MamModules generated by hbm2java
 */
@Entity
@Table(name = "MAM_Modules")
public class MamModule implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer moduleId;
	private Integer appID;
	private MamApplication mamApplication;
	private String moduleName;
	private String moduleDescription;
	private Boolean moduleEnable;
	private Set<MamDevModule> mamDevModules = new HashSet<MamDevModule>(0);
	private Set<MamModuleSetting> mamModuleSettings = new HashSet<MamModuleSetting>(0);

	public MamModule() {
	}

	public MamModule(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public MamModule(Integer moduleId, MamApplication mamApplication, String moduleName, Boolean moduleEnable, 
			String moduleDescription, Set<MamDevModule> mamDevModules, Set<MamModuleSetting> mamModuleSettings) {
		this.moduleId = moduleId;
		this.mamApplication = mamApplication;
		this.moduleName = moduleName;
		this.moduleDescription = moduleDescription;
		this.mamDevModules = mamDevModules;
		this.mamModuleSettings = mamModuleSettings;
		this.moduleEnable = moduleEnable;
	}

	@Id

	@Column(name = "module_id", unique = true, nullable = false)
	public Integer getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_id", insertable = false, updatable = false)
	public MamApplication getMamApplication() {
		return this.mamApplication;
	}

	public void setMamApplication(MamApplication mamApplication) {
		this.mamApplication = mamApplication;
	}

	@Column(name = "module_name")
	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Column(name = "module_description")
	public String getModuleDescription() {
		return this.moduleDescription;
	}

	public void setModuleDescription(String moduleDescription) {
		this.moduleDescription = moduleDescription;
	}
	
	@Column(name = "dev_module_enable")
	public Boolean getmoduleEnable() {
		return this.moduleEnable;
	}

	public void setmoduleEnable(Boolean moduleEnable) {
		this.moduleEnable = moduleEnable;
	}
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "mamModules")
	public Set<MamDevModule> getMamDevModules() {
		return this.mamDevModules;
	}

	public void setMamDevModules(Set<MamDevModule> mamDevModules) {
		this.mamDevModules = mamDevModules;
	}
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "mamModules")
	public Set<MamModuleSetting> getMamModuleSettings() {
		return this.mamModuleSettings;
	}

	public void setMamModuleSettings(Set<MamModuleSetting> mamModuleSettings) {
		this.mamModuleSettings = mamModuleSettings;
	}

}
