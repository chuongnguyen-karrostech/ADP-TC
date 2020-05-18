package com.APIMM.service;


import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APIMM.mam.model.MamApplication;
import com.APIMM.mam.model.MamDefaultModule;
import com.APIMM.mam.model.MamDevModule;
import com.APIMM.mam.model.MamDevRouteHistorical;
import com.APIMM.mam.model.MamDeviceEvent;
import com.APIMM.mam.model.MamDeviceRawEvent;
import com.APIMM.mam.model.MamDeviceSetting;
import com.APIMM.mam.model.MamEvents;
import com.APIMM.mam.model.MamFiles;
import com.APIMM.mam.model.MamSetting;
import com.APIMM.mam.model.MdmDevice;
import com.APIMM.mam.repository.MamApplicationRepository;
import com.APIMM.mam.repository.MamDefaultModuleRepository;
import com.APIMM.mam.repository.MamDevModuleRepository;
import com.APIMM.mam.repository.MamDevRouteHistoricalRepository;
import com.APIMM.mam.repository.MamDeviceEventRepository;
import com.APIMM.mam.repository.MamDeviceRawEventRepository;
import com.APIMM.mam.repository.MamDeviceSettingRepository;
import com.APIMM.mam.repository.MamEventsRepository;
import com.APIMM.mam.repository.MamFilesRepository;
import com.APIMM.mam.repository.MamSettingRepository;
import com.APIMM.mam.repository.MdmDeviceRepository;
import com.APIMM.util.AppConst;
import com.APIMM.util.DateFunction;
import com.APIMM.util.message.CommonMessage;
import com.APIMM.util.message.EventMessage;
import com.APIMM.util.message.RouteHistoricalMessage;

@Service
public class MamFileService {
	@Autowired
	MamFilesRepository  MAM_File_Server;
	@Autowired
		
	public List<MamFiles> GetFiles() {
		return MAM_File_Server.findAll();
	}	
	public boolean checkFile(String url)
	{		
		try {
		      HttpURLConnection.setFollowRedirects(false);
		      // note : you may also need
		      //        HttpURLConnection.setInstanceFollowRedirects(false)
		      HttpURLConnection con =
		         (HttpURLConnection) new URL(url).openConnection();
		      con.setRequestMethod("HEAD");
		      return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		    }
		    catch (Exception e) {
		       e.printStackTrace();
		       return false;
		    }	
	}
}
