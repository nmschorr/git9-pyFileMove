/* TestRestJson by Nancy Schorr, 2016
 Demonstrates use of apis to read a json formatted text file and store it as a JSON object for further manipulation.
 Also goes to url http://jsonplaceholder.typicode.com/albums and reads the data and store it in various objects.
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
	public static String myJsonFileName = "C:\\jasondata2.json";
	static Exception e;

	public static void main(String[] args) throws Exception {
		System.setProperty("http.agent", "Chrome");
		System.out.println("Beginning...");
		readUrlData(myUrlString);  // reads json data from a url http page
		readFile(myJsonFileName);  // read json data from a file
		System.out.println("\nProgram Finished");
	}

	static void readUrlData (String locUrlString) throws Exception {
		System.out.println("\nRunning readUrlData");
		String builderLine;
		Reader bufReader = new BufferedReader(
				new InputStreamReader(new URL(locUrlString).openStream()));	
		StringBuilder myStrBuilder = new StringBuilder();
		while((builderLine = ((BufferedReader)bufReader).readLine()) != null) {
			myStrBuilder.append(builderLine);
		}
		System.out.println("\\n" + myStrBuilder.toString());		
		out.println("\nDone with readUrlData ");
	}


	static void readFile(String fname) {
		System.out.println("Running readFile");
		System.out.println("name of file " + fname);
		StringBuilder strBuilder = new StringBuilder();
		try {
			scanFileContentsString = readScanFile(fname);  // read from file
			System.out.println("scanFileContentsString: " + scanFileContentsString);
			readJson(scanFileContentsString); 
			System.out.println("in readFile: working on this: " + strBuilder.toString());
			//System.out.println( "" );  // placeholder for debugger
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//IOUtils.closeQuietly(in);
		}
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
			for(int i=0; i<myJsonArry.length(); i++){
				JSONObject jsonObj  = myJsonArry.getJSONObject(i);
			} //for
		} //while
	}

	public static String readScanFile(String filename) throws FileNotFoundException {
		final Scanner myScanner;
		String locString = new String("");  // new empty string
		File myFile = new File(filename);
		myScanner = new Scanner(myFile);
		locString = myScanner.useDelimiter(myDelim2).next();
		myScanner.close();			
		return locString;
	}  //myScanner()

	// thanks to: http://stackoverflow.com/questions/21720759/convert-a-json-string-to-a-hashmap
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

	// thanks to: http://stackoverflow.com/questions/21720759/convert-a-json-string-to-a-hashmap
	public static List<Object> toList(JSONArray myJArray) throws JSONException {
		List<Object> myList = new ArrayList<Object>();
		for(int i = 0; i < myJArray.length(); i++) {
			Object myObject= myJArray.get(i);
			if (myObject instanceof JSONArray) {
				myObject = toList((JSONArray) myObject);
			}
			else if (myObject instanceof JSONObject) {
				myObject = toMap((JSONObject) myObject);
			}
			myList.add(myObject);
		}
		return myList;
	}
}	//class










