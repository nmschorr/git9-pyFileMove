/* SFCALeditor.java by Nancy Schorr, 2015
 *
 * This program removes extra calendar events from an ics calendar file.
 * 
 * The data for the calendar is calculated with Solar Fire 8 on Windows XP.  
 * It is run through the Perl script, then the Word macros.  
 * Ideally that would all take place in one Java executable -
 * but that's a project for the future.

 * This little program is the final step in preparing the SFCAL.ics file 
 * for uploading in Google or another calendar system.
**/

// Note: can't use an iterator because the file isn't being read that way

package com.nmschorr;


import java.io.File;
import java.io.IOException;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

/**
 * This class contains methods that remove extra calendar events from an ics calendar file.
 * @author Nancy M. Schorr
 * @version 1.1
 * 
 *
 */
public class SFCALeditor {
	static List<String> memoryList = new ArrayList<String>();
	static int eventcount = 0;	
	//final static int SECTION_LINES_EIGHT = 8;	
	 
	static String MainInDirNm="E:\\sfcalfiles";
	static String MainOutDirNm="C:\\tmp";
	static String InDirVdsNm=MainInDirNm+"\\vds";
	static String InFileName=InDirVdsNm +"\\SFCAL.ics";
	static String OutFileName = MainOutDirNm + "\\SFCALvds-out.ics";
	static String tempOutName1 = MainOutDirNm + "\\SFCALtemp1.ics";
	static String tempOutName2 = MainOutDirNm + "\\SFCALtemp2.ics";
	static File myInFile = new File(InFileName);
	static File myOutFile = new File(OutFileName);

	final static String LINE_FEED = System.getProperty("line.separator");
	//String eventSecString;
	
	static final int MAX_EVENTS=15;
	static int totalLineCount = 0;
	static int totInFileLines = 0;
	static File myTempOut;  // the inFileName we're reading from
	
	public static void main(String[] args) {
		myTempOut = new File(tempOutName1);  // the inFileName we're reading from
		myTempOut.delete();  // delete the inFileName we made last time
		myOutFile.delete();  // delete the inFileName we made last time
		newplMimic();
		fileSetup();
		mainPart();
	
		
		System.out.println("Finished");
		System.exit(0);
	}
 
	/**
	 * 
	 */
	protected static void mySleep(int timewait) {
		try {
			Thread.sleep(timewait * 1000);	//sleep is in milliseconds
		} catch (Exception e) {
			System.out.println(e);
		} 
	} 


	static void mainPart() {
		try {
			while (eventcount < MAX_EVENTS) {
				
			  while (totalLineCount < totInFileLines) {
				  
				System.out.println("--------     !!!   !!!    !! starting over main loop");
				List<String>  eventSectionLNs= new ArrayList<String>();
				
					// do this for the 10 lines of each section
				for (int minorSectionCt=0; minorSectionCt<10; minorSectionCt++) 
				{  					
					eventSectionLNs.add(getNextLine(eventcount, minorSectionCt));
					totalLineCount++;
					System.out.println("    CURRENT total Line count is: " +totalLineCount );
				}
				
				String summaryLine = eventSectionLNs.get(6);
				System.out.println("--@@@@____@@@__@@@   the 6th line is" + summaryLine);
				if ( !summaryLine.contains("Quarter")  )     // we are removing the quarters
				{
					System.out.println ("==========    ===== !!!!! FOUND a non quarter!");
					FileUtils.writeLines(myOutFile, eventSectionLNs, true);	
				}
			  }
				eventcount++;
			}  // while
			 
			FileUtils.writeStringToFile(myOutFile, "END:VCALENDAR"+LINE_FEED, true);	
	
		}    
		catch (IOException e) { e.printStackTrace();	}
		 
		mySleep(1);	
		
		//File cpath = new File("C:\\tmp");
		try {
		//FileUtils.copyFileToDirectory(myOutFile, cpath);
		} catch (Exception e) {
			System.out.println(e);
		} 
	}
	  
	
	static void newplMimic() {   
		try {
			String firstfront;
			String newback;
			String newComboStr;  
			String newfront = "DTEND:";
				
			File myTempIn = new File(InFileName);  // the inFileName we're reading from
			mySleep(1);
			//FileUtils.waitFor(myTempOut,2);
			List<String> newplList =  FileUtils.readLines(myTempIn);
			int newplListInt = newplList.size() + 10;
			System.out.println("total lines: " + newplListInt);
			// get ics header lines in 1st-first four header lines of ics inFileName

			// for each line in file:
			for (String mylinenow : newplList)  {
			if (mylinenow.length() > 0 )
			{
				StringUtils.chomp(mylinenow);
				if (mylinenow.contains("Moon goes void")) {
					mylinenow = "SUMMARY:Moon void of course";
				}
						
				FileUtils.writeStringToFile(myTempOut, mylinenow, true);	
				FileUtils.writeStringToFile(myTempOut,"\n", true);	 
		
				firstfront = mylinenow.substring(0,6);

				if ( firstfront.equals("DTSTAR") )   {  					
					newback = mylinenow.substring(8,23) + "Z";
					System.out.println("!!@@@@@  the line is  " + mylinenow);
					newComboStr = newfront + newback +"\n";  					
					System.out.println("DTEND: new line is " + newComboStr);
					FileUtils.writeStringToFile(myTempOut, newComboStr, true);	
				}
			  }	
			}
		}
		catch (IOException e)  { 
			e.printStackTrace();	 
		}
	}	

	static void fileSetup() {
		try {
			memoryList =  FileUtils.readLines(myTempOut);
			totInFileLines = memoryList.size() + 10;
			System.out.println("total lines: " + totInFileLines);
			// get ics header lines in 1st-first four header lines of ics inFileName
			for (int i = 0; i < 4; i++)	{
				FileUtils.writeStringToFile(myOutFile, memoryList.get(i)+LINE_FEED, true);			
			}
		}    catch (IOException e) { e.printStackTrace();	} 
	} 

	/**
	 * 
	 * @param loopLocationCount The loop counter
	 * @param minorLocation The minor location
	 * @return Returns newString
	 */
	static String getNextLine(int loopLocationCount, int minorLocation) {
		String newString = null;
		int currentline;
		                                     // the file is offset by 4 lines 
		try {
			currentline=(loopLocationCount * 9 ) +minorLocation + 4;
			newString = memoryList.get(currentline);	//exact line to get	
			System.out.println("GNL Newval: " + newString);
			
		}    catch (Exception e)  {}
		return newString;
	}

}



 