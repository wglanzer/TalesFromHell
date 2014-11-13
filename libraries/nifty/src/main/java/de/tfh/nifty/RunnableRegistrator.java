package de.tfh.nifty;

import de.tfh.core.exceptions.TFHException;
import de.tfh.core.utils.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Hier können sich runnables registrieren und per
 * UUID wieder ausgeführt werden
 *
 * @author W.Glanzer, 13.11.2014
 */
class RunnableRegistrator
{

  private static final Map<String, Runnable> MAP = new HashMap<>();
  private static final Logger logger = LoggerFactory.getLogger(RunnableRegistrator.class);

  /**
   * Registriert ein Runnable
   *
   * @param pRunnable  Runnable das registriert werden soll
   * @return UUID als String
   */
  public static String register(Runnable pRunnable)
  {
    String uid = UUID.randomUUID().toString();
    MAP.put(uid, pRunnable);
    return uid;
  }

  /**
   * Entfernt ein Runnable mit der übergebenen UUID
   *
   * @param pUUID  UUID des Runnables, das deregistriert werden soll
   * @return <tt>true</tt>, wenn das Runnable entfernt wurde, andernfalls <tt>false</tt>
   */
  public static boolean unregister(String pUUID)
  {
    Runnable removed = MAP.remove(pUUID);
    return removed != null;
  }

  /**
   * Führt ein Runnable anhand der UUID aus
   *
   * @param pUUID  UUID des Runnables
   * @throws TFHException Wenn ein Fehler während der Ausführung auftritt
   */
  public static void exec(String pUUID) throws TFHException
  {
    Runnable run = MAP.get(pUUID);
    if(run == null)
      // Runnable nicht registriert
      throw new TFHException(null, 15, "uuid=", pUUID);

    try
    {
      run.run();
    }
    catch(Exception e)
    {
      // Fehler beim Ausführen des Runnables
      ExceptionUtil.logError(logger, 16, e, "uuid=", pUUID);
    }
  }


}
