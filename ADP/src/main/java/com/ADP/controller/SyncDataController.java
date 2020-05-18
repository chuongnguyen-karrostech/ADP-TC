package com.ADP.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ADP.etdb.model.MamSyncInfo;
import com.ADP.service.DeviceService;
import com.ADP.util.DateFunction;
import com.ADP.util.Utilities;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/syncdata")
@CrossOrigin(origins = "*")
@Api(value="syncdata", description="syncdata")
public class SyncDataController {
	private final Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	DeviceService deviceService;	
	
	@GetMapping(value="/download/updatedate/{appid}/{devid}/{date_request}")
	@ApiOperation(value = "update date", response = Iterable.class)
	@ResponseBody
	public Object updatedate(@PathVariable Integer appid ,@PathVariable Integer devid,@PathVariable String date_request) {
		long timekey = DateFunction.TimeKey();	
		//logger.info("[" +  timekey + "] update sync date for device ["+devid+"] set date = [" + date_request + "].");
		try
		{
			Object objArr = deviceService.updateDate(appid, devid, date_request);
			logger.info(Utilities.LogSave(timekey, "update sync date", "device", ""+devid+ ", date = [" + date_request + "] "));								
			return objArr;			
		}
		catch (HttpServerErrorException ex)
		{
			String jsonString = ex.getResponseBodyAsString();
			try{
				JSONObject jsonObject = new JSONObject(jsonString);
				String message = jsonObject.getString("message");
				logger.info(Utilities.LogError(timekey, "update sync date", "device", ""+devid+ ", date = [" + date_request + "] ",ex.getMessage()));
				throw new IllegalArgumentException(message);
			}catch (Exception e){
				logger.info(Utilities.LogError(timekey, "update sync date", "device", ""+devid+ ", date = [" + date_request + "] ",ex.getMessage()));			
				throw new IllegalArgumentException(e.toString());
			}
		}		
	}
	@GetMapping(value="/download/info/{date_request}")
	@ApiOperation(value = "Check device", response = Iterable.class)
	@ResponseBody
	public Object[] download(@PathVariable String date_request){
		long timekey = DateFunction.TimeKey();	
		//logger.info(Utilities.LogReceive(timekey, "Download", "date request", date_request ));
		try
		{
			Object[] objArr = deviceService.getInfoSync(date_request);
			logger.info(Utilities.LogReturn(timekey, "Download", "date request", date_request ));		
			return objArr;			
		}
		catch (HttpServerErrorException ex)
		{
			String jsonString = ex.getResponseBodyAsString();
			try{
			   JSONObject jsonObject = new JSONObject(jsonString);
				String message = jsonObject.getString("message");
				logger.info(Utilities.LogError(timekey, "Download", "date request", date_request, message));	
				throw new IllegalArgumentException(message);
			}catch (Exception e){
				logger.info(Utilities.LogError(timekey, "Download", "date request", date_request, e.getMessage()));		
				throw new IllegalArgumentException(e.getMessage());
			}
//			logger.info(Utilities.LogError(timekey, "Download", "date request", date_request, ex.toString()));			
//			throw new IllegalArgumentException(ex.toString());
		}
	}	
	
	@RequestMapping(value="/download/databyid/{id}", method=RequestMethod.GET)
	public void getDownload(@PathVariable String id, HttpServletResponse response) throws IOException {
		long timekey = DateFunction.TimeKey();	
		//logger.info(Utilities.LogReceive(timekey, "Get download", "sync data ID", id ));
		try
		{
			String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/download/databyid/"+id;
			UriComponentsBuilder builder = UriComponentsBuilder
			    .fromUriString(transactionUrl); 
			RestTemplate restTemplate = new RestTemplate();
			MamSyncInfo info = restTemplate.getForObject(builder.toUriString(), MamSyncInfo.class);
			if (info!=null){
				// Get your file stream from wherever.
				InputStream initialStream = new FileInputStream(
					      new File(info.getUrl()));
				// Set the content type and attachment header.
				response.addHeader("Content-disposition", "attachment;filename="+ info.getName()+"."+info.getFileType());
				response.setContentType("txt/plain");
		
				// Copy the stream to the response's output stream.
				IOUtils.copy(initialStream, response.getOutputStream());
				response.flushBuffer();
				logger.info(Utilities.LogReturn(timekey, "Get download", "sync data ID", id));
			}
			else
			{
				logger.info(Utilities.LogError(timekey, "Get download", "sync data ID", id, "The sync data ID "+ id +" does not exist."));
				throw new IllegalArgumentException("The sync data ID "+ id +" does not exist.");
			}
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "Get download", "sync data ID", id, ex.toString()));			
			throw new IllegalArgumentException(ex.toString());
		}
	}
	
	@RequestMapping(value="/download/databydate/{date_request}", method=RequestMethod.GET)
	public void getDownloadByDate(@PathVariable String date_request, HttpServletResponse response) throws IOException {
		long timekey = DateFunction.TimeKey();	
		//logger.info(Utilities.LogReceive(timekey, "Get download", "date", date_request ));
		try
		{
			String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/download/databydate/"+date_request;
			UriComponentsBuilder builder = UriComponentsBuilder
			    .fromUriString(transactionUrl); 
			RestTemplate restTemplate = new RestTemplate();
			MamSyncInfo info = restTemplate.getForObject(builder.toUriString(), MamSyncInfo.class);
			if (info!=null){
				// Get your file stream from wherever.
				InputStream initialStream = new FileInputStream(
					      new File(info.getUrl()));
				// Set the content type and attachment header.
				response.addHeader("Content-disposition", "attachment;filename="+ info.getName()+"."+info.getFileType());
				response.setContentType("txt/plain");
		
				// Copy the stream to the response's output stream.
				IOUtils.copy(initialStream, response.getOutputStream());
				response.flushBuffer();
				logger.info(Utilities.LogReturn(timekey, "Get download", "date", date_request));
			}
			else
			{
				logger.info(Utilities.LogError(timekey, "Get download", "date", date_request, "The sync data of "+ date_request +" does not exist."));
				throw new IllegalArgumentException("The sync data of "+ date_request +" does not exist.");
			}
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "Get download", "date", date_request, ex.toString()));			
			throw new IllegalArgumentException(ex.toString());
		}
	}
	@RequestMapping(value="/downloadresume/databydate/{date_request}", method=RequestMethod.GET)
	public void getDownloadByDateResume(@PathVariable String date_request, HttpServletResponse response,HttpServletRequest request ) throws IOException {
		long timekey = DateFunction.TimeKey();	
		long start = 0;
		//logger.info(Utilities.LogReceive(timekey, "Get download", "date", date_request ));
		try
		{
			String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/download/databydate/"+date_request;
			UriComponentsBuilder builder = UriComponentsBuilder
			    .fromUriString(transactionUrl); 
			RestTemplate restTemplate = new RestTemplate();
			MamSyncInfo info = restTemplate.getForObject(builder.toUriString(), MamSyncInfo.class);
			
			if (info!=null){
				InputStream fis =new FileInputStream(info.getUrl());
				long length = (int) new File(info.getUrl()).length();
                
                response.setHeader("Accept-Ranges", "bytes");
                response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);// 206
                if (request.getHeader("Range") != null) 
                {
                    int x = request.getHeader("Range").indexOf("-");
                    start = Long.parseLong(request.getHeader("Range").substring(0, x)
                            .replaceAll("bytes=", ""));
                }
                response.setHeader("Content-Length", new Long(length - start).toString());

                if(start == 0)
                    response.setHeader("Content-Range", "bytes 0-"        +new Long(length - 1).toString()+"/"+length);
                else
                    response.setHeader("Content-Range", "bytes "+start+"-"+new Long(length - 1).toString()+"/"+length);
                fis.skip(start);
                byte[] b = new byte[1024];
                int i;
                IOUtils.copy(fis, response.getOutputStream());
                while ((i = fis.read(b)) != -1) {
                    response.getOutputStream().write(b, 0, i);
                    response.flushBuffer();
                }
                fis.close();
//				// Get your file stream from wherever.
//				InputStream initialStream = new FileInputStream(
//					      new File(info.getUrl()));
//				// Set the content type and attachment header.
//				response.addHeader("Content-disposition", "attachment;filename="+ info.getName()+"."+info.getFileType());
//				response.setContentType("txt/plain");
//		
//				// Copy the stream to the response's output stream.
//				IOUtils.copy(initialStream, response.getOutputStream());
//				response.flushBuffer();
				logger.info(Utilities.LogReturn(timekey, "Get download resume from range bytes " + start , "date", date_request));
			}
			else
			{
				logger.info(Utilities.LogError(timekey, "Get download resume from range bytes " + start, "date", date_request, "The sync data of "+ date_request +" does not exist."));
				throw new IllegalArgumentException("The sync data of "+ date_request +" does not exist.");
			}
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey,"Get download resume from range bytes " + start, "date", date_request, ex.toString()));			
			throw new IllegalArgumentException(ex.toString());
		}
	}
	@RequestMapping(value="/downloadresume/databyid/{id}", method=RequestMethod.GET)
	public void getDownloadByIdResume(@PathVariable String id, HttpServletResponse response,HttpServletRequest request ) throws IOException {
		long timekey = DateFunction.TimeKey();	
		long start = 0;
		//logger.info(Utilities.LogReceive(timekey, "Get download", "date", date_request ));
		try
		{
			String transactionUrl = com.ADP.util.Utilities.MMAPI_Url+"mam/download/databyid/"+id;
			UriComponentsBuilder builder = UriComponentsBuilder
			    .fromUriString(transactionUrl); 
			RestTemplate restTemplate = new RestTemplate();
			MamSyncInfo info = restTemplate.getForObject(builder.toUriString(), MamSyncInfo.class);
			
			if (info!=null){
				InputStream fis =new FileInputStream(info.getUrl());
				long length = (int) new File(info.getUrl()).length();
                
                response.setHeader("Accept-Ranges", "bytes");
                response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);// 206
                if (request.getHeader("Range") != null) 
                {
                    int x = request.getHeader("Range").indexOf("-");
                    start = Long.parseLong(request.getHeader("Range").substring(0, x)
                            .replaceAll("bytes=", ""));
                }
                response.setHeader("Content-Length", new Long(length - start).toString());

                if(start == 0)
                    response.setHeader("Content-Range", "bytes 0-"        +new Long(length - 1).toString()+"/"+length);
                else
                    response.setHeader("Content-Range", "bytes "+start+"-"+new Long(length - 1).toString()+"/"+length);
                fis.skip(start);
                byte[] b = new byte[1024];
                int i;
                IOUtils.copy(fis, response.getOutputStream());
                while ((i = fis.read(b)) != -1) {
                    response.getOutputStream().write(b, 0, i);
					response.flushBuffer();
                }
                fis.close();
                logger.info(Utilities.LogReturn(timekey, "Get download resume", "sync data ID", id));
			}
			else
			{
				logger.info(Utilities.LogError(timekey, "Get download resume", "sync data ID", id, "The sync data ID "+ id +" does not exist."));
				throw new IllegalArgumentException("The sync data ID "+ id +" does not exist.");
			}
		}
		catch (Exception ex)
		{
			logger.info(Utilities.LogError(timekey, "Get download resume", "sync data ID", id, ex.toString()));			
			throw new IllegalArgumentException(ex.toString());
		}
	}
}
