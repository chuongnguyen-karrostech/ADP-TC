package com.ADP.util.message;

public class AssignmentMessage {
	public static String RequireRoute = "Missed Route";
	public static String RequireVehicleSub = "Missed Vehicle Sub";
	public static String RequireVehiclePlan = "Missed Vehicle Plan";
	public static String RequireVehicleSwicth = "Not found vehicle exchange";
	public static String RequireVehicleSwicthActive = "Exchanging only happen on 2 vehicles with Active status.";
	public static String NotSubForRouteNoVehiclePlan = "You can not make subtitution for route no plan vehicle";
	public static String NotFoundAssignment = "Assignment for route is not found.";
	public static String VehicleCurrentSubForRoute = "Vehicle current subtituted for route.";
	public static String VehicleCurrentPlanForRoute = "Vehicle current planned for route.";
	public static String VehicleCurrentPlanForAnotherRoute = "Vehicle current planned for xxx route.";
	public static String StatusVehicleCurrentPlanForRouteMustBeActive = "Currently, vehicle planned for route is off service, so you can not change.";
	public static String CannotSwitchSameAssigment = "Can not switch plan vehile same assigment.";
	public static String RequireVehiclePlanActive = "Plan vehicle staus is not Active so you can not remove it.";
	public static String VehiclePlanOffServiceCannotAndSub = "Currently, vehicle planned for route is off service. You can not end sub";
	public static String VehiclePlanNotEqualVehicleSub = "Vehicle Sub can not equal Vehicle Plan";
	
}
