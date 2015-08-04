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
 	static String GLOBAL_TEMPOUT_STRNAME;
 	static String GLOBAL_ORIG_FILE_NAME_WDIR;
	final static String LINE_FEED = System.getProperty("line.separator");
	static int totalLineCount = 0;
	static int totInFileLines;
	static int currentCount = 0;
	static int locLineCount;  // start at 5th line
	static int newListSizeMinus;
	static boolean checkToss = false;
	public static File filesDir;
	
	public static String GLOBAL_DATE_FILE_NAME;
	public 	static File GLOBAL_DATE_FILE;
	public static String GLOBAL_ORIG_FILE_NAME;
	public static File GLOBAL_ORIG_FILE;
	public 	static File GLOBAL_TEMP_FILE;
	public 	static String GLOBAL_DATE_FILE_NAME_DIR;
 	
	
	static int GLOBAL_VERBOSE=0;
	 
	public static void main(String[] args) {
		File filesDir = new File(IndirVoidsName);  //READ the list of files in sfcalfiles/vds dir
		String[] arryOfInFiles = filesDir.list();	// create a list of names of those files	
		out.println("	NEW LIST: " + filesDir.list());
		int myCount=0;
		
		int arraysize = arryOfInFiles.length;
	arraysize = 2;

		while (myCount < arraysize) {  
			String currentInfile= arryOfInFiles[myCount];
			   
			out.println("-----------------------------------");
			out.println("-----------------------------------filename is: " + currentInfile);
			out.println("--------------######---------------------LOOP# " + myCount);

			GLOBAL_ORIG_FILE_NAME = 	currentInfile;
			GLOBAL_ORIG_FILE_NAME_WDIR = IndirVoidsName +"\\" + GLOBAL_ORIG_FILE_NAME;
			GLOBAL_ORIG_FILE = new File(GLOBAL_ORIG_FILE_NAME_WDIR);
				
			GLOBAL_DATE_FILE_NAME = make_new_file_date_name(currentInfile);
			GLOBAL_DATE_FILE_NAME_DIR = MainOutdirName + "\\" + GLOBAL_DATE_FILE_NAME;
			GLOBAL_DATE_FILE = new File(GLOBAL_DATE_FILE_NAME_DIR);
			out.println("-----------------------------------datefilename is: " + GLOBAL_DATE_FILE_NAME_DIR);
			 
			GLOBAL_TEMPOUT_STRNAME = MainOutdirName + "\\tempfiles\\SFCALtmp" + System.currentTimeMillis() +".ics";
			GLOBAL_TEMP_FILE = new File(GLOBAL_TEMPOUT_STRNAME);
				
			delFiles(GLOBAL_TEMP_FILE);  // delete the inFileName we made last time
			delFiles(GLOBAL_DATE_FILE);  // delete the inFileName we made last time
			mySleep(2);
			generalStringFixing(GLOBAL_TEMPOUT_STRNAME, GLOBAL_ORIG_FILE);
			
			sectionTask(GLOBAL_TEMP_FILE, GLOBAL_DATE_FILE);
			FileUtils.waitFor(GLOBAL_DATE_FILE, 4);
			
			GLOBAL_ORIG_FILE = null;
			out.println("------------------NEW filename is: "+GLOBAL_DATE_FILE);
			out.println("------------------End of Loop");
		
			
			myCount++;		
		}
			
			
		FileUtils.waitFor(GLOBAL_DATE_FILE, 4);
			
		System.out.println("Finished");
		//System.exit(0);
	}

	static String make_new_file_date_name(String ORIG_INFILE_STR) {
		String LocalDateNmStr = null;
		
		try {

			dateStringFileList =  FileUtils.readLines(GLOBAL_ORIG_FILE);
			String newDateString=dateStringFileList.get(5);
			String newDateStr = newDateString.substring(8, 16);
			verboseOut("new date string is: "+ newDateStr);
			LocalDateNmStr = ORIG_INFILE_STR + "." + newDateStr + ".ics";
			verboseOut("new LocalDateNmStr string is: "+ LocalDateNmStr);

		} catch (IOException e) { 
			e.printStackTrace();	
		}	// catch

		return LocalDateNmStr; 
	}
	
	public static void verboseOut(String theoutline) {
		if (GLOBAL_VERBOSE==1) {
			out.println(theoutline);
		}
	}
	
	
	static void sectionTask(File theREADING_FROM_TMP_FILE, File theDATEFILE_WRTINGTO) {   // this part was done by perl script
		List<String> tinySectionList;
		int tinyCounter =0;
		totInFileLines=0;
		newListSizeMinus=0;
		locLineCount=4;  // start at 5th line

 
		try {
			tempFileList =  FileUtils.readLines(theREADING_FROM_TMP_FILE);
			totInFileLines = tempFileList.size() + 9;
			
	System.out.println("!!! INSIDE sectiontask. total lines: " + totInFileLines +" " 
	+ theDATEFILE_WRTINGTO.getName());
			// get ics header lines in 1st-first four header lines of ics inFileName
			for (int i = 0; i < 4; i++)	{
				FileUtils.writeStringToFile(theDATEFILE_WRTINGTO, tempFileList.get(i)+LINE_FEED, true);		
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
				
			tinySectionList=null;
			tinySectionList = new ArrayList<String>();
				
			  while (tinyCounter < 10) {         //tiny while
				String theString = tempFileList.get(locLineCount);  //get one string
						//StringUtils.chomp(theString);
				tinySectionList.add(theString);
				locLineCount++;
				tinyCounter++;
				}  // tiny while
			  
				checkToss = checkForTossouts(tinySectionList);	 
				
				if (checkToss) {
					FileUtils.writeLines(theDATEFILE_WRTINGTO, tinySectionList, true);	
					FileUtils.waitFor(theDATEFILE_WRTINGTO,2);
				}
					
				} //  // while locLineCount
			System.out.println("!!! INSIDE sectiontask. filename -------------------------"  
					+ theDATEFILE_WRTINGTO.getName());
			out.println("!!!###   name out outfile" + theDATEFILE_WRTINGTO);
			FileUtils.writeStringToFile(theDATEFILE_WRTINGTO, "END:VCALENDAR"+LINE_FEED, true);	
			mySleep(1);
			FileUtils.waitFor(theDATEFILE_WRTINGTO, 4);
			  
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
						//verboseOut ("==========    ===== !!!!! FOUND a non quarter!");
						//verboseOut ("========== writing: "+ sumLine);		
						return true;
					}
					else  {
						//verboseOut("not writing this line:  " + sumLine);
						return false;
					}
		}			
			 
		 

}  // class
 