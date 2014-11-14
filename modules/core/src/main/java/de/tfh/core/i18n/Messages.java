package de.tfh.core.i18n;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 * @author W.Glanzer, 14.11.2014
 */
public class Messages
{

  private static final MessageResources res = new MessageResources(Locale.getDefault());

  /**
   * Liefert eine ErrorMessage anhand einer ID.
   * Falls die ID nicht vorhanden ist, wird eine Dummy-Message zur�ckgegeben
   *
   * @param pID  ID der MessageResource
   * @return die ErrorMessage als �bersetzten String
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
