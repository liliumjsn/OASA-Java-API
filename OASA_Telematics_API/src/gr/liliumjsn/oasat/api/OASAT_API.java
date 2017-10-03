package gr.liliumjsn.oasat.api;

import java.util.LinkedHashMap;

public interface OASAT_API {

	void getLinesFromWeb(DataResponseCallback<LinkedHashMap<String, Line>> mCal);
	void getRoutesForLineFromWeb(Line line, DataResponseCallback<LinkedHashMap<Integer, Route>> mCal);
	void getStopsForRouteFromWeb(Route route, DataResponseCallback<LinkedHashMap<Integer, Stop>> mCal);
	void getRoutesForStopFromWeb(Stop stop, DataResponseCallback<LinkedHashMap<Integer, Route>> mCal);
	void getArrivalsForStopFromWeb(Stop stop, DataResponseCallback<LinkedHashMap<Integer, Arrival>> mCal);
	void getClosestStopsFromWeb(double lat, double lng, DataResponseCallback<LinkedHashMap<Double, Stop>> mCal);
	void getBusLocationsFromWeb(Route route);
	//Route getLinesAndRoutesForMl();
}
