/* TestRestsJson by Nancy Schorr, 2016
 Demonstrates use of apis to read text file and store it as a JSON object for further manipulation
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
	public static Map<Integer, String> f = new HashMap<>();
 

	public static void main(String[] args) {
		try {
			readJson();
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Program Finished");
	}

	static void readJson() throws Exception {
		myString = myScanner();
		List<Object> mlist = new ArrayList<Object>();

		JSONArray myJsonArry = new JSONArray(myString); 
		mlist = toList(myJsonArry);
		Object tempObj = mlist.get(0);
		 
		
		HashMap<String, String> map = (HashMap<String, String>) tempObj;
		
		
		JSONObject myJsonObj = myJsonArry.optJSONObject(0);
		Iterator<Object> myJsonItr = myJsonArry.iterator();

		while(myJsonItr.hasNext()) {
			Object myObj = myJsonItr.next();
			System.out.println("myJsonArry element obj" + myObj + " ");
			System.out.println("--------------------" + myJsonObj.toString());
			ArrayList<String> a = new ArrayList<String>();
			for(int i=0; i<myJsonArry.length(); i++){
				JSONObject jsonObj  = myJsonArry.getJSONObject(i);
			} //for

		}//while
	}


	public static String myScanner() throws FileNotFoundException {
		final Scanner myScanner;
		String locString = new String(" ");
		File myFile = new File(myFilenm);
		myScanner = new Scanner(myFile);
		locString = myScanner.useDelimiter(myDelim2).next();
		myScanner.close();			
		return locString;
	}  //myScanner()




// from: http://stackoverflow.com/questions/21720759/convert-a-json-string-to-a-hashmap
	
	public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
		Map<String, Object> retMap = new HashMap<String, Object>();

		if(json != JSONObject.NULL) {
			retMap = toMap(json);
		}
		return retMap;
	}

	// from: http://stackoverflow.com/questions/21720759/convert-a-json-string-to-a-hashmap
	public static Map<String, Object> toMap(JSONObject object) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();

		Iterator<String> keysItr = object.keys();
		while(keysItr.hasNext()) {
			String key = keysItr.next();
			Object value = object.get(key);

			if(value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if(value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			map.put(key, value);
		}
		return map;
	}

	// from: http://stackoverflow.com/questions/21720759/convert-a-json-string-to-a-hashmap
	public static List<Object> toList(JSONArray array) throws JSONException {
		List<Object> list = new ArrayList<Object>();
		for(int i = 0; i < array.length(); i++) {
			Object value = array.get(i);
			if(value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if(value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			list.add(value);
		}
		return list;
	}


}	//class










