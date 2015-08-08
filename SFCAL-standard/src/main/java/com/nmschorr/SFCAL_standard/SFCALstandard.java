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
	static String MainIndirName = "E:\\sfcalfiles";
 	static String MainOutdirName = "C:\\SFCALOUT\\standard";
	static String IndirVoidsName = MainIndirName +"\\standard";
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
	public 	static String G_DATE_FILE_NAME_DIR;
 	static int yes;
	
	static int G_VERBOSE=0;
	
	 
	public static void main(String[] args) {	
		File filesDir = new File(IndirVoidsName);  //READ the list of files in sfcalfiles/vds dir
		String[] arryOfInFiles = filesDir.list();	// create a list of names of those files	
		out.println("	NEW LIST: " + filesDir.list());
		int fileInDirCNT=0;
		
		//int arraysize = arryOfInFiles.length;
	int arraysize = 1;

		while (fileInDirCNT < arraysize) {  
			G_ORIG_FILE_NAME= arryOfInFiles[fileInDirCNT];
			   
			out.println("----------- starting over in main-----------LOOP# " + fileInDirCNT+1);
			out.println("-----------------------------------filename is: " + G_ORIG_FILE_NAME);

			G_ORIG_FILE_NAME_WDIR = IndirVoidsName +"\\" + G_ORIG_FILE_NAME;
			G_ORIG_FILE = new File(G_ORIG_FILE_NAME_WDIR);
				
			G_DATE_FILE_NAME = make_new_file_date_name(G_ORIG_FILE_NAME);
			G_DATE_FILE_NAME_DIR = MainOutdirName + "\\" + G_DATE_FILE_NAME;
			G_DATE_FILE = new File(G_DATE_FILE_NAME_DIR);
			out.println("-----------------------------------datefilename is: " + G_DATE_FILE_NAME_DIR);
			 
			G_TEMPOUT_STRNAME = MainOutdirName + "\\tempfiles\\SFCALtmp" + System.currentTimeMillis() +".ics";
			G_TEMP_FILE = new File(G_TEMPOUT_STRNAME);
				
			//delFiles(G_TEMP_FILE);  // delete the inFileName we made last time
			delFiles(G_DATE_FILE);  // delete the inFileName we made last time
			mySleep(1);
			generalStringFixing(G_TEMPOUT_STRNAME, G_ORIG_FILE_NAME_WDIR);
			
			//sectionTask(G_TEMP_FILE, G_DATE_FILE);
			//FileUtils.waitFor(G_DATE_FILE, 4);
			
			G_ORIG_FILE = null;
			out.println("--------End of Loop------------NEW filename is: "+G_DATE_FILE);		
			
			fileInDirCNT++;		
		}			
		FileUtils.waitFor(G_DATE_FILE, 2);
		System.out.println("Finished");
	}

// new method // --------------------------------------------------------------	 	

	static HashMap makeSpellhm() {
		HashMap <String, String> spellhm  =  new HashMap<String, String>();
		spellhm.put("Stabilise","Stabilize");
		spellhm.put("organised","organized");
		spellhm.put("vital","very vital");
		
		//.Text = "Stabilise","Stabilize"
		//.Text = "organised","organized"
		//.Text = "excelent","excellent"
		//.Text = "realise","realize"
		//.Text = "spiritualilty","spirituality"
		//.Text = "possibiities","possibilities"
		//.Text = "fantasise","fantasize"
		//"\n"
		return spellhm;
	}

// new method // --------------------------------------------------------------	 	
	static String gofixDES(String oldstrg) {
		System.out.println("just entered gofixDES. oldstrg is: " +oldstrg );
		String newstr = "empty";
		//StringBuffer newbuf = new StringBuffer(oldstrg);
		///HashMap <String, String>  hmSpell = makeSpellhm();
		//String tempcheck = "Stabilise";
		///String tempcheck = "\\n";
		CharSequence tempcheckCS = "\\n";
		String tempcheck = (String)tempcheckCS;
		//int locIndex=0;
		    
	    if (oldstrg.contains(tempcheckCS)) {
	    	System.out.println("!! Inside checker. found a misspelling : " + tempcheck);
			//String torep = hmSpell.get(tempcheck);
			String torep = " - ";
//	    	locIndex = newbuf.indexOf(tempcheck);
//	    	System.out.println("index is : " + locIndex);
//			newbuf.delete(locIndex, locIndex + 11); 
//			newbuf.insert(locIndex,torep);
//			System.out.println("new buf is: " + newbuf);
//			newstr =   newbuf.toString();
	    	newstr = oldstrg.replace(tempcheck, torep);
			System.out.println("replaced string with new string... now fixed: " + newstr);
			System.out.println("!!!! =======  !!!  value of newstr:  " + newstr+ "return this new value  " + newstr);
			return newstr;
	    }		
	    else return oldstrg;
	}
	
// new method // --------------------------------------------------------------	 	
	static String gofixhash(String oldstrg) {
		System.out.println("just entered gofixhash");
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
		String firstrep = hm.get(firstthird) + " ";
		System.out.println("found this in hash:  " + firstrep );
		start = 8;
		end = 17;
		newbuf.delete(start, end); 
		newbuf.insert(9,firstrep);
		System.out.println("new buf is: " + newbuf);
		newstr =   newbuf.toString();
		System.out.println("replaced string with new string... now fixed: " + newstr);
		System.out.println("value of newstr:  " + newstr+ "return this new value  " + newstr);
		return newstr;
	} // gofixhash

	
// new method // --------------------------------------------------------------	 	
	static void sectionTask(File theREADING_FROM_TMP_FILE, File theDATEFILE_WRTINGTO) {   // this part was done by perl script
		List<String> tinySectionList;
		int tinyCounter =0;
		totInFileLines=0;
		int realInlineCOUNT = 0;
		newListSizeMinus=0;
		locLineCount=4;  // start at 5th line
		int countMinusTen = realInlineCOUNT-10;
 
		try {
			tempFileList =  FileUtils.readLines(theREADING_FROM_TMP_FILE);
			totInFileLines = tempFileList.size() + 9;
			realInlineCOUNT = tempFileList.size();
			
	System.out.println("!!! INSIDE sectiontask. total lines: " + realInlineCOUNT +" " 
	+ theDATEFILE_WRTINGTO.getName());
			// get ics header lines in 1st-first four header lines of ics inFileName
			for (int i = 0; i < 4; i++)	{
				FileUtils.writeStringToFile(theDATEFILE_WRTINGTO, tempFileList.get(i)+LINE_FEED, true);		
			}
			newListSizeMinus = tempFileList.size()-1;
			
			while ( locLineCount < countMinusTen )  
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

// new method // --------------------------------------------------------------	 	
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

//	String[] plansArry = {"Sun", "Mon","Mer", "Ven", "Mar", "Jup", "Sat","Nep", "Ura", "Plu"};		
//	String[] signsLista = {"Aries", "Taurus","Gemini", "Cancer", "Leo", 
//				"Virgo", "Libra","Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"};		

 