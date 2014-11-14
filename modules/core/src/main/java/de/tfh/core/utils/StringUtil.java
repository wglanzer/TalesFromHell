package de.tfh.core.utils;

import java.util.Arrays;

/**
 * Enth�lt alle Aktionen, die mit Strings ausgef�hrt werden k�nnen
 *
 * @author W.Glanzer, 04.11.2014
 */
public class StringUtil
{

  public static String concat(char pConcatChar, Object... pStrings)
  {
    StringBuilder string = new StringBuilder();

    string.append(Arrays.deepToString(pStrings));

    return string.toString();
  }

}
