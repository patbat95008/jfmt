// $Id: jcat.java,v 1.2 2013-09-24 14:41:31-07 - - $
//
// SYNOPSIS
//    jcat [filename...]
//
// DESCRIPTION
//    The jcat utility functions like cat(1) and copies the contents
//    of all files to the standard output, with error messages to
//    the standard error.
//
// EXIT STATUS
//    0 if no errors were detected.
//    1 if errors were detected and messages printed.
//

import java.io.*;
import java.util.Scanner;
import static java.lang.System.*;

class jcat {
   // Static variables keeping the general status of the program.
   public static final String JARNAME = get_jarname ();
   public static final int EXIT_SUCCESS = 0;
   public static final int EXIT_FAILURE = 1;
   public static int exit_status = EXIT_SUCCESS;

   // A basename is the final component of a pathname.
   // If a java program is run from a jar, the classpath is the
   // pathname of the jar.
   static String get_jarname () {
      String jarpath = getProperty ("java.class.path");
      int lastslash = jarpath.lastIndexOf ('/');
      if (lastslash < 0) return jarpath;
      return jarpath.substring (lastslash + 1);
   }


   // Copies a single opened file to stdout.
   static void copylines (Scanner infile) {
      // Read each line from the opened file, one after the other.
      // Stop the loop at end of file.
      while (infile.hasNextLine ()) {
         String line = infile.nextLine ();
         out.printf ("%s%n", line);
      }
   }

   // Open input file and copy contents to stdout.
   static void catfile (String filename) {
      if (filename.equals ("-")) {
         copylines (new Scanner (System.in));
      }else {
         try {
            Scanner infile = new Scanner (new File (filename));
            copylines (infile);
            infile.close ();
         }catch (IOException error) {
            exit_status = EXIT_FAILURE;
            err.printf ("%s: %s%n", JARNAME, error.getMessage ());
         }
      }
   }

   // Main function scans arguments and opens/closes files.
   public static void main (String[] args) {
      if (args.length == 0) {
         // No files specified on the command line.
         catfile ("-");
      }else {
         // Iterate over each filename given on the command line.
         for (int argi = 0; argi < args.length; ++argi) {
            catfile (args[argi]);
         }
      }
      exit (exit_status);
   }

}

//TEST// mkpspdf jcat.ps jcat.java

