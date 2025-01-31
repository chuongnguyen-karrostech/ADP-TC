package com.APIMM.mam.model;
// Generated Sep 13, 2017 6:30:10 PM by Hibernate Tools 5.2.3.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * MamDevRouteHistorical generated by hbm2java
 */
@Entity
@Table(name = "MAM_Dev_Route_Historical")
public class MamDevRouteHistorical implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer devRouteID;
	private MdmDevice mdmDevices;
	private String routeNumber;
	private String routeDescription;
	private String routeBeginPlan;
	private String routeEndPlan;
	private Date devRouteCreated;
	private List<MamDevRunHistorical> mamDevRuns;
	private String busId;
	private Boolean devRoutePlan;
	public MamDevRouteHistorical() {
	}

	public MamDevRouteHistorical(Integer devRouteId, MdmDevice mdmDevices) {
		this.devRouteID = devRouteId;
		this.mdmDevices = mdmDevices;
	}

	public MamDevRouteHistorical(Integer devRouteId, MdmDevice mdmDevices, String routeId, Date devRouteCreated) {
		this.devRouteID = devRouteId;
		this.mdmDevices = mdmDevices;
		this.routeNumber = routeId;
		this.devRouteCreated = devRouteCreated;
	}
	@OrderBy("runId asc, devRunCreated desc")
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL,CascadeType.PERSIST,CascadeType.MERGE }, mappedBy = "mamRoute", orphanRemoval=true)	
	public List<MamDevRunHistorical> getmamDevRuns() {
		return this.mamDevRuns;
	}

	public void setmamDevRuns(List<MamDevRunHistorical> mamDevRunHistoricals) {
		this.mamDevRuns = mamDevRunHistoricals;
	}

	@Id
	@JsonIgnore
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "dev_route_id", unique = true, nullable = false, length = 1)
	public Integer getDevRouteID() {
		return this.devRouteID;
	}

	public void setDevRouteID(Integer devRouteId) {
		this.devRouteID = devRouteId;
	}

	@ManyToOne(cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
	@JoinColumn(name = "dev_id", nullable = false)
	//@JsonIgnoreProperties({ "devModel", "devSerialnumber", "devName", "devMacaddress",
//		"devOs", "devUpdated", "mamDevModules", "mamDevRouteHistoricals", "mdmDeviceModes" })
	@JsonIgnore
	public MdmDevice getMdmDevices() {
		return this.mdmDevices;
	}
	
	@JsonProperty	
	public void setMdmDevices(MdmDevice mdmDevices) {
		this.mdmDevices = mdmDevices;
	}

	@Column(name = "route_id")
	public String getrouteNumber() {
		return this.routeNumber;
	}

	public void setrouteNumber(String routeId) {
		this.routeNumber = routeId;
	}
	@Column(name = "route_description")
	public String getrouteDescription() {
		return this.routeDescription;
	}

	public void setrouteDescription(String routedescription) {
		this.routeDescription = routedescription;
	}
	@Column(name = "route_beginplan")
	public String getrouteBeginPlan() {
		return this.routeBeginPlan;
	}

	public void setrouteBeginPlan(String route_beginplan) {
		this.routeBeginPlan = route_beginplan;
	}
	@Column(name = "route_endplan")
	public String getrouteEndPlan() {
		return this.routeEndPlan;
	}

	public void setrouteEndPlan(String route_beginplan) {
		this.routeEndPlan = route_beginplan;
	}
	//@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "dev_route_created", length = 23)
	public Date getDevRouteCreated() {
		return this.devRouteCreated;
	}

	public void setDevRouteCreated(Date devRouteCreated) {
		this.devRouteCreated = devRouteCreated;
	}
	@Column(name = "bus_id")
	public String getbusId() {
		return this.busId;
	}

	public void setbusId(String busNumber) {
		this.busId = busNumber;
	}
	@Transient
	public Boolean getdevRoutePlan() {
		return this.devRoutePlan;
	}

	public void setdevRoutePlan(Boolean devRoutePlan) {
		this.devRoutePlan = devRoutePlan;
	}
}
