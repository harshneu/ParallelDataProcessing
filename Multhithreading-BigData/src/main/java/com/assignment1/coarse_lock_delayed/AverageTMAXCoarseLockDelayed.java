package com.assignment1.coarse_lock_delayed;

import com.assignment1.utilities.Fibonacci;
import com.assignment1.utilities.Station;

// Checks for stationid
// on encountring a station runs a delayed update to the tempratures
// delayed because of running fibbonacci(17) first.
public class AverageTMAXCoarseLockDelayed {
	public synchronized static void updateList(String[] temp) {
		// checks if the key is present
		if (ThreadsStartclnodelay.stations.containsKey(temp[0])) {
			// delayed run
			Fibonacci.fib(17);
			Station obj = ThreadsStartclnodelay.stations.get(temp[0]);
			// checking for all the records
			obj.setListRecords(obj.getListRecords() + 1);
			obj.setTmax(obj.getTmax() + Double.parseDouble(temp[3]));
			// .put i sused to update the list.
			ThreadsStartclnodelay.stations.put(temp[0], obj);
		} else {
			// else condition runs if the satationkey is not matched.
			Station obj = new Station(temp[0], Double.parseDouble(temp[3]), (double) 1, 0.0);
			ThreadsStartclnodelay.stations.put(temp[0], obj);
		}

	}
}
