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

//import org.apache.commons.io.*;   // for IOUtils
//import org.apache.commons.io.input.*;   // for IOUtils
//import java.nio.charset.StandardCharsets;

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
		
		URL myUrl = new URL(myUrlString);
		HttpURLConnection httpConnection = (HttpURLConnection) myUrl.openConnection(); 			
		BufferedInputStream bufferedInputStream = new BufferedInputStream(httpConnection.getInputStream());
		InputStreamReader inputStreamReader= new InputStreamReader(bufferedInputStream);			
		BufferedReader bufReader = new BufferedReader(inputStreamReader);
		
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
		List<Object> myArrayList = new ArrayList<Object>();

		JSONArray myJsonArry = new JSONArray(longJsonString); 
		myArrayList = toList(myJsonArry);
		
		Object arryListObject = myArrayList.get(0);   // to test and look at it in debugger
		HashMap<String,String> myArryListHashMap = (HashMap<String, String>) myArrayList.get(0); // shows casting of Object to HashMap
		 
		HashMap<String, String> map = (HashMap<String, String>) arryListObject;  // shows casting of Object to HashMap
		
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

	// this was code used to test a question
	public static void testDateQuestion() throws Exception {

		
		
		JSONObject js1 = new JSONObject( "{\"a\": 1, \"b\": \"str\", \"dob\":\"/Date(1463667774000+0000)/\"}");

		Iterator<String> keys = js1.keys();
		while(keys.hasNext()){
			Object aObj = js1.get(keys.next());
			if(aObj instanceof Integer){
				System.out.println(aObj+" is Integer");
			}else if(aObj instanceof String){
				if (aObj.toString().startsWith("/Date")) {
					System.out.println(aObj+" is Date");
				}	
				else
				System.out.println(aObj+" is String");
			}else if(aObj instanceof Date){
				System.out.println(aObj+" is Date");
			}
		}
				                
		 String str = "/Date(1463667774000-9000)/";
	     Date date = new Date(Long.parseLong(str.replaceAll(".*?(\\d+).*", "$1")));
	     System.out.println("1st "+ date);					                
	}				                


	
}	//class










