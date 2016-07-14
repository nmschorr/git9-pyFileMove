/* TestRestsJson by Nancy Schorr, 2016
 Demonstrates use of apis to read text file and store it as a Json object for further manipulation
*/

package com.nmschorr;

import java.io.*;
import org.json.*;
import java.util.Scanner;
import java.util.*;


public class TestRestsJson {
	public static String myFilenm = "C:\\jasondata2.json";
	public static String myString = null;
	public static String myDelim = "\\r";
	public static String myDelim2 = "\\Z";
	

	public static void main(String[] args) {
		readJson();
		System.out.println("Finished");
	}

	static void readJson() {
		try {
			File myFile = new File(myFilenm);
			Scanner myScanner = new Scanner(myFile);
			
			myString = myScanner.useDelimiter(myDelim2).next();
			//System.out.println(myString);
			
			JSONArray myArry = new JSONArray(myString); 
			JSONObject j = myArry.optJSONObject(0);
			System.out.println("--------------------");
			System.out.println(j.toString());
			System.out.println("--------------------");
			ArrayList<String> a = new ArrayList<String>();
			//String[] ms = (String[])myArry;
			
			myScanner.close();

			for(int i=0; i<myArry.length(); i++){
				JSONObject jsonObj  = myArry.getJSONObject(i);
				//System.out.println("Value of id:" + jsonObj.get("id"));
				//System.out.println("Done with record");
			}			
			
		} catch (Exception e) { 
			System.out.println("Exception caught - something didn't work!");
			e.printStackTrace();	
		}
		finally {}
	}
}


