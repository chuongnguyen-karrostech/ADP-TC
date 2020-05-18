package com.APIMM.etdb.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the routeview database table.
 * 
 */
@Entity
@NamedQuery(name="Routeview.findAll", query="SELECT r FROM Routeview r")
public class Routeview implements Serializable {
	private static final long serialVersionUID = 1L;

	private String beginplanned;

	private String endplanned;

	private String routedescription;

	private String routenumber;

	private String rundescription;

	private String runid;
	@Id
	private String runroute;

	private String school;

	private String schoolname;

	private Integer section;

	private String stoploc;

	private Integer studentcount;

	private String tofrom;

	public Routeview() {
	}

	public String getBeginplanned() {
		return this.beginplanned;
	}

	public void setBeginplanned(String beginplanned) {
		this.beginplanned = beginplanned;
	}

	public String getEndplanned() {
		return this.endplanned;
	}

	public void setEndplanned(String endplanned) {
		this.endplanned = endplanned;
	}

	public String getRoutedescription() {
		return this.routedescription;
	}

	public void setRoutedescription(String routedescription) {
		this.routedescription = routedescription;
	}

	public String getRoutenumber() {
		return this.routenumber;
	}

	public void setRoutenumber(String routenumber) {
		this.routenumber = routenumber;
	}

	public String getRundescription() {
		return this.rundescription;
	}

	public void setRundescription(String rundescription) {
		this.rundescription = rundescription;
	}

	public String getRunid() {
		return this.runid;
	}

	public void setRunid(String runid) {
		this.runid = runid;
	}

	public String getRunroute() {
		return this.runroute;
	}

	public void setRunroute(String runroute) {
		this.runroute = runroute;
	}

	public String getSchool() {
		return this.school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getSchoolname() {
		return this.schoolname;
	}

	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

	public Integer getSection() {
		return this.section;
	}

	public void setSection(Integer section) {
		this.section = section;
	}

	public String getStoploc() {
		return this.stoploc;
	}

	public void setStoploc(String stoploc) {
		this.stoploc = stoploc;
	}

	public Integer getStudentcount() {
		return this.studentcount;
	}

	public void setStudentcount(Integer studentcount) {
		this.studentcount = studentcount;
	}

	public String getTofrom() {
		return this.tofrom;
	}

	public void setTofrom(String tofrom) {
		this.tofrom = tofrom;
	}

}