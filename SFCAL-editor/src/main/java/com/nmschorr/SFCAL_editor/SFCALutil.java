package com.nmschorr.SFCAL_editor;


import java.io.File;
import java.io.IOException;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
//static String MainInDirNm="E:\\sfcalfiles";

	
public class SFCALutil {
	static String MainInDirNm2="E:\\sfcalfiles";
	static String MainOutDirNm2="C:\\tmp";
	static String InDirVdsNm2=MainInDirNm2+"\\vds";
	static String InFileName2=InDirVdsNm2 +"\\SFCAL.ics";
	static String OutFileName2 = MainOutDirNm2 + "\\SFCALvds-out.ics";
	static String tempOutName2 = MainOutDirNm2 + "\\SFCALtemp1.ics";
	static File myInFile2 = new File(InFileName2);
	static File tempFile2 = new File(tempOutName2);
	static File myOutFile2 = new File(OutFileName2);
	final static String LINE_FEED = System.getProperty("line.separator");
	static final int MAX_EVENTS=15;
	static int totalLineCount2 = 0;
	static int totInFileLines2 = 0;
	
	static void remQuarterMoons() {   
		try {
			String firstfront;
			String newback;
			String newComboStr;  
			String newfront = "DTEND:";
			String newestString;
			String mylinenow2;
			delFiles(tempFile2,myOutFile2);  // delete the inFileName we made last time
			List<String> newList2 =  FileUtils.readLines(myInFile2);
			int newplListInt = newList2.size() + 10;
			System.out.println("total lines: " + newplListInt);
			// get ics header lines in 1st-first four header lines of ics inFileName

			// for each line in file:
			for (String mylinenow : newList2)  {
			if (mylinenow.length() > 0 )
			{
				StringUtils.chomp(mylinenow);
				
				newestString= checkForChar(mylinenow);
				mylinenow2 = replaceSigns(newestString);
				System.out.println("value of mylinenow2 is: "+ mylinenow2);
				

				if (mylinenow2.contains("Moon goes void")) {
					mylinenow2 = "SUMMARY:Moon void of course";
				}
						
				FileUtils.writeStringToFile(tempFile2, mylinenow2, true);	
				FileUtils.writeStringToFile(tempFile2,"\n", true);	 
		
				firstfront = mylinenow2.substring(0,6);

				if ( firstfront.equals("DTSTAR") )   {  					
					newback = mylinenow2.substring(8,23) + "Z";
					System.out.println("!!@@@@@  the line is  " + mylinenow2);
					newComboStr = newfront + newback +"\n";  					
					System.out.println("DTEND: new line is " + newComboStr);
					FileUtils.writeStringToFile(tempFile2, newComboStr, true);	
				}
			  }	
			}
		}
		catch (IOException e)  { 
			e.printStackTrace();	 
		}
	}	
	
	static void delFiles(File f1, File f2) {
		f1.delete();  // delete the inFileName we made last time
		f2.delete();  // delete the inFileName we made last time		 
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

	static String checkForSigns(String checkLine, String theVal, String theRep) {
		String newStringyBoo;
	 	// System.out.println("inside checkForStuff");		
	 	// System.out.println("checking val rep: "+theVal + theRep);		
		if (checkLine.contains(theVal))  {
			System.out.println("!!!---            ---FOUND sign CHAR -----!!!!  !!!  ");
			System.out.println(checkLine);	
			newStringyBoo = checkLine.replace( theVal, theRep);  
			System.out.println("------------------------The fixed line: " + newStringyBoo);
			return newStringyBoo;
		}
		else {   
			return checkLine;
			}
		}

	static HashMap<String, String>  makemyhash() {
		HashMap <String, String> myHashmap = new HashMap<String, String>();
		myHashmap.put("Cn", " Cancer");
		myHashmap.put("Ar", " Aries");
		myHashmap.put("Ta", " Taurus");
		return myHashmap;
	}
		
		static String replaceSigns(String theStrg) {
			String returnString=null;
			HashMap <String, String> theHashmap = makemyhash();
		 	 System.out.println("inside replaceSigns");		

			for (String key : theHashmap.keySet()) {
				returnString=  checkForSigns(theStrg, key, theHashmap.get(key));
			}   
			if ( !returnString.equals(null)) {
				System.out.println("%%%%%%%%%5     %%%%%% returning this: " + returnString);
			
				return returnString;
			}
			else  {
				System.out.println("!!!!!! aaaccck returning wrong value");
				return theStrg;
			}
	}	
	
	
	
	
}



//static String getNextLine(int loopLocationCount, int minorLocation) {
//	String newString = null;
//	int currentline;
//	                                     // the file is offset by 4 lines 
//	try {
//		currentline=(loopLocationCount * 9 ) +minorLocation + 4;
//		newString = tempFileList.get(currentline);	//exact line to get	
//		System.out.println("GNL Newval: " + newString);
//		
//	}    catch (Exception e)  {}
//	return newString;
//}
//




//static void firstPart() {   
//	try {
//		String firstfront;
//		String newback;
//		String newComboStr;  
//		String newfront = "DTEND:";
//		delFiles();  // delete the inFileName we made last time
//		 
//		List<String> newList =  FileUtils.readLines(myInFile);
//		int newplListInt = newList.size() + 10;
//		System.out.println("total lines: " + newplListInt);
//		// get ics header lines in 1st-first four header lines of ics inFileName
//
//		// for each line in file:
//		for (String mylinenow : newList)  {
//		if (mylinenow.length() > 0 )
//		{
//			StringUtils.chomp(mylinenow);
//			if (mylinenow.contains("Moon goes void")) {
//				mylinenow = "SUMMARY:Moon void of course";
//			}
//					
//			FileUtils.writeStringToFile(tempFile, mylinenow, true);	
//			FileUtils.writeStringToFile(tempFile,"\n", true);	 
//	
//			firstfront = mylinenow.substring(0,6);
//
//			if ( firstfront.equals("DTSTAR") )   {  					
//				newback = mylinenow.substring(8,23) + "Z";
//				System.out.println("!!@@@@@  the line is  " + mylinenow);
//				newComboStr = newfront + newback +"\n";  					
//				System.out.println("DTEND: new line is " + newComboStr);
//				FileUtils.writeStringToFile(tempFile, newComboStr, true);	
//			}
//		  }	
//		}
//	}
//	catch (IOException e)  { 
//		e.printStackTrace();	 
//	}
 	