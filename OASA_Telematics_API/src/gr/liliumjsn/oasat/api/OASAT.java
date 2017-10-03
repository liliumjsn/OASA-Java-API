package gr.liliumjsn.oasat.api;

import java.util.LinkedHashMap;

import org.json.JSONArray;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.MultipartBody;

public class OASAT implements OASAT_API {
	private final static String reqURL = "http://telematics.oasa.gr/api/";

	public void getLinesFromWeb(final DataResponseCallback<LinkedHashMap<String, Line>> mCal) {
		Callback<JsonNode> postReqCal = new Callback<JsonNode>() {
			public void cancelled() {
				mCal.onDataFailed(new UnirestException("Req Cancelled"));
			}

			public void completed(HttpResponse<JsonNode> arg0) {
				JSONArray ar = arg0.getBody().getArray();
				LinkedHashMap<String, Line> lines = new LinkedHashMap<String, Line>();
				for (int i = 0; i < ar.length(); i++) {
					Line myLine = Line.parseLine(ar.getJSONObject(i));
					lines.put(myLine.getLineID(), myLine);
				}
				mCal.onDataReady(lines);
			}

			public void failed(UnirestException e) {
				mCal.onDataFailed(e);
			}

		};
		newPostRequest("webGetLinesWithMLInfo", postReqCal, (String[]) null);
	}

	public void getRoutesForLineFromWeb(Line line, final DataResponseCallback<LinkedHashMap<Integer, Route>> mCal) {
		Callback<JsonNode> postReqCal = new Callback<JsonNode>() {
			public void cancelled() {
				mCal.onDataFailed(new UnirestException("Req Cancelled"));
			}

			public void completed(HttpResponse<JsonNode> arg0) {
				JSONArray ar = arg0.getBody().getArray();
				LinkedHashMap<Integer, Route> routes = new LinkedHashMap<Integer, Route>();
				for (int i = 0; i < ar.length(); i++) {
					Route myRoute = Route.parseRoute(ar.getJSONObject(i));
					routes.put(myRoute.getRouteCode(), myRoute);
				}
				mCal.onDataReady(routes);
			}

			public void failed(UnirestException e) {
				mCal.onDataFailed(e);
			}

		};
		newPostRequest("webGetRoutes", postReqCal, String.valueOf(line.getLineCode()));
	}

	public void getStopsForRouteFromWeb(Route route, final DataResponseCallback<LinkedHashMap<Integer, Stop>> mCal) {
		Callback<JsonNode> postReqCal = new Callback<JsonNode>() {
			public void cancelled() {
				mCal.onDataFailed(new UnirestException("Req Cancelled"));
			}

			public void completed(HttpResponse<JsonNode> arg0) {
				JSONArray ar = arg0.getBody().getArray();
				LinkedHashMap<Integer, Stop> stops = new LinkedHashMap<Integer, Stop>();
				for (int i = 0; i < ar.length(); i++) {
					Stop myStop = Stop.parseStop(ar.getJSONObject(i));
					stops.put(myStop.getStopCode(), myStop);
				}
				mCal.onDataReady(stops);
			}

			public void failed(UnirestException e) {
				mCal.onDataFailed(e);
			}

		};
		newPostRequest("webGetStops", postReqCal, String.valueOf(route.getRouteCode()));
	}

	public void getRoutesForStopFromWeb(Stop stop, final DataResponseCallback<LinkedHashMap<Integer, Route>> mCal) {
		Callback<JsonNode> postReqCal = new Callback<JsonNode>() {
			public void cancelled() {
				mCal.onDataFailed(new UnirestException("Req Cancelled"));
			}

			public void completed(HttpResponse<JsonNode> arg0) {
				JSONArray ar = arg0.getBody().getArray();
				LinkedHashMap<Integer, Route> routes = new LinkedHashMap<Integer, Route>();
				for (int i = 0; i < ar.length(); i++) {
					Route myRoute = Route.parseRoute(ar.getJSONObject(i));
					Line myLine = Line.parseLine(ar.getJSONObject(i));
					myRoute.setRouteLine(myLine);
					routes.put(myRoute.getRouteCode(), myRoute);
				}
				mCal.onDataReady(routes);
			}

			public void failed(UnirestException e) {
				mCal.onDataFailed(e);
			}

		};
		newPostRequest("webRoutesForStop", postReqCal, String.valueOf(stop.getStopCode()));
	}

	public void getArrivalsForStopFromWeb(Stop stop, final DataResponseCallback<LinkedHashMap<Integer, Arrival>> mCal) {
		Callback<JsonNode> postReqCal = new Callback<JsonNode>() {
			public void cancelled() {
				mCal.onDataFailed(new UnirestException("Req Cancelled"));
			}

			public void completed(HttpResponse<JsonNode> arg0) {
				LinkedHashMap<Integer, Arrival> arrivals = new LinkedHashMap<Integer, Arrival>();
				if (arg0.getBody().isArray()) {
					JSONArray ar = arg0.getBody().getArray();
					System.out.println(arg0.getBody());

					for (int i = 0; i < ar.length(); i++) {
						Arrival myArrival = Arrival.parseArrival(ar.getJSONObject(i));
						arrivals.put(myArrival.getTime(), myArrival);
					}
				}

				mCal.onDataReady(arrivals);
			}

			public void failed(UnirestException e) {
				mCal.onDataFailed(e);
			}

		};
		newPostRequest("getStopArrivals", postReqCal, String.valueOf(stop.getStopCode()));
	}

	public void getClosestStopsFromWeb(double lat, double lng, DataResponseCallback<LinkedHashMap<Double, Stop>> mCal) {
		// ParseJSON<LinkedHashMap<Double, Stop>> myClosestStopParser = new
		// ClosestStopParser();
		// ParserCallback<LinkedHashMap<Double, Stop>> mParserCall = new
		// ParserCallback<LinkedHashMap<Double, Stop>>(myClosestStopParser,
		// mCal);
		// newPostRequest("getClosestStops", mParserCall, String.valueOf(lat),
		// String.valueOf(lng));
	}

	public void getBusLocationsFromWeb(Route route) {

	}

	private void newPostRequest(String act, Callback<JsonNode> mcal, String... params) {
		System.out.println("newPostRequest called");
		HttpRequestWithBody postReq = Unirest.post(reqURL).header("accept", "application/json");
		MultipartBody mPB = postReq.field("act", act);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				mPB = mPB.field("p" + (i + 1), params[i]);
				System.out.println(act + params[0]);
			}
		}
		mPB.asJsonAsync(mcal);

	}

}
