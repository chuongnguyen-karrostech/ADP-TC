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
import com.APIMM.mam.repository.MamDefaultModuleRepository;
import com.APIMM.mam.repository.MamDevModuleRepository;
import com.APIMM.mam.repository.MamDeviceSettingRepository;
import com.APIMM.mam.repository.MamModuleRepository;
import com.APIMM.mam.repository.MamSettingRepository;
import com.APIMM.mam.repository.MdmDeviceModeRepository;
import com.APIMM.mam.repository.MdmDeviceRepository;
import com.APIMM.mam.repository.MdmModeRepository;

@Service
public class MdmDeviceService {

	@Autowired
	MdmDeviceRepository deviceServer;
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

	public MdmDevice findDevicebyid(int devid) {
		return deviceServer.findByDevID(devid);
	}
	
	public List<MdmDevice> findDevicebyserial(String serial) {
		return deviceServer.findDeviceBySerialNumber(serial);
	}

	public List<MdmDevice> findAll() {
		return deviceServer.findAll();
	}

	@Transactional
	public Exception save(String macAddress, String serialNumber) {
		try {
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
				//devmode.setDevID(dev.getdevID());
				devmode.setModeID(mode.getModeId());
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
					devmodule.setDevId(dev.getdevID());
					devmodule.setModuleId(module.getModuleId());

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
			}
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			return e;
		}

	}

	public Exception update(MdmDevice device) {
		try {

			Date date = new Date();
			Timestamp devUpdated = new Timestamp(date.getTime());
			device.setDevUpdated(devUpdated);
			deviceServer.save(device);

			return null;

		} catch (Exception e) {
			// TODO: handle exception
			return e;
		}
	}

	public Boolean checkExists(String macAddress) {
		MdmDevice d = deviceServer.findByMacAddress(macAddress);
		if (d == null) {
			return false;
		}
		return true;
	}

	@Transactional
	public Exception delete(MdmDevice device) {
		try {

			// devModeRepo
			// devmode
			// devmoduleserver
			// devmodule
			// devsettingserver
			// devsetting

			List<MdmDeviceMode> devModes = devModeRepo.findModebyDeviceMacAddress(device.getDevMacaddress());
			if (devModes.size() > 0) {
				devModeRepo.deleteInBatch(devModes);
			}
			List<MamDevModule> devModules = devmoduleserver.findModulebyDeviceMacAddress(1, device.getdevID());
			if (devModules.size() > 0) {
				devmoduleserver.deleteInBatch(devModules);
			}
			List<MamDeviceSetting> devSettings = devsettingserver.findSettingbyApp(1, device.getdevID());
			if(devSettings.size() > 0) {
				devsettingserver.deleteInBatch(devSettings);
			}
			
			deviceServer.delete(device.getdevID());

			return null;
		} catch (Exception e) {
			// TODO: handle exception
			return e;
		}
	}

	public Boolean checkExists(Integer Id) {
		return deviceServer.exists(Id);
	}

}
