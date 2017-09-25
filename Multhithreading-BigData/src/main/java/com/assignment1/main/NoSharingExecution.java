package com.assignment1.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.assignment1.no_sharing.AverageTMAXNoSharing;

public class NoSharingExecution {
	//Takes in division and iterationa as the parameter to run the method.
	// Iterations are the number of times the data is run.
	public static void noSharingLock(List<List<String>> divisions, Integer iteration){
		ArrayList<Long> runningTime = new ArrayList<Long>();
		for(int i=0;i<iteration;i++){
			// Program Initialization
				Long progStart = System.currentTimeMillis();
				AverageTMAXNoSharing objNoSharing= new AverageTMAXNoSharing();
				try {
					objNoSharing.noSharingCalculation(divisions);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Long end = System.currentTimeMillis();
				runningTime.add((end - progStart));
		}
		
		Collections.sort(runningTime);
		System.out.println("The averages for No Sharing Program is");
		System.out.println("The Minimum Run Time is: "+runningTime.get(0) + " milliseconds");
		System.out.println("The Average Run Time is: "+Main.average(runningTime) + " milliseconds");
		System.out.println("The Maximum Run Time is: "+runningTime.get(runningTime.size()-1) + " milliseconds");
		System.out.println("End of No sharing");
	}
	public static void noSharingLockWithDelay(List<List<String>> divisions, Integer iteration){
		ArrayList<Long> runningTime = new ArrayList<Long>();
		for(int i=0;i<iteration;i++){
				Long progStart = System.currentTimeMillis();
				com.assignment1.no_sharing_delayed.AverageTMAXNoSharingDelayed objNoSharing= new com.assignment1.no_sharing_delayed.AverageTMAXNoSharingDelayed();
				try {
					objNoSharing.noSharingCalculation(divisions);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Long end = System.currentTimeMillis();
				runningTime.add((end - progStart));
		}
		Collections.sort(runningTime);
		System.out.println("The No Sharing Program With Delay(fib) is");
		System.out.println("The Minimum Running Time is: "+runningTime.get(0) );
		System.out.println("The Average Running Time is: "+Main.average(runningTime)  + " milliseconds");
		System.out.println("The Maximum Running Time is: "+runningTime.get(runningTime.size()-1) +  " milliseconds");
		System.out.println("End of No sharing with Fibbonacci and Overall Execution");	
	}	

}
