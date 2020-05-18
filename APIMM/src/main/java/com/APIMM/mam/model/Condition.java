package com.APIMM.mam.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "condition")
public class Condition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer condId;
	private String condOperator;
	private String condType;
	private String[] condValue;
	private String condValue1;
	private Date condUpdated;
	private String condDescription;
	//private List<MamSetting> mamSettings;	

	public Condition() {

	}

	public Condition(Integer cond_id, String operator, String cond_type,String cond_value,String[] cond_value1, Date cond_updated , String condDescription) {
		super();
		this.setCondId(cond_id);
		this.setCondOperator(operator);
		this.setCondType(cond_type);
		this.setCondValue(cond_value1);
		this.condValue1 = cond_value;	
		this.setCondUpdated(cond_updated);
		this.setCondDescription(condDescription);
		//this.setMamSetting(mamSetting);	
	}
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="condition_id")
	public Integer getCondId() {
		return condId;
	}

	public void setCondId(Integer condId) {
		this.condId = condId;
	}
	
	@Column(name="condition_operator")
	public String getCondOperator() {
		return condOperator;
	}

	public void setCondOperator(String condOperator) {
		this.condOperator = condOperator;
	}

	@Column(name="condition_type")
	public String getCondType() {
		return condType;
	}

	public void setCondType(String condType) {
		this.condType = condType;
	}

	@JsonIgnore
	@Column(name = "condition_value")
	public String getCondValue1() {
		return condValue1;
	}

	public void setCondValue1(String condValue) {
		this.condValue1 = condValue;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	@Column(name="condition_updated")
	public Date getCondUpdated() {
		return condUpdated;
	}

	public void setCondUpdated(Date condUpdated) {
		this.condUpdated = condUpdated;
	}

	//@JsonIgnore
	//@OneToMany(mappedBy="condSetting", cascade = CascadeType.ALL, fetch = FetchType.LAZY )

	@Transient
	public String[] getCondValue() {
		if(condValue1!=null) {
		condValue = this.condValue1.split(";");}
		return condValue;
	}

	public void setCondValue(String[] condValue) {
		this.condValue = condValue;
	}

	@Column(name="condition_description")
	public String getCondDescription() {
		return condDescription;
	}

	public void setCondDescription(String condDescription) {
		this.condDescription = condDescription;
	}
	
	
}
