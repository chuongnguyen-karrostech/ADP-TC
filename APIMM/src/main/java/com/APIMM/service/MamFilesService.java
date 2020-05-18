package com.APIMM.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APIMM.mam.model.MamFiles;
import com.APIMM.mam.nonemodel.NoneMamFiles;
import com.APIMM.mam.repository.MamFilesRepository;

@Service
public class MamFilesService {

	@Autowired
	MamFilesRepository mamFilesRepo;

	public List<MamFiles> findAll() {
		return mamFilesRepo.findAll();
	}

	public Exception update(NoneMamFiles noneMamFiles) {
		try {
			Date date = new Date();
			Timestamp dateUpdated = new Timestamp(date.getTime());

			MamFiles mamFiles = mamFilesRepo.findOne(noneMamFiles.getfileId());
			if (mamFiles != null) {
				mamFiles.setfileUpdate(dateUpdated);
				mamFiles.seturl(noneMamFiles.geturl());
				mamFilesRepo.save(mamFiles);
			}
			return null;

		} catch (Exception e) {
			// TODO: handle exception
			return e;
		}
	}

	public Exception create(NoneMamFiles noneMamFiles) {
		try {
			Date date = new Date();
			Timestamp dateUpdated = new Timestamp(date.getTime());

			MamFiles mamFiles = new MamFiles();
			mamFiles.setfileUpdate(dateUpdated);
			mamFiles.seturl(noneMamFiles.geturl());
			mamFiles.setfileType(noneMamFiles.getfileType());
			mamFiles.setversion(noneMamFiles.getversion());
			mamFilesRepo.save(mamFiles);

			return null;

		} catch (Exception e) {
			// TODO: handle exception
			return e;
		}
	}

	public Boolean checkExists(Integer Id) {
		return mamFilesRepo.exists(Id);
	}

	public boolean checkFile(String url)
	{		
		try {
		      HttpURLConnection.setFollowRedirects(false);
		      // note : you may also need
		      //        HttpURLConnection.setInstanceFollowRedirects(false)
		      HttpURLConnection con =
		         (HttpURLConnection) new URL(url).openConnection();
		      con.setRequestMethod("HEAD");
		      return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		    }
		    catch (Exception e) {
		       e.printStackTrace();
		       return false;
		    }	
	}
}
