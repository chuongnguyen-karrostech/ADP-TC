package com.APIMM.mam.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "check_hardware")
public class CheckHardware {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id")
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="critical")
	private String critical;
	
	@Column(name="eventcode")
	private Integer eventcode;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	@Column(name="updated")
	private Date updated;
	
	@Column(name="description")
	private String description;
	
	@Column(name="check_order")
	private Integer order;
	
	
	public CheckHardware() {

	}

	public CheckHardware(Integer id, String name, String critical,Integer value, Date updated , String Description) {
		super();
		this.setId(id);
		this.setCritical(critical);;
		this.setName(name);;
		this.setEventcode(value);
		this.setUpdated(updated);
		this.setDescription(Description);		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCritical() {
		return critical;
	}
	public void setCritical(String critical) {
		this.critical = critical;
	}
	
	public Integer getEventcode() {
		return eventcode;
	}
	public void setEventcode(Integer eventcode) {
		this.eventcode = eventcode;
	}
	
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

}
