package com.assignment1.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.assignment1.coarse_lock.ThreadsStart;
import com.assignment1.coarse_lock_delayed.ThreadsStartclnodelay;
import com.assignment1.utilities.Station;

public class CoarseLockExecution {
	//Takes in division and iterationa as the parameter to run the method.
	// Iterations are the number of times the data is run.
	public static void coarseLock(List<List<String>> divisions, Integer iteration){
		ArrayList<Long> runningTime = new ArrayList<Long>();
		for(int i=0;i<iteration;i++){
			// Program Initialization
				Long progStart = System.currentTimeMillis();
				ThreadsStart objCoarseLock= new ThreadsStart();
				ThreadsStart.stations = new HashMap<String,Station>();
					try {
						objCoarseLock.coarseLockCalculation(divisions);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				Long end = System.currentTimeMillis();
				runningTime.add((end - progStart));
		}
		
		// Outputs the timings and average calculations.
		Collections.sort(runningTime);
		// Outputs the timings and average calculations.
		System.out.println("The averages for Coarse Lock Program is");
		System.out.println("The Minimum Run Time is: "+runningTime.get(0) + " milliseconds");
		System.out.println("The Average Run Time is: "+Main.average(runningTime) + " milliseconds");
		System.out.println("The Maximum Run Time is: "+runningTime.get(runningTime.size()-1) + " milliseconds");
		System.out.println("End of Coarse Lock");
	}
	


		
	public static void coarseLockWithDelay(List<List<String>> divisions, Integer iteration){
		ArrayList<Long> runningTime = new ArrayList<Long>();
		for(int i=0;i<iteration;i++){
			// program execution same as above just with a delay. 
				Long progStart = System.currentTimeMillis();
				ThreadsStartclnodelay objCoarseLock= new ThreadsStartclnodelay();
				ThreadsStartclnodelay.stations = new HashMap<String,Station>();
				try {
					objCoarseLock.coarseLockCalculation(divisions);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Long end = System.currentTimeMillis();
				runningTime.add((end - progStart));
		}	
		
		// Outputs the timings and average calculations.
		Collections.sort(runningTime);
		System.out.println("The Coarse Lock Program With Delay(fib) is");
		System.out.println("The Minimum Run Time is: "+runningTime.get(0) + " milliseconds");
		System.out.println("The Average Run Time is: "+Main.average(runningTime) + " milliseconds");
		System.out.println("The Maximum Run Time is: "+runningTime.get(runningTime.size()-1) + " milliseconds");
		System.out.println("End of Coarse Lock with Fibbonacci");
	}

}
