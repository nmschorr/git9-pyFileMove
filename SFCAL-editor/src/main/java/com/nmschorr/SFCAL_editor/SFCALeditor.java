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

package com.nmschorr.SFCAL_editor;

import java.io.File;
import java.io.IOException;
import java.util.*;
import static java.lang.System.out;

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
	static List<String> dateStringFileList = new ArrayList<String>();
	static int eventcount = 0;		 
	static String MainIndirName = "E:\\sfcalfiles";
 	static String MainOutdirName = "C:\\tmp";
	static String IndirVoidsName = MainIndirName +"\\vds";
	static String OutFileNmTmp = MainOutdirName + "\\SFCALvds-";
	static String OutFileNmFinal=null;
 	static String tempOutNm = MainOutdirName + "\\SFCALtemp1.ics";
 	static String InfileNm;
	static File actualINFILE;
    static File tempFileOne = new File(tempOutNm);
 	static File myOutFileOne = null;
	final static String LINE_FEED = System.getProperty("line.separator");
	static final int MAX_EVENTS = 15;
	static int totalLineCount = 0;
	static int totInFileLines = 0;
	static int currentCount = 0;
	static int locLineCount=4;  // start at 5th line
	static int newListSizeMinus;
	static boolean checkToss = false;
	static File filesDir;
	static List<String> myfilelist;
	
	
	public static void main(String[] args) {
		
	 	
		
		File filesDir = new File(IndirVoidsName);
		File[] myFileArray = filesDir.listFiles();		
		for (File i : myFileArray) {
			out.println("filename is: " + i);
			makeNewFinalFileWDate(i.getName());
			generalStringFixing();
			sectionTask();
		}
			
			
			
		System.out.println("Finished");
		System.exit(0);
	}

	static void makeNewFinalFileWDate(String localFilename) {
		String LocalDateNm=null;
		
		try {
			InfileNm = IndirVoidsName +"\\" + localFilename;

			actualINFILE = new File(InfileNm);
			dateStringFileList =  FileUtils.readLines(actualINFILE);
			String newDateString=dateStringFileList.get(5);
			String newDate=newDateString.substring(8, 16);
			System.out.println("new date string is: "+ newDate);
			LocalDateNm = OutFileNmTmp  + newDate + ".ics";
			System.out.println("new LocalDateNm string is: "+ LocalDateNm);
			myOutFileOne = new File(LocalDateNm);
			delFiles(myOutFileOne);  // delete the inFileName we made last time

		} catch (IOException e) { 
			e.printStackTrace();	
		}	// catch
	}
	
	
	
	
	static void sectionTask() {   // this part was done by perl script
		int tinyCounter =0;

		try {
			tempFileList =  FileUtils.readLines(tempFileOne);
			totInFileLines = tempFileList.size() + 10;
			
			System.out.println("total lines: " + totInFileLines);
			// get ics header lines in 1st-first four header lines of ics inFileName
			for (int i = 0; i < 4; i++)	{
				FileUtils.writeStringToFile(myOutFileOne, tempFileList.get(i)+LINE_FEED, true);		
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
				locLineCount++;
				tinyCounter++;
				}  // tiny while
			  
				checkToss = checkForTossouts(tinySectionList);	 
				
				if (checkToss) {
					FileUtils.writeLines(myOutFileOne, tinySectionList, true);	
				}
					
				} //  // while locLineCount
			FileUtils.writeStringToFile(myOutFileOne, "END:VCALENDAR"+LINE_FEED, true);	
			  
	}  // try  
	catch (IOException e) { 
		e.printStackTrace();	
	}	// catch
	}

		static boolean checkForTossouts(List<String> tinyList) {
			String sumLine = tinyList.get(6);
			 if ( sumLine.contains("void of") || sumLine.contains("SUMMARY:Full") || 
					 sumLine.contains("SUMMARY:New Moon") )     // we are removing the quarters
					{
						//System.out.println ("==========    ===== !!!!! FOUND a non quarter!");
						//System.out.println ("========== writing: "+ sumLine);		
						return true;
					}
					else  {
						//System.out.println("not writing this line:  " + sumLine);
						return false;
					}
		}			
			 
		 

}  // class
 