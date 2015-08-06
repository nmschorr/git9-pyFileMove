package com.nmschorr.SFCAL_standard;


import java.io.File;
import java.io.IOException;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import static com.nmschorr.SFCAL_standard.SFCALstandard.*;

	
public class SFCALstandardutil {
	final static String LINE_FEED = System.getProperty("line.separator");
	static final String newfront = "DTEND:";
	static String NEWREPLACEDstring;
	static String perfectString;
	
	static void generalStringFixing(String SFCALtempOneFilename, File localOriginalFile) {   
		String firstfront;
		String newback;
		String newComboStr;  
		String checkCharString;
		String replacedSignString;
		String voidFixedString;

		try {
			Map<String, String> myHASHMAP = makeNewhash();
			File SFCALtempONE = new File(SFCALtempOneFilename);

			List<String> origSFCALarray =  FileUtils.readLines(localOriginalFile);
			System.out.println("----------------------------------%%%%%%%##### total lines: " +  origSFCALarray.size());
			// get ics header lines in 1st-first four header lines of ics inFileName

			// for each line in file:
			for (String currentLineInArray : origSFCALarray)  {
			if (currentLineInArray.length() > 0 )
			{
				StringUtils.chomp(currentLineInArray);
				verboseOut("current line:"+currentLineInArray);
				
				checkCharString= checkForChar(currentLineInArray);
				System.out.println("char string is " + checkCharString);
			
				for (String key : myHASHMAP.keySet()) {
				//	System.out.println("key is: " + key + "  key set is: " +myHASHMAP.get(key));
					checkCharString.replaceAll(key, myHASHMAP.get(key));
				}   
				
				
				verboseOut("value of checkCharString is: "+ checkCharString);
				
				
				FileUtils.writeStringToFile(SFCALtempONE, checkCharString, true);	
				FileUtils.writeStringToFile(SFCALtempONE,"\n", true);	 
		//
				firstfront = checkCharString.substring(0,6);

				if ( firstfront.equals("DTSTAR") )   {  					
					newback = checkCharString.substring(8,23) + "Z";
					verboseOut("!!@@@@@  the line is  " + checkCharString);
					newComboStr = newfront + newback +"\n";  					
					verboseOut("DTEND: new line is " + newComboStr);
					FileUtils.writeStringToFile(SFCALtempONE, newComboStr, true);	
				}
			  }	
			}
			FileUtils.waitFor(SFCALtempONE, 4);

		}
		catch (IOException e)  { 
			e.printStackTrace();	 
		}
	}	
	
	static HashMap<String, String>  makeNewhash() {
		HashMap <String, String> localHash = new HashMap<String, String>();
		localHash.put("Ari", "Aries");
		localHash.put("Tau", "Taurus");
		localHash.put("Gem", "Gemini");
		localHash.put("Can", "Cancer");
		localHash.put("Vir", "Virgo");
		localHash.put("Lib", "Libra");
		localHash.put("Sco", "Scropio");
		localHash.put("Sag", "Sagittarius");
		localHash.put("Cnj", "Conjunct");
		localHash.put("Tri", "Trine");
		localHash.put("Opp", "Opposite");
		localHash.put("Sat", "Saturn");
		localHash.put("Mon", "Moon");
		localHash.put("Mer", "Mercury");
	return localHash;
	}
	
	
	public static void delFiles(File f1) {
		if ( f1.exists() ) {
			f1.delete();  // delete the inFileName we made last time
		}
	}

	static void fileSetup() {
	} 

	
	protected static void mySleep(int timewait) {
		try {
			Thread.sleep(timewait * 1000);	//sleep is in milliseconds
		} catch (Exception e) {
			System.out.println(e);
		} 
	  } // mySleep
	
	static String checkForChar(String checkLine) {
		 
		if (checkLine.contains( "\uFFFD"))  {
			System.out.println("!!!---            ---FOUND WEIRD CHAR -----!!!!  !!!  ");
			System.out.println(checkLine);	
			String newStringy = checkLine.replace( "\uFFFD", " ");  
			//String newStringy2 = newStringy.replace( "'", " ");  
			System.out.println("The fixed line: " + newStringy);
			return newStringy;
		}
		else { return checkLine;
			}
		}

	static void checkForSigns(String origLine, String theVal, String theRep) {
		String theFixedLine;
		verboseOut("inside checkForSigns checking val rep: "+theVal + theRep);		
		if (origLine.contains(theVal))  {
			System.out.println("!!!---            ---FOUND sign CHAR -----!!!!  !!! /n"+origLine);
			theFixedLine = origLine.replace( theVal, theRep);  
			//theFixedLine = origLine.replace( "Cn", "Cancer ");  
			System.out.println("------------------------The fixed line: " + theFixedLine);
			NEWREPLACEDstring= theFixedLine;
		}
	}

		
		static void replaceSigns(String theInputStr) {
			String returnString=null;
			perfectString=null;
			verboseOut("inside replaceSigns");		
			HashMap <String, String> theHashmap = makemyhash();

			for (String key : theHashmap.keySet()) {
				checkForSigns(theInputStr, key, theHashmap.get(key));
			}   
			
			verboseOut("val of perfectString is: " + perfectString);
						
			 
	 
			 
	}	
	

		
		
		
		
		
		static HashMap<String, String>  makemyhash() {
			HashMap <String, String> myHashmap = new HashMap<String, String>();
			myHashmap.put("Cn", "Cancer ");
			myHashmap.put("Ar", "Aries ");
			myHashmap.put("Ta", "Taurus ");
			myHashmap.put("Sg", "Sagittarius ");
			myHashmap.put("Ge", "Gemini ");
			myHashmap.put("Le", "Leo ");
			myHashmap.put("Vi", "Virgo ");
			myHashmap.put("Li", "Libra ");
			myHashmap.put("Sc", "Scorpio ");
			myHashmap.put("Cp", "Capricorn ");
			myHashmap.put("Aq", "Aquarius ");
			myHashmap.put("Pi", "Pisces ");
			return myHashmap;
		}

	
	
}



 	