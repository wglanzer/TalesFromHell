package de.tfh.core;

/**
 * Enth�lt alle statischen Ressourcen
 *
 * @author W.Glanzer, 25.10.2014
 */
public interface IStaticResources
{

  /**
   * Der Titel des Spiels
   */
  public static final String MAIN_TITLE = "Tales From Hell";

  /**
   * Die Version des Spiels.
   * Falls diese ge�ndert wird, sollte in den pom.xmls die
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
   * Relativer Pfad zur config.xml
   */
  public static final String CONFIG_RELATIVE_PATH = "config.xml";
}
