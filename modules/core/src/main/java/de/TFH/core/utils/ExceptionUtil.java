package de.tfh.core.utils;

import de.tfh.core.i18n.IErrorMessage;
import org.jetbrains.annotations.Nullable;

/**
 * Allgemeine Util-Klasse für Exceptions
 *
 * W.Glanzer, 22.10.2014.
 */
public class ExceptionUtil
{

  private static final String MESSAGEFORMAT = "%s - %s";

  /**
   * Wandelt eine IErrorMessage in einen String um
   *
   * @param pMessage  Message, die geparst werden soll
   * @return Message als String, oder <tt>null</tt>
   */
  @Nullable
  public static String parseErrorMessage(IErrorMessage pMessage)
  {
    if(pMessage == null)
      return null;

    return String.format(MESSAGEFORMAT, pMessage.getID(), pMessage.getMessage());
  }

}
