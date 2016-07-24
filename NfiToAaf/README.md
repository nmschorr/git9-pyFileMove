Program Name: NfiToAaf

Programmer/Author:  Nancy Schorr

Design concept:  Axel Becker

Date: June 1, 2016 

This program translates ".nfi" files into ".aaf" files. Argus 4 is a program used mostly in Europe, and it outputs the .nfi files which contains its stored data. A typical record from an Argus .nfi file looks like this:
```
$a1452041420561600000E4345N01056EMAXXXXXLeonardo Da Vinci
```
In order for this program to convert the files they must be located in:  'C:\astin'    

The .aaf output looks like this:
```
#A93:Da Vinci,Leonardo,M,14.04.1452,20:56:16,*,*
#B93:*,43N45,010E56,00E00,0
#COM:
```
The resulting .aaf will show up in 'C:\astout' and will be nameed the same as the input file, except for the extension which will be .aaf. If there is no directory/folder at this location, it will be created. Please be sure that the user running this program can also write to 'C:\astout' if it's there, or 'C:\' if it's not.

The files will be in a subfolder named with the timestamp of the time it's created. You can re-run over and over without worrying about overwriting past files because each timestamp will be unique.

The compiled exe version runs on Windows 7 64-bit only. Unzip the zip file to anywhere you like. It creates a folder called "dist". Inside "dist" is a file called "nfitoaaf.exe". Double click on nfitoaaf.exe to run it.



