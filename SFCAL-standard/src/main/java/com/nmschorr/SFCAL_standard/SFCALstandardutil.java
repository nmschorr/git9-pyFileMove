package com.nmschorr.SFCAL_standard;


import java.io.File;
import java.io.IOException;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import static com.nmschorr.SFCAL_standard.SFCALstandard.*;

	
public class SFCALstandardutil {
	final static String LINE_FEED  =  System.getProperty("line.separator");
	static final String newfront  =  "DTEND:";
	static String NEWREPLACEDstring;
	static String perfectString;
	static int keepcount = 1;
	static String replacedSignString;
	static String voidFixedString;
	 
	
	static void generalStringFixing(String SFCALtempOneFilename, String sfcalFilename) {   
		String genMainCharString = "";
		int keepGoing = 1;
		File sfcalFile = new File(sfcalFilename);
		
		File SFCALtempONE  =  new File(SFCALtempOneFilename);
		File SFCALtempTWO  =  new File(SFCALtempOneFilename +"two");
		CharSequence newcs = "SUMMARY:Tr-Tr";

		try {
			Map<String, String> regHASHMAP  =  makeNewhash();
			List<String> genFileARRAY  =   FileUtils.readLines(sfcalFile);
			int realCOUNT  =  genFileARRAY.size();
			int safeCount = realCOUNT-5;
			System.out.println("safecount:  " +  safeCount);			
			System.out.println("----------------------------------%%%%%%%##### total lines: " +  genFileARRAY.size());
			// get ics header lines in 1st-first four header lines of ics inFileName

			// for each line in file:
for (String cLINE : genFileARRAY)  {
	keepGoing = checkLINEfunction(cLINE, safeCount) ;		
 	G_VERBOSE = 1;
				
 	if ( cLINE.contains(newcs)) {
				
			if (keepGoing < 2 ) { 
				StringUtils.chomp(cLINE);
				String newSTR = "";
				System.out.println("realcount:  " + realCOUNT);
				verboseOut(        "current line:               " + cLINE);
				
				genMainCharString  =   checkForChar(cLINE);
				System.out.println("    char string is:         " + genMainCharString);
			
				newSTR =  gofixhash(genMainCharString);
				System.out.println("just got this new string back from tofixhash " + newSTR);

				genMainCharString  =   newSTR;
				
				verboseOut("value of checkCharString is: "+ genMainCharString);
		G_VERBOSE = 0; 
			
				FileUtils.writeStringToFile(SFCALtempONE, genMainCharString, true);	
				FileUtils.writeStringToFile(SFCALtempONE,"\n", true);	 
 
				keepcount++;
			  }	
 				}  // cs
			}  // for
		}  //try
		catch (IOException e)  { 
			e.printStackTrace();	 
		     }  // catch
	}	// end of method
	
	static HashMap<String, String>  makeNewhash() {
		HashMap <String, String> localHash  =  new HashMap<String, String>();
		localHash.put("Mon", "Moon");
		
		localHash.put("Ari", "Aries");
		localHash.put("Tau", "Taurus");
		localHash.put("Gem", "Gemini");
		localHash.put("Can", "Cancer");
		localHash.put("Vir", "Virgo");
		localHash.put("Lib", "Libra");
		localHash.put("Sco", "Scropio");
		localHash.put("Sag", "Sagittarius");
		localHash.put("Cap", "Capricorn");
		localHash.put("Aqu", "Aquarius");
		localHash.put("Pis", "Pisces");

		localHash.put("Cnj", "Conjunct");
		localHash.put("Tri", "Trine");
		localHash.put("Opp", "Opposite");
		localHash.put("Squ", "Square");
		localHash.put("Sxt", "Sextile");
		localHash.put("Qnx", "Quincunx");
		
		localHash.put("Mer", "Mercury");
		localHash.put("Ven", "Ven");
		localHash.put("Mar", "Mars");
		localHash.put("Jup", "Jupiter");
		localHash.put("Sat", "Saturn");
		localHash.put("Nep", "Neptune");
		localHash.put("Ura", "Uranus");
		localHash.put("Plu", "Pluto");
		
		
		
		
	return localHash;
	}
	
	static String myREPLACE(String bigstr, String oldStr, String newStr) {
		if ( bigstr.contains(oldStr) ) {
			bigstr.replace(oldStr, newStr);
		}		
		return bigstr;
	}
	
	public static void delFiles(File f1) {
		if ( f1.exists() ) {
			f1.delete();  // delete the inFileName we made last time
		}
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
			String newStringy  =  checkLine.replace( "\uFFFD", " ");  
			//String newStringy2  =  newStringy.replace( "'", " ");  
			System.out.println("The fixed line: " + newStringy);
			return newStringy;
		}
		else { return checkLine;
			}
		}

//	static void checkForSigns(String origLine, String theVal, String theRep) {
//		String theFixedLine;
//		verboseOut("inside checkForSigns checking val rep: "+theVal + theRep);		
//		if (origLine.contains(theVal))  {
//			System.out.println("!!!---            ---FOUND sign CHAR -----!!!!  !!! /n"+origLine);
//			theFixedLine  =  origLine.replace( theVal, theRep);  
//			//theFixedLine  =  origLine.replace( "Cn", "Cancer ");  
//			System.out.println("------------------------The fixed line: " + theFixedLine);
//			NEWREPLACEDstring =  theFixedLine;
//		}
//	}

		
//		static void replaceSigns(String theInputStr) {
//			String returnString = null;
//			perfectString = null;
//			verboseOut("inside replaceSigns");		
//			HashMap <String, String> theHashmap  =  makemyhash();
//
//			for (String key : theHashmap.keySet()) {
//				checkForSigns(theInputStr, key, theHashmap.get(key));
//			}   		
//			verboseOut("val of perfectString is: " + perfectString);
//		}	
//	

		static int checkLINEfunction(String theLocLine, int safecount) {
			int KG=1;
			if   ((theLocLine.length() > 0 ) && (KG < safecount) )   {

				if   ((theLocLine.length() > 0 )  
						&& (keepcount < 100) )   { KG  =  1; } 
				else { KG = 0; }

				if ( ( theLocLine.contains("TRANSPARENT")) ||
						( theLocLine.contains("DTEND")) || 
						( theLocLine.contains("VCALENDAR")) || 
						( theLocLine.contains("VEVENT")) || 
						( theLocLine.contains("DTSTART")) || 
						( theLocLine.contains("PRODID:")) || 
						( theLocLine.contains("VERSION:")) || 
						( theLocLine.contains("METHOD:")) || ( theLocLine.contains("UID")) 
						|| ( theLocLine.contains("CATEGORIES")) || ( theLocLine.contains("DTSTAMP")) 
						)

				{ KG = 0; }
			}
			//System.out.println ("val of KG is :" + KG);
			return KG;
		}
		
		
		static HashMap<String, String>  makemyhash() {
			HashMap <String, String> myHashmap  =  new HashMap<String, String>();
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

//		static void dtstar() {
//		String firstfront;
//		String newback;
//		String newComboStr;  
//		firstfront  =  checkCharString.substring(0,6);
//		if ( firstfront.equals("DTSTAR") )   {  					
//			String newback  =  checkCharString.substring(8,23) + "Z";
//			verboseOut("!!@@@@@  the line is  " + checkCharString);
//			newComboStr  =  newfront + newback +"\n";  					
//			verboseOut("DTEND: new line is " + newComboStr);
//			FileUtils.writeStringToFile(SFCALtempONE, newComboStr, true);	
//			}
//		}
	
}



 	