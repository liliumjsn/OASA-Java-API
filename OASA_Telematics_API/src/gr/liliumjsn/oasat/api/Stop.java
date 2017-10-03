package gr.liliumjsn.oasat.api;

import java.util.LinkedHashMap;
import org.json.JSONObject;

public class Stop {
	private int stopCode;
	private String stopID;
	private String stopDescr;
	private String stopDescrEng;
	private String stopStreet;
	private String stopStreetEng;
	private int stopHeading;
	private double stopLat;
	private double stopLng;
	private int routeStopOrder;
	private int stopType;
	private boolean stopAmea;
	private LinkedHashMap<Integer, Route>  stopRoutes;
	private LinkedHashMap<Integer, Arrival> stopArrivals;
	private double distance;
	
	public Stop(int stopCode, String stopID, String stopDescr,String stopDescrEng, String stopStreet, 
			String stopStreetEng, int stopHeading, double stopLat, double stopLng){
		this.setStopCode(stopCode);
		this.setStopID(stopID);
		this.setStopDescr(stopDescr);
		this.setStopDescrEng(stopDescrEng);
		this.setStopStreet(stopStreetEng);
		this.setStopStreetEng(stopStreetEng);
		this.setStopHeading(stopHeading);
		this.setStopLat(stopLat);
		this.setStopLng(stopLng);
	}
	
	public Stop(int stopCode, String stopID, String stopDescr,String stopDescrEng, String stopStreet, 
			String stopStreetEng, int stopHeading, double stopLat, double stopLng, 
			double distance){
		this(stopCode, stopID, stopDescr, stopDescrEng, stopStreet, stopStreetEng, stopHeading, stopLat, stopLng);
		this.setStopDistance(distance);
	}
	
	public Stop(int stopCode, String stopID, String stopDescr,String stopDescrEng, String stopStreet, 
			String stopStreetEng, int stopHeading, double stopLat, double stopLng, 
			int routeStopOrder, int stopType, boolean stopAmea){
		this(stopCode, stopID, stopDescr, stopDescrEng, stopStreet, stopStreetEng, stopHeading, stopLat, stopLng);
		this.setRouteStopOrder(routeStopOrder);
		this.setStopAmea(stopAmea);
		this.setStopType(stopType);
	}
	
	public int getStopCode() {return stopCode;}
	public void setStopCode(int stopCode) {this.stopCode = stopCode;}

	public String getStopID() {return stopID;}
	public void setStopID(String stopID) {this.stopID = stopID;}

	public String getStopDescr() {return stopDescr;}
	public void setStopDescr(String stopDescr) {this.stopDescr = stopDescr;}

	public String getStopDescrEng() {return stopDescrEng;}
	public void setStopDescrEng(String stopDescrEng) {this.stopDescrEng = stopDescrEng;}

	public String getStopStreet() {return stopStreet;}
	public void setStopStreet(String stopStreet) {this.stopStreet = stopStreet;}

	public String getStopStreetEng() {return stopStreetEng;}
	public void setStopStreetEng(String stopStreetEng) {this.stopStreetEng = stopStreetEng;}

	public int getStopHeading() {return stopHeading;}
	public void setStopHeading(int stopHeading) {this.stopHeading = stopHeading;}

	public double getStopLat() {return stopLat;}
	public void setStopLat(double stopLat) {this.stopLat = stopLat;}

	public double getStopLng() {return stopLng;}
	public void setStopLng(double stopLng) {this.stopLng = stopLng;}

	public int getRouteStopOrder() {return routeStopOrder;}
	public void setRouteStopOrder(int routeStopOrder) {this.routeStopOrder = routeStopOrder;}

	public int getStopType() {return stopType;}
	public void setStopType(int stopType) {this.stopType = stopType;}

	public boolean isStopAmea() {return stopAmea;}
	public void setStopAmea(boolean stopAmea) {this.stopAmea = stopAmea;}

	public LinkedHashMap<Integer, Route>  getStopRoutes() {return stopRoutes;}
	public void setStopRoutes(LinkedHashMap<Integer, Route>  stopRoutes) {this.stopRoutes = stopRoutes;}

	public double getStopDistance() {return distance;}
	public void setStopDistance(double distance) {this.distance = distance;}
	
	public LinkedHashMap<Integer, Arrival> getStopArrivals() {return this.stopArrivals;}
	public void setStopArrivals(LinkedHashMap<Integer, Arrival> stopArrivals) {this.stopArrivals = stopArrivals;}
	
	public String toString(){
		return getStopDescrEng();
	}
	
	public static Stop parseStop(JSONObject object) {
		String stopCodeStr = String.valueOf(object.get("StopCode"));
		int stopCode = Integer.parseInt(stopCodeStr);
		
		String stopID = String.valueOf(object.get("StopID"));
		
		String stopDescr = String.valueOf(object.get("StopDescr"));
		String stopDescrEng = String.valueOf(object.get("StopDescrEng"));
		String stopStreet = String.valueOf(object.get("StopStreet"));
		String stopStreetEng = String.valueOf(object.get("StopStreetEng"));

		String stopHeadingStr = String.valueOf(object.get("StopHeading"));
		int stopHeading = -1;
		if(stopHeadingStr != "null") stopHeading = Integer.parseInt(stopHeadingStr);
		
		double stopLat = -1.0;
		String stopLatStr = String.valueOf(object.get("StopLat"));
		if(stopLatStr != "null") stopLat = Double.parseDouble(stopLatStr);
		
		double stopLng = -1.0;
		String stopLngStr = String.valueOf(object.get("StopLng"));
		if(stopLngStr != "null") stopLng = Double.parseDouble(stopLngStr);
		
		int routeStopOrder = -1;
		String routeStopOrderStr = String.valueOf(object.get("RouteStopOrder"));
		if(routeStopOrderStr != "null") routeStopOrder = Integer.parseInt(routeStopOrderStr);
		
		int stopType = -1;
		String stopTypeStr = String.valueOf(object.get("StopType"));
		if(stopTypeStr != "null")stopType = Integer.parseInt(stopTypeStr);
		
		String stopAmeaStr = String.valueOf(object.get("StopAmea"));
		boolean stopAmea = false;
		if (stopAmeaStr.equals("1")) stopAmea = true;

		return new Stop(stopCode, stopID, stopDescr, stopDescrEng, stopStreet, 
				stopStreetEng, stopHeading, stopLat, stopLng, routeStopOrder, stopType, stopAmea);
	}
}
