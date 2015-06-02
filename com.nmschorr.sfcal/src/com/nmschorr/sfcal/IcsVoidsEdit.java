//  by Nancy Schorr, 2015
//  can't use an iterator because the file isn't being read that way

package com.nmschorr.sfcal;


import java.io.File;
import java.io.IOException;
import java .util.*;

import org.apache.commons.io.FileUtils;

public class IcsVoidsEdit {
	static List<String> memoryList = new ArrayList<String>();
	static int eventcount = 0;	
	final static int SECTION_LINES_EIGHT = 8;	
	static String SFCALfileName="E:\\SFCAL.ics";
	static String outFileName = "E:\\SFCAL4.txt";
	static File myOutFile = new File(outFileName);
	static File inFileName;
	final static String LINE_FEED = System.getProperty("line.separator");
	static List<String>  eventSectionLines= new ArrayList<String>();
	static final int MAX_EVENTS=17;
	static int totalLineCount = 0;
	static int totInFileLines = 0;
	static String outputLine = null;
		//String testStringNow;
	
	public static void main(String[] args) {
		fileSetup();
		
		try {
			while (eventcount < MAX_EVENTS) {
			  while (totalLineCount < totInFileLines) {
				System.out.println("starting over main loop");
				
					// do this for the 10 lines of each section
				for (int minorSectionCt=0; minorSectionCt<9; minorSectionCt++) {  					
					eventSectionLines.add(getNextLine(eventcount, minorSectionCt));
					totalLineCount++;
					System.out.println("Current total Line count is: " +totalLineCount );
				}
			
				if ( eventSectionLines.get(5).contains("void")  )  {
					System.out.println ("!!!!! found a LIST void!");
					    FileUtils.writeLines(myOutFile, eventSectionLines, true);	
				  }
				}
				eventcount++;
			}  // while
			 
			FileUtils.writeStringToFile(myOutFile, "END:VCALENDAR"+LINE_FEED, true);	
	
		}    
		catch (IOException e) { e.printStackTrace();	}
		 		
		System.out.println("Finished");
		System.exit(0);
	}
 

	static void fileSetup() {
		try {
			myOutFile.delete();  // delete the inFileName we made last time
			Thread.sleep(2000);
			inFileName = new File(SFCALfileName);  // the inFileName we're reading from
			memoryList =  FileUtils.readLines(inFileName);
			totInFileLines = memoryList.size() + 10;
			System.out.println("total lines: " + totInFileLines);
			// get ics header lines in 1st-first four header lines of ics inFileName
			for (int i = 0; i < 4; i++)	{
				FileUtils.writeStringToFile(myOutFile, memoryList.get(i)+LINE_FEED, true);			
			}
		}    catch (IOException e) { e.printStackTrace();	} 
			 catch (InterruptedException e) { e.printStackTrace(); }
	} 

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



 