#!/usr/bin/perl
#    PATH=$PATH:.
$special="SUMMARY:Moon goes void";

$localdir="/Users/lawrenceschorr/Desktop/code";
$NEWFRONTa = "DTSTART:";
$NEWFRONT = "DTEND:";
$timestamp = `date  "+%m%d%H%M%S"`;
chomp $timestamp ;

print "Datestamp is $timestamp\n  We are Shawn!!! \nLocaldir is $localdir\n";
 
$in_cal1 = "$localdir/SFCAL.ics";

$out_cal1 = "$localdir/out1_$timestamp.ics";
$in_cal2 = "$localdir/out1_$timestamp.ics";  ## same as out_cal1
$out_cal2 = "$localdir/out2_$timestamp-b.ics";
print "ouput file is: $out_cal1 \n";
 print "ouput file2 is: $out_cal2 \n";
 


open(INFILE1, "<$in_cal1");
open(OUTFILE1, ">$out_cal1");

print "We are here!!! \nLocaldir is $localdir\n";


@lines = <INFILE1> ;     
close(INFILE1);

foreach $line (@lines)                # assign @lines to $line, one at a time
  {
  my @Chars = split("", $line);
  $charcount= scalar @Chars;
  $bestcount=$charcount-4;  # was 2
  print OUTFILE1 $line;
#print "just printed line:  $line \n";


  if (substr($line,0,8) eq 'DTSTART:' )    
          {  
  #print "Found it! \n\n";
       chomp $line;
        $NEWBACK = substr($line,8,16);  
        $NEWT=$NEWFRONT . $NEWBACK . "\n";   # added 1Z
 print "newline is $NEWT";
              print OUTFILE1 $NEWT;
          }  
   }  
close(OUTFILE1);   
   
open(INFILE2, "<$in_cal2");   
open(OUTFILE2, ">$out_cal2"); 

@lines_2 = <INFILE2> ;     
close(INFILE2);

$a="";
$aa="";
$b="";
$c="";
$d="";
$e="";
$f="";

foreach $lineTwo (@lines_2)                # assign @lines to $line, one at a time
  {
   #print "We are here in SECOND PART!!!  ";
  $aa=$a;  #DTSTART
  $a=$b;  #DTSTART
  $b=$c;   #DTEND
  $c=$e;
  $e=$f;
  $f=$lineTwo;


  print "      --NEW aa IS:  \n $aa \n";

 #print "      --NEW a IS:  \n $a \n";
 #print "      --NEW b IS:  \n $b \n";
 #print "      --NEW c IS:  \n $c \n";
 #print "      --NEW c IS:  \n $d \n";



 $newsubstring = substr($lineTwo,0,22);
 #print "      --NEW SUBSTRING IS:  \n";
 #print $newsubstring;
 #print "\n    --END \n";

  if (substr($lineTwo,0,22) eq 'SUMMARY:Moon goes void' )    
     {  
       print "\n\n  ----------------------------------------------------------found void!!!\n";
       print "\n\n  ********************************************************************\n";
        print $lineTwo;
       print "      ----------------------LINE aa IS:  \n";
        print $aa , "  \n";
       print "      ----------------------LINE a IS:  \n";
       print $a , "  \n";
       chomp $aa;
       chomp $a;
       $NEWBACK = substr($aa,8,14);  
       print "\n     newBACK is :          $NEWBACK   \n\n";
       
       $NEWTaa="DTSTART:" . $NEWBACK . '1Z' . "\n";   # added 1Z
       $NEWTa= "DTEND:"   . $NEWBACK . '1Z' . "\n";   # added 1Z
       print OUTFILE2 $NEWTaa;
       $a=$NEWTa;   #DTEND

 #print OUTFILE2 $NEWTa;

        print "  printing aa !!!!!!!!!!!!    $NEWTaa   \n";
         print "  reset a !!!!!!!!!!!!    $NEWTa   \n";
        print "\n\n";
       }
    else
    {
      print OUTFILE2 $aa;
      #print OUTFILE2 $a;
  					print "\nPRINTING THIS ONLY  aa ------- ";
  					print $aa ;
    				#	print "PRINTING THIS ONLY  a --------------\n";
				 	#print $a;
 					print "    --------------\n\n";
 
     }
}
   
      #print OUTFILE2 $a;
      print OUTFILE2 $b;
      print OUTFILE2 $c;
      print OUTFILE2 $d;
      print OUTFILE2 $e;
      print OUTFILE2 $f;
close(OUTFILE2); 
system("`/bin/rm -rf OUTFILE1`");

