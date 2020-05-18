package com.APIMM.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APIMM.mam.model.MdmMode;
import com.APIMM.mam.repository.MdmModeRepository;

@Service
public class MdmModeService {

	@Autowired
	MdmModeRepository modeRepo;

	public List<MdmMode> findAll() {
		return modeRepo.findAll();
	}

}
