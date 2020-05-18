package com.ADP.util.message;

public class OffServiceMessage {
	public static String RequireVehicle = "OffService haven't assigned to a vehicle or a assigned vehicle is not found.";
	public static String RequireTo = "To date missed.";
	public static String RequireFrom = "From date missed.";
	//public static String ToDateLessThanCurrentDate= "To date must be greater than current date.";
	//public static String NotUpdateWhenToDateLessThanCurrentDate= "Can not update off service have to date less than current date.";
	public static String FromDateGreaterThanToDate = "From date time must be less than to date.";
	public static String FromDateGreaterThanCurrentDate = "From date must be greater than current date.";
	public static String NotFound = "Off service is not found.";
	public static String Overlap = "One vehicle can not have 2 off service overlap.";
	public static String VehicleCurrentOffService = "Vehicle status is currently Off Service. You can not create Off Service";
	public static String VehicleCurrentSubtitutingForOtherVehicle = "Vehicle currently subtituted for vehicle xxx , so you can not create off service";
}
