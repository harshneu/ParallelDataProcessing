package com.assignment1.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Implemented file reader.
public class Readers {
	public static List<String> loaderFile(String file){
		List<String> rows = new ArrayList<>();
		try 
		// Readline implemented reads the data line by line.
		(BufferedReader br = new BufferedReader(new FileReader(file))) {
			String row;
			while ((row = br.readLine()) != null) {
				rows.add(row);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rows;
	}
}
