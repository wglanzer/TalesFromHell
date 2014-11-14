package de.tfh.core.utils;

import java.util.Arrays;

/**
 * Enthält alle Aktionen, die mit Strings ausgeführt werden können
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
