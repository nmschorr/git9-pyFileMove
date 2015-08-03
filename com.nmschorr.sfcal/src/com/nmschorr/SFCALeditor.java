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
//import org.apache.commons.lang.StringUtils;

/**
 * This class contains methods that remove extra calendar events from an ics calendar file.
 * @author Nancy M. Schorr
 * @version 1.1
 * 
 */

public class SFCALeditor extends SFCALutil {
	static List<String> tempFileList = new ArrayList<String>();
	static int eventcount = 0;		 
	static String MainInDirNm1="E:\\sfcalfiles";
 	static String MainOutDirNm1="C:\\tmp";
	static String InDirVdsNm1=MainInDirNm1+"\\vds";
	static String InFileName1=InDirVdsNm1 +"\\SFCAL.ics";
	static String OutFileName1 = MainOutDirNm1 + "\\SFCALvds-out.ics";
 	static String tempOutName1 = MainOutDirNm1 + "\\SFCALtemp1.ics";
 	static File myInFile1 = new File(InFileName1);
    static File tempFile1 = new File(tempOutName1);
 	static File myOutFile1 = new File(OutFileName1);
	final static String LINE_FEED = System.getProperty("line.separator");
	static final int MAX_EVENTS=15;
	static int totalLineCount = 0;
	static int totInFileLines = 0;
	static int currentCount = 0;
	static int locLineCount=4;  // start at 5th line
	static int newListSizeMinus;
	
	public static void main(String[] args) {
		remQuarterMoons();
		sectionTask();
		System.out.println("Finished");
		System.exit(0);
	}
 
	static void sectionTask() {   // this part was done by perl script
		int tinyCounter =0;

		try {
			tempFileList =  FileUtils.readLines(tempFile1);
			totInFileLines = tempFileList.size() + 10;
			
			System.out.println("total lines: " + totInFileLines);
			// get ics header lines in 1st-first four header lines of ics inFileName
			for (int i = 0; i < 4; i++)	{
				FileUtils.writeStringToFile(myOutFile1, tempFileList.get(i)+LINE_FEED, true);		
			}
			newListSizeMinus = tempFileList.size()-1;
			
			while ( locLineCount < newListSizeMinus )  
			{  // while locLineCount
				//  while there are still lines left in array
			  // starting on 5th line, load
				tinyCounter = 0;
			
				// first load sections of 10x lines each into smaller arrarys
				// then check each section for voids etc
				// then correct
			
			List<String> tinySectionList = new ArrayList<String>();
				
			  while (tinyCounter < 10) {         //tiny while
				String theString = tempFileList.get(locLineCount);  //get one string
						//StringUtils.chomp(theString);
				tinySectionList.add(theString);
				tinyCounter++;
				}  // tiny while
			  
				locLineCount++;
						 
				FileUtils.writeLines(myOutFile1, tinySectionList, true);	

//					if ( sumLine.contains("void of") || sumLine.contains("SUMMARY:Full") || sumLine.contains("SUMMARY:New Moon") )     // we are removing the quarters
//					{
//						System.out.println ("==========    ===== !!!!! FOUND a non quarter!");
//						System.out.println ("========== writing: "+ sumLine);
//						FileUtils.writeLines(myOutFile1, eventSection, true);	
//					}
//					else  {
//						System.out.println("not writing this line:  " + sumLine);
//					}
					
					
				} //  // while locLineCount
			FileUtils.writeStringToFile(myOutFile1, "END:VCALENDAR"+LINE_FEED, true);	
			  
	}  // try  
	catch (IOException e) { 
		e.printStackTrace();	
		}	// catch
}  // class

	/* 
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
			newString = tempFileList.get(currentline);	//exact line to get	
			System.out.println("GNL Newval: " + newString);
			
		}    catch (Exception e)  {}
		return newString;
	}


}  // class
 