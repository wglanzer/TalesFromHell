package de.tfh.core.i18n;

/**
 * Enthält alle Error-Meldungen
 *
 * W.Glanzer, 22.10.2014.
 */
public class Errors
{

  public static final IErrorMessage E0_0 = _create(-1, "An unexpected exception occured");
  public static final IErrorMessage E1 = _create(0, "Failed to install Slick2D");

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
