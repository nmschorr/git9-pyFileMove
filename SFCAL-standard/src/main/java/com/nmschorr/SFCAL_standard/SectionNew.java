package com.nmschorr.SFCAL_standard;

import static java.lang.System.out;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

//import static com.nmschorr.SFCAL_standard.*;


public class SectionNew {

	
	
	// new method: ----------------------------------------------------------------	
	static void sectionTask(String tFILEin, String finFILE, String tFILEtwo) {   // this part was done by perl script
		int totInFileLines=0;
		int totInfilesMinusNine=0;
		int locLineCount=4;  // start at 5th line
		String LINE_FEED = "\n";
		File tmpFILtwo = new File(tFILEtwo);
		File theREADING_FROM_TMP_FILE = new File(tFILEin);
		File finalFILE = new File(finFILE);
		
		try {
			List<String> tempFILE_ARRAY =  FileUtils.readLines(theREADING_FROM_TMP_FILE);
			totInFileLines = tempFILE_ARRAY.size();
			//int totInFileLinesBIG = totInFileLines + 9;

			System.out.println("!!! INSIDE sectiontask. total lines: " + totInFileLines +" " 
					+ tmpFILtwo.getName() + "and lincount is : " + locLineCount);
			// get ics header lines in 1st-first four header lines of ics inFileName

			for (int i = 0; i < 4; i++)	{
				FileUtils.writeStringToFile(tmpFILtwo, tempFILE_ARRAY.get(i)+LINE_FEED, true);		
			}
			totInfilesMinusNine = totInFileLines-9;  //  
			System.out.println("!!! totInfilesMinusNine: " + totInfilesMinusNine );

			while ( locLineCount < totInfilesMinusNine )  
			{  // while there are still lines left in array // starting on 5th line, load
			//	int tinyCounter = 0;
				ArrayList<String> tinySectionList = new ArrayList<String>();

				// first load sections of 10x lines each into smaller arrarys
				// then check each section for voids etc  then correct

				for (int tc=0; tc < 17; tc++) {         //tiny while
				//while (tinyCounter < 10) {         //tiny while
					String theString = tempFILE_ARRAY.get(locLineCount);  //get one string
					boolean checkforend = theString.startsWith("END:VEVENT", 0);
					if (checkforend) {
						tinySectionList.add(theString);
						locLineCount++;
						break;
					}
					if ( !checkforend) {
						tinySectionList.add(theString);
						locLineCount++;
						System.out.println(" lincount is -----------" + locLineCount); 
					}
					//tinyCounter++;
				}  // tiny while

				boolean checkToKeep = checkSUMMARYforToss(tinySectionList);	 // true is keep and write 

				if (checkToKeep) {   // IF 	checkfortoss comes back TRUE, then write this section
					FileUtils.writeLines(tmpFILtwo, tinySectionList, true);	
					FileUtils.waitFor(tmpFILtwo,2);
				}

			} //  // while locLineCount
			System.out.println("!!! INSIDE sectiontask. filename -------------------------"  
					+ tmpFILtwo.getName());
			out.println("!!!###   name out outfile" + tmpFILtwo);
			FileUtils.writeStringToFile(tmpFILtwo, "END:VCALENDAR"+LINE_FEED, true);	
	// new code		
			List<String> lastFILE_ARRAY =  FileUtils.readLines(tmpFILtwo);
		//	List<String> lastFILE_ARRAY2 =  FileUtils.readLines(tmpFILtwo);
		//	List<String> bigARRAY =  new ArrayList<String>() ;
			//List<String> lastARRAY =  new ArrayList<String>();
			int arSIZE = lastFILE_ARRAY.size();
			int newARRAYSIZE=arSIZE;
			String tline="";
			int curLINEct = 0;
			List<String> tinyARRY =  new ArrayList<String>() ;
			//boolean mygo = true;
			boolean yesDESC = false;
			String concatline1 ="";
			String concatline2 ="";
			String concatline3 ="";
			String concatline4 ="";
			String longstr ="";
			List<Integer> cntLONG = new ArrayList<Integer>();
			
			while ( curLINEct < newARRAYSIZE) {    // while we're still in the file
				tline="";
				longstr ="";				
				tline = lastFILE_ARRAY.get(curLINEct);
				yesDESC = false;
				System.out.println("curLINEct is --- "  + curLINEct);
				System.out.println("arSIZE is --- "  + arSIZE);
				//System.out.println("linect is --- "  + curLINEct);
			
				if (tline.contains("DESCRIPTION")) {
					yesDESC = true;
					cntLONG.clear();
					cntLONG.add(curLINEct);
					tinyARRY.add(tline);   // check the next lines for continuation of DESCRIPTION
					if (lastFILE_ARRAY.get(curLINEct+1).startsWith(" ")) {
						concatline1=lastFILE_ARRAY.get(curLINEct+1);
						tinyARRY.add(concatline1);
						cntLONG.add(curLINEct+1);

						if (lastFILE_ARRAY.get(curLINEct+2).startsWith(" ")) {
							concatline2=lastFILE_ARRAY.get(curLINEct+2);
							tinyARRY.add(concatline2);
							cntLONG.add(curLINEct+2);

							if (lastFILE_ARRAY.get(curLINEct+3).startsWith(" ")) {
								concatline3=lastFILE_ARRAY.get(curLINEct+3);
								tinyARRY.add(concatline3);
								cntLONG.add(curLINEct+3);

								if (lastFILE_ARRAY.get(curLINEct+4).startsWith(" ")) {
									concatline4=lastFILE_ARRAY.get(curLINEct+4);
									tinyARRY.add(concatline4);
									cntLONG.add(curLINEct+4);

								}  
							}  
						}  
					}
					
					longstr=longstr.concat(tline + concatline1 + concatline2 + concatline3 + concatline4);
					out.println("longstring is : " +longstr);

					concatline1 ="";
					concatline2 ="";
					concatline3 ="";
					concatline4 ="";
					tline="";
				}	// if DESCRIPTION

				int anInt = 0;
				
				if (yesDESC) {
					int numberRemoved = cntLONG.size();  // should be around 3
				//	int numberRemovedMinus = cntLONG.size()-2;  // should be around 3
					//curLINEct = curLINEct - numberRemoved;  // minus because we add a new DESC line
					
					anInt=cntLONG.get(0);
					for (int i=0; i < numberRemoved; i++) {
						anInt=cntLONG.get(0);
						lastFILE_ARRAY.remove(anInt);  // remove little strings
					}
					int wheretoaddline = cntLONG.get(0);
					lastFILE_ARRAY.add(wheretoaddline, longstr);  // add new long string back
				}
				tinyARRY.clear();
				cntLONG.clear();
				newARRAYSIZE=lastFILE_ARRAY.size();
				out.println("new array size is: " + newARRAYSIZE);
				
				curLINEct = curLINEct + 1;  //move the line counter up to the next group
		}  // while
		 
			FileUtils.writeLines(finalFILE, lastFILE_ARRAY, true);
			
			SFCALstandardutil.mySleep(2);
			FileUtils.waitFor(finalFILE, 1);
		}  // try  
		catch (IOException e) {  	e.printStackTrace();	 }	// catch
	}  // end of method


	// new method: ----------------------------------------------------------------
		static boolean checkSUMMARYforToss(List<String> tinyList) {
			String sl = tinyList.get(6);  // checking the 6th line : SUMMARY
			out.println("\n\n" +"               %%%%%%%%%%%%%%%%% starting over in checkForTossouts");
			out.println("The string is:  " + sl );

			if ( (sl.contains("SUMMARY")) && (sl.contains("Eclipse")) )
			{
				out.println("==========    ===== !!!!! reg method FOUND ECLIPSE!!! !!  !");
				out.println("========== tossing: "+ sl);		
				return false;  // toss
			}
			else if ( (sl.contains("SUMMARY")) && (sl.contains("Moon enters")) )
			{
				out.println("==========    ===== !!!!! reg method FOUND ECLIPSE!!! !!  !");
				out.println("========== tossing: "+ sl);		
				return false;  // toss
			}
			else if ( (sl.contains("SUMMARY")) && (sl.contains("Quarter")) )
			{
				out.println("==========    ===== !!!!! reg method FOUND ECLIPSE!!! !!  !");
				out.println("========== tossing: "+ sl);		
				return false;  // toss
			}

			else if ( (sl.contains("void of")) || (sl.contains("SUMMARY:Full")) || 
					( sl.contains("SUMMARY:New Moon"))   )     // we are removing the quarters
			{
				out.println("==========    ===== !!!!! reg method FOUND !");
				out.println("========== tossing: "+ sl);		
				return false;   //toss
			}
			else  {
				//verboseOut("writing this line:  " + sumLine);
				return true;
			}
		} // method end
	}  // class
			

 
