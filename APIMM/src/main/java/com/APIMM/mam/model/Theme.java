package com.APIMM.mam.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name = "dp_theme")
public class Theme implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id")
	public Integer id;
	
	@Column(name="name")
	public String name;
	
	@Column(name="is_default")
	public Boolean isDefault;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "theme")
	private Set<ColorTheme> style = new HashSet<ColorTheme>(0);
	
	public Theme() {
		
	}	
	public Theme(Integer themeId , Set<ColorTheme> style, String themeName, Boolean themeDefault) {
		this.id = themeId;
		this.name = themeName;
		this.style = style;
		this.isDefault = themeDefault;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer Id) {
		this.id = Id;
	}
	public Set<ColorTheme> getStyle() {
		return style;
	}
	public void setStyle(Set<ColorTheme> style) {
		this.style = style;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}	
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
}
