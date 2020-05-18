package com.APIMM.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APIMM.mam.repository.MamSettingRepository;



@Service
public class MamWebService {
	@Autowired
	MamSettingRepository MAM_Setting_Server;
	

}
