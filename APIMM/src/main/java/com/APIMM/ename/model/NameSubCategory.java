package com.APIMM.ename.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "NAME_SUBCATEGORY")
public class NameSubCategory {

	@Id
	@Column(name = "NAME_SUBCAT")
	private String billingId;
	@Column(name = "NAME_SUBCATDESC")
	private String desc;
	
	
	public NameSubCategory() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the billingId
	 */
	public String getBillingId() {
		return billingId;
	}
	/**
	 * @param billingId the billingId to set
	 */
	public void setBillingId(String billingId) {
		this.billingId = billingId;
	}
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
	
}
