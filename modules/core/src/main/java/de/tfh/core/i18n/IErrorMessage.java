package de.tfh.core.i18n;

/**
 * Beschreibt eine Fehlermeldung
 *
 * W.Glanzer, 22.10.2014.
 */
public interface IErrorMessage
{

  /**
   * Nachricht der Fehlermeldung als String
   *
   * @return Fehlermeldung als String
   */
  String getMessage();

  /**
   * ID der Fehlermeldung
   *
   * @return ID als Zahl
   */
  int getID();

}
