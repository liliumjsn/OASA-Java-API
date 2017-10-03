package gr.liliumjsn.oasat.api;

import java.util.LinkedHashMap;
import org.json.JSONObject;

public class Route {
	private int routeCode;
	private String routeDescr;
	private String routeDescrEng;
	private Line routeLine;
	
	public enum routeType {
		TYPE1, TYPE2
	};

	private routeType rT;
	private double routeDistance;
	private Details[] routeDetails;
	private LinkedHashMap<Integer, Stop> routeStops;

	public class Details {
		public double routed_x;
		public double routed_y;
		public int routed_order;
	}

	public Route() {
	}

	public Route(int routeCode, String routeDescr, String routeDescrEng, routeType rT, double routeDistance) {
		this.setRouteDistance(routeDistance);
		this.setRouteCode(routeCode);
		this.setRouteDescr(routeDescr);
		this.setRouteDescrEng(routeDescrEng);
		this.setRT(rT);
	}

	public Route(int routeCode, String routeDescr, String routeDescrEng, routeType rT, double routeDistance,
			Details[] routeDetails) {
		this(routeCode, routeDescr, routeDescrEng, rT, routeDistance);
		this.setRouteDetails(routeDetails);
	}

	public Route(int routeCode, String routeDescr, String routeDescrEng, routeType rT, double routeDistance,
			LinkedHashMap<Integer, Stop> routeStops) {
		this(routeCode, routeDescr, routeDescrEng, rT, routeDistance);
		this.setRouteStops(routeStops);
	}

	public Route(int routeCode, String routeDescr, String routeDescrEng, routeType rT, double routeDistance,
			Details[] routeDetails, LinkedHashMap<Integer, Stop> routeStops) {
		this(routeCode, routeDescr, routeDescrEng, rT, routeDistance, routeDetails);
		this.setRouteStops(routeStops);
	}

	public Line getRouteLine() {
		return routeLine;
	}

	public void setRouteLine(Line routeLine) {
		this.routeLine = routeLine;
	}

	
	public int getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(int routeCode) {
		this.routeCode = routeCode;
	}

	public String getRouteDescr() {
		return routeDescr;
	}

	public void setRouteDescr(String routeDescr) {
		this.routeDescr = routeDescr;
	}

	public String getRouteDescrEng() {
		return routeDescrEng;
	}

	public void setRouteDescrEng(String routeDescrEng) {
		this.routeDescrEng = routeDescrEng;
	}

	public routeType getRT() {
		return rT;
	}

	public void setRT(routeType rT) {
		this.rT = rT;
	}

	public double getRouteDistance() {
		return routeDistance;
	}

	public void setRouteDistance(double routeDistance) {
		this.routeDistance = routeDistance;
	}

	public Details[] getRouteDetails() {
		return routeDetails;
	}

	public void setRouteDetails(Details[] routeDetails) {
		this.routeDetails = routeDetails;
	}

	public LinkedHashMap<Integer, Stop> getRouteStops() {
		return routeStops;
	}

	public void setRouteStops(LinkedHashMap<Integer, Stop> routeStops) {
		this.routeStops = routeStops;
	}
	
	public String toString(){
		return getRouteDescrEng();
	}
	
	public static Route parseRoute(JSONObject object) {
		String routeCodeStr = String.valueOf(object.get("RouteCode"));
		int routeCode = -1;
		if(routeCodeStr != "null") routeCode = Integer.parseInt(routeCodeStr);
		
		String routeDescr = String.valueOf(object.get("RouteDescr"));
		String routeDescrEng = String.valueOf(object.get("RouteDescrEng"));
		
		String rTStr = String.valueOf(object.get("RouteType"));
		routeType rT;
		if(rTStr.equals("1")){
			rT = routeType.TYPE1;
		}else {
			rT = routeType.TYPE2;
		}
		
		String routeDistanceStr = String.valueOf(object.get("RouteDistance"));
		double  routeDistance = -1.0;
		if(routeDistanceStr != "null")routeDistance= Double.parseDouble((String) object.get("RouteDistance"));
					
		return new Route(routeCode, routeDescr, routeDescrEng, rT, routeDistance);
	}

	
	
}
