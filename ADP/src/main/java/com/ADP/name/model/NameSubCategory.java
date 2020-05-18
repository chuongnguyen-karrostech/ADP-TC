package com.ADP.name.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "NAME_SUBCATEGORY")
public class NameSubCategory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private Set<Driver> drivers;
	private Set<PrsType> prsTypes;
	
	@Id
	@GeneratedValue
	@Column(name = "NAME_SUBCAT", length = 4)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "NAME_SUBCATDESC", length = 25)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@OneToMany(mappedBy="nameSubCategory")
	public Set<Driver> getDrivers() {
		return drivers;
	}
	public void setDrivers(Set<Driver> drivers) {
		this.drivers = drivers;
	}
	@OneToMany(mappedBy="nameSubCategory")
	public Set<PrsType> getPrsTypes() {
		return prsTypes;
	}
	public void setPrsTypes(Set<PrsType> prsTypes) {
		this.prsTypes = prsTypes;
	}
	
}
