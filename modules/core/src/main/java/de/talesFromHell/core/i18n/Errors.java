package de.talesFromHell.core.i18n;

/**
 * Enthält alle Error-Meldungen
 *
 * W.Glanzer, 22.10.2014.
 */
public class Errors
{

  public static final IErrorMessage E0 = _create(0, "Failed to install Slick2D");

  /**
   * Erstellt aus einer ID und einer Message eine IErrorMessage
   *
   * @param pID       ID des Fehlers
   * @param pMessage  Nachricht, die angezeigt werden soll
   * @return IErrorMessage
   */
  private static IErrorMessage _create(int pID, String pMessage)
  {
    return new AbstractErrorMessage(pMessage, pID);
  }
}
