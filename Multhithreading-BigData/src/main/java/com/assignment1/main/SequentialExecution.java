package com.assignment1.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.assignment1.sequential.AverageTMAXSequentialDelayed;
import com.assignment1.sequential_delayed.AverageTMAXSequential;
import com.assignment1.utilities.Station;

public class SequentialExecution {
	//Takes in division and iterationa as the parameter to run the method.
	// Iterations are the number of times the data is run.
	public static void sequential(List<String> records, Integer repeats){
		ArrayList<Long> runTime = new ArrayList<Long>();
		for(int i=0;i<repeats;i++){
				Long progStart = System.currentTimeMillis();
		      	AverageTMAXSequential obj = new AverageTMAXSequential();
		      	AverageTMAXSequential.stations = new HashMap<String,Station>();
		      	try {
					obj.sequentialTmax(records);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        Long progEnd = System.currentTimeMillis();
				runTime.add((progEnd - progStart));
		}	
		Collections.sort(runTime);
		// Outputs the timings and average calculations.
		System.out.println("The averages for Sequential Program");
		System.out.println("The Minimum Run Time is: " +runTime.get(0) + " milliseconds");
		System.out.println("The Average Run Time is: " +Main.average(runTime) + " milliseconds");
		System.out.println("The Maximum Run Time is: " +runTime.get(runTime.size()-1) + " milliseconds");
		System.out.println("End of Sequential");
	}	
	
	public static void sequentialWithDelay(List<String> recordList, Integer iteration){
		// Program execution with delay(fibbonacci(17)
		ArrayList<Long> runningTime = new ArrayList<Long>();
		for(int i=0;i<iteration;i++){
				Long progStart = System.currentTimeMillis();
		      	AverageTMAXSequentialDelayed obj = new AverageTMAXSequentialDelayed();
		      	AverageTMAXSequentialDelayed.stations = new HashMap<String,Station>();
		      	obj.sequentialCalculation(recordList);
		        Long end = System.currentTimeMillis();
				runningTime.add((end - progStart));
		}
		
		Collections.sort(runningTime);
		// Outputs the timings and average calculations.
		System.out.println("The averages for Sequential Program With Delay(fib) is");
		System.out.println("The Minimum Run Time is: "+runningTime.get(0) + " milliseconds");
		System.out.println("The Average Run Time is: "+Main.average(runningTime) + " milliseconds");
		System.out.println("The Maximum Run Time is: "+runningTime.get(runningTime.size()-1) + " milliseconds");
		System.out.println("End of Sequential with Fibbonacci");
	}

}
