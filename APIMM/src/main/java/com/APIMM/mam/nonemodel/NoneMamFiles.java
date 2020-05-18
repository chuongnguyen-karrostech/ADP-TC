package com.APIMM.mam.nonemodel;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class NoneMamFiles implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer fileId;
	private String fileType;
	private String version;
	private String url;
	private Date fileUpdate;

	public NoneMamFiles() {
	}

	public NoneMamFiles(String fileType, String version, String url, Date fileUpdate) {
		this.fileType = fileType;
		this.version = version;
		this.url = url;
		this.fileUpdate = fileUpdate;
	}

	public Integer getfileId() {
		return this.fileId;
	}

	public void setfileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Column(name = "file_type")
	public String getfileType() {
		return this.fileType;
	}

	public void setfileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name = "file_version")
	public String getversion() {
		return this.version;
	}

	public void setversion(String version) {
		this.version = version;
	}
	@Column(name = "file_url")
	public String geturl() {
		return this.url;
	}

	public void seturl(String url) {
		this.url = url;
	}
	
	public Date getfileUpdate() {
		return this.fileUpdate;
	}

	public void setfileUpdate(Date fileUpdate) {
		this.fileUpdate = fileUpdate;
	}
}
