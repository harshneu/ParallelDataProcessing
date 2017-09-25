package com.assignment1.main;

import java.util.List;

import com.assignment1.utilities.LineDivision;
import com.assignment1.utilities.Readers;

public class Main {
	// Main class, all the calls are made from this class.
	// Loads the file from which the input is read.
	
	public static void main(String args[]) throws Exception{
		List<String> recordList = Readers.loaderFile(args[0]);
		//divisions take the list.
		List<List<String>> divisions = LineDivision.divideLines(recordList, 4);
		
		//sequential run without fibonacci(17).
		SequentialExecution.sequential(recordList, 10);
		//sequential run with fibonacci(17).
		SequentialExecution.sequentialWithDelay(recordList, 10);
		
		//no lock execution without fibonacci(17)
		NoLockExecution.noLock(divisions,10);
		// no lock with fibbonacci(17)
		NoLockExecution.noLockWithDelay(divisions,10);
		
		// no lock execution without fibonacci(17)
		FineLockExecution.fineLock(divisions, 10);
		// no lock execution with fibonacci(17)
		FineLockExecution.fineLockWithDelay(divisions, 10);

		// coarselock without fibonacci(17)
		CoarseLockExecution.coarseLock(divisions, 10);
		// coarselock with fibonacci(17)
		CoarseLockExecution.coarseLockWithDelay(divisions, 10);
		
		// no sharing without fibonacci(17)
		NoSharingExecution.noSharingLock(divisions, 10);
		// no sharing with fibonacci(17)
		NoSharingExecution.noSharingLockWithDelay(divisions, 10);
	}

	// Average Calculator.
	public static Double average(List<Long> element){
		Double tempratureData = 0.0;
		for(Long increment : element){
			tempratureData+=increment;
		}
		return (tempratureData/element.size());
	}
	
}
