package com.APIMM.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.APIMM.etdb.model.etdbRunInfo;
import com.APIMM.mam.model.MamDevSyncInfo;
import com.APIMM.mam.model.MamSyncInfo;
import com.APIMM.service.EtdbService;
import com.APIMM.service.MamSyncInfoService;
import com.APIMM.util.CommonFunction;

@RequestMapping("/mam")
@Controller
public class DownloadController {
	private final Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	MamSyncInfoService mamSynsInfo;
	@Autowired
	EtdbService etdbInfoService;
	
	@RequestMapping(path = "/download/info/{date_request}", method = RequestMethod.GET)
	@ResponseBody
	public List<MamSyncInfo> downloadinfo(@PathVariable String date_request) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy"); 
		df.setLenient(false);	
		Date date = new Date();
		//logger.info(CommonFunction.LogReceive(timekey, "Get download", "date", date_request ));
		List<MamSyncInfo> lstResult = new ArrayList<MamSyncInfo>();
		try {
		    date = df.parse(date_request);		    
		} catch (ParseException e) {
			logger.info(CommonFunction.LogError(timekey, "Get download", "date", date_request,e.getMessage()));
			//logger.info("[" + timekey + "] Get download info by date [" + date_request + "] is fail, return error "+e.getMessage());
			throw new IllegalArgumentException("Date parameter invalid!!!");
		}
		
		lstResult = mamSynsInfo.getSyncInfoByDate(date);
		if (lstResult.size()>com.APIMM.configurations.APIMMConfig.maxdownload) {
			logger.info(CommonFunction.LogError(timekey, "Get download", "date", date_request,"local database too old"));
			//logger.info("[" + timekey + "] Get download info by date [" + date_request + "] is fail, return error local database too old.");
			throw new IllegalArgumentException("Date of database too old. Please contact to admin!!!");
		}else 
		{
			logger.info(CommonFunction.LogReturn(timekey, "Get download", "date", date_request));
			//logger.info("[" + timekey + "] Get download info by date [" + date_request + "] is successfully.");
			return lstResult;
		}
		
	}
	
	@RequestMapping(path = "/download/databyid/{id}", method = RequestMethod.GET)
	@ResponseBody
	public MamSyncInfo downloadById(@PathVariable Integer id) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		//logger.info(CommonFunction.LogReceive(timekey, "Get download file", "id", ""+id ));		
		MamSyncInfo lstResult = mamSynsInfo.findSynsInfoByID(id);		
		logger.info(CommonFunction.LogReturn(timekey, "Get download file", "id", ""+id));		
		return lstResult;
		
	}
	@RequestMapping(path = "/download/databydate/{date_request}", method = RequestMethod.GET)
	@ResponseBody
	public MamSyncInfo downloadByDate(@PathVariable String date_request) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		df.setLenient(false);		
		Date date = new Date();
		//logger.info(CommonFunction.LogReceive(timekey, "Get download file", "date", date_request ));	
		
		MamSyncInfo lstResult = new MamSyncInfo();
		try {
		    date = df.parse(date_request);		    		    
		    lstResult = mamSynsInfo.findSynsInfoByDate(date);
		    logger.info(CommonFunction.LogReturn(timekey, "Get download file", "date", date_request));	
		    
		    return lstResult;
		} catch (ParseException e) {		     
			logger.info(CommonFunction.LogError(timekey, "Get download file", "date", date_request,"Date parameter invalid!!!"));
		    throw new IllegalArgumentException("Date parameter invalid!!!"); 
		}
			
	}
	
	@RequestMapping(path = "/download/updatedate/{appid}/{devid}/{date_request}", method = RequestMethod.GET)
	@ResponseBody
	public MamDevSyncInfo downloadByDate(@PathVariable Integer appid ,@PathVariable Integer devid,@PathVariable String date_request) {
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		df.setLenient(false);		
		Date date = new Date();
		//logger.info(CommonFunction.LogReceive(timekey, "update sync date", "device", ""+devid+ ", date = [" + date_request + "] " ));
		MamDevSyncInfo lstResult = new MamDevSyncInfo();
		try {
		    date = df.parse(date_request);		    		    
		    lstResult = mamSynsInfo.updateDevSyncInfoByAppDevice(appid, devid,date);
		    logger.info(CommonFunction.LogSave(timekey, "update sync date", "device", ""+devid+ ", date = [" + date_request + "] " ));		    
		    return lstResult;
		} catch (ParseException e) {		     
			logger.info(CommonFunction.LogError(timekey, "update sync date", "device", ""+devid+ ", date = [" + date_request + "] ","Date parameter invalid!!!" ));
		    throw new IllegalArgumentException("Date parameter invalid!!!"); 
		}			
	}
	@RequestMapping(path = "/download/runbyid/{runid:.+}", method = RequestMethod.GET)
	@ResponseBody
	public List <etdbRunInfo> downloadByRunId(@PathVariable String runid) {
		List <etdbRunInfo> result= new ArrayList<etdbRunInfo>();
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		//logger.info(CommonFunction.LogReceive(timekey, "Get download file", "id", ""+id ));		
		etdbRunInfo lstResult = etdbInfoService.findRunInfobyrunId(runid);
		logger.info(CommonFunction.LogReturn(timekey, "Get download runID : ", "id", ""+ runid));
		if (lstResult !=null) {
			result.add(lstResult);
			return result;
		}else {
			return null;
		}
//		return lstResult;
		
	}
	@RequestMapping(path = "/download/runbyid/{runid:.+}/{date_request}", method = RequestMethod.GET)
	@ResponseBody
	public List <etdbRunInfo> downloadByRunIdandDate(@PathVariable String runid,@PathVariable String date_request) {
		List <etdbRunInfo> result= new ArrayList<etdbRunInfo>();
		long timekey = com.APIMM.util.DateFunction.TimeKey();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		df.setLenient(false);		
		Date date = new Date();
		try {
			date = df.parse(date_request);			
//			etdbRunInfo lstResult = etdbInfoService.findRunInfobyrunIdandDate(runid, date);
			etdbRunInfo lstResult = etdbInfoService.findRunInfobyrunId(runid);
			if (lstResult !=null) {
				if (lstResult.getDate().after(date)) {
					result.add(lstResult);
					logger.info(CommonFunction.LogSave(timekey, "update sync date", "RunID", ""+runid+ ", date = [" + date_request + "] " ));
					return result;
				}else {
					logger.info(CommonFunction.LogSave(timekey, "update sync date", "RunID", ""+runid+ ", date = [" + date_request + "] not existed"));
					return result;
				}
			}else {
				logger.info(CommonFunction.LogSave(timekey, "update sync date", "RunID", ""+runid+ ", date = [" + date_request + "] " ));
				return null;
			}
//			logger.info(CommonFunction.LogSave(timekey, "update sync date", "RunID", ""+runid+ ", date = [" + date_request + "] " ));
//			return lstResult;
		} catch (ParseException e) {		     
			logger.info(CommonFunction.LogError(timekey, "update sync date", "RunID", ""+runid+ ", date = [" + date_request + "] ","Date parameter invalid!!!" ));
		    throw new IllegalArgumentException("Date parameter invalid!!!"); 
		}
	}
}
