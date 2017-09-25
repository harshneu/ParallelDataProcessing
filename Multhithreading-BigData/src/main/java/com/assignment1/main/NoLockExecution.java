package com.assignment1.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.assignment1.no_lock.AverageTMAXNoLock;
import com.assignment1.no_lock_delayed.AverageTMAXNoLockDelayed;
import com.assignment1.utilities.Station;

public class NoLockExecution {
	//Takes in division and iterationa as the parameter to run the method.
	// Iterations are the number of times the data is run.
	public static void noLock(List<List<String>> dividedList, Integer repeats){
		ArrayList<Long> runTime = new ArrayList<Long>();
		for(int i=0;i<repeats;i++){
			// Program Initialization
				Long progStart = System.currentTimeMillis();
				AverageTMAXNoLock objNoLock= new AverageTMAXNoLock();
				AverageTMAXNoLock.stations = new HashMap<String,Station>();
					try {
						objNoLock.noLockCalculation(dividedList);
					} catch (Exception e) {
						//  Auto-generated catch block
						e.printStackTrace();
					}    
				Long end = System.currentTimeMillis();
				runTime.add((end - progStart));
		}
		
		Collections.sort(runTime);
		// Outputs the timings and average calculations.
		System.out.println("The averages for No Lock Program");
		System.out.println("The Minimum Run Time is: "+runTime.get(0) + " milliseconds");
		System.out.println("The Average Run Time is: "+Main.average(runTime) + " milliseconds");
		System.out.println("The Maximum Run Time is: "+runTime.get(runTime.size()-1) + " milliseconds");
		System.out.println("End of No Lock");
	}
	public static void noLockWithDelay(List<List<String>> divisions, Integer iteration){
		ArrayList<Long> runningTime = new ArrayList<Long>();
		for(int i=0;i<iteration;i++){
			//Program Execution with delay
				Long progStart = System.currentTimeMillis();
				AverageTMAXNoLockDelayed objNoLock= new AverageTMAXNoLockDelayed();
				AverageTMAXNoLockDelayed.stations = new HashMap<String,Station>();
				try {
					objNoLock.noLockCalculation(divisions);
				} catch (Exception e) {
					e.printStackTrace();
				}    
				Long end = System.currentTimeMillis();
				runningTime.add((end - progStart));
		}
		
		Collections.sort(runningTime);
		// Outputs the timings and average calculations.
		System.out.println("The averages for No Lock Program With Delay(fib) is");
		System.out.println("The Minimum Run Time is: "+runningTime.get(0) + " milliseconds");
		System.out.println("The Average Run Time is: "+Main.average(runningTime) + " milliseconds");
		System.out.println("The Maximum Run Time is: "+runningTime.get(runningTime.size()-1) + " milliseconds");
		System.out.println("End of No Lock with Fibbonacci");
	}

}
