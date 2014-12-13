package de.tfh.slick;

import de.tfh.core.exceptions.TFHException;
import de.tfh.slick.logging.TFHLogSystem;
import org.newdawn.slick.util.Log;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.File;
import java.lang.reflect.Field;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

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

      System.setProperty("java.library.path", _getLibraryPath());

      // Neuberechnung des java.library.path's
      Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
      fieldSysPath.setAccessible(true);
      fieldSysPath.set(null, null);
      fieldSysPath.setAccessible(false);
    }
    catch(Exception e)
    {
      throw new TFHException(e, 0);
    }
  }

  /**
   * Liefert den Pfad der "natives"-Library
   *
   * @return Pfad der natives-Library
   */
  private static String _getLibraryPath()
  {
    File outterIDE = new File("natives");
    if(outterIDE.exists() && outterIDE.isDirectory())
      return outterIDE.getAbsolutePath();
    else
    {
      String relPath = SlickInit.class.getProtectionDomain().getCodeSource().getLocation().getFile();
      File file = new File(relPath + "../natives");
      return file.getAbsolutePath();
    }
  }

  /**
   * Initialisiert das Logging
   */
  private static void _initLogging()
  {
    Log.setLogSystem(new TFHLogSystem());

    // Filter, um die sinnlose Zeile der Log-Ausgabe zu vermeiden
    Logger.getLogger("net.java.games.input.ControllerEnvironment").setFilter(new Filter()
    {
      @Override
      public boolean isLoggable(LogRecord record)
      {
        record.setMessage(record.getMessage().replaceAll("\\n", ""));
        return true;
      }
    });

    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();
  }
}
