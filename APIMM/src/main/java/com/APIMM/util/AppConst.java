package com.APIMM.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;

import com.APIMM.mam.model.MamEvents;
import com.APIMM.mam.repository.MamEventsRepository;

public class AppConst {
	@Autowired
	static MamEventsRepository  MamEventsRepository_Server;
	public static Map<String, String> dictEvent = new HashMap<String, String>();
	public static void initEvent(List<MamEvents> lstEvent){		
		for (int i = 0; i<lstEvent.size();i++){
			MamEvents event = lstEvent.get(i);
			AppConst.dictEvent.put(event.getEventName().trim(),""+event.getEventId());			
		}
	}
	public static Integer getEventId(String eventName){		
		if (dictEvent.containsKey(eventName))
			return Integer.parseInt(dictEvent.get(eventName));		
		else
			return -1;		
	}
}
