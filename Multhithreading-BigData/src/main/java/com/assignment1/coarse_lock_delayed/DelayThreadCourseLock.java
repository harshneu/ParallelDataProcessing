package com.assignment1.coarse_lock_delayed;

import java.util.List;

//reads the records and passes them up to the maps.
public class DelayThreadCourseLock implements Runnable {
	// list of records.
	private final List<String> records;

	DelayThreadCourseLock(List<String> records) {
		this.records = records;
	}

	// run method looks for Tmax and updates the list.
	@Override
	public void run() {
		for (String a : records) {
			String[] temprature = a.split(",");
			// checks if for the entry in the list the data is matched.
			if (temprature[2].equals("TMAX"))
				AverageTMAXCoarseLockDelayed.updateList(temprature);
		}

	}

}
