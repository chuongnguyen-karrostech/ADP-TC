package com.APIMM.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.APIMM.mam.model.MamFiles;
import com.APIMM.mam.nonemodel.NoneMamFiles;
import com.APIMM.service.MamFilesService;
import com.APIMM.util.CommonFunction;
import com.APIMM.util.Mapping;
import com.APIMM.util.message.ActionReturn;
import com.APIMM.util.message.CommonMessage;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/mam")
@CrossOrigin("*")
@Api("mam")
public class MamFilesController {

	private final Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	MamFilesService fileService;

	/// full colulmn in json
	//
	@GetMapping("/fullfiles")
	@ResponseBody
	public ResponseEntity<?> findAll() {
		try {
			List<MamFiles> files = new ArrayList<>();
			files = fileService.findAll();
			NoneMamFiles destFields = new NoneMamFiles();
			Mapping mapping = new Mapping();

			Object lstReturn = mapping.FieldMapping(files, destFields);

			return new ResponseEntity<>(lstReturn, HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handl exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}

	@GetMapping(value = "/files")
	@ResponseBody
	public List<MamFiles> getfiles() {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		// logger.info("[" + timekey + "] get files.");
		List<MamFiles> lstReturn = new ArrayList<MamFiles>();
		lstReturn = fileService.findAll();
		logger.info(CommonFunction.LogReturn(timekey, "getfiles", "", ""));
		// logger.info("[" + timekey + "] get files is completed.");
		return lstReturn;
	}

	@PostMapping("/files/update")
	@ResponseBody
	public ResponseEntity<?> update(@RequestBody NoneMamFiles mamFiles) {
		try {
			Integer Id = mamFiles.getfileId();
			if (!fileService.checkExists(Id)) {
				return new ResponseEntity<>("Unable update. Files not found.", HttpStatus.NOT_FOUND);
			}
			if (Id == null) {
				return new ResponseEntity<>("Unable update. Files Id cannot be null.", HttpStatus.CONFLICT);
			}

			Exception ex = fileService.update(mamFiles);
			ActionReturn ar = ActionReturn.Response(CommonMessage.Ok);
			if (ex == null) {
				return new ResponseEntity<>(ar, HttpStatus.OK);
			} else {
				ar = ActionReturn.Response(CommonMessage.False);
				return new ResponseEntity<>(ar, HttpStatus.CONFLICT);
			}

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}

	@PostMapping("/files/create")
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody NoneMamFiles mamFiles) {
		try {
			Exception ex = fileService.create(mamFiles);
			ActionReturn ar = ActionReturn.Response(CommonMessage.Ok);
			if (ex == null) {
				return new ResponseEntity<>(ar, HttpStatus.CREATED);
			} else {
				ar = ActionReturn.Response(CommonMessage.False);
				return new ResponseEntity<>(ar, HttpStatus.CONFLICT);
			}

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.CONFLICT);
		}
	}
	@PostMapping("/files/check")
	@ResponseBody
	public ResponseEntity<?> checkfile(@RequestBody NoneMamFiles mamFiles) {
		try {
			Boolean bCheck = fileService.checkFile(mamFiles.geturl());
			ActionReturn ar = ActionReturn.Response(CommonMessage.Ok);
			if (bCheck == true) {
				return new ResponseEntity<>(ar, HttpStatus.OK);
			} else {
				ar = ActionReturn.Response(CommonMessage.False);
				return new ResponseEntity<>(ar, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}

}
