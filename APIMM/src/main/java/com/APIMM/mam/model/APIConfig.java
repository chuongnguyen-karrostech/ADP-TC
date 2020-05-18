package com.APIMM.mam.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "API_Config")
public class APIConfig implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer setting_id;
	private String setting_name;
	private String setting_value;
	private Date setting_updated;
	
	public APIConfig () {
		
	}
	public APIConfig(Integer setting_id , String setting_name, String setting_value , Date setting_updated) {
		this.setting_id = setting_id;
		this.setting_name = setting_name;
		this.setting_value = setting_value;
		this.setting_updated = setting_updated;
	}
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Column(name = "setting_id")
	public Integer getSetting_id() {
		return setting_id;
	}
	public void setSetting_id(Integer setting_id) {
		this.setting_id = setting_id;
	}
	@Column(name = "setting_name")
	public String getSetting_name() {
		return setting_name;
	}
	public void setSetting_name(String setting_name) {
		this.setting_name = setting_name;
	}

	@Column(name = "setting_value")
	public String getSetting_value() {
		return setting_value;
	}
	public void setSetting_value(String setting_value) {
		this.setting_value = setting_value;
	}
	
	@Column(name = "setting_updated")
	public Date getSetting_time() {
		return setting_updated;
	}
	public void setSetting_time(Date setting_updated) {
		this.setting_updated = setting_updated;
	}
}
