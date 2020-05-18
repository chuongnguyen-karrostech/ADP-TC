package com.ADP.etmain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Settings")
public class Setting implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nameId;
	private String  valueSetting;
	
	public Setting()
	{
		
	}
	
	public Setting(String name, String value)
	{
		this.nameId = name;
		this.valueSetting = value;
	}
	
	@Id
	@GeneratedValue
	@Column(name = "Name")
	public String getNameId() {
		return (nameId != null? nameId.trim():nameId); 
	}
	public void setNameId(String nameId) {
		this.nameId = nameId;
	}
	
	@Column(name = "Value")
	public String getValueSetting() {
		return (valueSetting != null? valueSetting.trim():valueSetting);
	}
	public void setValueSetting(String valueSetting) {
		this.valueSetting = valueSetting;
	}
}
