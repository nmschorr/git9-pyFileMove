[1mdiff --git a/NfiToAaf/.project b/NfiToAaf/.project[m
[1mnew file mode 100644[m
[1mindex 0000000..c2c3455[m
[1m--- /dev/null[m
[1m+++ b/NfiToAaf/.project[m
[36m@@ -0,0 +1,17 @@[m
[32m+[m[32m<?xml version="1.0" encoding="UTF-8"?>[m
[32m+[m[32m<projectDescription>[m
[32m+[m	[32m<name>nfiToaaf</name>[m
[32m+[m	[32m<comment></comment>[m
[32m+[m	[32m<projects>[m
[32m+[m	[32m</projects>[m
[32m+[m	[32m<buildSpec>[m
[32m+[m		[32m<buildCommand>[m
[32m+[m			[32m<name>org.python.pydev.PyDevBuilder</name>[m
[32m+[m			[32m<arguments>[m
[32m+[m			[32m</arguments>[m
[32m+[m		[32m</buildCommand>[m
[32m+[m	[32m</buildSpec>[m
[32m+[m	[32m<natures>[m
[32m+[m		[32m<nature>org.python.pydev.pythonNature</nature>[m
[32m+[m	[32m</natures>[m
[32m+[m[32m</projectDescription>[m
[1mdiff --git a/NfiToAaf/.pydevproject b/NfiToAaf/.pydevproject[m
[1mnew file mode 100644[m
[1mindex 0000000..037bd25[m
[1m--- /dev/null[m
[1m+++ b/NfiToAaf/.pydevproject[m
[36m@@ -0,0 +1,8 @@[m
[32m+[m[32m<?xml version="1.0" encoding="UTF-8" standalone="no"?>[m
[32m+[m[32m<?eclipse-pydev version="1.0"?><pydev_project>[m
[32m+[m[32m<pydev_pathproperty name="org.python.pydev.PROJECT_SOURCE_PATH">[m
[32m+[m[32m<path>/${PROJECT_DIR_NAME}</path>[m
[32m+[m[32m</pydev_pathproperty>[m
[32m+[m[32m<pydev_property name="org.python.pydev.PYTHON_PROJECT_VERSION">python 2.7</pydev_property>[m
[32m+[m[32m<pydev_property name="org.python.pydev.PYTHON_PROJECT_INTERPRETER">Default</pydev_property>[m
[32m+[m[32m</pydev_project>[m
[1mdiff --git a/NfiToAaf/aasample.nfi b/NfiToAaf/aasample.nfi[m
[1mdeleted file mode 100644[m
[1mindex e69de29..0000000[m
[1mdiff --git a/NfiToAaf/astfiles.py b/NfiToAaf/astfiles.py[m
[1mdeleted file mode 100644[m
[1mindex 2e365a8..0000000[m
[1m--- a/NfiToAaf/astfiles.py[m
[1m+++ /dev/null[m
[36m@@ -1,275 +0,0 @@[m
[31m-'''[m
[31m-Created on Nov 22, 2015, May 25, 2016[m
[31m-@author: Nancy Schorr[m
[31m-'''[m
[31m-#  translate the old "nfi"-files, that I have (they are basically text-files with a certain structure, as far as I understand... one file consists of a lot of astrological dates, each in one line... each line would be one aaf-file) into aaf data.[m
[31m-## old is nfi, new is aaf[m
[31m-## to do py2exe : use cygwin. cd to prj dir. run python command from there. it's in the path.[m
[31m-[m
[31m-[m
[31m-[m
[31m-import pymsgbox[m
[31m-pymsgbox.confirm("Put nfi files in C:\\astin - new aaf files will be in C:\\astout", 'Welcome to Astrofiles',["Start Converting", 'Cancel'])[m
[31m-#response = pymsgbox.prompt('What is your name?')[m
[31m-[m
[31m-[m
[31m-#"Translate nfi files into aaf files\n")[m
[31m-#"Put nfi files in C:\\astin - new aaf files will be in C:\\astout")[m
[31m-#"Press the return key to start the program. Go to C:\astout when the program ends to find your files")[m
[31m-[m
[31m- [m
[31m-[m
[31m-PYTHONDONTWRITEBYTECODE = True   #prevents bytecode .pyc files from being created[m
[31m-import sys[m
[31m-import datetime[m
[31m-import astrs[m
[31m-import os[m
[31m-#import re[m
[31m-[m
[31m-if __name__ == '__main__':   pass[m
[31m-[m
[31m-############### constants:[m
[31m-DIR_SEP ='\\'[m
[31m-NLINE = "\n"[m
[31m-cSTAR = "*"[m
[31m-cCOMMA = ","[m
[31m-cInputDIR ='C:\\astin'      ## must be there[m
[31m-cOutDIR =  'C:\\astout'      ## where you are sorting to, created if not there[m
[31m-locInFileList = [][m
[31m-[m
[31m-#-----------------globals:[m
[31m-MAIN_DATE_STR = datetime.datetime.now().strftime('%Y%m%d%H%M%S')[m
[31m-global_INDIR_DONE = cInputDIR +  DIR_SEP + 'done' + str(MAIN_DATE_STR)[m
[31m-global_OUTDIR =  cOutDIR +  DIR_SEP + 'done' + str(MAIN_DATE_STR)[m
[31m-global target_FILE[m
[31m-[m
[31m-############### end setup vars[m
[31m-#A93:*,Axel,*,27.7.1968,15:31:00,Geseke,D[m
[31m-#B93:2440065.10500,51N38:00,008E31:00,1hE00:00,*[m
[31m-[m
[31m-[m
[31m-#----------------------------------------------------[m
[31m-def splitNfiFile(inFile):[m
[31m-    adbcFinal = 'AD'[m
[31m-    AAF_END = '.aaf'[m
[31m-    infile_firstpart = inFile[:-4][m
[31m-    FINAL_OUTFILE= global_OUTDIR + DIR_SEP + infile_firstpart + AAF_END[m
[31m-    target_FILE2 = open(FINAL_OUTFILE, 'w')  ## target_FILE is OPENED[m
[31m-    target_FILE2.close()[m
[31m-    [m
[31m-    theInfile = cInputDIR +  DIR_SEP + inFile[m
[31m-    print "now the infile is: " + theInfile[m
[31m-   [m
[31m-    #target_FILE = open(FINAL_OUTFILE, 'w')  ## target_FILE is OPENED[m
[31m-    #fn = target_FILE.fileno()[m
[31m- [m
[31m-    print "----------###!!! just got passed: " + inFile [m
[31m-    print "-------------working on new file: " + FINAL_OUTFILE[m
[31m-       [m
[31m-    print NLINE + "datestring: " + MAIN_DATE_STR + "  cInputDIR : " + cInputDIR[m
[31m-    print "global_OUTDIR: " + global_OUTDIR +" global_INDIR_DONE: " + global_INDIR_DONE + " FINAL_OUTFILE: " + FINAL_OUTFILE + NLINE[m
[31m-   [m
[31m-    #print "file number of newfile is: " + fn[m
[31m-                [m
[31m-                [m
[31m-                [m
[31m-    fileexists = os.path.exists(theInfile)[m
[31m-    if not fileexists :[m
[31m-        pymsgbox.alert("ERROR!! No aaf files in C:\\astin", 'Astrofiles Aborted')[m
[31m-    [m
[31m-    theLines = open( theInfile ).read().splitlines()[m
[31m-    for myLine in theLines :[m
[31m-        endName = 0[m
[31m-        #endNameComma = '#'[m
[31m-        hasComma = False[m
[31m-        hasAtsign = False[m
[31m-        atsignLoc = 0[m
[31m-        hasFirstName = False[m
[31m-        firname = "*"[m
[31m-        fnameEnd = 0[m
[31m-        firnameStart = 0[m
[31m-        #print hasComma + " " + hasAtsign[m
[31m-        lnameEnd = 0[m
[31m-  [m
[31m-        if myLine.startswith("$"):   ### start next record[m
[31m-            if "," in myLine :    ### has first and last name[m
[31m-                hasComma = True;[m
[31m-                commaLoc = myLine.find(',')  ## , is at end of last name[m
[31m-                lnameEnd = commaLoc  ### most simple case[m
[31m-                hasFirstName = True[m
[31m-[m
[31m-            if '@' in myLine :    ### has first and last name[m
[31m-                hasAtsign = True;[m
[31m-                atsignLoc = myLine.find('@')  ## @ is at end of full name[m
[31m-                [m
[31m-                if (( hasComma == False ) and (hasAtsign == True )) :[m
[31m-                    lnameEnd = atsignLoc    ### use the @ if there is not comma for lname end[m
[31m-                                            ## has no firstname[m
[31m-               [m
[31m-            if ((hasComma == False) and (hasAtsign == False)) :  # no comma, no @ [m
[31m-                lnameEnd = len(myLine)    ## has no firstname[m
[31m-            [m
[31m-            if ((hasComma == True) and (hasAtsign == False)) :    # has comma no @[m
[31m-                fnameEnd = len(myLine)     ## firstname ends at end of line[m
[31m-                [m
[31m-            if ((hasComma == True) and (hasAtsign == True)) :     # has comma and @[m
[31m-                fnameEnd = atsignLoc          ## firstname ends at @[m
[31m-            [m
[31m-            if (hasFirstName is True) :[m
[31m-                endName = fnameEnd[m
[31m-            else :[m
[31m-                endName = lnameEnd[m
[31m-            print "lnameEnd= " + str(lnameEnd) + " ---------- !!!! inside qualifying if loop "[m
[31m-            adbcFinal =  astrs.fixadbc(myLine[1:2])[m
[31m-            lname =myLine[40:lnameEnd][m
[31m-            if (hasFirstName is True) :[m
[31m-                firnameStart = lnameEnd + 1[m
[31m-                firname = myLine[firnameStart:fnameEnd][m
[31m-            print "firname = " + firname[m
[31m-[m
[31m-            yr = myLine[2:6][m
[31m-            mon =  myLine[6:8][m
[31m-            day =  myLine[8:10][m
[31m-            tmraw =  myLine[10:16][m
[31m-            tm = astrs.formatTime(tmraw)[m
[31m-            zn_offset_orig = myLine[17:22][m
[31m-            zn_offset = astrs.fixzn_offset(zn_offset_orig)[m
[31m-            lat_orig =  myLine[22:27][m
[31m-            lat = astrs.fixlat(lat_orig)[m
[31m-            longt_orig = myLine[27:33][m
[31m-            gender = myLine[33:34][m
[31m-            longt = astrs.fixlongt(longt_orig)[m
[31m-            country="*"[m
[31m-            city =myLine[endName+1:][m
[31m-            fullDate=day + "." + mon + "." +yr[m
[31m-            if len(city) < 1:[m
[31m-                city = "*"[m
[31m-            if ((gender != 'M') and  (gender != 'F') ):[m
[31m-                gender = '*'[m
[31m-            if city == 'Bonn' :[m
[31m-                country = "D"[m
[31m-            writeOutFileContent(FINAL_OUTFILE,firname,lname,fullDate,tm,zn_offset,lat,longt,gender,city,country)[m
[31m-       [m
[31m-        ##### special case for extra comment lines:[m
[31m-        if not myLine.startswith("$"):  ## should work for 2 extra comment lines at least[m
[31m-            lineLength = len(myLine)[m
[31m-            print "---------------------------------######################---------------!!!!!!here is empty comments extra myLine:"[m
[31m-            print myLine + "\n" +  " lineLength +  is: " + str(lineLength)[m
[31m-            if lineLength > 0:[m
[31m-                target_FILE2 = open(FINAL_OUTFILE, 'a')  ## target_FILE is OPENED[m
[31m-               [m
[31m-                target_FILE2.write(myLine)  ## no return written, just chars[m
[31m-                target_FILE2.write(" ")   ## so the words in the comments are separated when from 2 lines       [m
[31m-                print "-----------------------------!@@@@@@@@@@@@@@@@ here now!"[m
[31m-                print lname+" Month="+mon+" Day="+day+" "+yr+" "+adbcFinal+" Time="+tm+" Zone="+zn_offset+" Long="+longt+" Lat="+lat[m
[31m-                target_FILE2.close()[m
[31m-             [m
[31m-#------------------- writeOutFileContent() ----------------------------------[m
[31m-def writeOutFileContent(FINAL_OUTFILE,fname,lname,tdate,tm,tzone,tlat,tlong,tGender,city,country):[m
[31m-    target_FILE = open(FINAL_OUTFILE, 'a')  ## target_FILE is OPENED[m
[31m-    print NLINE + "inside writeOutFileContent:" + " lname: " + lname[m
[31m-[m
[31m-    julianDay = "*"  [m
[31m-    whiteSpaceLoc = -1[m
[31m-    print whiteSpaceLoc[m
[31m-[m
[31m-    try:[m
[31m-        whiteSpaceLoc = lname.index(' ')[m
[31m-    except ValueError:[m
[31m-        print "Could not find space in string"[m
[31m-        whiteSpaceLoc = -2[m
[31m-    except:[m
[31m-        print "Unexpected error:", sys.exc_info()[0][m
[31m-        raise[m
[31m-    if whiteSpaceLoc > 0 :[m
[31m-        firstname, lastname  = lname.split(' ',1)[m
[31m-        print "---------------!!! new firstname is: " + firstname[m
[31m-        print "---------------!!! new lastname is: " + lastname[m
[31m-        fname = firstname[m
[31m-        lname = lastname[m
[31m-[m
[31m-    print "-------------------wholename is: " + lname + "."[m
[31m-    print "-------------------------------------- whiteSpaceLoc = " + str(whiteSpaceLoc)[m
[31m-    [m
[31m-    target_FILE.write(NLINE)[m
[31m-    target_FILE.write("#A93:")    [m
[31m-    target_FILE.write(lname)[m
[31m-    target_FILE.write(cCOMMA)[m
[31m-    [m
[31m-    target_FILE.write(fname)[m
[31m-    target_FILE.write(cCOMMA)[m
[31m-    [m
[31m-    target_FILE.write(tGender)[m
[31m-    target_FILE.write(cCOMMA)[m
[31m-    [m
[31m-    target_FILE.write(tdate)[m
[31m-    target_FILE.write(cCOMMA)[m
[31m-    [m
[31m-    target_FILE.write(tm)[m
[31m-    target_FILE.write(cCOMMA)[m
[31m-[m
[31m-    target_FILE.write(city)[m
[31m-    target_FILE.write(cCOMMA)[m
[31m-[m
[31m-    target_FILE.write(country)[m
[31m-    target_FILE.write(NLINE)[m
[31m-[m
[31m-    target_FILE.write("#B93:")[m
[31m-    target_FILE.write(julianDay)[m
[31m-    target_FILE.write(cCOMMA)[m
[31m-    [m
[31m-    target_FILE.write(tlat)[m
[31m-    target_FILE.write(cCOMMA)[m
[31m-    [m
[31m-    target_FILE.write(tlong)[m
[31m-    target_FILE.write(cCOMMA)[m
[31m-    [m
[31m-    target_FILE.write(tzone)[m
[31m-    target_FILE.write(cCOMMA)[m
[31m-    [m
[31m-    target_FILE.write("0")[m
[31m-    [m
[31m-    target_FILE.write(NLINE)   [m
[31m-    target_FILE.write("#COM:")[m
[31m-    #target_FILE.write(comments)[m
[31m-    #target_FILE.write(" ")  ## add a space so we can add onto the comments if needed[m
[31m-    whiteSpaceLoc = -1[m
[31m-    target_FILE.close()[m
[31m-   [m
[31m-#--------------------------------------------------------------[m
[31m-[m
[31m-[m
[31m-[m
[31m-indirExists2 = os.path.exists(cInputDIR)[m
[31m-    [m
[31m-if indirExists2 is not True :[m
[31m-    pymsgbox.alert("ERROR!! Directory C:\\astin not there!", "Astrofiles Aborted")[m
[31m-    quit()[m
[31m-[m
[31m-[m
[31m-[m
[31m-astrs.printFrontMatter()[m
[31m-astrs.chkFixDirs(global_OUTDIR)  ## this is where alerts are[m
[31m-[m
[31m-locInFileList = astrs.getInFilePathList()[m
[31m-print "local file list: " + str(locInFileList)[m
[31m-[m
[31m-mylength = locInFileList[m
[31m-[m
[31m-for f in locInFileList:[m
[31m-    splitNfiFile(f)[m
[31m-    #target_FILE.close()[m
[31m-pymsgbox.alert("If program worked, new aaf files will be in C:\\astout", 'Astrofiles Finished')[m
[31m-[m
[31m-print NLINE + "end of program"[m
[31m-[m
[31m-[m
[31m-[m
[31m-[m
[31m-[m
[31m-[m
[31m-[m
[31m-[m
[31m-[m
[31m-[m
[31m-[m
[1mdiff --git a/NfiToAaf/more/aasample.nfi b/NfiToAaf/more/aasample.nfi[m
[1mnew file mode 100644[m
[1mindex 0000000..e69de29[m
[1mdiff --git a/NfiToAaf/more/sample.nfi b/NfiToAaf/more/sample.nfi[m
[1mnew file mode 100644[m
[1mindex 0000000..e69de29[m
[1mdiff --git a/NfiToAaf/nfitoaaf.py b/NfiToAaf/nfitoaaf.py[m
[1mindex b731e4e..533246d 100644[m
[1m--- a/NfiToAaf/nfitoaaf.py[m
[1m+++ b/NfiToAaf/nfitoaaf.py[m
[36m@@ -1,201 +1,265 @@[m
 '''[m
[31m-Created on Jan 31, 2016[m
[31m-[m
[31m-@author: user[m
[32m+[m[32mCreated on Nov 22, 2015, May 25, 2016[m
[32m+[m[32m@author: Nancy Schorr[m
 '''[m
[31m-[m
[31m-#Created on Nov 22, 2015[m
[31m-#@author: Nancy Schorr[m
[31m-[m
 #  translate the old "nfi"-files, that I have (they are basically text-files with a certain structure, as far as I understand... one file consists of a lot of astrological dates, each in one line... each line would be one aaf-file) into aaf data.[m
 ## old is nfi, new is aaf[m
[32m+[m[32m## to do py2exe : use cygwin. cd to prj dir. run python command from there. it's in the path.[m
[32m+[m
[32m+[m
[32m+[m
[32m+[m[32mimport pymsgbox[m
[32m+[m[32mpymsgbox.confirm("Put nfi files in C:\\astin - new aaf files will be in C:\\astout", 'Welcome to Astrofiles',["Start Converting", 'Cancel'])[m
[32m+[m[32m#response = pymsgbox.prompt('What is your name?')[m
[32m+[m
[32m+[m
[32m+[m[32m#"Translate nfi files into aaf files\n")[m
[32m+[m[32m#"Put nfi files in C:\\astin - new aaf files will be in C:\\astout")[m
[32m+[m[32m#"Press the return key to start the program. Go to C:\astout when the program ends to find your files")[m
[32m+[m
[32m+[m[41m [m
 [m
 PYTHONDONTWRITEBYTECODE = True   #prevents bytecode .pyc files from being created[m
[31m-import glob[m
[31m-import os[m
[31m-#import sys[m
[31m-#import re[m
[31m-#import shutil[m
[32m+[m[32mimport sys[m
 import datetime[m
[31m-import pymsgbox[m
[32m+[m[32mimport nfitoastmods[m
[32m+[m[32mimport os[m
[32m+[m[32m#import re[m
 [m
 if __name__ == '__main__':   pass[m
 [m
[31m-__all__ = ["printFrontMatter"][m
[32m+[m[32m############### constants:[m
[32m+[m[32mDIR_SEP ='\\'[m
[32m+[m[32mNLINE = "\n"[m
[32m+[m[32mcSTAR = "*"[m
[32m+[m[32mcCOMMA = ","[m
[32m+[m[32mcInputDIR ='C:\\astin'      ## must be there[m
[32m+[m[32mcOutDIR =  'C:\\astout'      ## where you are sorting to, created if not there[m
[32m+[m[32mlocInFileList = [][m
[32m+[m
[32m+[m[32m#-----------------globals:[m
[32m+[m[32mMAIN_DATE_STR = datetime.datetime.now().strftime('%Y%m%d%H%M%S')[m
[32m+[m[32mglobal_INDIR_DONE = cInputDIR +  DIR_SEP + 'done' + str(MAIN_DATE_STR)[m
[32m+[m[32mglobal_OUTDIR =  cOutDIR +  DIR_SEP + 'done' + str(MAIN_DATE_STR)[m
[32m+[m[32mglobal target_FILE[m
[32m+[m
[32m+[m[32m############### end setup vars[m
[32m+[m[32m#A93:*,Axel,*,27.7.1968,15:31:00,Geseke,D[m
[32m+[m[32m#B93:2440065.10500,51N38:00,008E31:00,1hE00:00,*[m
 [m
 [m
[31m-[m
[31m-#--------------constants--------------------------------------[m
[31m-ccDirSEP ='\\'[m
[31m-ccNLINE = "\n"[m
[31m-ccSTAR = "*"[m
[31m-ccInputDIR ='C:\\astin'      ## must be there[m
[31m-ccOutDIR =  'C:\\astout'      ## where you are sorting to, created if not there[m
[31m-ccAaf_END = '.aaf'[m
[31m-ccNFI_END = '.nfi'[m
[31m-#--------------globals--------------------------------------[m
[31m-#ggDateStr=datetime.datetime.now().strftime('%Y%m%d%H%M%S')[m
[31m-#ggOutdirDone =  ccOutDIR +  ccDirSEP + 'done' + str(ggDateStr)[m
[31m-[m
[31m-#-------------------------------------------------[m
[31m-[m
[31m-def printFrontMatter():[m
[31m-    print ccNLINE + "Starting program to translate .nfi to .aaf files"[m
[31m-    print "    Program concept by Axel Becker. Code by Nancy Schorr"[m
[31m-[m
[31m-def setGlobalEachFile(locFileList):[m
[31m-    global ggEachFileList; [m
[31m-    ggEachFileList = locFileList;[m
[32m+[m[32m#----------------------------------------------------[m
[32m+[m[32mdef splitNfiFile(inFile):[m
[32m+[m[32m    adbcFinal = 'AD'[m
[32m+[m[32m    AAF_END = '.aaf'[m
[32m+[m[32m    infile_firstpart = inFile[:-4][m
[32m+[m[32m    FINAL_OUTFILE= global_OUTDIR + DIR_SEP + infile_firstpart + AAF_END[m
[32m+[m[32m    target_FILE2 = open(FINAL_OUTFILE, 'w')  ## target_FILE is OPENED[m
[32m+[m[32m    target_FILE2.close()[m
     [m
[31m-def getGlobalEachFile():[m
[31m-    return ggEachFileList;[m
[31m-[m
[31m-def prtExitEarlyStatements():[m
[31m-    print ccNLINE + "ERROR!! nfi files not there. Program exiting early. "[m
[31m-    print '  Please put C:\\astin\\filename.nfi there and start over.'[m
[31m-    print '  Can be any filename as long as it ends with \".nfi\"'[m
[31m-    pymsgbox.alert("ERROR!! No files in C:\\astin", "Astrofiles Aborted")[m
[31m-[m
[31m-#2------------------- chkFixDirs() ----------------------------------[m
[31m-### check both dirs present; if inDirToChg not there, stop & give error  [m
[31m-### if cOutDIR isn't there, create it[m
[32m+[m[32m    theInfile = cInputDIR +  DIR_SEP + inFile[m
[32m+[m[32m    print "now the infile is: " + theInfile[m
[32m+[m[41m   [m
[32m+[m[32m    #target_FILE = open(FINAL_OUTFILE, 'w')  ## target_FILE is OPENED[m
[32m+[m[32m    #fn = target_FILE.fileno()[m
  [m
[31m-def chkFixDirs(loc_outdir):[m
[31m-    indirExists = os.path.exists(ccInputDIR)[m
[31m-    outdirExists = os.path.exists(ccOutDIR)[m
[31m-    [m
[31m-    if indirExists is True :[m
[31m-        print   ccNLINE +"inputdir pathexists. Continuing..."  [m
[31m-         [m
[31m-    if indirExists is True :[m
[31m-        if outdirExists is True :[m
[31m-            print ccNLINE + "outdirExists YES path exists boolean value: " + str(outdirExists)[m
[31m-        [m
[31m-        if outdirExists is False :[m
[31m-            print ccNLINE + "outdirExists NO not there : " + str(outdirExists)+  "Creating new dir: " + ccOutDIR[m
[31m-            os.makedirs(ccOutDIR)[m
[31m-    [m
[31m-        os.makedirs(loc_outdir)[m
[31m-        [m
[31m-    ckNfi(ccInputDIR)[m
[31m-[m
[31m-#3--------------------------------------------------[m
[31m-def getInFilePathList():[m
[31m-    print    ccNLINE +"inside getInFilePathList(). storing vals to array"[m
[31m-    eachFileList = [][m
[31m-    eachFilePathList = [][m
[31m-    [m
[31m-    for inputFile in os.listdir(ccInputDIR):[m
[31m-        if inputFile.endswith(".nfi"):[m
[31m-            eachFileList.append(inputFile);[m
[31m-            eachFilePathList.append(ccInputDIR + ccDirSEP + inputFile)[m
[31m-     [m
[31m-    setGlobalEachFile(eachFileList)[m
[31m-    print ccNLINE + "  now eachFileList: "[m
[31m-    for i in ggEachFileList :[m
[31m-        print i;[m
[31m-    print ccNLINE + "  now eachFilePathList: "[m
[31m-    for ii in eachFilePathList :[m
[31m-        print ii;[m
[31m-    print "  End of getInFilePathList()" + ccNLINE[m
[31m-    print "-------------------!!! entire eachFilePathList : " + str(eachFilePathList)[m
[31m-    return eachFileList[m
[31m-    [m
[31m-#--------------------------------------------------[m
[31m-[m
[31m-def formatTime(tim):[m
[31m-    p1 =  tim[0:2][m
[31m-    p2 =  tim[2:4][m
[31m-    p3 =  tim[4:6]   [m
[31m-    print "rawtime: " + tim[m
[31m-    print "-----------time parts: "[m
[31m-    newtime = p1 + ":" + p2 + ":" + p3[m
[31m-    print newtime   [m
[31m-    return newtime[m
[31m-[m
[31m-#--------------------------------------------------[m
[31m-### not used?[m
[31m-[m
[31m-def ckNfi(input_dir):   [m
[31m-    loc_nfi_files = input_dir + ccDirSEP + ccSTAR + ccNFI_END[m
[31m-    #### check if there is at least one filename.nfi file[m
[31m-    if glob.glob(loc_nfi_files):[m
[31m-        print ccNLINE + "YES nfi files are there!" + ccNLINE[m
[31m-    else:[m
[31m-        prtExitEarlyStatements()[m
[31m-        quit();[m
[32m+[m[32m    print "----------###!!! just got passed: " + inFile[m[41m [m
[32m+[m[32m    print "-------------working on new file: " + FINAL_OUTFILE[m
[32m+[m[41m       [m
[32m+[m[32m    print NLINE + "datestring: " + MAIN_DATE_STR + "  cInputDIR : " + cInputDIR[m
[32m+[m[32m    print "global_OUTDIR: " + global_OUTDIR +" global_INDIR_DONE: " + global_INDIR_DONE + " FINAL_OUTFILE: " + FINAL_OUTFILE + NLINE[m
[32m+[m[41m   [m
[32m+[m[32m    #print "file number of newfile is: " + fn[m
                 [m
[31m-    myfiles = glob.iglob(loc_nfi_files)   [m
[31m-    print ccNLINE + "Here are the nfi files to be translated to type aaf: "[m
[31m-   [m
[31m-    for f in myfiles :[m
[31m-        print "    " + f[m
[31m-   [m
[31m-#--------------------------------------------------[m
[32m+[m[41m                [m
[32m+[m[41m                [m
[32m+[m[32m    fileexists = os.path.exists(theInfile)[m
[32m+[m[32m    if not fileexists :[m
[32m+[m[32m        pymsgbox.alert("ERROR!! No aaf files in C:\\astin", 'Astrofiles Aborted')[m
     [m
[31m- [m
[31m-def addCommentLn(targt, mLine):[m
[31m-        print "---------------------------------######################---------------!!!!!!here is empty comments extra myLine:"[m
[31m-        print mLine;[m
[31m-        lineLen = len(mLine)[m
[31m-        print "lineLen is: " + str(lineLen)[m
[31m-        if lineLen > 0:[m
[31m-            targt.write(mLine)  ## no return written, just chars[m
[31m-            targt.write(" ")   ## so the words in the comments are separated when from 2 lines       [m
[31m-        print "-----------------------------!@@@@@@@@@@@@@@@@ here now!"[m
[32m+[m[32m    theLines = open( theInfile ).read().splitlines()[m
[32m+[m[32m    for myLine in theLines :[m
[32m+[m[32m        endName = 0[m
[32m+[m[32m        #endNameComma = '#'[m
[32m+[m[32m        hasComma = False[m
[32m+[m[32m        hasAtsign = False[m
[32m+[m[32m        atsignLoc = 0[m
[32m+[m[32m        hasFirstName = False[m
[32m+[m[32m        firname = "*"[m
[32m+[m[32m        fnameEnd = 0[m
[32m+[m[32m        firnameStart = 0[m
[32m+[m[32m        #print hasComma + " " + hasAtsign[m
[32m+[m[32m        lnameEnd = 0[m
   [m
[31m-#------------------------------------------------[m
[31m-def fixadbc(adbc_loc):[m
[31m-    adbc_fin = adbc_loc[m
[31m-    if adbc_loc == 'B' :[m
[31m-        adbc_fin = 'BC'[m
[31m-    return adbc_fin[m
[32m+[m[32m        if myLine.startswith("$"):   ### start next record[m
[32m+[m[32m            if "," in myLine :    ### has first and last name[m
[32m+[m[32m                hasComma = True;[m
[32m+[m[32m                commaLoc = myLine.find(',')  ## , is at end of last name[m
[32m+[m[32m                lnameEnd = commaLoc  ### most simple case[m
[32m+[m[32m                hasFirstName = True[m
[32m+[m
[32m+[m[32m            if '@' in myLine :    ### has first and last name[m
[32m+[m[32m                hasAtsign = True;[m
[32m+[m[32m                atsignLoc = myLine.find('@')  ## @ is at end of full name[m
[32m+[m[41m                [m
[32m+[m[32m                if (( hasComma == False ) and (hasAtsign == True )) :[m
[32m+[m[32m                    lnameEnd = atsignLoc    ### use the @ if there is not comma for lname end[m
[32m+[m[32m                                            ## has no firstname[m
[32m+[m[41m               [m
[32m+[m[32m            if ((hasComma == False) and (hasAtsign == False)) :  # no comma, no @[m[41m [m
[32m+[m[32m                lnameEnd = len(myLine)    ## has no firstname[m
[32m+[m[41m            [m
[32m+[m[32m            if ((hasComma == True) and (hasAtsign == False)) :    # has comma no @[m
[32m+[m[32m                fnameEnd = len(myLine)     ## firstname ends at end of line[m
[32m+[m[41m                [m
[32m+[m[32m            if ((hasComma == True) and (hasAtsign == True)) :     # has comma and @[m
[32m+[m[32m                fnameEnd = atsignLoc          ## firstname ends at @[m
[32m+[m[41m            [m
[32m+[m[32m            if (hasFirstName is True) :[m
[32m+[m[32m                endName = fnameEnd[m
[32m+[m[32m            else :[m
[32m+[m[32m                endName = lnameEnd[m
[32m+[m[32m            print "lnameEnd= " + str(lnameEnd) + " ---------- !!!! inside qualifying if loop "[m
[32m+[m[32m            adbcFinal =  nfitoaafmods.fixadbc(myLine[1:2])[m
[32m+[m[32m            lname =myLine[40:lnameEnd][m
[32m+[m[32m            if (hasFirstName is True) :[m
[32m+[m[32m                firnameStart = lnameEnd + 1[m
[32m+[m[32m                firname = myLine[firnameStart:fnameEnd][m
[32m+[m[32m            print "firname = " + firname[m
[32m+[m
[32m+[m[32m            yr = myLine[2:6][m
[32m+[m[32m            mon =  myLine[6:8][m
[32m+[m[32m            day =  myLine[8:10][m
[32m+[m[32m            tmraw =  myLine[10:16][m
[32m+[m[32m            tm = nfitoaafmods.formatTime(tmraw)[m
[32m+[m[32m            zn_offset_orig = myLine[17:22][m
[32m+[m[32m            zn_offset = nfitoaafmods.fixzn_offset(zn_offset_orig)[m
[32m+[m[32m            lat_orig =  myLine[22:27][m
[32m+[m[32m            lat = nfitoaafmods.fixlat(lat_orig)[m
[32m+[m[32m            longt_orig = myLine[27:33][m
[32m+[m[32m            gender = myLine[33:34][m
[32m+[m[32m            longt = nfitoaafmods.fixlongt(longt_orig)[m
[32m+[m[32m            country="*"[m
[32m+[m[32m            city =myLine[endName+1:][m
[32m+[m[32m            fullDate=day + "." + mon + "." +yr[m
[32m+[m[32m            if len(city) < 1:[m
[32m+[m[32m                city = "*"[m
[32m+[m[32m            if ((gender != 'M') and  (gender != 'F') ):[m
[32m+[m[32m                gender = '*'[m
[32m+[m[32m            if city == 'Bonn' :[m
[32m+[m[32m                country = "D"[m
[32m+[m[32m            writeOutFileContent(FINAL_OUTFILE,firname,lname,fullDate,tm,zn_offset,lat,longt,gender,city,country)[m
[32m+[m[41m       [m
[32m+[m[32m        ##### special case for extra comment lines:[m
[32m+[m[32m        if not myLine.startswith("$"):  ## should work for 2 extra comment lines at least[m
[32m+[m[32m            lineLength = len(myLine)[m
[32m+[m[32m            print "---------------------------------######################---------------!!!!!!here is empty comments extra myLine:"[m
[32m+[m[32m            print myLine + "\n" +  " lineLength +  is: " + str(lineLength)[m
[32m+[m[32m            if lineLength > 0:[m
[32m+[m[32m                target_FILE2 = open(FINAL_OUTFILE, 'a')  ## target_FILE is OPENED[m
[32m+[m[41m               [m
[32m+[m[32m                target_FILE2.write(myLine)  ## no return written, just chars[m
[32m+[m[32m                target_FILE2.write(" ")   ## so the words in the comments are separated when from 2 lines[m[41m       [m
[32m+[m[32m                print "-----------------------------!@@@@@@@@@@@@@@@@ here now!"[m
[32m+[m[32m                print lname+" Month="+mon+" Day="+day+" "+yr+" "+adbcFinal+" Time="+tm+" Zone="+zn_offset+" Long="+longt+" Lat="+lat[m
[32m+[m[32m                target_FILE2.close()[m
[32m+[m[41m             [m
[32m+[m[32m#------------------- writeOutFileContent() ----------------------------------[m
[32m+[m[32mdef writeOutFileContent(FINAL_OUTFILE,fname,lname,tdate,tm,tzone,tlat,tlong,tGender,city,country):[m
[32m+[m[32m    target_FILE = open(FINAL_OUTFILE, 'a')  ## target_FILE is OPENED[m
[32m+[m[32m    print NLINE + "inside writeOutFileContent:" + " lname: " + lname[m
[32m+[m
[32m+[m[32m    julianDay = "*"[m[41m  [m
[32m+[m[32m    whiteSpaceLoc = -1[m
[32m+[m[32m    print whiteSpaceLoc[m
[32m+[m
[32m+[m[32m    try:[m
[32m+[m[32m        whiteSpaceLoc = lname.index(' ')[m
[32m+[m[32m    except ValueError:[m
[32m+[m[32m        print "Could not find space in string"[m
[32m+[m[32m        whiteSpaceLoc = -2[m
[32m+[m[32m    except:[m
[32m+[m[32m        print "Unexpected error:", sys.exc_info()[0][m
[32m+[m[32m        raise[m
[32m+[m[32m    if whiteSpaceLoc > 0 :[m
[32m+[m[32m        firstname, lastname  = lname.split(' ',1)[m
[32m+[m[32m        print "---------------!!! new firstname is: " + firstname[m
[32m+[m[32m        print "---------------!!! new lastname is: " + lastname[m
[32m+[m[32m        fname = firstname[m
[32m+[m[32m        lname = lastname[m
[32m+[m
[32m+[m[32m    print "-------------------wholename is: " + lname + "."[m
[32m+[m[32m    print "-------------------------------------- whiteSpaceLoc = " + str(whiteSpaceLoc)[m
     [m
[31m-#------------------------------------------------[m
[32m+[m[32m    target_FILE.write(NLINE)[m
[32m+[m[32m    target_FILE.write("#A93:")[m[41m    [m
[32m+[m[32m    target_FILE.write(lname)[m
[32m+[m[32m    target_FILE.write(cCOMMA)[m
     [m
[31m-def fixlat(alat):[m
[31m-    p1 =  alat[0:2][m
[31m-    p2 =  alat[2:4][m
[31m-    p3 =  alat[4:5]  [m
[31m-    newlat = p1 + p3 + p2 [m
[31m-    print "p1: " +  p1[m
[31m-    print "p2: " +  p2[m
[31m-    print "p3: " +  p3[m
[31m-    print "input lat: " + alat[m
[31m-    print "newlat = " + newlat[m
[31m-    print[m
[31m-    return newlat[m
[31m-#------------------------------------------------[m
[32m+[m[32m    target_FILE.write(fname)[m
[32m+[m[32m    target_FILE.write(cCOMMA)[m
[32m+[m[41m    [m
[32m+[m[32m    target_FILE.write(tGender)[m
[32m+[m[32m    target_FILE.write(cCOMMA)[m
[32m+[m[41m    [m
[32m+[m[32m    target_FILE.write(tdate)[m
[32m+[m[32m    target_FILE.write(cCOMMA)[m
[32m+[m[41m    [m
[32m+[m[32m    target_FILE.write(tm)[m
[32m+[m[32m    target_FILE.write(cCOMMA)[m
 [m
[31m-def fixlongt(longt_org):[m
[31m-    p1 =  longt_org[0:3][m
[31m-    p2 =  longt_org[3:5][m
[31m-    p3 =  longt_org[5:6]  [m
[31m-    newlongt = p1 + p3 + p2 [m
[31m-    print "p1: " +  p1[m
[31m-    print "p2: " +  p2[m
[31m-    print "p3: " +  p3[m
[31m-    print "input longt_org: " + longt_org[m
[31m-    print "newlongt = " + newlongt[m
[31m-    print[m
[31m-    return newlongt[m
[31m-#------------------------------------------------[m
[32m+[m[32m    target_FILE.write(city)[m
[32m+[m[32m    target_FILE.write(cCOMMA)[m
 [m
[31m-def fixzn_offset(zn):[m
[31m-    p1 =  zn[0:2][m
[31m-    p2 =  zn[2:4][m
[31m-    p3 =  zn[4:5]  [m
[31m-    newzn = p1 + p3 + p2 [m
[31m-    print "p1: " +  p1[m
[31m-    print "p2: " +  p2[m
[31m-    print "p3: " +  p3[m
[31m-    print "input zone_offset_orig: " + zn[m
[31m-    print "newzone_offset = " + newzn[m
[31m-    print[m
[31m-    #if (newzn == '00E28'):[m
[31m-    #    newzn = '01E00'    [m
[31m-    return newzn[m
[32m+[m[32m    target_FILE.write(country)[m
[32m+[m[32m    target_FILE.write(NLINE)[m
[32m+[m
[32m+[m[32m    target_FILE.write("#B93:")[m
[32m+[m[32m    target_FILE.write(julianDay)[m
[32m+[m[32m    target_FILE.write(cCOMMA)[m
[32m+[m[41m    [m
[32m+[m[32m    target_FILE.write(tlat)[m
[32m+[m[32m    target_FILE.write(cCOMMA)[m
[32m+[m[41m    [m
[32m+[m[32m    target_FILE.write(tlong)[m
[32m+[m[32m    target_FILE.write(cCOMMA)[m
[32m+[m[41m    [m
[32m+[m[32m    target_FILE.write(tzone)[m
[32m+[m[32m    target_FILE.write(cCOMMA)[m
[32m+[m[41m    [m
[32m+[m[32m    target_FILE.write("0")[m
[32m+[m[41m    [m
[32m+[m[32m    target_FILE.write(NLINE)[m[41m   [m
[32m+[m[32m    target_FILE.write("#COM:")[m
[32m+[m[32m    #target_FILE.write(comments)[m
[32m+[m[32m    #target_FILE.write(" ")  ## add a space so we can add onto the comments if needed[m
[32m+[m[32m    whiteSpaceLoc = -1[m
[32m+[m[32m    target_FILE.close()[m
[32m+[m[41m   [m
[32m+[m[32m#--------------------------------------------------------------[m
 [m
 [m
 [m
[32m+[m[32mindirExists2 = os.path.exists(cInputDIR)[m
[32m+[m[41m    [m
[32m+[m[32mif indirExists2 is not True :[m
[32m+[m[32m    pymsgbox.alert("ERROR!! Directory C:\\astin not there!", "Astrofiles Aborted")[m
[32m+[m[32m    quit()[m
[32m+[m
[32m+[m
[32m+[m
[32m+[m[32mnfitoaafmods.printFrontMatter()[m
[32m+[m[32mnfitoaafmods.chkFixDirs(global_OUTDIR)  ## this is where alerts are[m
[32m+[m
[32m+[m[32mlocInFileList = nfitoaafmods.getInFilePathList()[m
[32m+[m[32mprint "local file list: " + str(locInFileList)[m
[32m+[m
[32m+[m[32mmylength = locInFileList[m
[32m+[m
[32m+[m[32mfor f in locInFileList:[m
[32m+[m[32m    splitNfiFile(f)[m
[32m+[m[32m    #target_FILE.close()[m
[32m+[m[32mpymsgbox.alert("If program worked, new aaf files will be in C:\\astout", 'Astrofiles Finished')[m
[32m+[m
[32m+[m[32mprint NLINE + "end of program"[m
 [m
[1mdiff --git a/NfiToAaf/nfitoastmods.py b/NfiToAaf/nfitoastmods.py[m
[1mnew file mode 100644[m
[1mindex 0000000..b731e4e[m
[1m--- /dev/null[m
[1m+++ b/NfiToAaf/nfitoastmods.py[m
[36m@@ -0,0 +1,201 @@[m
[32m+[m[32m'''[m
[32m+[m[32mCreated on Jan 31, 2016[m
[32m+[m
[32m+[m[32m@author: user[m
[32m+[m[32m'''[m
[32m+[m
[32m+[m[32m#Created on Nov 22, 2015[m
[32m+[m[32m#@author: Nancy Schorr[m
[32m+[m
[32m+[m[32m#  translate the old "nfi"-files, that I have (they are basically text-files with a certain structure, as far as I understand... one file consists of a lot of astrological dates, each in one line... each line would be one aaf-file) into aaf data.[m
[32m+[m[32m## old is nfi, new is aaf[m
[32m+[m
[32m+[m[32mPYTHONDONTWRITEBYTECODE = True   #prevents bytecode .pyc files from being created[m
[32m+[m[32mimport glob[m
[32m+[m[32mimport os[m
[32m+[m[32m#import sys[m
[32m+[m[32m#import re[m
[32m+[m[32m#import shutil[m
[32m+[m[32mimport datetime[m
[32m+[m[32mimport pymsgbox[m
[32m+[m
[32m+[m[32mif __name__ == '__main__':   pass[m
[32m+[m
[32m+[m[32m__all__ = ["printFrontMatter"][m
[32m+[m
[32m+[m
[32m+[m
[32m+[m[32m#--------------constants--------------------------------------[m
[32m+[m[32mccDirSEP ='\\'[m
[32m+[m[32mccNLINE = "\n"[m
[32m+[m[32mccSTAR = "*"[m
[32m+[m[32mccInputDIR ='C:\\astin'      ## must be there[m
[32m+[m[32mccOutDIR =  'C:\\astout'      ## where you are sorting to, created if not there[m
[32m+[m[32mccAaf_END = '.aaf'[m
[32m+[m[32mccNFI_END = '.nfi'[m
[32m+[m[32m#--------------globals--------------------------------------[m
[32m+[m[32m#ggDateStr=datetime.datetime.now().strftime('%Y%m%d%H%M%S')[m
[32m+[m[32m#ggOutdirDone =  ccOutDIR +  ccDirSEP + 'done' + str(ggDateStr)[m
[32m+[m
[32m+[m[32m#-------------------------------------------------[m
[32m+[m
[32m+[m[32mdef printFrontMatter():[m
[32m+[m[32m    print ccNLINE + "Starting program to translate .nfi to .aaf files"[m
[32m+[m[32m    print "    Program concept by Axel Becker. Code by Nancy Schorr"[m
[32m+[m
[32m+[m[32mdef setGlobalEachFile(locFileList):[m
[32m+[m[32m    global ggEachFileList;[m[41m [m
[32m+[m[32m    ggEachFileList = locFileList;[m
[32m+[m[41m    [m
[32m+[m[32mdef getGlobalEachFile():[m
[32m+[m[32m    return ggEachFileList;[m
[32m+[m
[32m+[m[32mdef prtExitEarlyStatements():[m
[32m+[m[32m    print ccNLINE + "ERROR!! nfi files not there. Program exiting early. "[m
[32m+[m[32m    print '  Please put C:\\astin\\filename.nfi there and start over.'[m
[32m+[m[32m    print '  Can be any filename as long as it ends with \".nfi\"'[m
[32m+[m[32m    pymsgbox.alert("ERROR!! No files in C:\\astin", "Astrofiles Aborted")[m
[32m+[m
[32m+[m[32m#2------------------- chkFixDirs() ----------------------------------[m
[32m+[m[32m### check both dirs present; if inDirToChg not there, stop & give error[m[41m  [m
[32m+[m[32m### if cOutDIR isn't there, create it[m
[32m+[m[41m [m
[32m+[m[32mdef chkFixDirs(loc_outdir):[m
[32m+[m[32m    indirExists = os.path.exists(ccInputDIR)[m
[32m+[m[32m    outdirExists = os.path.exists(ccOutDIR)[m
[32m+[m[41m    [m
[32m+[m[32m    if indirExists is True :[m
[32m+[m[32m        print   ccNLINE +"inputdir pathexists. Continuing..."[m[41m  [m
[32m+[m[41m         [m
[32m+[m[32m    if indirExists is True :[m
[32m+[m[32m        if outdirExists is True :[m
[32m+[m[32m            print ccNLINE + "outdirExists YES path exists boolean value: " + str(outdirExists)[m
[32m+[m[41m        [m
[32m+[m[32m        if outdirExists is False :[m
[32m+[m[32m            print ccNLINE + "outdirExists NO not there : " + str(outdirExists)+  "Creating new dir: " + ccOutDIR[m
[32m+[m[32m            os.makedirs(ccOutDIR)[m
[32m+[m[41m    [m
[32m+[m[32m        os.makedirs(loc_outdir)[m
[32m+[m[41m        [m
[32m+[m[32m    ckNfi(ccInputDIR)[m
[32m+[m
[32m+[m[32m#3--------------------------------------------------[m
[32m+[m[32mdef getInFilePathList():[m
[32m+[m[32m    print    ccNLINE +"inside getInFilePathList(). storing vals to array"[m
[32m+[m[32m    eachFileList = [][m
[32m+[m[32m    eachFilePathList = [][m
[32m+[m[41m    [m
[32m+[m[32m    for inputFile in os.listdir(ccInputDIR):[m
[32m+[m[32m        if inputFile.endswith(".nfi"):[m
[32m+[m[32m            eachFileList.append(inputFile);[m
[32m+[m[32m            eachFilePathList.append(ccInputDIR + ccDirSEP + inputFile)[m
[32m+[m[41m     [m
[32m+[m[32m    setGlobalEachFile(eachFileList)[m
[32m+[m[32m    print ccNLINE + "  now eachFileList: "[m
[32m+[m[32m    for i in ggEachFileList :[m
[32m+[m[32m        print i;[m
[32m+[m[32m    print ccNLINE + "  now eachFilePathList: "[m
[32m+[m[32m    for ii in eachFilePathList :[m
[32m+[m[32m        print ii;[m
[32m+[m[32m    print "  End of getInFilePathList()" + ccNLINE[m
[32m+[m[32m    print "-------------------!!! entire eachFilePathList : " + str(eachFilePathList)[m
[32m+[m[32m    return eachFileList[m
[32m+[m[41m    [m
[32m+[m[32m#--------------------------------------------------[m
[32m+[m
[32m+[m[32mdef formatTime(tim):[m
[32m+[m[32m    p1 =  tim[0:2][m
[32m+[m[32m    p2 =  tim[2:4][m
[32m+[m[32m    p3 =  tim[4:6][m[41m   [m
[32m+[m[32m    print "rawtime: " + tim[m
[32m+[m[32m    print "-----------time parts: "[m
[32m+[m[32m    newtime = p1 + ":" + p2 + ":" + p3[m
[32m+[m[32m    print newtime[m[41m   [m
[32m+[m[32m    return newtime[m
[32m+[m
[32m+[m[32m#--------------------------------------------------[m
[32m+[m[32m### not used?[m
[32m+[m
[32m+[m[32mdef ckNfi(input_dir):[m[41m   [m
[32m+[m[32m    loc_nfi_files = input_dir + ccDirSEP + ccSTAR + ccNFI_END[m
[32m+[m[32m    #### check if there is at least one filename.nfi file[m
[32m+[m[32m    if glob.glob(loc_nfi_files):[m
[32m+[m[32m        print ccNLINE + "YES nfi files are there!" + ccNLINE[m
[32m+[m[32m    else:[m
[32m+[m[32m        prtExitEarlyStatements()[m
[32m+[m[32m        quit();[m
[32m+[m[41m                [m
[32m+[m[32m    myfiles = glob.iglob(loc_nfi_files)[m[41m   [m
[32m+[m[32m    print ccNLINE + "Here are the nfi files to be translated to type aaf: "[m
[32m+[m[41m   [m
[32m+[m[32m    for f in myfiles :[m
[32m+[m[32m        print "    " + f[m
[32m+[m[41m   [m
[32m+[m[32m#--------------------------------------------------[m
[32m+[m[41m    [m
[32m+[m[41m [m
[32m+[m[32mdef addCommentLn(targt, mLine):[m
[32m+[m[32m        print "---------------------------------######################---------------!!!!!!here is empty comments extra myLine:"[m
[32m+[m[32m        print mLine;[m
[32m+[m[32m        lineLen = len(mLine)[m
[32m+[m[32m        print "lineLen is: " + str(lineLen)[m
[32m+[m[32m        if lineLen > 0:[m
[32m+[m[32m            targt.write(mLine)  ## no return written, just chars[m
[32m+[m[32m            targt.write(" ")   ## so the words in the comments are separated when from 2 lines[m[41m       [m
[32m+[m[32m        print "-----------------------------!@@@@@@@@@@@@@@@@ here now!"[m
[32m+[m[41m  [m
[32m+[m[32m#------------------------------------------------[m
[32m+[m[32mdef fixadbc(adbc_loc):[m
[32m+[m[32m    adbc_fin = adbc_loc[m
[32m+[m[32m    if adbc_loc == 'B' :[m
[32m+[m[32m        adbc_fin = 'BC'[m
[32m+[m[32m    return adbc_fin[m
[32m+[m[41m    [m
[32m+[m[32m#------------------------------------------------[m
[32m+[m[41m    [m
[32m+[m[32mdef fixlat(alat):[m
[32m+[m[32m    p1 =  alat[0:2][m
[32m+[m[32m    p2 =  alat[2:4][m
[32m+[m[32m    p3 =  alat[4:5][m[41m  [m
[32m+[m[32m    newlat = p1 + p3 + p2[m[41m [m
[32m+[m[32m    print "p1: " +  p1[m
[32m+[m[32m    print "p2: " +  p2[m
[32m+[m[32m    print "p3: " +  p3[m
[32m+[m[32m    print "input lat: " + alat[m
[32m+[m[32m    print "newlat = " + newlat[m
[32m+[m[32m    print[m
[32m+[m[32m    return newlat[m
[32m+[m[32m#------------------------------------------------[m
[32m+[m
[32m+[m[32mdef fixlongt(longt_org):[m
[32m+[m[32m    p1 =  longt_org[0:3][m
[32m+[m[32m    p2 =  longt_org[3:5][m
[32m+[m[32m    p3 =  longt_org[5:6][m[41m  [m
[32m+[m[32m    newlongt = p1 + p3 + p2[m[41m [m
[32m+[m[32m    print "p1: " +  p1[m
[32m+[m[32m    print "p2: " +  p2[m
[32m+[m[32m    print "p3: " +  p3[m
[32m+[m[32m    print "input longt_org: " + longt_org[m
[32m+[m[32m    print "newlongt = " + newlongt[m
[32m+[m[32m    print[m
[32m+[m[32m    return newlongt[m
[32m+[m[32m#------------------------------------------------[m
[32m+[m
[32m+[m[32mdef fixzn_offset(zn):[m
[32m+[m[32m    p1 =  zn[0:2][m
[32m+[m[32m    p2 =  zn[2:4][m
[32m+[m[32m    p3 =  zn[4:5][m[41m  [m
[32m+[m[32m    newzn = p1 + p3 + p2[m[41m [m
[32m+[m[32m    print "p1: " +  p1[m
[32m+[m[32m    print "p2: " +  p2[m
[32m+[m[32m    print "p3: " +  p3[m
[32m+[m[32m    print "input zone_offset_orig: " + zn[m
[32m+[m[32m    print "newzone_offset = " + newzn[m
[32m+[m[32m    print[m
[32m+[m[32m    #if (newzn == '00E28'):[m
[32m+[m[32m    #    newzn = '01E00'[m[41m    [m
[32m+[m[32m    return newzn[m
[32m+[m
[32m+[m
[32m+[m
[32m+[m
[1mdiff --git a/NfiToAaf/sample.nfi b/NfiToAaf/sample.nfi[m
[1mdeleted file mode 100644[m
[1mindex e69de29..0000000[m
[1mdiff --git a/NfiToAaf/setup.py b/NfiToAaf/setup.py[m
[1mindex cd46f6c..6a100af 100644[m
[1m--- a/NfiToAaf/setup.py[m
[1m+++ b/NfiToAaf/setup.py[m
[36m@@ -5,10 +5,7 @@[m [mfrom distutils.core import setup[m
 import py2exe[m
 [m
                                             #data_files=[][m
[31m-[m
 setup ( [m
[31m-                                            #name = 'E:\Workspace\astrofiles\astfiles.py', [m
[31m-       console = ['astfiles.py'][m
[31m-                                               #name=["E:astrofiles"][m
[32m+[m[32m       console = ['nfitoaaf.py'][m
                                                #options = {'includes':['sys','glob', 'os', 'datetime']}[m
                   )[m
\ No newline at end of file[m
