package de.tfh.nifty;

import de.tfh.core.exceptions.TFHException;
import de.tfh.core.utils.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Kann Runnables ausführen
 *
 * @author W.Glanzer, 13.11.2014
 */
public class RunnableRegistratorScreenController extends DefaultScreenController
{

  public static final String REGISTERED_RUNNABLE_METHOD_DECLARATION = "registeredRunnable(%s)";

  private static final Logger logger = LoggerFactory.getLogger(RunnableRegistratorScreenController.class);

  /**
   * Führt per Reflection ein Runnable aus
   *
   * @param pUUID  UUID des Runnables des Buttons
   */
  @SuppressWarnings("UnusedDeclaration") // Per Reflection!
  public void registeredRunnable(String pUUID)
  {
    try
    {
      RunnableRegistrator.exec(pUUID);
    }
    catch(TFHException e)
    {
      ExceptionUtil.logError(logger, 17, e, "uuid=", pUUID);
    }
  }

}
