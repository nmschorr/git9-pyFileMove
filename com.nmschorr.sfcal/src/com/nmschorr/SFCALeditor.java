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
	
	public static void main(String[] args) {
		fileSetup();
		newplMimic();
		
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
				
				String theSixthLine = eventSectionLNs.get(6);
				System.out.println("--@@@@____@@@__@@@   the 6th line is" + theSixthLine);
				if ( !theSixthLine.contains("Quarter")  )     // we are removing the quarters
				{
					System.out.println ("==========    ===== !!!!! FOUND a LIST void!");
					FileUtils.writeLines(myOutFile, eventSectionLNs, true);	
				}
			  }
				eventcount++;
			}  // while
			 
			FileUtils.writeStringToFile(myOutFile, "END:VCALENDAR"+LINE_FEED, true);	
	
		}    
		catch (IOException e) { e.printStackTrace();	}
		 
		mySleep(1);	
		
		File cpath = new File("C:\\tmp");
		try {
		//FileUtils.copyFileToDirectory(myOutFile, cpath);
		} catch (Exception e) {
			System.out.println(e);
		} 
		
		
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

	static void newplMimic() {   
		try {
			File myTempIn = new File(InFileName);  // the inFileName we're reading from
			File myTempOut = new File(tempOutName1);  // the inFileName we're reading from
			myTempOut.delete();  // delete the inFileName we made last time
			List<String> newplList =  FileUtils.readLines(myTempIn);
			int newplListInt = newplList.size() + 10;
			System.out.println("total lines: " + newplListInt);
			// get ics header lines in 1st-first four header lines of ics inFileName

			// for each line in file:
			for (String mylinenow : newplList)  {
				String newfront = "DTEND:";
				String oldfront = mylinenow.substring(0, 8);
				String newback = mylinenow.substring(8,16);
				String newComboStr = newfront + newback +"\n";  

				FileUtils.writeStringToFile(myTempOut, mylinenow, true);	

				if ( oldfront.equals("DTSTART:") )   {  

					System.out.println("Replacing: new line is " + newComboStr);
					FileUtils.writeStringToFile(myTempOut, newComboStr, true);	
				}
			}	

		}
		catch (IOException e)  { 

			e.printStackTrace();	 
		}
	}	

	static void fileSetup() {
		try {
			myOutFile.delete();  // delete the inFileName we made last time
			Thread.sleep(4000);
			myInFile = new File(InFileName);  // the inFileName we're reading from
			  // FileUtils.waitFor(InFileName,2);
			memoryList =  FileUtils.readLines(myInFile);
			totInFileLines = memoryList.size() + 10;
			System.out.println("total lines: " + totInFileLines);
			// get ics header lines in 1st-first four header lines of ics inFileName
			for (int i = 0; i < 4; i++)	{
				FileUtils.writeStringToFile(myOutFile, memoryList.get(i)+LINE_FEED, true);			
			}
		}    catch (IOException e) { e.printStackTrace();	} 
			 catch (InterruptedException e) { e.printStackTrace(); }
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



 