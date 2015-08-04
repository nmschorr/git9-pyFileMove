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
 	static String tempOutNm = MainOutdirName + "\\SFCALtemp1.ics";
 	static String GLOBAL_ORIG_FILE_NAME_WDIR;
    static File tempFileOne = new File(tempOutNm);
	final static String LINE_FEED = System.getProperty("line.separator");
	static int totalLineCount = 0;
	static int totInFileLines = 0;
	static int currentCount = 0;
	static int locLineCount=4;  // start at 5th line
	static int newListSizeMinus;
	static boolean checkToss = false;
	public static File filesDir;
	
	public static String GLOBAL_DATE_FILE_NAME;
	static File GLOBAL_DATE_FILE;
	public static String GLOBAL_ORIG_FILE_NAME;
	public static File GLOBAL_ORIG_FILE;
	static File GLOBAL_TEMP_FILE;
	static String GLOBAL_TEMP_FILE_NAME;
	static String GLOBAL_DATE_FILE_NAME_DIR;
	 
	public static void main(String[] args) {

		File filesDir = new File(IndirVoidsName);  //READ the list of files in sfcalfiles/vds dir
		String[] myFileArray = filesDir.list();	// create a list of names of those files	

		//String[] myFileArray = filesDir.listFiles();	// create a list of names of those files	

		for (String sfcalOrigName : myFileArray) {  
			out.println("filename is: " + sfcalOrigName);

			GLOBAL_ORIG_FILE_NAME = 	sfcalOrigName;
			GLOBAL_ORIG_FILE_NAME_WDIR = IndirVoidsName +"\\" + GLOBAL_ORIG_FILE_NAME;
			GLOBAL_ORIG_FILE = new File(GLOBAL_ORIG_FILE_NAME_WDIR);
	
			
			GLOBAL_DATE_FILE_NAME = make_new_file_date_name(sfcalOrigName);
			GLOBAL_DATE_FILE_NAME_DIR = MainOutdirName + "\\" + GLOBAL_DATE_FILE_NAME;
			GLOBAL_DATE_FILE = new File(GLOBAL_DATE_FILE_NAME);

			GLOBAL_TEMP_FILE = new File(tempOutNm);
				
			delFiles(GLOBAL_DATE_FILE);  // delete the inFileName we made last time
			delFiles(GLOBAL_TEMP_FILE);  // delete the inFileName we made last time
			generalStringFixing(GLOBAL_ORIG_FILE);
			
			sectionTask(GLOBAL_DATE_FILE);
		}
			
			
			
		System.out.println("Finished");
		System.exit(0);
	}

	static String make_new_file_date_name(String ORIG_INFILE_STR) {
		String LocalDateNmStr = null;
		
		try {

			dateStringFileList =  FileUtils.readLines(GLOBAL_ORIG_FILE);
			String newDateString=dateStringFileList.get(5);
			String newDateStr = newDateString.substring(8, 16);
			System.out.println("new date string is: "+ newDateStr);
			LocalDateNmStr = ORIG_INFILE_STR + "." + newDateStr + ".ics";
			System.out.println("new LocalDateNmStr string is: "+ LocalDateNmStr);

		} catch (IOException e) { 
			e.printStackTrace();	
		}	// catch

		return LocalDateNmStr; 
	}
	
	
	
	
	static void sectionTask(File SECTION_FILE) {   // this part was done by perl script
		int tinyCounter =0;

		try {
			tempFileList =  FileUtils.readLines(tempFileOne);
			totInFileLines = tempFileList.size() + 10;
			
			System.out.println("total lines: " + totInFileLines);
			// get ics header lines in 1st-first four header lines of ics inFileName
			for (int i = 0; i < 4; i++)	{
				FileUtils.writeStringToFile(GLOBAL_DATE_FILE, tempFileList.get(i)+LINE_FEED, true);		
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
					FileUtils.writeLines(GLOBAL_DATE_FILE, tinySectionList, true);	
				}
					
				} //  // while locLineCount
			FileUtils.writeStringToFile(GLOBAL_DATE_FILE, "END:VCALENDAR"+LINE_FEED, true);	
			  
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
 