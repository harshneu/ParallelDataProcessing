package com.assignment1.coarse_lock;


import com.assignment1.utilities.Station;
public class AverageTMAXCoarseLock {
	// Checks for stationid
	// on encountring a station runs a update to the tempratures
	public synchronized static void updateRecords(String[] stationData){
		if(stationData[2].equals("TMAX")){
			if(ThreadsStart.stations.containsKey(stationData[0])){
				// checking for all the records
				Station entry = ThreadsStart.stations.get(stationData[0]);
				//added the record
				entry.setListRecords(entry.getListRecords()+1);
				entry.setTmax(entry.getTmax()+Double.parseDouble(stationData[3]));
				//updates the record
				ThreadsStart.stations.put(stationData[0],entry);
			}
			else{
				// else condition runs if the satationkey is not matched.
				Station entry = new Station(stationData[0],Double.parseDouble(stationData[3]),(double) 1,0.0);
				ThreadsStart.stations.put(stationData[0],entry);
			}		
		}
	}

}
