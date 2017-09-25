package com.assignment1.coarse_lock;

import java.util.List;

public class ThreadCoarseLock implements Runnable {
	private final List<String> records;
	ThreadCoarseLock(List<String> records){
		this.records = records;
	}
	// run method looks for Tmax and updates the list.

	@Override
	public void run() {
		for(String anchor: records){
	//updates the record
			String []stationData = anchor.split(",");
			AverageTMAXCoarseLock.updateRecords(stationData);
		}
		
	}
	
}
