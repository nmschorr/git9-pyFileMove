package com.nmschorr.SFCAL_editor;


import java.io.File;
import java.io.IOException;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
//static String MainInDirNm="E:\\sfcalfiles";

	
public class SFCALutil {
	static String MainInDirNam="E:\\sfcalfiles";
	static String CtmpDir="C:\\tmp";
	static String InDirVdsNam=MainInDirNam+"\\vds";
	static String OrigSFCALfileNm = InDirVdsNam +"\\SFCAL.ics";
	static String SFCALtempOneFilename = CtmpDir + "\\SFCALtemp1.ics";
	static File ORIGsfcalFILE = null;
	final static String LINE_FEED = System.getProperty("line.separator");
	static final int MAX_EVENTS=15;
	static final String newfront = "DTEND:";
	static File SFCALtempONE;
	
	static void generalStringFixing() {   
		String firstfront;
		String newback;
		String newComboStr;  
		String checkCharString;
		String replacedSignString;
		String voidFixedString;
		mySleep(2);
		try {
			ORIGsfcalFILE = new File(OrigSFCALfileNm);
			SFCALtempONE = new File(SFCALtempOneFilename);
			//delFiles(tmpFileTwo);  // delete the inFileName we made last time
			List<String> origSFCALarray =  FileUtils.readLines(ORIGsfcalFILE);
			System.out.println("----------------------------------%%%%%%%##### total lines: " +  origSFCALarray.size());
			// get ics header lines in 1st-first four header lines of ics inFileName

			// for each line in file:
			for (String currentLineInArray : origSFCALarray)  {
			if (currentLineInArray.length() > 0 )
			{
				StringUtils.chomp(currentLineInArray);
				System.out.println("current line:"+currentLineInArray);
				checkCharString= checkForChar(currentLineInArray);
				replacedSignString = replaceSigns(checkCharString);
				System.out.println("value of replacedSignString is: "+ replacedSignString);
				

				if (replacedSignString.contains("Moon goes void")) {
					voidFixedString = "SUMMARY:Moon void of course";
				}
				else {
					voidFixedString = replacedSignString;

				}
				FileUtils.writeStringToFile(SFCALtempONE, voidFixedString, true);	
				FileUtils.writeStringToFile(SFCALtempONE,"\n", true);	 
		
				firstfront = voidFixedString.substring(0,6);

				if ( firstfront.equals("DTSTAR") )   {  					
					newback = voidFixedString.substring(8,23) + "Z";
					//System.out.println("!!@@@@@  the line is  " + voidFixedString);
					newComboStr = newfront + newback +"\n";  					
					//System.out.println("DTEND: new line is " + newComboStr);
					FileUtils.writeStringToFile(SFCALtempONE, newComboStr, true);	
				}
			  }	
			}
		}
		catch (IOException e)  { 
			e.printStackTrace();	 
		}
	}	
	
	static void delFiles(File f1) {
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
			String newStringy2 = newStringy.replace( "'", " ");  
			System.out.println("The fixed line: " + newStringy2);
			return newStringy2;
		}
		else { return checkLine;
			}
		}

	static String checkForSigns(String origLine, String theVal, String theRep) {
		String theFixedLine;
	 	// System.out.println("inside checkForSigns"+ "/n" +checking val rep: "+theVal + theRep);		
		if (origLine.contains(theVal))  {
			System.out.println("!!!---            ---FOUND sign CHAR -----!!!!  !!! /n"+origLine);
			theFixedLine = origLine.replace( theVal, theRep);  
			//System.out.println("------------------------The fixed line: " + theFixedLine);
			return theFixedLine;
		} else {   return origLine;      }
	}

		
		static String replaceSigns(String theInputStr) {
			String returnString=null;
		 	//System.out.println("inside replaceSigns");		
			HashMap <String, String> theHashmap = makemyhash();

			for (String key : theHashmap.keySet()) {
				returnString=  checkForSigns(theInputStr, key, theHashmap.get(key));
			}   
			if ( !returnString.equals(null)) {
				System.out.println("%%%%%%%%%5     %%%%%% returning this: " + returnString);
			
				return returnString;
			}
			else  {
				System.out.println("!!!!!! aaaccck returning wrong value");
				return theInputStr;
			}
	}	
	
		static HashMap<String, String>  makemyhash() {
		HashMap <String, String> myHashmap = new HashMap<String, String>();
		myHashmap.put("Cn", " Cancer");
		myHashmap.put("Ar", " Aries");
		myHashmap.put("Ta", " Taurus");
		return myHashmap;
	}

	
	
}



 	