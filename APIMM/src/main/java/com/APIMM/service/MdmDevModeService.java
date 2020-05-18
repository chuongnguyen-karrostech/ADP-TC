package com.APIMM.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

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
import com.APIMM.mam.nonemodel.NoneMdmDeviceMode;
import com.APIMM.mam.repository.MamDefaultModuleRepository;
import com.APIMM.mam.repository.MamDevModuleRepository;
import com.APIMM.mam.repository.MamDeviceSettingRepository;
import com.APIMM.mam.repository.MamModuleRepository;
import com.APIMM.mam.repository.MamSettingRepository;
import com.APIMM.mam.repository.MdmDeviceModeRepository;
import com.APIMM.mam.repository.MdmDeviceRepository;
import com.APIMM.mam.repository.MdmModeRepository;

@Service
public class MdmDevModeService {

	@Autowired
	MdmDeviceModeRepository devModeRepo;
	@Autowired
	MdmDeviceRepository deviceserver;
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

	public List<MdmDeviceMode> findAll() {
		return devModeRepo.findAll();
	}

	@Transactional
	public Exception update(List<NoneMdmDeviceMode> devModes) {
		try {
			Date date = new Date();
			Timestamp dateUpdated = new Timestamp(date.getTime());
			for (NoneMdmDeviceMode mdmDeviceMode : devModes) {
				MdmDeviceMode devMode = devModeRepo.findOne(mdmDeviceMode.getDevModeID());
				MdmMode mdmModes = modeserver.findOne(mdmDeviceMode.getModeID());
				if (devMode != null && mdmModes != null) {
					devMode.setDevModeUpdated(dateUpdated);
					devMode.setDevBusID(mdmDeviceMode.getDevBusID());
					devMode.setMdmModes(mdmModes);;
					devModeRepo.save(devMode);
				}
			}
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			return e;
		}
	}

	@Transactional
	public List<MdmDeviceMode> findModebyDeviceMacAddress(String macAddress) {

		List<MdmDeviceMode> rs = new ArrayList<MdmDeviceMode>();
		List<MdmDeviceMode> ls = devModeRepo.findModebyDeviceMacAddress(macAddress);
		if (ls.size() > 0) {
			rs.add(ls.get(0));
		}
		return rs;
	}

	@Transactional
	public List<MdmDeviceMode> findModebyDeviceMacAddress(String macAddress, String serialNumber) {

		List<MdmDeviceMode> rs = new ArrayList<MdmDeviceMode>();
		List<MdmDeviceMode> ls = devModeRepo.findModebyDeviceMacAddress(macAddress);
		if (ls.size() > 0) {
			rs.add(ls.get(0));
		} else {
			long devcount = deviceserver.getdevicecount();
			long devmax = Long.parseLong(settingserver.findSettingbyName("LIMIT_NUMBER_OF_DEVICES"));
			if (devcount < devmax || true) {
				// new device
				MdmDevice dev = new MdmDevice();
				dev.setDevMacaddress(macAddress);
				dev.setDevSerialnumber(serialNumber);
				dev.setDevModel("Tablet");
				Date date = new Date();
				Timestamp dateUpdated = new Timestamp(date.getTime());
				dev.setDevUpdated(dateUpdated);
				dev = deviceserver.save(dev);
				// new device mode
				MdmMode mode = modeserver.findByModeName("Floating");
				MdmDeviceMode devmode = new MdmDeviceMode();
				devmode.setMdmDevices(dev);
				devmode.setMdmModes(mode);
				devmode.setmodeName(mode.getModeName());
				devmode.setDevModeUpdated(dateUpdated);
				devmode.setDevBusID("");
				devmode = devModeRepo.save(devmode);
				// new device module
				Integer defaulemoduleid = -1;
				List<MamDefaultModule> lstdm = MAM_DefaultModule_Server.findModulebyAppId(1);
				if (lstdm.size() > 0)
					defaulemoduleid = lstdm.get(0).getmoduleId();
				MamModule[] lstmodule = moduleserver.findByApp(1);
				for (int i = 0; i < lstmodule.length; i++) {
					MamModule module = lstmodule[i];
					MamDevModule devmodule = new MamDevModule();
					devmodule.setMdmDevices(dev);
					devmodule.setMamModules(module);
					devmodule.setDevModuleUpdated(dateUpdated);
					devmodule.setDevModuleEnable(module.getmoduleEnable());
					// if (module.getModuleName().equalsIgnoreCase("Settings"))
					// devmodule.setDevModuleEnable(false);
					// else
					// devmodule.setDevModuleEnable(true);
					//
					if (module.getModuleId() != defaulemoduleid)
						devmodule.setisDefault(false);
					else
						devmodule.setisDefault(true);
					devmodule = devmoduleserver.save(devmodule);
				}
				// new device setting
				List<MamSetting> lstsetting = settingserver.findDeviceSettingbyApp(1);
				for (int i = 0; i < lstsetting.size(); i++) {
					MamSetting setting = lstsetting.get(i);
					MamDeviceSetting devsetting = new MamDeviceSetting();
					devsetting.setappID(1);
					devsetting.setdevID(dev.getdevID());
					devsetting.setSettingDescription(setting.getSettingDescription());
					devsetting.setSettingName(setting.getSettingName());
					devsetting.setSettingValue(setting.getSettingValue());
					devsetting.setSettingType(setting.getSettingType());
					devsetting.setSettingUpdated(dateUpdated);
					devsetting = devsettingserver.save(devsetting);
				}
				// add result
				rs.add(devmode);
			}
		}
		return rs;
	}

	public Boolean checkExists(Integer id) {
		return devModeRepo.exists(id);
	}

}
