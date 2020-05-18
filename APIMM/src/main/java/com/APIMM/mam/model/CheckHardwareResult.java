package com.APIMM.mam.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Immutable
@Subselect("SELECT id,mam.mdm_devices.dev_serialnumber as devSerialNumber, message, split_part(split_part(mam.devmessage.message, ',',2),'ID ',2) unitId, "+
		"messagetime , lastupdated  " + 
		" FROM mam.devmessage left join mam.mdm_devices on mam.mdm_devices.dev_id = mam.devmessage.dev_id where modulename like '%LMU%' and functionname like '%Result%'  order by 1 desc")
//@Subselect("SELECT id,mam.mdm_devices.dev_serialnumber as devSerialNumber, message, split_part(mam.devmessage.message, '-',1) col1,"+ 
//		"split_part(mam.devmessage.message, ',',2) unitId, split_part(mam.devmessage.message, ',',3) cellular,split_part(mam.devmessage.message, ',',4) doorclosed, "+
//		"split_part(mam.devmessage.message, ',',5) dooropen,split_part(mam.devmessage.message, ',',6) ignitionoff,split_part(mam.devmessage.message, ',',7) iginitionon, "+
//		"split_part(mam.devmessage.message, ',',8) stopclose,split_part(mam.devmessage.message, ',',9) stopopen,split_part(mam.devmessage.message, ',',10) islocation, "+
//		"split_part(mam.devmessage.message, ',',11) swipecard,split_part(mam.devmessage.message, ',',12) liftdown,split_part(mam.devmessage.message, ',',13) liftup, "+
//		"messagetime as messageDeviceTime , lastupdated as serverTime\r\n" + 
//		"FROM mam.devmessage left join mam.mdm_devices on mam.mdm_devices.dev_id = mam.devmessage.dev_id where modulename like '%LMU%' and functionname like '%Result%'  order by 1 desc")
//@Subselect("SELECT id, mam.devmessage.dev_id,mam.mdm_devices.dev_serialnumber as devSerialNumber, split_part(mam.devmessage.message, '-',1) vername, case strpos(split_part(split_part(mam.devmessage.message, ',',2),'-',3))>0 unitid, case when strpos(split_part(mam.devmessage.message, ',',3),'passed') > 0 then true else false end as isCellularOK,\r\n" + 
//		"case when strpos(split_part(mam.devmessage.message, ',',4),'passed')>0 then true else false end as isDoorCloseOK,case when strpos(split_part(mam.devmessage.message, ',',5),'passed')>0 then true else false end as isDoorOpenOK,case when strpos(split_part(mam.devmessage.message, ',',6),'passed')>0 then true else false end as isIgnitionOff,case when strpos(split_part(mam.devmessage.message, ',',7),'passed')>0 then true else false end as isIgnitionOn,\r\n" + 
//		"case when strpos(split_part(mam.devmessage.message, ',',8),'passed')>0 then true else false end as isStopArmCloseOK,case when strpos(split_part(mam.devmessage.message, ',',9),'passed')>0 then true else false end as isStopArmOpenOK,\r\n" + 
//		"case when strpos(split_part(mam.devmessage.message, ',',10),'passed')>0 then true else false end as isLocationOK,case when strpos(split_part(mam.devmessage.message, ',',11),'passed')>0 then true else false end as isSwipCardOK,case when strpos(split_part(mam.devmessage.message, ',',12),'passed')>0 then true else false end as isWcLiftDownOK,case when strpos(split_part(mam.devmessage.message, ',',13),'passed')>0 then true else false end as isWcLiftUpOK,\r\n" + 
//		"messagetime as messageDeviceTime , lastupdated as serverTime \r\n" +
//		"FROM mam.devmessage left join mam.mdm_devices on mam.mdm_devices.dev_id = mam.devmessage.dev_id where modulename like '%LMU%' and functionname like '%Result%'  order by 1 desc")
public class CheckHardwareResult {

	public String devSerialNumber;	

	@JsonIgnore
	public String message;		
	public String unitId;
	@Transient
	public Boolean isCellularOK;
	@Transient
	public Boolean isDoorCloseOK;
	@Transient
	public Boolean isDoorOpenOK;
	@Transient
	public Boolean isLocationOK;
	@Transient
	public Boolean isStopArmOpenOK;
	@Transient
	public Boolean isStopArmCloseOK;
	@Transient
	public Boolean isSwipCardOK;
	@Transient
	public Boolean isWcLiftDownOK;
	@Transient
	public Boolean isWcLiftUpOK;
	@Transient
	public Boolean isIgnitionOnOk;
	@Transient
	public Boolean isIgnitionOffOk;
	@Id
	public Integer id;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	public Date messageTime;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	public Date lastUpdated;
	@Transient
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	public Date messageDeviceTime;
	@Transient
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	public Date serverTime;
	
	public CheckHardwareResult() {
		
	}

	public CheckHardwareResult(String unitId, Boolean isCellularOK, Boolean isDoorCloseOK, Boolean isDoorOpenOK,
			Boolean isLocationOK, Boolean isStopArmOpenOK, Boolean isStopArmCloseOK, Boolean isSwipCardOK,
			Boolean isWcLiftDownOK, Boolean isWcLiftUpOK, Date messageDeviceTime, Date serverTime) {
		super();
		this.unitId = unitId;
		this.isCellularOK = isCellularOK;
		this.isDoorCloseOK = isDoorCloseOK;
		this.isDoorOpenOK = isDoorOpenOK;
		this.isLocationOK = isLocationOK;
		this.isStopArmOpenOK = isStopArmOpenOK;
		this.isStopArmCloseOK = isStopArmCloseOK;
		this.isSwipCardOK = isSwipCardOK;
		this.isWcLiftDownOK = isWcLiftDownOK;
		this.isWcLiftUpOK = isWcLiftUpOK;
		this.messageTime = messageDeviceTime;
		this.lastUpdated = serverTime;
	}

	public String getDevSerialNumber() {
		return devSerialNumber;
	}

	public void setDevSerialNumber(String devSerialNumber) {
		this.devSerialNumber = devSerialNumber;
	}

	public String getUnitId() {		
		if(unitId.length()>1)
		{
			return unitId.substring(1);
		}
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public Boolean getIsCellularOK() {
		if(this.message.contains("CHECK CELLULAR :  passed"))
			return true;
		return false;
		//return isCellularOK;		
		
	}

	public void setIsCellularOK(Boolean isCellularOK) {
		this.isCellularOK = isCellularOK;
	}

	public Boolean getIsDoorCloseOK() {
		if(this.message.contains("DOOR CLOSE :  passed"))
			return true;
		return false;
		//return isDoorCloseOK;	
	}

	public void setIsDoorCloseOK(Boolean isDoorCloseOK) {
		this.isDoorCloseOK = isDoorCloseOK;
	}

	public Boolean getIsDoorOpenOK() {
		if(this.message.contains("DOOR OPEN :  passed"))
			return true;
		return false;
		//return isDoorOpenOK;
	}

	public void setIsDoorOpenOK(Boolean isDoorOpenOK) {
		this.isDoorOpenOK = isDoorOpenOK;
	}

	public Boolean getIsLocationOK() {
		if(this.message.contains("LOCATION :  passed"))
			return true;
		return false;
		//return isLocationOK;
	}

	public void setIsLocationOK(Boolean isLocationOK) {
		this.isLocationOK = isLocationOK;
	}

	public Boolean getIsStopArmOpenOK() {
		if(this.message.contains("STOP ARM OPEN :  passed"))
			return true;
		return false;
//		return isStopArmOpenOK;
	}

	public void setIsStopArmOpenOK(Boolean isStopArmOpenOK) {
		this.isStopArmOpenOK = isStopArmOpenOK;
	}

	public Boolean getIsStopArmCloseOK() {
		if(this.message.contains("STOP ARM CLOSE :  passed"))
			return true;
		return false;
//		return isStopArmCloseOK;
	}

	public void setIsStopArmCloseOK(Boolean isStopArmCloseOK) {
		this.isStopArmCloseOK = isStopArmCloseOK;
	}

	public Boolean getIsSwipCardOK() {
		if(this.message.contains("SWIPE CARD :  passed"))
			return true;
		return false;
//		return isSwipCardOK;
	}

	public void setIsSwipCardOK(Boolean isSwipCardOK) {
		this.isSwipCardOK = isSwipCardOK;
	}

	public Boolean getIsWcLiftDownOK() {
		if(this.message.contains("WC LIFT DOWN :  passed"))
			return true;
		return false;
//		return isWcLiftDownOK;
	}

	public void setIsWcLiftDownOK(Boolean isWcLiftDownOK) {
		this.isWcLiftDownOK = isWcLiftDownOK;
	}

	public Boolean getIsWcLiftUpOK() {
		if(this.message.contains("WC LIFT UP :  passed"))
			return true;
		return false;
//		return isWcLiftUpOK;
	}

	public void setIsWcLiftUpOK(Boolean isWcLiftUpOK) {
		this.isWcLiftUpOK = isWcLiftUpOK;
	}

	
	public Boolean getIsIgnitionOnOk() {
		if(this.message.contains("IGNITION ON :  passed"))
			return true;
		return false;
		//return isIgnitionOnOk;
	}

	public void setIsIgnitionOnOk(Boolean isIgnitionOnOk) {
		this.isIgnitionOnOk = isIgnitionOnOk;
	}

	public Boolean getIsIgnitionOffOk() {
		if(this.message.contains("IGNITION OFF :  passed"))
			return true;
		return false;
		//return isIgnitionOffOk;
	}

	public void setIsIgnitionOffOk(Boolean isIgnitionOffOk) {
		this.isIgnitionOffOk = isIgnitionOffOk;
	}

	public Date getMessageTime() {
		return messageTime;
	}

	public void setMessageTime(Date messageTime) {
		this.messageTime = messageTime;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Date getMessageDeviceTime() {
		//return messageDeviceTime;
		return this.messageTime;
	}

	public void setMessageDeviceTime(Date messageDeviceTime) {
		this.messageDeviceTime = messageDeviceTime;
	}

	public Date getServerTime() {
		return this.lastUpdated;
		//return serverTime;
	}

	public void setServerTime(Date serverTime) {
		this.serverTime = serverTime;
	}

}
