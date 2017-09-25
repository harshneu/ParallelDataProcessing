package com.assignment1.utilities;


//recursive program for Fibonacci(17)
public class Fibonacci {
	public static long fib(int n){
		if(n <=1)
			return n;
		else
			return fib(n-1)+fib(n-2);		
	}
}
