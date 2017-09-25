package com.assignment1.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.assignment1.fine_lock.AverageTMAXFineLock;
import com.assignment1.fine_lock_delayed.AverageTMAXFineLockDelayed;
import com.assignment1.utilities.Station;

public class FineLockExecution {
	//Takes in division and iterationa as the parameter to run the method.
	// Iterations are the number of times the data is run.
	public static void fineLock(List<List<String>> divisions, Integer iteration){
		ArrayList<Long> runningTime = new ArrayList<Long>();
		for(int i=0;i<iteration;i++){
			// Program Initialization
				Long progStart = System.currentTimeMillis();
				AverageTMAXFineLock objFineLock= new AverageTMAXFineLock();
				AverageTMAXFineLock.stations = new HashMap<String,Station>();
				try {
					objFineLock.fineCalculation(divisions);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Long end = System.currentTimeMillis();
				runningTime.add((end - progStart));
		}
		
		Collections.sort(runningTime);
		// Outputs the timings and average calculations.
		System.out.println("The averages for Fine Lock Program is");
		System.out.println("The Minimum Run Time is: "+runningTime.get(0) + " milliseconds");
		System.out.println("The Average Run Time is: "+Main.average(runningTime)+ " milliseconds");
		System.out.println("The Maximum Run Time is: "+runningTime.get(runningTime.size()-1)+ " milliseconds");
		System.out.println("End of Fine Lock");
	}
	public static void fineLockWithDelay(List<List<String>> divisions, Integer iteration) throws Exception{
		ArrayList<Long> runningTime = new ArrayList<Long>();
		for(int i=0;i<iteration;i++){
			// program execution same as above just with a delay. 
				Long progStart = System.currentTimeMillis();
				AverageTMAXFineLockDelayed objFineLock= new AverageTMAXFineLockDelayed();
				AverageTMAXFineLockDelayed.stations = new HashMap<String,Station>();
				try {
					objFineLock.fineCalculation(divisions);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Long end = System.currentTimeMillis();
				runningTime.add((end - progStart));
		}	
		// Outputs the timings and average calculations.
		Collections.sort(runningTime);
		System.out.println("The averages for Fine Lock Execution With Delay(fib) is");
		System.out.println("The Minimum Run Time is: "+runningTime.get(0) + " milliseconds");
		System.out.println("The Average Run Time is: "+Main.average(runningTime) + " milliseconds");
		System.out.println("The Maximum Run Time is: "+runningTime.get(runningTime.size()-1) + " milliseconds");
		System.out.println("End of Fine Lock with Fibbonacci");
	}

}
