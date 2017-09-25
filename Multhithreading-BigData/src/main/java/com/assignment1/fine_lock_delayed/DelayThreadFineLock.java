package com.assignment1.fine_lock_delayed;

import java.util.List;

import com.assignment1.fine_lock.AverageTMAXFineLock;
import com.assignment1.utilities.Station;

public class DelayThreadFineLock implements Runnable {
	// list of records
	private final List<String> records;

	DelayThreadFineLock(List<String> records) {
		this.records = records;
	}

	@Override
	public void run() {
		// checks for all records
		for (String anchor : records) {
			String[] stationData = anchor.split(",");
			if (stationData[2].equals("TMAX")) {

				// checks if a station data is received
				if (AverageTMAXFineLockDelayed.stations.containsKey(stationData[0])) {
					Station obj = AverageTMAXFineLockDelayed.stations.get(stationData[0]);
					obj.setPropertiesWithDelay(obj.getTmax() + Double.parseDouble(stationData[3]),
							obj.getListRecords() + 1);
					AverageTMAXFineLock.stations.put(stationData[0], obj);
				} else {
					// update data for new object
					Station newEntry = new Station(stationData[0], Double.parseDouble(stationData[3]), (double) 1, 0.0);
					synchronized (newEntry) {
						// if the station key does not exist at the first index
						if (!AverageTMAXFineLock.stations.containsKey(stationData[0]))
							AverageTMAXFineLock.stations.put(stationData[0], newEntry);
						else {
							// update data for existing object
							Station oldEntry = AverageTMAXFineLock.stations.get(stationData[0]);
							oldEntry.setPropertiesWithDelay(oldEntry.getTmax() + newEntry.getTmax(),
									oldEntry.getListRecords() + 1);
							AverageTMAXFineLock.stations.put(stationData[0], oldEntry);
						}
					}
				}
			}
		}

	}

}
