package com.ADP.name.model;

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
@Table(name = "PRS_TYPE")
public class PrsType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sysId;
	private NameSubCategory nameSubCategory;
	
	@Id
	@GeneratedValue
	@Column(name = "SYS_ID")
	public String getSysId() {
		return sysId;
	}
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="name_subcat")
	public NameSubCategory getNameSubCategory() {
		return nameSubCategory;
	}
	public void setNameSubCategory(NameSubCategory nameSubCategory) {
		this.nameSubCategory = nameSubCategory;
	}
}
