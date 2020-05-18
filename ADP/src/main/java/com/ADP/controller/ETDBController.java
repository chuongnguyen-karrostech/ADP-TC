package com.ADP.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ADP.etdb.model.MamSyncInfo;
import com.ADP.etdb.model.RunInfo;
import com.ADP.service.ETDBService;
import com.ADP.util.DateFunction;
import com.ADP.util.Utilities;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value="/etdb")
@CrossOrigin(origins = "*")
@Api(value="etdb", description="etdb")
public class ETDBController {
	private final Log logger = LogFactory.getLog(this.getClass());	
	@Autowired
	ETDBService etdbService;
	
	@GetMapping(value="/loadrouteview" )
	@ResponseBody	
	public Object[] loadrouteview(){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get files", "", ""));
		try
		{
			Object[] r = etdbService.loadRouteView();			
			logger.info(Utilities.LogReturn(timekey, "load route view", "", ""));
			return r;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "load route view", "", "", ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	@GetMapping(value="/loadstopview" )
	@ResponseBody	
	public Object[] loadstopview(){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get files", "", ""));
		try
		{
			Object[] r = etdbService.loadStopView();			
			logger.info(Utilities.LogReturn(timekey, "load stop view", "", ""));
			return r;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "load stop view", "", "", ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	@GetMapping(value="/loadrouteview/{run:.+}" )
	@ResponseBody	
	public Object[] loadrouteview(@PathVariable String run){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get files", "", ""));
		try
		{
			Object[] r = etdbService.loadRouteView(run);			
			logger.info(Utilities.LogReturn(timekey, "load route view ", "", run));
			return r;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "load route view ", "", run, ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	@GetMapping(value="/loadstopview/{run:.+}" )
	@ResponseBody	
	public Object[] loadstopview(@PathVariable String run){
		long timekey = DateFunction.TimeKey();
		//logger.info(Utilities.LogReceive(timekey, "get files", "", ""));
		try
		{
			Object[] r = etdbService.loadStopView(run);			
			logger.info(Utilities.LogReturn(timekey, "load stop view", "", run));
			return r;
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "load stop view", "", run, ex.toString()));
			throw new IllegalArgumentException(ex.toString());
		}		
	}
	@RequestMapping(value="/downloadrun", method=RequestMethod.POST)
	public String getDownload(@RequestBody RunInfo runinfo, HttpServletResponse response) throws IOException {
		long timekey = DateFunction.TimeKey();	
		//logger.info(Utilities.LogReceive(timekey, "Get download", "sync data ID", id ));
		try
		{
			String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/download/runbyid/"+ runinfo.getRunId();
			if(runinfo.getDate()!=null)				
			{ 
				//String string_date = DateFunction.DateTimeToString(runinfo.getDate(),"yyyy-MM-dd");
				transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/download/runbyid/"+runinfo.getRunId()+"/"+ runinfo.getDate();
			}
			UriComponentsBuilder builder = UriComponentsBuilder
			    .fromUriString(transactionUrl); 
			RestTemplate restTemplate = new RestTemplate();
			RunInfo[] infos = restTemplate.getForObject(builder.toUriString(), RunInfo[].class);
			if (infos!=null){
				if (infos.length>0)
				{
					RunInfo info = infos[0];
				// Get your file stream from wherever.
				InputStream initialStream = new FileInputStream(new File(info.getUrl()));
				// Set the content type and attachment header.
				response.addHeader("Content-disposition", "attachment;filename="+ info.getRunId()+"."+info.getFileType());
				response.setContentType("txt/plain");
		
				// Copy the stream to the response's output stream.
				IOUtils.copy(initialStream, response.getOutputStream());
				response.flushBuffer();
				logger.info(Utilities.LogReturn(timekey, "Get download", "sync data ID", runinfo.getRunId()));
				return "success";
				}else {
					logger.info(Utilities.LogError(timekey, "Get download", "sync data run ID", runinfo.getRunId(), "The sync data run ID "+ runinfo.getRunId() +" does not changed."));
					  return "The sync data run ID "+ runinfo.getRunId() +" does not changed.";
				}
			}
			else
			{
				logger.info(Utilities.LogError(timekey, "Get download", "sync data run ID", runinfo.getRunId(), "The sync data run ID "+ runinfo.getRunId() +" does not exist."));
				throw new IllegalArgumentException("The sync data run ID "+ runinfo.getRunId() +" does not exist.");
				
			}
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "Get download", "sync data run ID", runinfo.getRunId(), ex.toString()));			
			throw new IllegalArgumentException(ex.toString());
		}
	}
}
