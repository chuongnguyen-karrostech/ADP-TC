package com.APIMM.etdb.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;


/**
 * The persistent class for the stopview database table.
 * 
 */
@Entity
@NamedQuery(name="Stopview.findAll", query="SELECT s FROM Stopview s")
public class Stopview implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer countstudent;

	private String fstopid;

	private float latitude;

	private String location;

	private float longitude;

	private Integer nearby;

	private String runroute;

	private Time scheduledarrival;

	private Integer schoolstop;

	private String stopid;
	@Id
	private String stoprunid;

	private Integer stopseq;

	private String studentlist;

	private Time timehigh;

	private Time timelow;

	public Stopview() {
	}

	public Integer getCountstudent() {
		return this.countstudent;
	}

	public void setCountstudent(Integer countstudent) {
		this.countstudent = countstudent;
	}

	public String getFstopid() {
		return this.fstopid;
	}

	public void setFstopid(String fstopid) {
		this.fstopid = fstopid;
	}

	public float getLatitude() {
		return this.latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public float getLongitude() {
		return this.longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public Integer getNearby() {
		return this.nearby;
	}

	public void setNearby(Integer nearby) {
		this.nearby = nearby;
	}

	public String getRunroute() {
		return this.runroute;
	}

	public void setRunroute(String runroute) {
		this.runroute = runroute;
	}

	public Time getScheduledarrival() {
		return this.scheduledarrival;
	}

	public void setScheduledarrival(Time scheduledarrival) {
		this.scheduledarrival = scheduledarrival;
	}

	public Integer getSchoolstop() {
		return this.schoolstop;
	}

	public void setSchoolstop(Integer schoolstop) {
		this.schoolstop = schoolstop;
	}

	public String getStopid() {
		return this.stopid;
	}

	public void setStopid(String stopid) {
		this.stopid = stopid;
	}

	public String getStoprunid() {
		return this.stoprunid;
	}

	public void setStoprunid(String stoprunid) {
		this.stoprunid = stoprunid;
	}

	public Integer getStopseq() {
		return this.stopseq;
	}

	public void setStopseq(Integer stopseq) {
		this.stopseq = stopseq;
	}

	public String getStudentlist() {
		return this.studentlist;
	}

	public void setStudentlist(String studentlist) {
		this.studentlist = studentlist;
	}

	public Time getTimehigh() {
		return this.timehigh;
	}

	public void setTimehigh(Time timehigh) {
		this.timehigh = timehigh;
	}

	public Time getTimelow() {
		return this.timelow;
	}

	public void setTimelow(Time timelow) {
		this.timelow = timelow;
	}

}