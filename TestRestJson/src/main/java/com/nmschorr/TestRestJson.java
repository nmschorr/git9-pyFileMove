/* TestRestsJson by Nancy Schorr, 2016
 Demonstrates use of apis to read text file and store it as a JSON object for further manipulation
 Also goes to url http://jsonplaceholder.typicode.com/albums and reads the data
 */

package com.nmschorr;

import static java.lang.System.out;

import java.io.*;
import java.net.*;
import java.util.*;
import org.json.*;

public class TestRestJson {
	public static String scanFileContentsString = null;
	public static String myDelim = "\\r";
	public static String myDelim2 = "\\Z";
	public static String myUrlString = "http://jsonplaceholder.typicode.com/albums";
	static Exception e;

	public static void main(String[] args) throws Exception {
		System.setProperty("http.agent", "Chrome");
		System.out.println("Beginning...");

		String builderLine;

		BufferedReader bufReader = new BufferedReader(
		        new InputStreamReader(new URL(myUrlString).openStream()));	
				
		StringBuilder myStrBuilder = new StringBuilder();
		while((builderLine = bufReader.readLine()) != null) {
		    myStrBuilder.append(builderLine);
		}
		System.out.println(myStrBuilder.toString());		
		
		out.println("\nalmost done ");
		
		try {
			//scanFileContentsString = readScanFile();  // read from file
			//readJson(scanFileContentsString); 

			readJson(myStrBuilder.toString()); 
			System.out.println( "" );  // placeholder for debugger
				
		} catch(Exception e) {
			e.printStackTrace();
		}
    	finally {
	      //IOUtils.closeQuietly(in);
	    }			 
		System.out.println("Program Finished");
	}

	static void readJson(String longJsonString) throws Exception {
		// this method just plays around with converting JSON objects: use debugger to see them
		//List<Object> myArrayList = new ArrayList<Object>();
		//HashMap<String, String> map = new HashMap<String, String>();  // shows casting of Object to HashMap
		//Map<String,String> mapsObj = new HashMap<String,String>();	

		JSONArray myJsonArry = new JSONArray(longJsonString); 
		List<Object> myArrayList = new ArrayList<Object>(toList(myJsonArry));
		Collection<Map<String,String>> mapsCol = new HashSet<Map<String,String>>();	
		Object myTempObj;
		
		for (int i=0; i < myArrayList.size(); i++) {
			mapsCol.add((HashMap<String, String>)myArrayList.get(i));
			}
		
		JSONObject myJsonObj = myJsonArry.optJSONObject(0);
		Iterator<Object> myJsonItr = myJsonArry.iterator();

		while(myJsonItr.hasNext()) {
			Object myObj = myJsonItr.next();
			
			System.out.println("myJsonArry element obj" + myObj + " ");
			System.out.println("--------------------" + myJsonObj.toString());
			//ArrayList<String> a = new ArrayList<String>();
			for(int i=0; i<myJsonArry.length(); i++){
				JSONObject jsonObj  = myJsonArry.getJSONObject(i);
			} //for

		}//while
	}
	 
	
	public static String readScanFile() throws FileNotFoundException {
		final String myJsonFileName = "C:\\jasondata2.json";
		final Scanner myScanner;
		String locString = new String("");  // new empty string
		File myFile = new File(myJsonFileName);
		myScanner = new Scanner(myFile);
		locString = myScanner.useDelimiter(myDelim2).next();
		myScanner.close();			
		return locString;
	}  //myScanner()
	

	// partially from: http://stackoverflow.com/questions/21720759/convert-a-json-string-to-a-hashmap
	public static Map<String, Object> toMap(JSONObject object) throws JSONException {
		Map<String, Object> myMap = new HashMap<String, Object>();

		Iterator<String> keysItr = object.keys();
		while (keysItr.hasNext()) {
			String myKey = keysItr.next();
			Object myVal = object.get(myKey);

			if (myVal instanceof JSONArray) {
				myVal = toList((JSONArray) myVal);
			}

			else if (myVal instanceof JSONObject) {
				myVal = toMap((JSONObject) myVal);
			}
			myMap.put(myKey, myVal);
		}
		return myMap;
	}

	// partically from: http://stackoverflow.com/questions/21720759/convert-a-json-string-to-a-hashmap
	public static List<Object> toList(JSONArray myJArray) throws JSONException {
		List<Object> myList = new ArrayList<Object>();
		for(int i = 0; i < myJArray.length(); i++) {
			Object myObject= myJArray.get(i);
			if (myObject instanceof JSONArray) {
				myObject = toList((JSONArray) myObject);
			}
			else if(myObject instanceof JSONObject) {
				myObject = toMap((JSONObject) myObject);
			}
			myList.add(myObject);
		}
		return myList;
	}
	
}	//class










