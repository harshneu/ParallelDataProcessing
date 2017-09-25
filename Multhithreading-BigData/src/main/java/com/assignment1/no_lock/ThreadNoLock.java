package com.assignment1.no_lock;

import java.util.List;

import com.assignment1.utilities.Station;


public class ThreadNoLock implements Runnable {

	//list of records
	private final List<String> records;
	
	ThreadNoLock(List<String> records){
		this.records = records;
	}

	@Override
	public void run() {
		// checks for records for a particular station updates  the 
		// station data according to the condition.
		try{
		for(String anchor: records){
			String []stationData = anchor.split(",");
			if(stationData[2].equals("TMAX")){
				String stationId = stationData[0];
				String temp = stationData[3];
				if(AverageTMAXNoLock.stations.containsKey(stationId)){
					Station entry = AverageTMAXNoLock.stations.get(stationId);
					entry.setListRecords(entry.getListRecords()+1);
					entry.setTmax(entry.getTmax()+Double.parseDouble(temp));
					AverageTMAXNoLock.stations.put(stationId,entry);
				}
				else{
					Station entry = new Station(stationId,Double.parseDouble(temp),(double) 1,0.0);
					AverageTMAXNoLock.stations.put(stationId,entry);
				}
			}
		}
		}catch(Exception e){}
	}
	
}
