package com.APIMM.util.message;

public class ActionReturn {
	public String  result = "";
	public Object  object = "";
	public static String EDP_Url = "";
	public ActionReturn(String result) {
		super();
		this.result = result;
	}
	
	public ActionReturn(String result, Object obj) {
		super();
		this.result = result;
		this.object = obj;
		
	}	
	
	public static ActionReturn Response(String result)
	{
		return new ActionReturn(result);
	}
	
	public static ActionReturn Response(String result, Object obj)
	{
		return new ActionReturn(result, obj);
	}
}
