package com.assignment1.no_sharing_delayed;

import java.util.HashMap;

import java.util.List;

import com.assignment1.utilities.CalculateAverage;
import com.assignment1.utilities.Station;
// The thread with the station holds the lock.
public class AverageTMAXNoSharingDelayed {
	public static HashMap<String, Station> stations = new HashMap<String, Station>();

	public void noSharingCalculation(List<List<String>> divisions) throws Exception{
		DelayThreadNoSharing thread1 = new DelayThreadNoSharing(divisions.get(0));
		DelayThreadNoSharing thread2 = new DelayThreadNoSharing(divisions.get(1));
		DelayThreadNoSharing thread3 = new DelayThreadNoSharing(divisions.get(2));
		DelayThreadNoSharing thread4 = new DelayThreadNoSharing(divisions.get(3));
		
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
