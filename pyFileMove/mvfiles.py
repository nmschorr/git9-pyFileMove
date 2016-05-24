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


desktopLoc ='C:\\Users\\user\\Desktop'
inDirToSort = desktopLoc + "\\MYDOCS"  ## where your files to be sorted are
outDir = desktopLoc + '\\movedFinance'    ## where you are sorting to

print "inDirToSort : " + inDirToSort


impfiles=sys.modules.keys()  ## print out the python modules we're using
for i in impfiles :
    if re.search("fileslist", str(i)):  # make sure fileslist.py can be found
        print "Next module imported: " + str(i)

print "sys.path is: " + str(sys.path) + "\n" ## just for fun

 
def mywalk(inputDir):
    myyDir = "C:\Users\user\Desktop\MYDOCS"
    for eachFiles in os.listdir(myyDir):

        print "!!-----------------------------starting over --newval equals: " +  eachFile
        
        for dirTypeItem in dirNames:
                # print "dirTypeItem is: " + dirTypeItem

            if re.search(dirTypeItem, eachFile, re.IGNORECASE ):
                print "FOUND MATCH. eachFile is: " + str(eachFile)
               
                oldpath =  os.path.join(rootDir, eachFile)
                newdir =  outDir + "\\" + dirTypeItem
                ##newdir =  "c:\\Users\\user\\Desktop\\moved\\" + dirTypeItem
              
                print "rootDir is: " + rootDir +" oldpath is: " + oldpath
                print "newdir is: " + newdir
                print "os.path.isfile(oldpath): " + str(  os.path.isfile(oldpath) )    ## make sure they're there              
                print "os.path.isdir(newdir): " + str(  os.path.isdir(newdir) ) 
                
                ### need to check here for new dir before moving
                newDirexists = os.path.exists(newdir)
                print "val of new dir exists:  "   + str(newDirexists)              
               
                try :
                    
                    if dirTypeItem == 'test' :     ## an exception to the rule
                        print "found test"                
                        # shutil.move(oldpath, "C:\\Users\\user\\Desktop\\moved\\exceptions")
                    else :
                        print "would do" + str(oldpath) + " plus: " + str(newdir)
                       ## shutil.move(oldpath, newdir)
                except Exception, e: 
                    print "exception caught: " + str(e)
                    
                print "-------done with a loop" + "\n"
                break  ## breaking because there is no sense in continuing with this item
                
                        
mywalk(inDirToSort)

print "end of program"


