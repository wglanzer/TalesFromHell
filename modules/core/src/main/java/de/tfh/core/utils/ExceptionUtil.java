package de.tfh.core.utils;

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
   * Formatiert eine Fehlermeldung, um diese ausgeben zu können
   *
   * @param pMessage  Nachricht, die formatiert werden soll
   * @return Formatierter String
   */
  @Nullable
  public static String parseErrorMessage(String pMessage, int pID)
  {
    return String.format(MESSAGEFORMAT, pID, pMessage);
  }

}
