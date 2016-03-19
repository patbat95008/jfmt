// $Id: jfmt.java,v 1.15 2014-01-16 13:23:12-08 - - $
//
// Patrick Russell
// pcrussel
//
// This program is similar to the example code jcat.java,
// which iterates over all of its input files, except
// that this program shows how to use String.split to 
// extract non-whitespace sequences of characters
// from each line.
//

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import static java.lang.System.*;

class jfmt {
   // Static variables keeping the general status of the program.
   public static final String JAR_NAME = get_jarname();
   public static final int EXIT_SUCCESS = 0;
   public static final int EXIT_FAILURE = 1;
   public static int exit_status = EXIT_SUCCESS;

   // A basename is the final component of a pathname.
   // If a java program is run from a jar, the classpath is the
   // pathname of the jar.
   static String get_jarname() {
      String jarpath = getProperty ("java.class.path");
      int lastslash = jarpath.lastIndexOf ('/');
      if (lastslash < 0) return jarpath;
      return jarpath.substring (lastslash + 1);
   }


   // Formats a single file.
   static void format (int wrapL, Scanner infile) {
      wrapL = -wrapL;
      // Read each line from the opened file, one after the other.
      // Stop the loop at end of file.
      for (int linenr = 1; infile.hasNextLine(); ++linenr) {
         String line = infile.nextLine();
         // Create a LinkedList of Strings.
         List<String> words = new LinkedList<String>();

         // Split the line into words around white space and iterate
         // over the words.
         for (String word: line.split ("\\s+")) {

            // Skip an empty word if such is found.
            if (word.length() == 0) continue;
            // Append the word to the end of the linked list.
            words.add (word);

         }
         //Use print_paragraph to print words
         print_paragraph(wrapL, words);
      }
   }

   //Prints the paragraph
   static void print_paragraph (int wrapL, List<String> words){
      //Count the total length of characters per line
      //stands for lineTotalLength
      int lineTotalL = 0;
      for (String word: words) {
         lineTotalL += word.length() + 1;
         //for the word wrap, count the length
         //of each word, compare to the input
         //wrapLength
         if (lineTotalL <= wrapL) {
            out.printf (" %s", word );
         }else if(lineTotalL > wrapL){
            out.printf ("%n", word );
            out.printf (word);
            lineTotalL = 0;
         }
      }
      out.printf("%n");
   }



   // Main function scans arguments and opens/closes files.
   public static void main (String[] args) {
      int wrapL = -65;

      if (args.length == 0) {
         // There are no filenames given on the command line.
         out.printf ("FILE: -%n");
         format (wrapL, new Scanner (in));
      }else {
         // Iterate over each filename given on the command line.
         for (int argix = 0; argix < args.length; ++argix) {
            String filename = args[argix];
            if (filename.startsWith("-")){
               //Means the file is a number
               wrapL = Integer.parseInt(filename);
            }else if (filename.equals ("-")) {
               // Treat a filename of "-" to mean System.in.
               out.printf ("FILE: -%n");
               format (wrapL, new Scanner (in));
            }else {
               // If a wrap number is specified, uses it.
               // If not, uses default 65             
	       // Open the file and read it, or error out.
               try {
                  Scanner infile = new Scanner (new File (filename));
                  out.printf ("FILE: %s%n", filename);
                  format (wrapL, infile);
                  infile.close();
               }catch (IOException error) {
                  exit_status = EXIT_FAILURE;
                  err.printf ("%s: %s%n", JAR_NAME,
                              error.getMessage());
               }
            }
         }
      }
      exit (exit_status);
   }
}
