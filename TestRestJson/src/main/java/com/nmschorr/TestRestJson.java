/* TestRestsJson by Nancy Schorr, 2016
 Demonstrates use of apis to read text file and store it as a Json object for further manipulation
*/

package com.nmschorr;

import java.io.*;
import org.json.*;
import java.util.Scanner;
import java.util.*;


public class TestRestJson {
	public static String myFilenm = "C:\\jasondata2.json";
	public static String myString = null;
	public static String myDelim = "\\r";
	public static String myDelim2 = "\\Z";
	

	public static void main(String[] args) {
		readJson();
		System.out.println("Finished");
	}

	static void readJson() {
		
			myString = myScanner();
			
			try {
			JSONArray myJsonArry = new JSONArray(myString); 
			JSONObject myJsonObj = myJsonArry.optJSONObject(0);
			
			 Iterator myJsonItr = myJsonArry.iterator();
		      while(myJsonItr.hasNext()) {
		         Object myObj = myJsonItr.next();
		         System.out.println("myJsonArry element obj" + myObj + " ");
		      }
			
			
			System.out.println("--------------------");
			System.out.println(myJsonObj.toString());
			System.out.println("--------------------");
			ArrayList<String> a = new ArrayList<String>();
			//String[] ms = (String[])myArry;
			

			for(int i=0; i<myJsonArry.length(); i++){
				JSONObject jsonObj  = myJsonArry.getJSONObject(i);
				//System.out.println("Value of id:" + jsonObj.get("id"));
				//System.out.println("Done with record");
			} //for
		  } //try
	
		catch (Exception e) { 
			System.out.println("Exception caught - something didn't work!");
			e.printStackTrace();	
		}
	}

	
	public static String myScanner() {
		String locString = new String(" ");
		File myFile = new File(myFilenm);
		try {
			Scanner myScanner = new Scanner(myFile);
			myScanner.close();			
			locString = myScanner.useDelimiter(myDelim2).next();
			//System.out.println(myString);
		}
			catch (Exception e) { 
				System.out.println("Exception caught - something didn't work!");
				e.printStackTrace();	
			}
		return locString;
		}  //myScanner()
	

}	//class





	 




