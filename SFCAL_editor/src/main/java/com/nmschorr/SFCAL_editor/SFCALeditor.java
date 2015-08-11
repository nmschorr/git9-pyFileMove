/* SFCALeditor.java by Nancy Schorr, 2015
 *
 * This program removes extra calendar events from an ics calendar file.
 * 
 * The data for the calendar is calculated with Solar Fire 8 on Windows XP.  
 * It is run through the Perl script, then the Word macros.  
 * Ideally that would all take place in one Java executable -
 * but that's a project for the future.
 *
 * This little program is the final step in preparing the SFCAL.ics file 
 * for uploading in Google or another calendar system.
 * *
 * This class contains methods that remove extra calendar events from an ics calendar file.
 * @author Nancy M. Schorr
 * @version 1.1
 * 
 */

package com.nmschorr.SFCAL_editor;

import java.io.File;
import java.io.IOException;
import java.util.*;
import static java.lang.System.out;
import org.apache.commons.io.FileUtils;


public class SFCALeditor extends SFCALutil {
	static List<String> tmpARRAY = new ArrayList<String>();
	static List<String> dateStringFileList = new ArrayList<String>();
	static int eventcount = 0;		 
	static String MainIndirName = "E:\\sfcalfiles";
 	static String MainOutdirName = "C:\\SFCALOUT\\vds";
	static String IndirVoidsName = MainIndirName +"\\vds";
 	static String G_TEMPOUT_STRNAME;
 	static String G_ORIG_FILE_NAME_WDIR;
	final static String LFEED = System.getProperty("line.separator");
	static int totalLineCount = 0;
	static int currentCount = 0;
	static boolean checkToss = false;
	public static File filesDir;
	public static String G_DATE_FILE_NAME;
	public static File G_DATE_FILE;
	public static String G_ORIG_FILE_NAME;
	public static File G_ORIG_FILE;
	public static File G_TEMP_FILE;
	public static String G_DATE_FILE_NAME_DIR;
	static int G_VERBOSE=0;

// new method: ----------------------------------------------------------------	
	public static void main(String[] args) {
		File filesDir = new File(IndirVoidsName);  //READ the list of files in sfcalfiles/vds dir
		String[] arryOfInFiles = filesDir.list();	// create a list of names of those files	
		out.println("	NEW LIST: " + filesDir.list());
		int myCount=0;		 		
		int arraysize = arryOfInFiles.length;

		while (myCount < arraysize) {  
			String currentInfile= arryOfInFiles[myCount];
			   
			out.println("----------------------------------- filename is: " + currentInfile);
			out.println("--------------######---------------------LOOP# " + myCount);

			G_ORIG_FILE_NAME = 	currentInfile;
			G_ORIG_FILE_NAME_WDIR = IndirVoidsName +"\\" + G_ORIG_FILE_NAME;
			G_ORIG_FILE = new File(G_ORIG_FILE_NAME_WDIR);
				
			G_DATE_FILE_NAME = make_new_file_date_name(currentInfile);
			G_DATE_FILE_NAME_DIR = MainOutdirName + "\\" + G_DATE_FILE_NAME;
			G_DATE_FILE = new File(G_DATE_FILE_NAME_DIR);
			out.println("-----------------------------------datefilename is: " + G_DATE_FILE_NAME_DIR);
			 
			G_TEMPOUT_STRNAME = MainOutdirName + "\\tempfiles\\SFCALtmp" + System.currentTimeMillis() +".ics";
			G_TEMP_FILE = new File(G_TEMPOUT_STRNAME);
				
			delFiles(G_TEMP_FILE);  // delete the inFileName we made last time
			delFiles(G_DATE_FILE);  // delete the inFileName we made last time
			mySleep(1);
			generalStringFixing(G_TEMPOUT_STRNAME, G_ORIG_FILE);
			FileUtils.waitFor(G_DATE_FILE, 1);
		
			sectionTask(G_TEMP_FILE, G_DATE_FILE);
			FileUtils.waitFor(G_DATE_FILE, 1);
			
			G_ORIG_FILE = null;
			out.println("------------------NEW filename is: "+G_DATE_FILE);
			out.println("------------------End of Loop");
					
			myCount++;		
		}			
		FileUtils.waitFor(G_DATE_FILE, 1);
		System.out.println("Finished");
	}

// new method: ----------------------------------------------------------------	

	static String make_new_file_date_name(String ORIG_INFILE_STR) {
		String LocalDateNmStr = null;
		
		try {
			dateStringFileList =  FileUtils.readLines(G_ORIG_FILE);
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
	
// new method: ----------------------------------------------------------------	
	public static void verboseOut(String theoutline) {
		if (G_VERBOSE==1) {
			out.println(theoutline);
		}
	}
	
	
// new method: ----------------------------------------------------------------	
	static void sectionTask(File infileORIG, File dateFILE_OUT) {   // this part was done by perl script
		List<String> tinySectionList;
		int tinyCounter =0;
		int totLines=0;
		int locLineCount=4;  // start at 5th line
		int keepGoingSZ = 0;
		List<String> tmpARRAY2 = new ArrayList<String>();

		try {
			tmpARRAY =  FileUtils.readLines(infileORIG);
			totLines = tmpARRAY.size();
			keepGoingSZ = totLines-6;  // from trial and error with debugger

			System.out.println("!!! INSIDE sectiontask. total lines: " + totLines +" " 
					+ dateFILE_OUT.getName());
			// get ics header lines in 1st-first four header lines of ics inFileName

			for (int i = 0; i < 4; i++)	{
				//FileUtils.writeStringToFile(dateFILE_OUT, tmpARRAY.get(i)+LFEED, true);		
				tmpARRAY2.add(tmpARRAY.get(i));
			}

			while ( locLineCount < keepGoingSZ )  
			{  // while there are still lines left in array // starting on 5th line, load
				tinyCounter = 0;
				tinySectionList=null;
				tinySectionList = new ArrayList<String>();

				// first load sections of 10x lines each into smaller arrarys
				// then check each section for voids etc  then correct

				while (tinyCounter < 10) {         //tiny while
					String theString = tmpARRAY.get(locLineCount);  //get one string
					tinySectionList.add(theString);
					locLineCount++;
					tinyCounter++;
				}  // tiny while

				checkToss = checkForTossouts(tinySectionList);	 

				if (checkToss) {   // IF 	checkfortoss comes back TRUE, then write this section
					tmpARRAY2.addAll( tinySectionList);
					//FileUtils.writeLines(dateFILE_OUT, tinySectionList, true);	
				}

			} //  // while locLineCount
			FileUtils.writeLines(dateFILE_OUT, tmpARRAY2, false);	
			System.out.println("!!! INSIDE sectiontask. filename  - "+ dateFILE_OUT.getName());			
		}  // try  
		catch (IOException e) {  	e.printStackTrace();	 }	// catch
	}  // end

	
// new method: ----------------------------------------------------------------
	static boolean checkForTossouts(List<String> tinyList) {
		String sl = tinyList.get(6);
		out.println("\n\n");
		out.println("               %%%%%%%%%%%%%%%%% starting over in checkForTossouts");
		out.println("The string is:  " + sl );

		if ( (sl.contains("SUMMARY")) && (sl.contains("Eclipse")) )
		{
			out.println("==========    ===== !!!!! reg method FOUND ECLIPSE!!! !!  !");
			out.println("========== writing: "+ sl);		
			return true;
		}

		else if ( (sl.contains("void of")) || (sl.contains("SUMMARY:Full")) || 
				( sl.contains("SUMMARY:New Moon")) )     // we are removing the quarters
		{
			out.println("==========    ===== !!!!! reg method FOUND !");
			out.println("========== writing: "+ sl);		
			return true;
		}
		else  {
			return false;
		}
	} // method end
}  // class
		
		
 