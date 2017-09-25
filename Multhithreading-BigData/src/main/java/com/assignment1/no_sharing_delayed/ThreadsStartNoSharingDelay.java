package com.assignment1.no_sharing_delayed;

import java.util.HashMap;
import java.util.Map;

import com.assignment1.utilities.Station;

public class ThreadsStartNoSharingDelay {
	//checks for tempratures on source and destination and maps them.
	public static void join(HashMap<String,Station> source, HashMap<String,Station> destination){
		for(Map.Entry<String,Station> obj : source.entrySet()){
			if(destination.containsKey(obj.getKey())){
				//creates or updates the entry according to the condition.
				String stationId = obj.getKey();
				Station initialTemprature = obj.getValue();
				Station finalTemprature = destination.get(stationId);
				Station temp = new Station(stationId,initialTemprature.getTmax()+finalTemprature.getTmax(),initialTemprature.getListRecords()+finalTemprature.getListRecords(),0.0);
				destination.put(stationId,temp);		
			}
			else{
				String stationId = obj.getKey();
				Station initialTemprature = obj.getValue();
				Station temp = new Station
						(stationId,initialTemprature.getTmax(),initialTemprature.getListRecords(),0.0);
				destination.put(stationId,temp);
			}
		}
		
	}

}
