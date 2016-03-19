// $Id: jarname.java,v 1.2 2013-09-24 14:41:30-07 - - $
//
//
// NAME
//    jarname - Print out the name of the current jar file.
//
// DESCRIPTION
//    Makes use of the fact that the java.class.path, when Java
//    is run from a jar, is the name of the jar.
//

import static java.lang.System.*;

class jarname {
   public static void main (String[] args) {
      String jarpath = getProperty ("java.class.path");
      out.printf ("jarpath = \"%s\"%n", jarpath);
      int lastslash = jarpath.lastIndexOf ('/');
      String jarbase = lastslash < 0 ? jarpath
                     : jarpath.substring (lastslash + 1);
      out.printf ("jarbase = \"%s\"%n", jarbase);
   }
}

//TEST// ./jarname >jartest.out
//TEST// mkpspdf jarname.ps jarname.java* jartest*.out

