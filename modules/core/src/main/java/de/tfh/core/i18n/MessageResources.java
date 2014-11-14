package de.tfh.core.i18n;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Enthält Meldungen, die übersetzt werden können.
 * Keine Exceptions!
 *
 * @see de.tfh.core.i18n.Messages
 * @author W.Glanzer, 14.11.2014
 */
class MessageResources
{

  private ResourceBundle bundle;
  private Map<Integer, String> messageCache;

  private static final Pattern MESSAGE_REGEXP = Pattern.compile("m([0-9]+)");
  private static final Logger logger = LoggerFactory.getLogger(MessageResources.class);

  public static final int DEFAULT_NO_MESSAGE = 9999;
  private static final String DEFAULT_NO_MESSAGE_MESSAGE = "No message defined";

  /**
   * Initialisiert das Bundle mit der angegebenen Locale
   *
   * @param pLocale  Locale des Bundles, oder <tt>null</tt>
   */
  public MessageResources(@Nullable Locale pLocale)
  {
    bundle = ResourceBundle.getBundle("de/tfh/core/i18n/Messages", pLocale == null ? Locale.getDefault() : pLocale);
  }

  /**
   * Liefert eine Nachricht anhand seiner ID
   *
   * @param pID  ID der Nachricht
   * @return Die Nachricht, oder <tt>null</tt>, wenn keine Meldung gefunden wurde
   */
  @Nullable
  public String getMessage(int pID)
  {
    if(messageCache == null)
      getAllMessages(); //Cache aufbauen

    assert messageCache != null; //Wenn der Cache aufgebaut ist, kann das nicht vorkommen

    return messageCache.get(pID);
  }

  /**
   * Liefert alle Nachrichten, die im Bundle vorhanden sind
   *
   * @return Nachrichten als ID -> String - Map
   */
  @NotNull
  public Map<Integer, String> getAllMessages()
  {
    if(messageCache == null || messageCache.size() == 0)
    {
      Set<String> keys = bundle.keySet();
      messageCache = new HashMap<>();
      for(String currKey : keys)
      {
        Integer id = _getErrorID(currKey);
        if(id != null)
        {
          String string = bundle.getString(currKey);
          messageCache.put(id, string);
        }
        else
        {
          // Konnte nicht geladen werden
          logger.warn("Errormessage could not be loaded: %s", currKey);
        }
      }
    }

    return Collections.unmodifiableMap(messageCache);
  }

  /**
   * Nachricht, wenn keine ID angegeben ist
   *
   * @return DefaultNachricht
   */
  @NotNull
  public String getDefaultMessage()
  {
    String msg = getMessage(DEFAULT_NO_MESSAGE);
    return msg != null ? msg : DEFAULT_NO_MESSAGE_MESSAGE;
  }

  /**
   * Liefert die ErrorID, die der übergebene Key beschreibt
   *
   * @param pKey  Key der ErrorMeldung im Bundle
   * @return Die ID der ErrorMeldung, oder <tt>null</tt>, wenn keine ID gefunden werden konnte
   */
  private Integer _getErrorID(String pKey)
  {
    Matcher matcher = MESSAGE_REGEXP.matcher(pKey);
    if(matcher.find())
    {
      String id = matcher.group(1);
      return Integer.valueOf(id);
    }

    return null;
  }

}
