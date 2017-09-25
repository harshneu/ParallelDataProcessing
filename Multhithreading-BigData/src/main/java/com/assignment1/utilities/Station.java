package com.assignment1.utilities;

public class Station {
// Getters and setters implemented to call valeues globally whenever necessary.
	private String stationID;
	private Double listRecords;
	private Double tMax;
	private Double time;
	
	public Station(String stationID, Double tMax, Double listRecords, Double averageTMAX) {
		super();
		this.stationID = stationID;
		this.listRecords = listRecords;
		this.tMax = tMax;
		this.time = averageTMAX;
	}
	public String getStationID() {
		return stationID;
	}
	public void setStationID(String stationID) {
		this.stationID = stationID;
	}
	public Double getListRecords() {
		return listRecords;
	}
	public void setListRecords(Double listRecords) {
		this.listRecords = listRecords;
	}
	public Double getTmax() {
		return tMax;
	}
	public void setTmax(Double tmaxTotal) {
		this.tMax = tmaxTotal;
	}
	public Double getAverageTMAX() {
		return time;
	}
	public void setAverageTMAX(Double averageTMAX) {
		this.time = averageTMAX;
	}
	
	public synchronized void setPropertiesWithDelay(Double tMax, Double listRecords){
		Fibonacci.fib(17);
		this.listRecords = listRecords;
		this.tMax = tMax;
	}
	
	public synchronized void setProperties(Double tMax, Double listRecords){
		this.listRecords = listRecords;
		this.tMax = tMax;
	}
	
	
}
