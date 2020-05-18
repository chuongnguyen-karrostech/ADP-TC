package com.APIMM.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.APIMM.mam.model.ColorTheme;
import com.APIMM.mam.model.Condition;
import com.APIMM.mam.model.DriverColorTheme;
import com.APIMM.mam.model.Theme;
import com.APIMM.mam.repository.ColorThemeRepository;
import com.APIMM.mam.repository.DriverColorThemeRepository;
import com.APIMM.mam.repository.ThemeRepository;
import com.APIMM.util.message.CommonMessage;

@Service
public class ColorThemeService {

	@Autowired
	ColorThemeRepository colorThemeRep;
	@Autowired
	DriverColorThemeRepository driverColorThemeRep;
	@Autowired
	ThemeRepository themeRep;
	
	public List<ColorTheme> findAllColorTheme() {
		Sort sortbyid = new Sort(Sort.Direction.DESC,"id");
		List<ColorTheme> lstColorTheme = colorThemeRep.findAll(sortbyid);
		return lstColorTheme;
	}
	
	public DriverColorTheme findDriverColorTheme(String driver_id) {	
		String flag = checkDriverIdExist(driver_id);
		DriverColorTheme driverColorTheme = new DriverColorTheme();
		if(flag.equalsIgnoreCase(CommonMessage.Ok))
		{
			driverColorTheme = driverColorThemeRep.findDriverColorTheme(driver_id);
		}
		else {
			DriverColorTheme newDriverColorTheme = new DriverColorTheme();
			Date date = new Date();
			Timestamp dateUpdated = new Timestamp(date.getTime());
			driverColorTheme.setDriverId(driver_id);
			driverColorTheme.setThemeId(getDefaultTheme());
			driverColorTheme.setSendTime(dateUpdated);
			driverColorThemeRep.save(driverColorTheme);
			newDriverColorTheme = driverColorThemeRep.findDriverColorTheme(driver_id);		
			return newDriverColorTheme;
		}		
		return driverColorTheme;
	}
	public List<Theme> findAllTheme(){
		Sort sortbyid = new Sort(Sort.Direction.DESC,"id");
		List <Theme> lstTheme = themeRep.findAll(sortbyid);		
		return lstTheme;
	}
	
	public DriverColorTheme addDriverTheme(DriverColorTheme driverTheme) {
		String flag = CommonMessage.False;
		DriverColorTheme driverThemeReceive =  driverTheme;
		try {
			Date date = new Date();
			String driverId = driverTheme.getDriverId();
			flag = checkDriverIdExist(driverId);
			Boolean checkThemeExist = checkThemeExist(driverTheme.getThemeId());
			Timestamp dateUpdated = new Timestamp(date.getTime());
			if(!flag.equalsIgnoreCase(CommonMessage.Ok)) 
			{
				DriverColorTheme driverThemeNew = new DriverColorTheme();				
				driverThemeNew.setDriverId(driverTheme.getDriverId());
				driverThemeNew.setThemeId(driverTheme.getThemeId());
				driverThemeNew.setSendTime(dateUpdated);
				driverColorThemeRep.save(driverThemeNew);
				driverThemeReceive.setSendTime(dateUpdated);
				flag = CommonMessage.Ok;
			}
			else 
			{
				DriverColorTheme oldDriverColorTheme = driverColorThemeRep.findDriverColorTheme(driverTheme.getDriverId());
				if(driverTheme.getThemeId()!= oldDriverColorTheme.getThemeId() && checkThemeExist)
				{
					oldDriverColorTheme.setThemeId(driverTheme.getThemeId());
					oldDriverColorTheme.setSendTime(dateUpdated);
					driverColorThemeRep.save(oldDriverColorTheme);
					driverThemeReceive.setSendTime(dateUpdated);
				}				
				else 
				{
					flag = CommonMessage.False;
					return oldDriverColorTheme;
				}
			}
			return driverThemeReceive;			

		} catch (Exception e) {
			// TODO: handle exception
			return driverTheme;
		}
	}
	public String checkDriverIdExist(String driverId) {
			String flag = CommonMessage.False;			
			DriverColorTheme isDriverColorTheme = driverColorThemeRep.findDriverColorTheme(driverId);
			if(isDriverColorTheme != null)
			{	
				if(!isDriverColorTheme.toString().isEmpty())
				{
					flag = "Driver is existing!";
					return flag=CommonMessage.Ok;
				}
			}
			return flag;
	}
	public Boolean checkThemeExist(Integer themeId) 
	{
		Boolean flag = false;		
		Theme isThemeExist = themeRep.findTheme(themeId);
		if(isThemeExist != null)
		{	
			flag = true;
		}
		return flag;
	}
	public Integer getDefaultTheme() 
	{
		Integer themeNumber = 1;
		themeNumber = themeRep.findDefaultTheme().getId();
		return themeNumber;
	}
}
