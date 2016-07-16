/* TestRestsJson by Nancy Schorr, 2016
 Demonstrates use of apis to read text file and store it as a JSON object for further manipulation
 */

package com.nmschorr;

import java.io.*;
import org.json.*;
import java.util.Scanner;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.client.*;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.*;

//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.GET;

public class TestRestJson {
	public static String myFilenm = "C:\\jasondata2.json";
	public static String myString = null;
	public static String myDelim = "\\r";
	public static String myDelim2 = "\\Z";
	public static Map<Integer, String> f = new HashMap<>();
	public static String jUrl = "http://jsonplaceholder.typicode.com/albums";
 

	public static void main(String[] args) {
		try {
			readJson();
			restJersey();
			
			ObjectMapper mapper = new ObjectMapper(); // just need one
			
			System.out.println("Here's new stuff");
		    System.out.println(mapper.toString());
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
	 
	public static void restJersey() throws Exception {
		String jsonUrl = "http://jsonplaceholder.typicode.com";
		Client client = ClientBuilder.newClient();
	 	WebTarget webTarget = client.target(jsonUrl).path("albums");
		 
		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.TEXT_PLAIN_TYPE);


		
		Response response = invocationBuilder.get();
		System.out.println("yes");
		Object myobj = response.getEntity();
		System.out.println("Server response .... \n");
		System.out.println(myobj.toString());
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

	  private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }

		  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		    InputStream is = new URL(url).openStream();
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = readAll(rd);
		      JSONObject json = new JSONObject(jsonText);
		      return json;
		    } finally {
		      is.close();
		    }
		  }

}	//class










