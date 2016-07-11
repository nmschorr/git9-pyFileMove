//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import org.apache.commons.io.FileUtils;
//import java.io.FileNotFoundException;
//import java.util.Iterator;
//import org.json.simple.JSONArray;
//import org.json.simple.parser.ParseException;
//import org.json.*;


import java.io.FileReader;
import java.net.*;
import java.nio.charset.*;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import org.json.simple.parser.JSONParser;
 
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TestRestsJson {
  		 
	public static void main(String[] args) {
		//makelist();
		readja();
		System.out.println("Finished");
	}
	
	static void readja() {
		try {
			JSONParser parser = new JSONParser();
			String myFilenm = "C:\\jasondata2.json";
			Object fileObject = parser.parse(new FileReader(myFilenm));
			JSONObject jsonObject = (JSONObject) fileObject;
			System.out.println("Here's the json object:");
			System.out.println(jsonObject.toString());
			System.out.println("Here's the json object again:");
			System.out.println(jsonObject.toJSONString());
			String newString = (String) jsonObject.get("username");
			System.out.println("username: " + newString);
			 
		
		} catch (Exception e) { 
			System.out.println("json object didn't work!");
			e.printStackTrace();	
		}
	}

	
	static void makelist() {
		try {
			
		    InetAddress address = InetAddress.getByName("192.168.1.254");
		    boolean reachable = address.isReachable(100000);
		    System.out.println("Is host1 reachable? " + reachable);		
		    
		    InetAddress address2 = InetAddress.getByName("192.168.1.64");
		    boolean reachable2 = address2.isReachable(100000);
		    System.out.println("Is host google reachable? " + reachable2);		
			Document doc = Jsoup.connect( "http://www.google.com:80" ).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0").referrer("http://www.google.com").timeout(1000*5).ignoreHttpErrors(true).get();
			System.out.println( doc.toString() );		
			System.out.println("google worked!");
		} catch (Exception e) { 
			System.out.println("google didn't work!");
			e.printStackTrace();	
		}
				
				
				
				
		try {
			URL myUrl = new URL("http://jsonplaceholder.typicode.com/users");
			String myUrlStr = IOUtils.toString(myUrl, Charset.forName("UTF-8"));	
			//JSONObject obj = (JSONObject) new JSONTokener(myUrlStr);
            JSONObject jsonObject = (JSONObject) JSONValue.parseWithException(myUrlStr);		
            System.out.println(jsonObject.get("id"));

		} catch (Exception e) { 
			e.printStackTrace();	
		}	// catch

		 
	}


}
