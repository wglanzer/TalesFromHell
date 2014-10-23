package de.talesFromHell.slick;

import de.talesFromHell.core.exceptions.HellException;
import de.talesFromHell.core.i18n.Errors;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Initialisiert das Slick-Dependency
 *
 * W.Glanzer, 22.10.2014.
 */
public class SlickInit
{

  public static void installSlick() throws HellException
  {
    try
    {
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
      throw new HellException(e, Errors.E0);
    }
  }

}
