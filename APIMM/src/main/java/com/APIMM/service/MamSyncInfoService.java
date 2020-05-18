package com.APIMM.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APIMM.mam.model.MamDevSyncInfo;
import com.APIMM.mam.model.MamSyncInfo;
import com.APIMM.mam.repository.MamDevSyncInfoRepository;
import com.APIMM.mam.repository.MamSyncInfoRepository;
import com.APIMM.util.DateFunction;

@Service
public class MamSyncInfoService {
	@Autowired
	MamSyncInfoRepository mamSyncInfoRepository;	
	
	@Autowired
	MamDevSyncInfoRepository mamDevSyncInfoRepository;
	
	public List<MamSyncInfo> getSyncInfoByDate(Date date){
		return mamSyncInfoRepository.findByDateGreaterThanEqual(date);
	}
	
	public MamSyncInfo findSynsInfoByID(Integer id) {
		return mamSyncInfoRepository.findByid(id);
	}
	public MamSyncInfo findSynsInfoByDate(Date date) {
		return mamSyncInfoRepository.findByDate(date);
	}
	public MamDevSyncInfo updateDevSyncInfoByAppDevice(Integer appid,Integer devid, Date date) {
		MamDevSyncInfo mds;
		mds = mamDevSyncInfoRepository.findbyAppandDevice(appid, devid);
		Date dateUpdate = new Date();
		Timestamp dateReceived = DateFunction.ChangeTimewithZone(new Timestamp(dateUpdate.getTime()));
		if (mds ==null) mds = new MamDevSyncInfo();
		mds.setappID(appid);
		mds.setdevID(devid);
		mds.setsyncDate(date);
		mds.setUpdateTime(dateReceived);
		mds = mamDevSyncInfoRepository.save(mds);
		return mds;
	}
}
