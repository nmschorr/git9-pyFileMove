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

package com.nmschorr.SFCAL_standard;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.lang.System.out;

import org.apache.commons.io.FileUtils;
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.StringUtils;
import com.nmschorr.SFCAL_standard.SectionNew;

/**
 * This class contains methods that remove extra calendar events from an ics calendar file.
 * @author Nancy M. Schorr
 * @version 1.1
 * 
 */

public class SFCALstandard extends SFCALstandardutil {
	static List<String> tempFileList = new ArrayList<String>();
	static List<String> dateStringFileList = new ArrayList<String>();
	static int eventcount = 0;		 
	static String indirMAIN = "E:\\sfcalfiles\\standard";
	static String outDIR ="C:\\SFCALOUT\\standard";
	static String outDIRTMP = outDIR + "tempfiles";
	static String G_TEMPOUT_STRNAME;
 	static String G_ORIG_FILE_NAME_WDIR;
	final static String LINE_FEED = System.getProperty("line.separator");
	static int totalLineCount = 0;
	static int totInFileLines;
	static int currentCount = 0;
	static int locLineCount;  // start at 5th line
	static int newListSizeMinus;
	static boolean checkToss = false;
	public static File filesDir;
	
	public static String G_DATE_FILE_NAME;
	public 	static File G_DATE_FILE;
	public static String G_ORIG_FILE_NAME;
	public static File G_ORIG_FILE;
	public 	static File G_TEMP_FILE;
	public 	static File G_TEMP_FILE2;
	public 	static String G_DATE_FILE_NAME_DIR;
 	static int yes;
 	static String G_TEMPOUT_STRNAME2;
	
	static int G_VERBOSE=0;
	
	 
	public static void main(String[] args) {	
		File filesDir = new File(indirMAIN);  //READ the list of files in sfcalfiles/vds dir
		String[] arryOfInFiles = filesDir.list();	// create a list of names of those files	
		out.println("	NEW LIST: " + filesDir.list());
		int fileInDirCNT=0;
		
		//int arraysize = arryOfInFiles.length;
	int arraysize = 1;

		while (fileInDirCNT < arraysize) {  
			G_ORIG_FILE_NAME= arryOfInFiles[fileInDirCNT];
			   
			out.println("----------- starting over in main-----------LOOP# " + fileInDirCNT+1);
			out.println("-----------------------------------filename is: " + G_ORIG_FILE_NAME);

			G_ORIG_FILE_NAME_WDIR = indirMAIN +"\\" + G_ORIG_FILE_NAME;
			G_ORIG_FILE = new File(G_ORIG_FILE_NAME_WDIR);
				
			G_DATE_FILE_NAME = make_new_file_date_name(G_ORIG_FILE_NAME);
			G_DATE_FILE_NAME_DIR = outDIRTMP + "\\" + G_DATE_FILE_NAME;
			G_DATE_FILE = new File(G_DATE_FILE_NAME_DIR);
			delFiles(G_DATE_FILE);  // delete the inFileName we made last time
			out.println("-----------------------------------datefilename is: " + G_DATE_FILE_NAME_DIR);
			 
			G_TEMPOUT_STRNAME = outDIRTMP + "\\SFCALtmp" + System.currentTimeMillis() +".ics";
			G_TEMPOUT_STRNAME2 = outDIRTMP + "\\SFCALtmp" + System.currentTimeMillis() +"-2.ics";
			G_TEMP_FILE = new File(G_TEMPOUT_STRNAME);
			G_TEMP_FILE2 = new File(G_TEMPOUT_STRNAME2);
				
			mySleep(1);
			generalStringFixing( G_ORIG_FILE_NAME_WDIR, G_TEMPOUT_STRNAME);
			
			SectionNew.sectionTask(G_TEMP_FILE, G_DATE_FILE, G_TEMP_FILE2);
			//FileUtils.waitFor(G_DATE_FILE, 4);
			
			G_ORIG_FILE = null;
			out.println("--------End of Loop------------NEW filename is: "+G_DATE_FILE);		
			
			fileInDirCNT++;		
		}			
		FileUtils.waitFor(G_DATE_FILE, 2);
		System.out.println("Finished");
	}

	
// new method // --------------------------------------------------------------	 	
	static String fixDESCRIPTION_line( String  oldstrg) {
		String tString = oldstrg.replaceAll("%0A","");  // get rid of CRs  - \n
 
		oldstrg=tString;
		System.out.println("just entered gofixDES. oldstrg is: " +oldstrg );
		String newstr = "empty";
		String finSTR = "";
		HashMap<String, String> hMAP = makeSpellhm();
		CharSequence tempcheckCS = "\\n";
		String tempcheck = (String)tempcheckCS;
		String newTempStr="";
		
	    if (oldstrg.contains(tempcheckCS)) {  // for newline only
	    	System.out.println("!! Inside checker. found a misspelling : " + tempcheck);
			String torep = " - ";
	    	newstr = oldstrg.replace(tempcheck, torep);
	    	newTempStr = newstr.replace("Transiting ","" );
	    	newstr= newTempStr.replace("Conjunction","Conjunct"); 
	    	newTempStr= newstr.replace("Opposition","Opposite"); 
	    	newstr = newTempStr.replace("Entering","Enters" );
	    	newTempStr = newstr.replace("DESCRIPTION:The ","DESCRIPTION:" );
	    	newstr = newTempStr;
	    	
			System.out.println("replaced string with new string... now fixed: " + newstr);
			System.out.println("!!!! =======  !!!  value of newstr:  " + newstr+ "return this new value  " + newstr);
	 		finSTR = newstr;
	    }		
	    else if (oldstrg.startsWith(" ")) {   // spelling errors in extra lines of DESCRIPTION
	    	String oldVal;
	    	String newVal;
	    	for (String key : hMAP.keySet()) {
	    		oldVal= key;
	    		newVal= hMAP.get(key);
	    		out.println("\n\n" + "!!!----- value of hmap retrieval: " + oldVal + " " + newVal);
	    		if (oldstrg.contains((CharSequence)oldVal)) {
	    			newstr = oldstrg.replace(oldVal, newVal);
	    			System.out.println("SPELLING ERROR!!!! ----------replaced string with new string... now fixed: " + newstr);
	    			finSTR = newstr;
	    			break;
	    		}
	    		else finSTR = oldstrg;    	
	    	}  // for
	    }
	    else  { 
	 		finSTR = oldstrg; }
	    return finSTR;
	}
	
	
// new method // --------------------------------------------------------------	 	
	static String fixSUMMARYsigns(String oldstrg) {
		String tstring = oldstrg.replace("SUMMARY: ", "SUMMARY:");
		oldstrg=tstring;
		System.out.println("just entered fixSUMMARYsigns");
		String newstr = "empty";
		StringBuffer newbuf = new StringBuffer(oldstrg);
		String[] signsList = {"Ari", "Tau","Gem", "Can", "Leo", 
				"Vir", "Lib","Sco", "Sag", "Cap", "Aqu", "Pis"};		
		String tempv ="";
		boolean signmatch =false;
	 		 		
		Map<String, String> hm  =  makeNewhash();
		String firstthird = oldstrg.substring(14,17);
		String secondthird = oldstrg.substring(18,21);
		String lastthird = oldstrg.substring(22,25);
		System.out.println("first:  " + firstthird);
		System.out.println("2nd  :  " + secondthird);
		System.out.println("3rd  :  " + lastthird);
		
		for (int i=0; i<12; i++) {
		    tempv = signsList[i];
		    if (tempv.equals(lastthird)) {
		    	System.out.println("found a sign match in 3rd column");
		    	signmatch = true;
		    }
		}
//begin third column		    
		String thirdrep = hm.get(lastthird);
		System.out.println("found this in hash:  " + lastthird );
		int start = 22;
		int end = 25;
		newbuf.delete(start, end); 
		newbuf.insert(start,thirdrep);
		System.out.println("new buf is: " + newbuf);		
		
//begin second column
		String secondrep = hm.get(secondthird);
		System.out.println("value of signmatch:  " + signmatch );
		System.out.println("found this in hash:  " + secondrep );
		start = 18;
		end = 21;
		newbuf.delete(start, end); 
		if (signmatch) {   // change Conjunct a sign to Enters a sign
			newbuf.insert(start,"Enters");
		} else {
			newbuf.insert(start,secondrep);
		}
		System.out.println("new buf is: " + newbuf);
				
// begin first column		
		String firstrep = hm.get(firstthird);
		//String firstrep = hm.get(firstthird) + " ";
		System.out.println("found this in hash:  " + firstrep );
		start = 8;
		end = 17;
		newbuf.delete(start, end); 
		newbuf.insert(8,firstrep);
		System.out.println("new buf is: " + newbuf);
		newstr =   newbuf.toString();
		System.out.println("replaced string with new string... now fixed: " + newstr);
		System.out.println("value of newstr:  " + newstr+ "return this new value  " + newstr);
		return newstr;
	} // gofixhash
	
	
	
// new method // --------------------------------------------------------------	 	
	static HashMap<String, String> makeSpellhm() {
		HashMap <String, String> spellhm  =  new HashMap<String, String>();
		spellhm.put("Stabilise","Stabilize");
		spellhm.put("Socialise","Socialize");
		spellhm.put("Entering","Enters");
		spellhm.put("organised","organized");
		spellhm.put("excelent","excellent");
		spellhm.put("realise","realize");
		spellhm.put("spiritualilty","spirituality");
		spellhm.put("wilfull","willful");
		spellhm.put("possibiities","possibilities");
		spellhm.put("fantasise","fantasize");
		//spellhm.put("Transiting ","");
		//spellhm.put("Conjunction","Conjunct");
		return spellhm;
	}


	
// new method // --------------------------------------------------------------	 	
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
		public static void verboseOut(String theoutline) {
			if (G_VERBOSE==1) {
				out.println(theoutline);
			}
		}
	}  // class
 