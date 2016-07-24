Program Name: NfiToAaf

Programmer/Author:  Nancy Schorr

Design concept:  Axel Becker

Date: June 1, 2016 

This program translates ".nfi" files into ".aaf" files. Argus 4 is a program used mostly in Europe. The .nfi files contain the stored data for Argus 4. A typical line from an Argus .nfi file looks like this:

$a1452041420561600000E4345N01056EMAXXXXXLeonardo Da Vinci

You MUST put the nfi files to be converted into:  'C:\astin'    

.nfi output looks like this:
```
#A93:Da Vinci,Leonardo,M,14.04.1452,20:56:16,*,*
#B93:*,43N45,010E56,00E00,0
#COM:
```
The results will show up in 'C:\astout'. This is where you are sorting to, and it's created for you if it's not there.

The files will be in a subfolder that is named with the timestamp of the time it's created. You can re-run over and over without worrying about overwriting past files because each timestamp will be unique.

The compiled exe version runs on Windows 7 64-bit only. Unzip the zip file to anywhere you like. It creates a folder called "dist". Inside "dist" is a file called "nfitoaaf.exe". Double click on nfitoaaf.exe to run it.



