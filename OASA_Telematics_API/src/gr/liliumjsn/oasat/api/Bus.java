package gr.liliumjsn.oasat.api;
import java.util.Date;
import java.util.LinkedHashMap;

import org.json.JSONArray;

public class Bus {

	private int busNumber;
	private Date busDate;
	private double busLat;
	private double busLng;
	private int routeCode;
	
	public Bus(){}
	
	public Bus(int busNumber, Date busDate, double busLat,  double busLng,  int routeCode){
		this.setBusNumber(busNumber);
		this.setBusDate(busDate);
		this.setBusLat(busLat);
		this.setBusLng(busLng);
		this.setRouteCode(routeCode);
	}

	public int getBusNumber() {return busNumber;}
	public void setBusNumber(int busNumber) {this.busNumber = busNumber;}

	public Date getBusDate() {return busDate;}
	public void setBusDate(Date busDate) {this.busDate = busDate;}

	public double getBusLat() {return busLat;}
	public void setBusLat(double busLat) {this.busLat = busLat;}

	public double getBusLng() {return busLng;}
	public void setBusLng(double busLng) {this.busLng = busLng;}

	public int getRouteCode() {return routeCode;}
	public void setRouteCode(int routeCode) {this.routeCode = routeCode;}

	public LinkedHashMap<Integer, Arrival> parseJSON(JSONArray mArray) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
