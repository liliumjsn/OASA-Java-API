package gr.liliumjsn.oasat.api;

import org.json.JSONObject;

public class Arrival {
	private int routeCode;
	private int vehCode;
	private int time;
	
	public Arrival(int routeCode, int vehCode, int time){
		this.setRouteCode(routeCode);
		this.setVehCode(vehCode);
		this.setTime(time);
	}

	public int getRouteCode() {return routeCode;}
	public void setRouteCode(int routeCode) {this.routeCode = routeCode;}

	public int getVehCode() {return vehCode;}
	public void setVehCode(int vehCode) {this.vehCode = vehCode;}

	public int getTime() {return time;}
	public void setTime(int time) {this.time = time;}
	
	public static Arrival parseArrival(JSONObject object) {
		
		String routeCodeStr = String.valueOf(object.get("route_code"));
		int routeCode = -1;
		if(routeCodeStr != "null") routeCode = Integer.parseInt(routeCodeStr);
		
		String vehCodeStr = String.valueOf(object.get("veh_code"));
		int vehCode = -1;
		if(vehCodeStr != "null") vehCode = Integer.parseInt(vehCodeStr);
		
		String timeStr = String.valueOf(object.get("btime2"));
		int time = -1;
		if(timeStr != "null") time = Integer.parseInt(timeStr);

		return new Arrival(routeCode, vehCode, time);
	}
	
}