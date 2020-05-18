package com.APIMM.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APIMM.mam.model.MamDefaultModule;
import com.APIMM.mam.model.MamDevModule;
import com.APIMM.mam.model.MamDeviceSetting;
import com.APIMM.mam.model.MamModule;
import com.APIMM.mam.model.MamSetting;
import com.APIMM.mam.model.MdmDevice;
import com.APIMM.mam.model.MdmDeviceMode;
import com.APIMM.mam.model.MdmMode;
import com.APIMM.mam.repository.MamDefaultModuleRepository;
import com.APIMM.mam.repository.MamDevModuleRepository;
import com.APIMM.mam.repository.MamDeviceSettingRepository;
import com.APIMM.mam.repository.MamModuleRepository;
import com.APIMM.mam.repository.MamSettingRepository;
import com.APIMM.mam.repository.MdmDeviceModeRepository;
import com.APIMM.mam.repository.MdmDeviceRepository;
import com.APIMM.mam.repository.MdmModeRepository;

@Service
public class MdmService {
	@Autowired
	MdmDeviceModeRepository server;
	@Autowired
	MamSettingRepository settingserver;
	@Autowired
	MamDeviceSettingRepository devsettingserver;
	@Autowired
	MdmModeRepository modeserver;
	@Autowired
	MamModuleRepository moduleserver;
	@Autowired
	MamDevModuleRepository devmoduleserver;
	@Autowired
	MamDefaultModuleRepository MAM_DefaultModule_Server;




}
