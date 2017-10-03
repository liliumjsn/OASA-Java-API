package gr.liliumjsn.oasat.api;

import com.mashape.unirest.http.exceptions.UnirestException;

public interface DataResponseCallback<T>{
	void onDataReady(T response);
	void onDataFailed(UnirestException arg0);
}
