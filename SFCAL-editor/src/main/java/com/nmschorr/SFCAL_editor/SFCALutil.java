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
				if (mylinenow.contains("Moon goes void")) {
					mylinenow = "SUMMARY:Moon void of course";
				}
						
				FileUtils.writeStringToFile(tempFile2, mylinenow, true);	
				FileUtils.writeStringToFile(tempFile2,"\n", true);	 
		
				firstfront = mylinenow.substring(0,6);

				if ( firstfront.equals("DTSTAR") )   {  					
					newback = mylinenow.substring(8,23) + "Z";
					System.out.println("!!@@@@@  the line is  " + mylinenow);
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
}







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
 	