package de.tfh.core.utils;

import de.tfh.core.i18n.Exceptions;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

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

  /**
   * Loggt eine Fehlermeldung auf einem Logger
   *
   * @param pLogger   Logger, auf dem geloggt werden soll
   * @param pID       ID der Fehlermeldung, die geloggt werden soll
   * @param pCause    Fehler, oder <tt>null</tt>
   * @param pDetails  Details zur Fehlermeldung
   */
  public static void logError(@Nullable Logger pLogger, int pID, Throwable pCause, Object... pDetails)
  {
    if(pLogger != null)
      pLogger.error(getErrorString(pID, pDetails), pCause);
  }

  /**
   * Gibt den String zurück, der die ID und die Details kombiniert
   *
   * @param pID       ID der Fehlermeldung
   * @param pDetails  Details zur Fehlermeldung
   * @return Den String, der die ID und die Details kombiniert
   */
  public static String getErrorString(int pID, Object[] pDetails)
  {
    return parseErrorMessage(Exceptions.get(pID), pID) + (pDetails.length > 0 ? " " + StringUtil.concat(',', pDetails) : "");
  }
}
