'''
Created on Nov 22, 2015
@author: Nancy Schorr
'''
import glob
import re
import string
import os
import shutil
#import numpy

if __name__ == '__main__':  
    pass

workDir= 'C:\\Users\\user\\Desktop\\MYDOCS'
outDir='C:\\Users\\user\\Desktop\\moved'
dirTypes = ["pdf", "kaiser", "saab", "aaa", "ring", "avery", "avalon", "diann", "bofa", "amex", "capone", "smcu", "wach", "9943", "jpg", "pdf"]
newvar = []
quotes = '"'
 
def mywalk(inputDir):
    for mydir, dirr, eachfile in os.walk(inputDir):
        newtup=(mydir,dirr,eachfile)     #--------ss note1
        newvar.append(newtup)
        print "-------------  working on " + str(newtup)
        print ' mydir : ' +  str(mydir)
        print ' dirr : ' +  str(dirr)
        print "eachfile list is:    " + str(eachfile)
      
        for efile in eachfile:
            print "!!-----------------------------starting over --newval equals: " +  efile
            for dirType in dirTypes:
               
                #print "---- starting new loop, i = " + mysubStr
                if re.search(dirType, efile, re.IGNORECASE ):
                    print "dirType is:    " + dirType
                    print "dirr is:    " + str(dirr)
                    print "efile is:    " + str(efile)
                   
                    olddir =  "c:/Users/user/Desktop/MYDOCS"
                    newdir =  "c:/Users/user/Desktop/moved/" + dirType
                    print "olddir is: " + olddir
                    print "newdir is: " + newdir
                   
                    oldpath =  os.path.join(olddir, efile)
                    print "oldpath is: " + oldpath
                    
                    print(  os.path.isfile(oldpath) )                 
                    print(  os.path.isdir(newdir) )                 
                    shutil.move(oldpath, newdir)
                    print "-------done with a loop"
                    break
                break
                        

mywalk(workDir)
shutil.move("c:\\test 5.txt", "e:\\")

print "end of program"


"""
def get_filepaths(directory):
    loc_paths = []  
    filesDoubleArry = [[]]
     
    for root, dirr, eachfile in os.walk(directory):
        filesDoubleArry.append[root, eachfile]  # tuple
        ##filepath = os.path.join(root, itemf)
        loc_paths.append(root)  
       
    return loc_paths, filesDoubleArry  


fullpaths, fullDoubleArry = get_filepaths(dir1)
print fullpaths

 
for eachvar in full_file_paths:
    print "part 1: " +  eachvar[0]
    print "part 2: " + eachvar[1]
"""

###list_files( path2)
## filen = "\\f.txt";
# os.rename(dir1 + filen, dir2 + filen) 
"""
for fname in full_file_paths:
    if re.search(mysub, fname ):
        print "found dian in " + fname
        os.rename(dir1 + fname, dir2 + fname) 
"""


