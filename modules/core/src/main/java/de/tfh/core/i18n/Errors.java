package de.tfh.core.i18n;

/**
 * Enthält alle Error-Meldungen
 *
 * W.Glanzer, 22.10.2014.
 */
public class Errors
{

  public static final IErrorMessage EINVALID = _create(-99, "No message");
  public static final IErrorMessage E0_0 = _create(-1, "An unexpected exception occured");
  public static final IErrorMessage E0 = _create(0, "Failed to install Slick2D");
  public static final IErrorMessage E1 = _create(1, "DataModel already registered");
  public static final IErrorMessage E2 = _create(2, "Failed to register DataModel");
  public static final IErrorMessage E3 = _create(3, "Field could not be instantiated");
  public static final IErrorMessage E4 = _create(4, "Value of the field could not be determined");
  public static final IErrorMessage E5 = _create(5, "Failed to set value");
  public static final IErrorMessage E6 = _create(6, "Failed to register DataModel");

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
