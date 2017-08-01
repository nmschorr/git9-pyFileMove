package com.nmschorr;

import java.util.Date;
import java.util.Iterator;
//import java.io.*;
//import java.net.*;
//import java.util.*;
import org.json.*;
import org.json.JSONObject;

public class testDateQuestion {
	public static void main (String[] agrs) throws Exception {
		
		
		JSONObject js1 = new JSONObject( "{\"a\": 1, \"b\": \"str\", \"dob\":\"/Date(1463667774000+0000)/\"}");

		Iterator<String> keys = js1.keys();
		while(keys.hasNext()) {
			Object aObj = js1.get(keys.next());
			if (aObj instanceof Integer) {
				System.out.println(aObj+" is Integer");
			} else if (aObj instanceof String){
				if (aObj.toString().startsWith("/Date")) {
					System.out.println(aObj+" is Date");
				}	
				else System.out.println(aObj+" is String");
			} else if(aObj instanceof Date) {
				System.out.println(aObj+" is Date");
			}
		}
				                
		 String str = "/Date(1463667774000-9000)/";
	     Date date = new Date(Long.parseLong(str.replaceAll(".*?(\\d+).*", "$1")));
	     System.out.println("1st "+ date);					                
	}
}

