package com.assignment1.no_sharing;

import java.util.HashMap;
import java.util.Map;

import com.assignment1.utilities.Station;

// Function takes  station id and Station object, it combines the maps 
//takes into account the properties of same keys(Station id)
public class CombineMap {
	public static void combine(HashMap<String,Station> source, HashMap<String,Station> destination){
		for(Map.Entry<String,Station> obj : source.entrySet()){
			if(destination.containsKey(obj.getKey())){
				String stationId = obj.getKey();
				Station initialTemprature = obj.getValue();
				Station finalTemprature = destination.get(stationId);
				Station temprature = new Station(stationId,initialTemprature.getTmax()+finalTemprature.getTmax(),initialTemprature.getListRecords()+finalTemprature.getListRecords(),0.0);
				destination.put(stationId,temprature);		
			}
			else{
				String stationId = obj.getKey();
				Station initialTemprature = obj.getValue();
				Station temprature = new Station(stationId,initialTemprature.getTmax(),initialTemprature.getListRecords(),0.0);
				destination.put(stationId,temprature);
			}
		}
	}
}
