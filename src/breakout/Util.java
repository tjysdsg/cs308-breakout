package breakout;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Util {

  /**
   * Read a text file into an array of String containing each line
   */
  public static String[] readResourceTxtToLines(String filename) {
    // https://stackoverflow.com/a/46613809/7730917
    ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    InputStream is = classLoader.getResourceAsStream(filename);
    if (is == null) {
      return null;
    }
    InputStreamReader isr = new InputStreamReader(is);
    BufferedReader reader = new BufferedReader(isr);

    // create and add blocks
    return reader.lines().toArray(String[]::new);
  }

}
