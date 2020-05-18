package com.ADP.etdb.model;

import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RunInfo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String type;
	private String date;
	private String url;
	private String fileType;
	private String runId;
	
	public RunInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public RunInfo(Integer id, String name, String type, String date, String url, String fileType, String runid) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.date = date;
		this.url = url;
		this.fileType = fileType;
		this.setRunId(runid);
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@JsonFormat(pattern="yyyy-MM-dd")
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@JsonIgnore
	public String getUrl() {
		return url;
	}
	@JsonProperty
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getRunId() {
		return runId;
	}

	public void setRunId(String runId) {
		this.runId = runId;
	}
}
