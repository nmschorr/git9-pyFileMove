package com.nmschorr.SFCAL_standard;


import java.io.File;
import java.io.IOException;
import java.util.*;

import org.apache.commons.io.FileUtils;
//import org.apache.commons.lang.StringUtils;


import org.apache.commons.lang.StringUtils;

import static java.lang.System.out;
//import static com.nmschorr.SFCAL_editor.SFCALeditor.verboseOut;
import static com.nmschorr.SFCAL_standard.SFCALstandard.*;

	
public class SFCALstandardutil {
	static final String newfront  =  "DTEND:";
	static int myLINEct =0;
	//static String NEWREPLACEDstring;
	//static String perfectString;
	// static String replacedSignString;
	// static String voidFixedString;
	//static boolean useSUMMARYstr = false;
	//String[] plansArry = {"Sun", "Mon","Mer", "Ven", "Mar", "Jup", "Sat","Nep", "Ura", "Plu"};		
	
	
	// new method // --------------------------------------------------------------	 		
	static void generalStringFixing(String origFILEnm, String tmpFILEnmONE ) {   
		boolean keepGoing = true;
		CharSequence SUMstr = "SUMMARY:Tr-Tr";
		String newLocLINE1 = "";
		String newLocLINE2 = "";
		String DEStr = "DESCRIPTION";
		String LFEED = System.getProperty("line.separator");

		List<String> nwARRY  =  new ArrayList<String>();
		File origFILE = new File(origFILEnm);
		File SFCALtempONE  =  new File(tmpFILEnmONE);
		Map<String, String> newhash  =  makeNewhash();
	
		try {
			List<String> origFILEARRY  =   FileUtils.readLines(origFILE);
			int arraySIZE  =  origFILEARRY.size();
			int safeSIZE = arraySIZE-5;
			System.out.println("realCOUNT:  " +  arraySIZE + "   safecount:  " +  safeSIZE);			
			System.out.println("----------------------------------%%%%%%%##### total lines: " +  origFILEARRY.size());
			// get ics header lines in 1st-first four header lines of ics inFileName

			// for each line in file:
			for (String cLINE : origFILEARRY)  {
				keepGoing = checkLINEfunction(cLINE, safeSIZE) ;		
				G_VERBOSE = 1;

				if (keepGoing == true ) { 
					System.out.println("myLINEct:  " + myLINEct);
					newLocLINE2  =   StringUtils.chomp(cLINE);  // chomp is removing the Z
					String theDTENDline="";
					cLINE  =   chkForWeirdChar(newLocLINE2);
					
					System.out.println("    char string is:         " + cLINE);
   // the ifs start here
					if ( cLINE.contains(SUMstr)) {  /// if TR-TR only lines
						newLocLINE2 = fixSUMMARYsigns(cLINE) ;
						nwARRY.add(cLINE);
					}										
					else if ( cLINE.contains(DEStr) || cLINE.startsWith(" "))   {  /// if TR-TR only lines
						newLocLINE2 = fixDESCRIPTION_line(cLINE) ;
						nwARRY.add(newLocLINE2);
					}										
					else if (cLINE.startsWith("SUMMARY:Tr "))   { 
						newLocLINE2 = cLINE.replace("Tr ", "");
						
						String oldPlanet = "";
						String newPlanet = "";
						String dchar = " D";  
						String rchar = " R";  // MUST have a space first
						
						int cStart = newLocLINE2.length()-3;  // a space & there's a line ending too
						int cEnd = newLocLINE2.length()-1;
						String newSub = newLocLINE2.substring(cStart,cEnd);  // get the last char
						
						if (newSub.equals(rchar))  {  
							newLocLINE1  = newLocLINE2.replace(rchar, " goes Retrograde");
						}
						else if (newSub.equals(dchar))  { /// if TR-TR only lines
							newLocLINE1  = newLocLINE2.replace(dchar, " goes Direct");
						}
						
						oldPlanet = newLocLINE1.substring(8,11);
						if ( newhash.containsKey(oldPlanet)) {
							newPlanet = (newhash.get(oldPlanet));
						}
						cLINE= newLocLINE1.replace(oldPlanet, newPlanet);
						nwARRY.add(cLINE );
					}  // SUMMARY:TR 	
					else if ( cLINE.contains("DTSTAR") ) {
						if (!cLINE.contains("VALUE")) { //skip these; they are moon for the day
							theDTENDline = chkAddDTEND(cLINE);
							nwARRY.add(cLINE );
							nwARRY.add(theDTENDline );
						}
					}
					else {
						System.out.println("   writing ORIGINAL string to file         " + cLINE);
						nwARRY.add(cLINE );
					}
					myLINEct++;
					newLocLINE1 = "";
					newLocLINE2 = "";
				}	// if

			}  //for string in array
			System.out.println("Writing to file: " + SFCALtempONE.getName());
			FileUtils.writeLines(SFCALtempONE, nwARRY);	
		}  // try
		catch (IOException e)  { 
			e.printStackTrace();	 
		}  // catch

	}	// end of method
	
	
	
	// new method // --------------------------------------------------------------	 	
	static String chkAddDTEND (String theLine) {
		String newDTEND = theLine;
		if ( theLine.contains("DTSTAR") )   {  // double check
			if  ( !theLine.contains("VALUE")) { //moon for the  day			
			String newback = theLine.substring(8,23) + "Z";
			out.println("                   !! inside chkAddDTEND -----                        @@@@@  the line is  " + theLine);
			newDTEND = newfront + newback;  					
			out.println("DTEND: new line is " + newDTEND);
		}
	}
	return newDTEND;
	}
	

	// new method // --------------------------------------------------------------	 	
static HashMap<String, String>  makeNewhash() {
		HashMap <String, String> localHash  =  new HashMap<String, String>();
		localHash.put("Mon", "Moon");
		
		localHash.put("Ari", "Aries");
		localHash.put("Tau", "Taurus");
		localHash.put("Gem", "Gemini");
		localHash.put("Can", "Cancer");
		localHash.put("Leo", "Leo");
		localHash.put("Vir", "Virgo");
		localHash.put("Lib", "Libra");
		localHash.put("Sco", "Scorpio");
		localHash.put("Sag", "Sagittarius");
		localHash.put("Cap", "Capricorn");
		localHash.put("Aqu", "Aquarius");
		localHash.put("Pis", "Pisces");

		localHash.put("Cnj", "Conjunct");
		localHash.put("Tri", "Trine");
		localHash.put("Opp", "Opposite");
		localHash.put("Sqr", "Square");
		localHash.put("Sxt", "Sextile");
		localHash.put("Qnx", "Quincunx");
		
		localHash.put("Sun", "Sun");
		localHash.put("Mer", "Mercury");
		localHash.put("Ven", "Venus");
		localHash.put("Mar", "Mars");
		localHash.put("Jup", "Jupiter");
		localHash.put("Sat", "Saturn");
		localHash.put("Nep", "Neptune");
		localHash.put("Ura", "Uranus");
		localHash.put("Plu", "Pluto");		
	return localHash;
	}

	
	// new method // --------------------------------------------------------------	 	
	static String myREPLACE(String bigstr, String oldStr, String newStr) {
		if ( bigstr.contains(oldStr) ) {
			bigstr.replace(oldStr, newStr);
		}		
		return bigstr;
	}
	
	// new method // --------------------------------------------------------------	 	
	public static void delFiles(String f2in) {
		File f1 = new File(f2in);
		if ( f1.exists() ) {
			f1.delete();  // delete the inFileName we made last time
		}
	}

	// new method // --------------------------------------------------------------	 	
	protected static void mySleep(int timewait) {
		try {
			Thread.sleep(timewait * 1000);	//sleep is in milliseconds
		} catch (Exception e) {
			System.out.println(e);
		} 
	  } // mySleep
	

	// new method // --------------------------------------------------------------	 	
	static String chkForWeirdChar(String checkLine) {
		 
		if (checkLine.contains( "\uFFFD"))  {
			System.out.println("!!!---            ---FOUND WEIRD CHAR -----!!!!  !!!  ");
			System.out.println(checkLine);	
			String newStringy  =  checkLine.replace( "\uFFFD", " ");  
			System.out.println("The fixed line: " + newStringy);
			return newStringy;
		}
		else { return checkLine;
			}
		}

	
	// new method // --------------------------------------------------------------	 	
static boolean checkLINEfunction(String theLocLine, int safecount) {
			boolean KG = true;
			if   ((theLocLine.length() > 0 ) && (theLocLine.length() < safecount) )   {

				if   ((theLocLine.length() > 0 )  && (myLINEct < safecount) )   
					{ KG  =  true; } 
				else { KG = false; }

				if ( ( theLocLine.contains("THISISATESTONLY")) 
					//	( theLocLine.contains("TRANSPARENT")) 
						///     ||
					//	( theLocLine.contains("DTEND")) || 
					//	( theLocLine.contains("VCALENDAR")) || 
					//	( theLocLine.contains("VEVENT")) || 
					//	( theLocLine.contains("DTSTART")) || 
					//	( theLocLine.contains("PRODID:")) || 
					//	( theLocLine.contains("VERSION:")) || 
					//	( theLocLine.contains("METHOD:")) || ( theLocLine.contains("UID")) 
					//	|| ( theLocLine.contains("CATEGORIES")) || ( theLocLine.contains("DTSTAMP")) 
						)
				{ 
				 KG =false; 
				}
			}
			//System.out.println ("val of KG is :" + KG);
			return KG;
		}
		
		
		// new method // --------------------------------------------------------------	 	
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
}



 	