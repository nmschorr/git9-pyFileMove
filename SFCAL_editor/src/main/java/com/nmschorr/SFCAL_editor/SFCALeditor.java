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
	final static String LFEED = System.getProperty("line.separator");
	static int G_VERBOSE = 1;

// new method main: ----------------------------------------------------------------	
	public static void main(String[] args) {
	 	String inDIRNM = getindir();
 		String outDIRNM = getoutdir();
 		String[] infileLIST = getflist(inDIRNM);				
		//int arraysize = infileLIST.length;
		int myCount = 0;	
		String curINFILENM = "";

		while (myCount < infileLIST.length) {  
			curINFILENM =  infileLIST[myCount];			   
			out.println("----------------------------------- filename is: " + curINFILENM);
			out.println("--------------######---------------------LOOP# " + myCount);
						
			String dateNM = mkDateFileNM(curINFILENM, inDIRNM);
			String dateNMwDIR = outDIRNM + dateNM;			
			String tempNMwDIR = getTMPnmWdir(outDIRNM);
			
			out.println("-----------------------------------datefilename is: " + outDIRNM);
			 
			delFiles(dateNMwDIR);  // delete the FileName we made last time
			mySleep(1);
			generalStringFixing(tempNMwDIR, curINFILENM);
		
			sectionTask(tempNMwDIR, dateNMwDIR);
			
			out.println("------------------NEW filename w/dir is: "+dateNMwDIR + "\n" +" --End of Loop");					
			myCount++;		
		}			
		System.out.println("Finished");
	}


	// new method: ----------------------------------------------------------------	
	static String getindir() {  // 1 for name, 2 for file
		String iDIRNM = "E:\\sfcalfiles\\vds";
		return iDIRNM;
		}
	 
	// new method: ----------------------------------------------------------------	
	static String getoutdir() {  // 1 for name, 2 for file
		String oDIRNM =  "C:\\SFCALOUT\\vds";
		return oDIRNM;
		}
	 
	// new method: ----------------------------------------------------------------	
	static String getorignm(String a, String b) {  // 1 for name, 2 for file
		String iDIRNM = a + b;
		return iDIRNM;
		}
	 
	// new method: ----------------------------------------------------------------	
	static String getTMPnmWdir(String d) {  // 1 for name, 2 for file
		String sNAME = d + "\\tempfiles\\SFCALtmp" + System.currentTimeMillis() +".ics";
		return sNAME;
		}
	 
	// new method: ----------------------------------------------------------------	
	static String[] getflist(String dnm) {  // 1 for name, 2 for file
		File filesDir = new File(dnm);  //READ the list of files in sfcalfiles/vds dir
 		String[] arryOfInFiles = filesDir.list();	// create a list of names of those files	
 		return arryOfInFiles;
		}


	// new method: ----------------------------------------------------------------	
	static String mkDateFileNM(String oldname, String oldfiledir) {
		List<String> oldfilecontents = new ArrayList<String>();
		String newDateNM = "";
		
		try {
			oldfilecontents =  FileUtils.readLines(new File(oldfiledir));  //READ the list of files in sfcalfiles/vds dir
			String newDateString = oldfilecontents.get(5);
			String newDateStr = newDateString.substring(8, 16);
			newDateNM = oldname + "." + newDateStr + ".ics";
			verboseOut("new date string is: "+ newDateStr + "new LocalDateNmStr string is: "+ newDateNM);

		} catch (IOException e) { 
			e.printStackTrace();	
		}	// catch

		return newDateNM; 
	}
		
	
// new method: ----------------------------------------------------------------	
	public static void verboseOut(String theoutline) {
		if (G_VERBOSE==1) {
			out.println(theoutline);
		}
	}
	
	
// new method: ----------------------------------------------------------------	
	static void sectionTask(String infORIGstr, String dFILE_OUT) {   // this part was done by perl script
		List<String> inARRAY = new ArrayList<String>();
		List<String> outARRAY = new ArrayList<String>();
		List<String> tinySectionList;
		File infileORIG = new File(infORIGstr); 
		File dateFILE_OUT = new File(dFILE_OUT); 
		boolean shouldKEEP = false;
		int tinyCounter =0;
		int totLines=0;
		int locLineCount=4;  // start at 5th line
		inARRAY.clear();
		
		try {
			inARRAY =  FileUtils.readLines(infileORIG);
			totLines = inARRAY.size();

			System.out.println("!!! INSIDE sectiontask. lines: " + totLines +" " 
					+ dateFILE_OUT.getName());
			// get ics header lines in 1st-first four header lines of ics inFileName

			for (int i = 0; i < 4; i++)	{
				outARRAY.add(inARRAY.get(i));
			}

			while ( locLineCount < totLines )  
			{  // while there are still lines left in array // starting on 5th line, load
				tinyCounter = 0;
				tinySectionList = null;
				tinySectionList = new ArrayList<String>();

				// first load sections of 10x lines each into smaller arrarys
				// then check each section for voids etc  then correct

				while (tinyCounter < 10) {         //tiny while
					while (locLineCount < totLines ) {         //tiny while
						
					String theString = inARRAY.get(locLineCount);  //get one string
					tinySectionList.add(theString);
					locLineCount++;
					tinyCounter++;
					}  // tiny while
				}

				shouldKEEP = ckToKEEP(tinySectionList);	 

				if (shouldKEEP == true) {   // IF 	checkfortoss comes back TRUE, then write this section
					outARRAY.addAll( tinySectionList);
				}

			} //  // while locLineCount
			FileUtils.writeLines(dateFILE_OUT, outARRAY, true);	
			System.out.println("!!! INSIDE sectiontask. filename  - "+ dateFILE_OUT.getName());			
		}  // try  
		catch (IOException e) {  	e.printStackTrace();	 }	// catch
	}  // end

	
// new method: ----------------------------------------------------------------
	static boolean ckToKEEP(List<String> tinyList) {  // returns true to write
		String sl = tinyList.get(6);
		out.println("\n\n"+"               %%%%%%%%%%%%%%%%% starting over in checkForTossouts");
		out.println("The string is:  " + sl );

		if ( (sl.contains("SUMMARY")) && (sl.contains("Eclipse")) )
		{
			out.println("==========    ===== !!!!! reg method FOUND ECLIPSE!!! !!  !  ========== writing: "+ sl);		
			return true;  //keep
		}

		else if ( (sl.contains("void of")) || (sl.contains("SUMMARY:Full")) || 
				( sl.contains("SUMMARY:New Moon")) )     // we are removing the quarters
		{
			out.println("==========    ===== !!!!! reg method FOUND ! ========== writing: "+ sl);		
			return true; //keep
		}
		else  {
			return false;  // don't keep
		}
	} // method end
}  // class
		
		
 