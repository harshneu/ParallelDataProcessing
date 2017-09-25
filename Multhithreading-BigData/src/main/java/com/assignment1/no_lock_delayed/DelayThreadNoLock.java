package com.assignment1.no_lock_delayed;

import java.util.List;

import com.assignment1.no_lock.AverageTMAXNoLock;
import com.assignment1.utilities.Fibonacci;
import com.assignment1.utilities.Station;

public class DelayThreadNoLock implements Runnable {
	// list of records
	private final List<String> records;
	
	DelayThreadNoLock(List<String> records){
		this.records = records;
	}
	@Override
	public void run() {
		try{
			// checks for all records
		for(String anchor: records){
			String []temp = anchor.split(",");
			if(temp[2].equals("TMAX")){
				// checks if a station data is received
				if(AverageTMAXNoLockDelayed.stations.containsKey(temp[0])){
					//delayed run
					Fibonacci.fib(17);
					// checks for the presence of the station entry
					Station entry = AverageTMAXNoLockDelayed.stations.get(temp[0]);
					entry.setListRecords(entry.getListRecords()+1);
					entry.setTmax(entry.getTmax()+Double.parseDouble(temp[3]));
					AverageTMAXNoLock.stations.put(temp[0],entry);
				}
				else{
					Station entry = new Station(temp[0],Double.parseDouble(temp[3]),(double) 1,0.0);
					AverageTMAXNoLockDelayed.stations.put(temp[0],entry);
				}		
			}
		}
		}catch(Exception e){}
		
	}
	
}
