package de.tfh.core;

import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

import java.io.File;

/**
 * Liefert alle Pfade
 *
 * @author W.Glanzer, 13.12.2014
 */
interface IStaticPaths
{
  /**
   * Relativer Pfad zur config.xml
   */
  public static final String CONFIG_RELATIVE_PATH = "config.xml";

  /**
   * Pfad zum Speicherordner in [...]/Appdata/Roaming
   */
  public static final String ROAMING_PATH = _Helper.getOrCreateRoamingFolder();

  /**
   * Pfad des Map-Ordners in [...]/Appdata/Roaming
   */
  public static final String MAP_PATH = _Helper.getOrCreateMapFolder();


  /**
   * Impl einer Klasse
   * Hilfsklasse für Pfade
   */
  static class _Helper
  {
    /*AppDirs-Instanz*/
    private static final AppDirs APP_DIRS = AppDirsFactory.getInstance();

    /**
     * Liefert den Roaming-Ordner oder erstellt ihn neu, falls noch nicht vorhanden
     */
    static String getOrCreateRoamingFolder()
    {
      String path = APP_DIRS.getUserDataDir(IStaticResources.MAIN_TITLE.replaceAll(" ", "").toLowerCase(), IStaticResources.VERSION, null, true) + "/../..";
      _createPathIfNecessary(path);
      return path;
    }

    /**
     * Liefert den Map-Ordner oder erstellt ihn neu, falls noch nicht vorhanden
     */
    static String getOrCreateMapFolder()
    {
      String path = IStaticPaths.ROAMING_PATH + "/maps";
      _createPathIfNecessary(path);
      return path;
    }

    /**
     * Erstellt den übergebenen Pfad, wenn nötig
     */
    private static void _createPathIfNecessary(String pPath)
    {
      File file = new File(pPath);
      if(!file.exists())
        file.mkdirs();
    }
  }
}
