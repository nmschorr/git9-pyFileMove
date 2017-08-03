pyFileMove README.me
Author:  Nancy Schorr
Date:    12/9/2015

This program uses Python to sort a directory of files into subdirectories. 

You need to create a file called "fileslist.py" in the the same directory as this file
or somewhere in the PYTHONPATH.

Inside fileslist.py you need a line that contains the file types to move 
Put one line in the file the defines the list dirTypes like this:
  dirTypes = ["pdf", "cat", "car", "aaa", "ring", "avery", "avalon",  "jpg", "doc" ]

Change the value of inDirToSort to point to the directory you want sorted.

The output directory (outDir) should contain separate directories all named
exactly as in your fileslist.py file.  It will move any files with those labels 
anywhere in their name (case doesn't matter) to those subdirectories in the 
outDir directory.
