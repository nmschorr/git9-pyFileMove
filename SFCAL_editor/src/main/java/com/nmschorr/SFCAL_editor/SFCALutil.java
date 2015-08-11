/**
 * This class contains methods that remove extra calendar events from an ics calendar file.
 * @author Nancy M. Schorr
 * @version 1.1
 * 
 */
package com.nmschorr.SFCAL_editor;


import java.io.File;
import java.io.IOException;
import java.util.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import static com.nmschorr.SFCAL_editor.SFCALeditor.*;
	

public class SFCALutil {
  	//static String CtmpDir="C:\\tmp";
	final static String LFEED = System.getProperty("line.separator");
	
	static void generalStringFixing(String SFCALtempOneFilename, File localOriginalFile) {   
		String firstfront = "";
		String partialEND = "";
		String newDTENDstr = "";  
		String utilLINE1 = "";
		String utilLINE2 = "";
		List <String> newLINEARRY = new ArrayList<String>();
		boolean addDTEND=false;
		String curSTR = "";

		try {
			File SFCALtempONE = new File(SFCALtempOneFilename);

			List<String> origSFCALarray =  FileUtils.readLines(localOriginalFile);
			System.out.println("----------------------------------%%%%%%%##### total lines: " +  origSFCALarray.size());
			// get ics header lines in 1st-first four header lines of ics inFileName

			// for each line in file:
			int arrySize = origSFCALarray.size();
			System.out.println("array size: " + arrySize);
			int i = 0;
			while (i < arrySize)  {
				utilLINE1 = "";
				utilLINE2 = "";
				curSTR = "";
				newDTENDstr = "";
				curSTR = origSFCALarray.get(i);
				System.out.println("line count: " + i);
				addDTEND=false;
			
				if (curSTR.length() > 0 )
				{
					verboseOut("current line:"+curSTR);
					utilLINE2= StringUtils.chomp(curSTR);
					utilLINE1= checkForSignQuote(utilLINE2);

					if (utilLINE1.startsWith("SUMMARY:")) {
						utilLINE2 = replaceSigns(utilLINE1);
					} else utilLINE2 = utilLINE1;
					verboseOut("value of utilLINE2 is: "+ utilLINE2);				

					if (utilLINE2.contains("Moon goes void")) {
						utilLINE1 = "SUMMARY:Moon void of course";
					}
					else { 	utilLINE1 = utilLINE2; }
					G_VERBOSE=1;
					firstfront = utilLINE1.substring(0,6);
					if ( firstfront.equals("DTSTAR") )   {   // add DTEND line, chg  start line ending to 5Z to add 5 secs	 			
						String newDTSTART;
						verboseOut("!!@@@@@  the original DTSTART line is  " + utilLINE1);

						partialEND = utilLINE1.substring(8,22) + "5Z";
						newDTSTART = "DTSTART:" + partialEND;
						
						newDTENDstr ="DTEND:" + partialEND;					
						verboseOut("!!@@@@@  the new DTSTART line is  " + newDTSTART);
						verboseOut("DTEND: new line is " + newDTENDstr);
						addDTEND = true;
						utilLINE2 = newDTSTART;
						i++;
					}
					else { 	utilLINE2 = utilLINE1; }				
				}  //if curSTR				
				newLINEARRY.add(utilLINE2);
				if (addDTEND) { 
					newLINEARRY.add(newDTENDstr);  }
				G_VERBOSE=0;			
				i++;

			} // for curSTR
			FileUtils.writeLines(SFCALtempONE, newLINEARRY, LFEED, true);	
			//mySleep(1);

		}   catch (IOException e)  { 
			e.printStackTrace();	 
		}  // catch
	}	/// end of method
	
	
//----new Method ===============================================================//
	
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
	
	
	static String checkForSignQuote(String checkLine) {
		 
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

	
	static String checkForSigns(String origLine, String theVal, String theRep) {
		String theFixedLine = "";
		verboseOut("inside checkForSigns checking val rep: "+theVal + theRep);		
		if (origLine.contains(theVal))  {
			System.out.println("!!!---            ---FOUND sign CHAR -----!!!!  !!! \n"+origLine);
			theFixedLine = origLine.replace( theVal, theRep);  
			//theFixedLine = origLine.replace( "Cn", "Cancer ");  
			System.out.println("------------------------The fixed line: " + theFixedLine);
			return theFixedLine;
		}
		else return origLine;	
	}

		
	static String replaceSigns(String theInputStr) {
		String answerst =null;
		verboseOut("inside replaceSigns");		
		HashMap <String, String> theHashmap = makemyhash();

		for (String key : theHashmap.keySet()) {
			answerst = checkForSigns(theInputStr, key, theHashmap.get(key));
		}   
		verboseOut("val of answerst is: " + answerst);
		return answerst;
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
	
}  // end of class



 	