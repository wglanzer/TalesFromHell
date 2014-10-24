package de.tfh.slick;

import de.tfh.core.exceptions.TFHException;
import de.tfh.core.i18n.Errors;
import de.tfh.slick.logging.TFHLogSystem;
import org.newdawn.slick.util.Log;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Initialisiert das Slick-Dependency
 *
 * W.Glanzer, 22.10.2014.
 */
public class SlickInit
{

  public static void installSlick() throws TFHException
  {
    try
    {
      _initLogging();

      String relPath = SlickInit.class.getProtectionDomain().getCodeSource().getLocation().getFile();
      File file = new File(relPath + "../natives");
      System.setProperty("java.library.path", file.getAbsolutePath());

      Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
      fieldSysPath.setAccessible(true);
      fieldSysPath.set(null, null);
      fieldSysPath.setAccessible(false);
    }
    catch (Exception e)
    {
      throw new TFHException(e, Errors.E0);
    }
  }

  private static void _initLogging()
  {
    Log.setLogSystem(new TFHLogSystem());

    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();
  }
}
