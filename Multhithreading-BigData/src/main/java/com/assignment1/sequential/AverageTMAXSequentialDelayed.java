package com.assignment1.sequential;

import java.util.HashMap;
import java.util.List;

import com.assignment1.utilities.CalculateAverage;
import com.assignment1.utilities.Fibonacci;
import com.assignment1.utilities.Station;


public class AverageTMAXSequentialDelayed {
	//data structure to store the stations details

	public static HashMap<String,Station> stations = new HashMap<String,Station>();; 
	
	public void sequentialCalculation(List<String> records){
		//checks for all records
		for(String anchor: records){
			String []temp = anchor.split(",");
			if(temp[2].equals("TMAX")){
				//checks if the station key is matched at index[0]
				if(stations.containsKey(temp[0])){
					Fibonacci.fib(17);
					Station entry = stations.get(temp[0]);
					//record added to the list of records
					entry.setListRecords(entry.getListRecords()+1);
					entry.setTmax(entry.getTmax()+Double.parseDouble(temp[3]));
					//update the stations
					stations.put(temp[0],entry);
				}
				else{
					Station entry = new Station(temp[0],Double.parseDouble(temp[3]),(double) 1,0.0);
					stations.put(temp[0],entry);
				}		
			}
		}	
		//average temprature calculator for station data.
		CalculateAverage.averageTemprature(stations);

	}
}
