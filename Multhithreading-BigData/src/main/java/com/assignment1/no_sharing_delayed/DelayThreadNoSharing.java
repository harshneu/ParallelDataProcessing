package com.assignment1.no_sharing_delayed;

import java.util.HashMap;
import java.util.List;

import com.assignment1.utilities.Fibonacci;
import com.assignment1.utilities.Station;

public class DelayThreadNoSharing extends Thread {

	private HashMap<String,Station> stations; 
	//List of records.
	private final List<String> records;
	
	DelayThreadNoSharing(List<String> records){
		this.records = records;
		stations = new HashMap<String,Station>();
	}
	//Datastructure storing the mao data.
	public HashMap<String,Station> getMap(){
		return stations;
	}
		
	//run method checks for the data if it is present at the given index and creates 
	//or updates depending on the condition
	@Override
	public void run() {
		for(String anchor: records){
			String []stationData = anchor.split(",");
			if(stationData[2].equals("TMAX")){
				if(stations.containsKey(stationData[0])){
					//delayed run
					Fibonacci.fib(17);
					Station obj = stations.get(stationData[0]);
					obj.setListRecords(obj.getListRecords()+1);
					obj.setTmax(obj.getTmax()+Double.parseDouble(stationData[3]));
					stations.put(stationData[0],obj);
				}
				else{
					Station obj = new Station(stationData[0],Double.parseDouble(stationData[3]),(double) 1,0.0);
					stations.put(stationData[0],obj);
				}		
			}
		}
		
	}
	
}
