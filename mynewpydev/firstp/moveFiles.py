'''
Created on Nov 22, 2015
@author: Nancy Schorr
'''
import re
import os
import shutil

if __name__ == '__main__':  
    pass

workDir= 'C:\\Users\\user\\Desktop\\MYDOCS'
outDir='C:\\Users\\user\\Desktop\\moved'
dirTypes = ["pdf", "cat", "car", "aaa", "ring", "avery", "avalon",  "jpg", "doc" ]
newvar = []
quotes = '"'
 
def mywalk(inputDir):
    for mydir, dirr, eachfile in os.walk(inputDir):
        newlist=[mydir,dirr,eachfile]     #--------ss note1
        newvar.append(newlist)
        print "-------------  working on " + str(newlist)
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
                
                        
mywalk(workDir)

print "end of program"


