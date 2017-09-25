package com.assignment1.utilities;

import java.util.HashMap;
import java.util.Map;
public class CalculateAverage {
	//sets the average temprature.
public static void averageTemprature(HashMap<String,Station> stations){
	for(Map.Entry<String,Station> obj:stations.entrySet()){
		Station temp = obj.getValue();
		temp.setAverageTMAX(temp.getTmax()/temp.getListRecords());
	}
}
}