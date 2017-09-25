package com.assignment1.no_lock_delayed;

import java.util.HashMap;
import java.util.List;

import com.assignment1.utilities.CalculateAverage;
import com.assignment1.utilities.Station;

public class AverageTMAXNoLockDelayed {

	public static HashMap<String,Station> stations = new HashMap<String,Station>();
	
	public void noLockCalculation(List<List<String>> divisions) throws Exception{
		Thread thread1 = new Thread(new DelayThreadNoLock(divisions.get(0)));
		Thread thread2 = new Thread(new DelayThreadNoLock(divisions.get(1)));
		Thread thread3 = new Thread(new DelayThreadNoLock(divisions.get(2)));
		Thread thread4 = new Thread(new DelayThreadNoLock(divisions.get(3)));
		// start the thread

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
		
		// runs the average temprature calculator for the observed station data.

		CalculateAverage.averageTemprature(stations);

	}
	
	
}
