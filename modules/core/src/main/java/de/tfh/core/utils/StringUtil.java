package de.tfh.core.utils;

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

    for(int i = 0; i < pStrings.length; i++)
    {
      Object currString = pStrings[i];
      string.append(currString.toString());
      if(i + 1 < pStrings.length)
      {
        string.append(pConcatChar);
        string.append(" ");
      }
    }

    return string.toString();
  }

}
