package com.assignment1.coarse_lock_delayed;

import java.util.HashMap;
import java.util.List;

import com.assignment1.utilities.CalculateAverage;
import com.assignment1.utilities.Station;

public class ThreadsStartclnodelay {
	public static HashMap<String, Station> stations = new HashMap<String, Station>();;

	// The given threads are running simultaneously
	public void coarseLockCalculation(List<List<String>> divisions) throws Exception {
		Thread thread1 = new Thread(new DelayThreadCourseLock(divisions.get(0)));
		Thread thread2 = new Thread(new DelayThreadCourseLock(divisions.get(1)));
		Thread thread3 = new Thread(new DelayThreadCourseLock(divisions.get(2)));
		Thread thread4 = new Thread(new DelayThreadCourseLock(divisions.get(3)));
		// start the threads to run
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();

		// threads wait for other to end so that they can acccess each others
		// result hence join.
		thread1.join();
		thread2.join();
		thread3.join();
		thread4.join();

		// Runs the average temprature calculator for the received station data.
		CalculateAverage.averageTemprature(stations);

	}
}
