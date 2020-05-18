package com.APIMM.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import com.APIMM.mam.model.MamDefaultModule;
import com.APIMM.mam.repository.MamDefaultModuleRepository;

import org.springframework.beans.factory.annotation.Autowired;
public class CommonFunction {
	public static String CalAmp_mode = "";
	public static String EDP_Url = "";
    public static String ClientId = "dda54ae0a6b57658537d01ac47deca8799c965ca";
    public static String ClientKey = "67d4d3add1422877c1f451ac0b9e9e9e55e6e7ab361114eb7c9323c376c904cc";
    public static String tenantId = "client_credentials";
    public static String grantType = "client_credentials";    
    public static String VendorBrand = "TCM";
    public static String scope = "EDP";
    public static int NumEDPRouteAssign = 10;
    
	public static Hashtable hVeh = new Hashtable();
	public static Hashtable hRouteAssign = new Hashtable();
	
	public static String version= "";
	public static String EDPTokenEndpoint= "v1/signin";
	public static String EDPRouteAssignmentEndpoint= "v2/routeAssignments";
	public static String EDPGetVehicles= "v2/tenants/";
	
	@Autowired
	private static MamDefaultModuleRepository  defaulmodule_Server;
	public static boolean isInteger(String str)  
	{  
	  try  
	  {  
	    Integer.parseInt(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	public static String LogReceive (long timekey, String type, String byType, String vari)
    {    	
    	return "[" + timekey + "] Get " + type + " by " + byType + " = [" + vari + "].";
    }
    
    public static String LogReturn (long timekey, String type, String byType, String vari)
    {
    	long curtime = DateFunction.TimeKey();
    	long duration = curtime-timekey;
    	return "[" + duration + " ms] Get " + type + " by " + byType + " = [" + vari + "] is completed.";
    }
    
    public static String LogSave (long timekey, String type, String byType, String vari)
    {
    	long curtime = DateFunction.TimeKey();
    	long duration = curtime-timekey;
    	return "[" + duration + " ms] Add/Update " + type + " by " + byType + " = [" + vari + "] is completed.";
    }
    
    public static String LogError (long timekey, String type, String byType, String vari, String error)
    {
    	long curtime = DateFunction.TimeKey();
    	long duration = curtime-timekey;
    	return "[" + duration + " ms] Get " + type + " by " + byType + " = [" + vari + "] that were returned error: " + error;
    }
}
