package com.assignment1.no_sharing;

import java.util.HashMap;
import java.util.List;

import com.assignment1.utilities.Station;

public class ThreadNoSharing extends Thread {
	//l.ist of stations
	private HashMap<String,Station> stations; 
	
	// list of records
	private final List<String> records;
	
	ThreadNoSharing(List<String> records){
		this.records = records;
		stations = new HashMap<String,Station>();
	}
	
	public HashMap<String,Station> getMap(){
		return stations;
	}
	@Override
	public void run() {
		// checks for records for a particular station updates  the 
		// station data according to the condition.
		
		for(String anchor: records){
			String []stationData = anchor.split(",");
			if(stationData[2].equals("TMAX")){
				if(stations.containsKey(stationData[0])){
					Station entry = stations.get(stationData[0]);
					entry.setListRecords(entry.getListRecords()+1);
					entry.setTmax(entry.getTmax()+Double.parseDouble(stationData[3]));
					stations.put(stationData[0],entry);
				}
				else{
					Station entry = new Station(stationData[0],Double.parseDouble(stationData[3]),(double) 1,0.0);
					stations.put(stationData[0],entry);
				}		
			}
		}
		
		
		
	}
	
}
