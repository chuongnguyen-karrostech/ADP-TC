package com.APIMM.mam.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;

import com.APIMM.ename.model.NameSubCategory;
import com.APIMM.etext.model.EventCodes;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "billingtype_activitycode")
public class BillingActivity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// @SequenceGenerator(name="seq-gen",sequenceName="billingtype_activitycode_GEN",
	// initialValue=1, allocationSize=1)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // , generator = "seq-gen"
	@Column(name = "id", unique = true, nullable = false)
	private Integer Id;
	@Column(name = "billingid")
	private String billingId;
	@Column(name = "startid")
	private Integer startId;

	public BillingActivity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BillingActivity(Integer Id) {
		this.Id = Id;
	}

	public BillingActivity(Integer Id, String billingId, Integer startId) {
		this.Id = Id;
		this.billingId = billingId;
		this.startId = startId;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getBillingId() {
		return billingId;
	}

	public void setBillingId(String billingId) {
		this.billingId = billingId;
	}

	public Integer getStartId() {
		return startId;
	}

	public void setStartId(Integer startId) {
		this.startId = startId;
	}

}
