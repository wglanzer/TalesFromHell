package de.tfh.core.i18n;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 * Enthält alle Error-Meldungen
 *
 * W.Glanzer, 22.10.2014.
 */
public class Exceptions
{
  private static final ExceptionResources res = new ExceptionResources(Locale.getDefault());

  /**
   * Liefert eine ErrorMessage anhand einer ID.
   * FAlls diese ID nicht vorhanden ist, wird die DUMMY_EXC-Exception zurückgegeben
   *
   * @param pID  ID der ErrorMessage
   * @return die ErrorMessage als übersetzten String
   */
  @NotNull
  public static String get(int pID)
  {
    String msg = res.getMessage(pID);
    if(msg == null)
      msg = res.getDefaultMessage();

    return msg;
  }
}
