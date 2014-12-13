package de.tfh.core;

import java.awt.*;

/**
 * Enthält alle statischen Ressourcen
 *
 * @author W.Glanzer, 25.10.2014
 */
public interface IStaticResources extends IStaticPaths
{

  /**
   * Der Titel des Spiels
   */
  public static final String MAIN_TITLE = "Tales From Hell";

  /**
   * Die Version des Spiels.
   * Falls diese geändert wird, sollte in den pom.xmls die
   * Version ebenfalls angepasst werden!
   */
  public static final String VERSION = "1.0";

  /**
   * Titel des Fensters des Spiels
   * {0} = Titel
   * {1} = Version
   */
  public static final String WINDOW_TITLE = "{0} - {1}";

  /**
   * Standardgröße des Mappers
   */
  public static final Dimension MAPPER_SIZE = new Dimension(1600, 900);

  /**
   * Titel des Fensters des Mappers
   * {0} = Titel
   * {1} = Version
   */
  public static final String MAPPER_TITLE = "{0} - {1} - Mapper";

  /**
   * Dateiendung der Map-Datei
   */
  public static final String MAP_FILEENDING = "map";
}
