package com.assignment1.utilities;

import java.util.ArrayList;
import java.util.List;

public class LineDivision {
	//divides the record into subrecords and returns the latter.
	@SuppressWarnings("unused")
	public static List<List<String>> divideLines( List<String> linestring, int divisions )
	{
	    List<List<String>> lineDivision = new ArrayList<List<String>>();
	    int result = (linestring.size() / divisions);
	    for( int i = 0, j = linestring.size(); i < j; i += result )
	    {
	        if((linestring.size() % divisions) > 0 )
	        {
	    	    int newRecord = (linestring.size() % divisions);
	            newRecord--;
	            result = (linestring.size() / divisions) + 1;
	        }
	        else
	        {
	            result = (linestring.size() / divisions);
	        }
	        lineDivision.add( new ArrayList<String>
	        (linestring.subList(i, Math.min( j, i + (linestring.size() / divisions)) ) ) );
	    }
	    return lineDivision;
	}
}
