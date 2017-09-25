package com.assignment1.fine_lock;

import java.util.List;

import com.assignment1.utilities.Station;

public class ThreadFineLock implements Runnable {

	private final List<String> records;
	
	ThreadFineLock(List<String> records){
		this.records = records;
	}
	@Override
	public void run() {
		// checks for records for a particular station updates  the 
		// station data according to the condition.
		for(String anchor: records){
			String []stationData = anchor.split(",");
			if(stationData[2].equals("TMAX")){
				
				if(AverageTMAXFineLock.stations.containsKey(stationData[0])){
					Station entry = AverageTMAXFineLock.stations.get(stationData[0]);
					entry.setProperties(entry.getTmax()+Double.parseDouble(stationData[3]),entry.getListRecords()+1);
					AverageTMAXFineLock.stations.put(stationData[0],entry);
				}
				else{
					Station newEntry = new Station(stationData[0],Double.parseDouble(stationData[3]),(double) 1,0.0);
					synchronized (newEntry) {
						//nested else if for internal condition makes a new entry here.
						if(!AverageTMAXFineLock.stations.containsKey(stationData[0]))
						AverageTMAXFineLock.stations.put(stationData[0],newEntry);	
						
						else{
							//updates the entry.
							Station oldEntry = AverageTMAXFineLock.stations.get(stationData[0]);
							oldEntry.setProperties(oldEntry.getTmax()+newEntry.getTmax(), oldEntry.getListRecords()+1);
							AverageTMAXFineLock.stations.put(stationData[0],oldEntry);
						}
							
					}
				}
			}
		}
		

	}
	
}
