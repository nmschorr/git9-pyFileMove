'''
Created on Nov 22, 2015
@author: Nancy Schorr
'''
##  You need to create a file called "fileslist.py" in the the same directory as this file
##  inside fileslist.py you need a line that contains the file types to move 
##  Put one line in the file the defines the list dirTypes like this:
##  dirTypes = ["pdf", "cat", "car", "aaa", "ring", "avery", "avalon",  "jpg", "doc" ]

## if paths get mixed up, go into: Preferences/PyDev/Interpreter-Python and delete the 
##     python.exe interpreter, then re-add it back
## Also, make sure on Windows that system variable PYTHONPATH looks like below, and is in
##    the regular system PATH
## PYTHONPATH=C:\Python27;C:\Python27\libs;C:\Python27\Lib;C:\Python27\DLLs;C:\Python27\Scripts;C:\Python27\Lib\site-packages;
##    C:\Python27\lib\site-packages;C:\Python27\lib
## this file uses tabs that are equal to four spaces
## using \ for line continuation
     
PYTHONDONTWRITEBYTECODE = True   #prevents bytecode .pyc files from being created
     
import os
import sys
import re
import shutil
import traceback
import time

sys.path.append(os.path.abspath("E:\\Workspace\\PrivateFiles\\")) ## this is where the list
from fileslist import dirNames  ## this line must be after sys.path.append
                                             ### of file types is kept
if __name__ == "__main__":   pass

inDir =  "C:\\Users\\user\\Desktop\\MYDOCS"  ## where your files to be sorted are
outDir = "C:\\Users\\user\\Desktop\\movedFinance"     ## where you are sorting to
impfiles=sys.modules.keys()  ## print out the python modules we're using
changed_list = []
unchanged_list = []
DQT = "\"" ##double quote "

print "\n"

for i in impfiles :
    if re.search("fileslist", str(i)):  # make sure fileslist.py can be found
        print str(i);
        #print "sys.path is: " + str(sys.path) + "\n" ## just for fun

def listDirs(inputDir):
    for eachFile in os.listdir(inputDir):
        #print "here now"
        origFilenameWPath =  os.path.join(inputDir, eachFile)
      
        for dirTypeItem in dirNames:
            #print "dirTypeItem : " + str(dirTypeItem) + " dirNames: " + str(dirNames)
            
            if re.search(dirTypeItem, eachFile, re.IGNORECASE ):
                print "\nNew loop. Filename is: " + DQT + eachFile + DQT + " and dirtype = " \
                    + DQT + str(dirTypeItem) + DQT
                newOutDir =  outDir + "\\" + dirTypeItem
                newFilenameWPath =  os.path.join(newOutDir, eachFile)               
                newDirExistsBoolean = os.path.isdir(newOutDir)
                sameNewFnamAlreadyExistsBoo = os.path.exists(newFilenameWPath)
                print "Does newOutDir exist? :   " + str(  newDirExistsBoolean ) 
                
                if not newDirExistsBoolean :
                    print "  Can't move file: " + DQT + str(eachFile) + DQT
                    print "  ERROR!!! Destination dir " + DQT + str(newOutDir) + DQT \
                        + " is not there! Can't move to file-name-location : " \
                        +DQT + str(newFilenameWPath) + DQT
                    unchanged_list.append(str(origFilenameWPath))

                if sameNewFnamAlreadyExistsBoo :
                    print "  ERROR!! same destination filename already exists : " \
                        + DQT + str(newFilenameWPath) + DQT  + " - skipping"
                    unchanged_list.append(str(origFilenameWPath))
           
                if (newDirExistsBoolean and not sameNewFnamAlreadyExistsBoo) :
                    try :
                        print "-----> GOING TO MOVE " + DQT + str(origFilenameWPath) +DQT + " to: " \
                            +DQT + str(newOutDir) + DQT
                        shutil.move(origFilenameWPath, newOutDir)
                        time.sleep(.7)  ## give it 7/10ths of a second to move the file
                        newChangeExistsBoo = os.path.exists(newFilenameWPath)
                        print "New file is there now = " + str(newChangeExistsBoo)
                        print "------------->>Check manually for filename: " \
                            + DQT + str(newFilenameWPath) +DQT
                        
                        if newChangeExistsBoo :
                            print "write successful!"
                            changed_list.append(str(newFilenameWPath))
                        else :
                            print "write failed!"
                            unchanged_list.append(str(origFilenameWPath))

                    except Exception, e: 
                        print "Exception caught: " + str(e)
                
                ## end of newDirexists                   
                        
listDirs(inDir)

print "\nHere's what got changed:"
for s in changed_list :
    print s
    
print "\nHere's what didn't change:"
for s in unchanged_list :
    print s

print "\nEnd of Program"


