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

PYTHONDONTWRITEBYTECODE = True   #prevents bytecode .pyc files from being created

import os
import sys
import re
import shutil
import traceback

sys.path.append(os.path.abspath("E:\\Workspace\\PrivateFiles\\")) ## this is where the list
                                    ### of file types is kept

if __name__ == '__main__':   pass

from fileslist import dirNames


inDir =  "C:\\Users\\user\\Desktop\\MYDOCS"  ## where your files to be sorted are
outDir = "C:\\Users\\user\\Desktop\\movedFinance"    ## where you are sorting to


impfiles=sys.modules.keys()  ## print out the python modules we're using

for i in impfiles :
    
    if re.search("fileslist", str(i)):  # make sure fileslist.py can be found
        print str(i);

    #print "sys.path is: " + str(sys.path) + "\n" ## just for fun

 
def mywalk(inputDir):
    print " "
    
    for eachFile in os.listdir(inputDir):
        #print "here now"
        inFilenameWPath =  os.path.join(inputDir, eachFile)
      
        for dirTypeItem in dirNames:
            #print "inside for loop"
            #print "dirTypeItem : " + str(dirTypeItem) + " dirNames: " + str(dirNames)
            
            

            if re.search(dirTypeItem, eachFile, re.IGNORECASE ):
                print ""
                print "New loop. Filename is: " + eachFile + " dirTypeItem: " + str(dirTypeItem)

             
                newOutDir =  outDir + "\\" + dirTypeItem
                outFilenameWPath =  os.path.join(newOutDir, eachFile)               
                newDirExists = os.path.isdir(newOutDir)
                
                
                
                sameNewFilenameAlreadyExists = os.path.exists(outFilenameWPath)
               
              
                print "Does newOutDir exist?   " + str(  newDirExists ) 
                if not newDirExists :
                    print "  ERROR!!! Destination dir not there! Can't move to location :" + str(outFilenameWPath)
                    print "  Can't move file: " + str(eachFile)
                    
                if sameNewFilenameAlreadyExists :
                    print "  ERROR!! same filename Already exists : " + str(outFilenameWPath) + " - skipping"
                               
                if (newDirExists and not sameNewFilenameAlreadyExists) :
                    
                    try :
                        print "--> Going to move " + str(inFilenameWPath) + " to " + str(newOutDir)
                        shutil.move(inFilenameWPath, newOutDir)
                        
                    except Exception, e: 
                        print "Exception caught: " + str(e)
                
                ## end of newDirexists   
                #print "-------done with a loop" + "\n"
        
        ##break  ## breaking because there is no sense in continuing with this item
                
                        
mywalk(inDir)

print "End of Program"


