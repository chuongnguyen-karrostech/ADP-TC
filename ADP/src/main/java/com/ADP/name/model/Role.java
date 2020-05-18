package com.ADP.name.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*@Entity
@Table(name = "[Role]")*/
@JsonIgnoreProperties({"e_user"})
public class Role {
	
	private int roleid;
	private String name;	
	private String description;
	private List<User> e_user;
	
	public Role() {
		super();
	}	
	public Role(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="e_role")	
	public List<User> getE_user() {
		return e_user;
	}
	public void setE_user(List<User> e_user) {
		this.e_user = e_user;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getRoleid() {
		return roleid;
	}
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
}
