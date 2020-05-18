package com.APIMM.mam.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "dp_driver_theme")
public class DriverColorTheme implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="driver_id")
	private String driverId;
	
	@Column(name="theme_id")
	private Integer themeId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	@Column(name="send_time")
	private Date sendTime;
	
	public DriverColorTheme() {}
	
	public DriverColorTheme(Integer id, String driverId , Integer themeId, Date sendTime) {
		this.id = id;
		this.driverId = driverId;
		this.themeId = themeId;		
		this.sendTime = sendTime;	
	}
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driver_id) {
		this.driverId = driver_id;
	}
	
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date send_time) {
		this.sendTime = send_time;
	}
	
	public Integer getThemeId() {
		return themeId;
	}
	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}
	
}
