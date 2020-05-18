package com.APIMM.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.APIMM.mam.model.AthenaContainer;
import com.APIMM.mam.repository.AthenaContainerRepositoy;
import com.APIMM.util.message.CommonMessage;

@Service
public class AthenaContainerService {
@Autowired
AthenaContainerRepositoy AthenaRep;

	public List<AthenaContainer> findAllContainer(){
		return AthenaRep.findAll();
	}

	public List<AthenaContainer> findContainer(String containerType){
		return AthenaRep.findAllContainerByType(containerType);
	}

	public String addContainer(String container, String containerType) {
		String flag = CommonMessage.False;		
		try {		
			if(!flag.equalsIgnoreCase(CommonMessage.Ok) && !checkIsExisted(container)) 
			{
				Date date = new Date();
				Timestamp dateUpdated = new Timestamp(date.getTime());
				AthenaContainer athContainer = new AthenaContainer();
				athContainer.setContainer(container);
				athContainer.setContainerType(containerType);
				athContainer.setReceiveTime(dateUpdated);
				AthenaRep.save(athContainer);
				flag = CommonMessage.Ok;
			}
			return flag;			
	
		} catch (Exception e) {
			// TODO: handle exception
			return e.toString();
		}
	}

	public String deleteContainer(Integer athContainerId) {
		String flag = CommonMessage.False;
		try {						
			AthenaRep.delete(athContainerId);						
			flag = "Delete Container by ContainerId = "+ athContainerId +" is Ok";					
		} catch (Exception e) {
			flag = e.getMessage();// CommonMessage.False;
			throw new IllegalArgumentException(flag);
		}
		return flag;
	}

	private Boolean checkIsExisted(String container) {		
		boolean flag = false;
		List<AthenaContainer> lstAthenaContainer = AthenaRep.findAllContainerExisted(container);
		if(lstAthenaContainer.isEmpty())
		{
			flag = false;
		}
		else
		{
			flag = true;
		}
		return flag;
	}
}
