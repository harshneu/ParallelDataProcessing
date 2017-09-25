package com.assignment1.no_sharing;

import java.util.HashMap;
import java.util.List;

import com.assignment1.utilities.CalculateAverage;
import com.assignment1.utilities.Station;

public class AverageTMAXNoSharing {
	
	//list of records.
	public void noSharingCalculation(List<List<String>> divisions) throws Exception{
		HashMap<String,Station> stations = new HashMap<String,Station>();
		ThreadNoSharing thread1 = new ThreadNoSharing(divisions.get(0));
		ThreadNoSharing thread2 = new ThreadNoSharing(divisions.get(1));
		ThreadNoSharing thread3 = new ThreadNoSharing(divisions.get(2));
		ThreadNoSharing thread4 = new ThreadNoSharing(divisions.get(3));
		
		//start the thread
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
		//calls function from combineMap over the threads
		CombineMap.combine(thread1.getMap(),stations);
		CombineMap.combine(thread2.getMap(),stations);
		CombineMap.combine(thread3.getMap(),stations);
		CombineMap.combine(thread4.getMap(),stations);
			
		//Average Temprature Calculator over station data.
		CalculateAverage.averageTemprature(stations);
		
	}	
}
