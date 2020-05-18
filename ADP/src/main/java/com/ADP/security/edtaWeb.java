package com.ADP.security;

import java.io.Serializable;

public class edtaWeb implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String employeeId;
    private String vehicleId;
    
    public edtaWeb() {}
    public edtaWeb(String employeeId , String vehicleId) {
    	this.employeeId = employeeId;
    	this.vehicleId = vehicleId;
    }
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
}
