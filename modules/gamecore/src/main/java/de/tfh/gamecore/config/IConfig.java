package de.tfh.gamecore.config;

/**
 * Beschreibt eine Configuration
 *
 * @author W.Glanzer, 02.11.2014
 */
public interface IConfig
{

  /**
   * Liefert die Breite der Auflösung zur?ck
   *
   * @return Breite der Auflösung
   */
  int getScreenWidth();

  /**
   * Liefert die Höhe der Auflösung zur?ck
   *
   * @return Höhe der Auflösung
   */
  int getScreenHeight();

  /**
   * Gibt zurück, wie oft gemultisamplet wird
   *
   * @return Anzahl der Multisamples
   */
  int getMultisamples();

  /**
   * Liefert die Lautstärke der Musik in Prozent, sprich von 0 - 1
   *
   * @return Lautstärke von 0 - 1
   */
  double getMusicVolume();

  /**
   * Liefert die Lautstärke des Sounds in Prozent, sprich von 0 - 1
   *
   * @return Lautstärke von 0 - 1
   */
  double getSoundVolume();

  /**
   * Liefert zurück, ob der Vollbildmodus verwendet wird
   *
   * @return <tt>true</tt>, wenn Vollbild
   */
  boolean isFullscreen();

  /**
   * Gibt an, ob das Spiel nur geupdated werden soll,
   * wenn es im Vordergrund ist
   *
   * @return <tt>true</tt>, wenn dies zutrifft
   */
  boolean isOnlyUpdateWhenVisible();

  /**
   * Gibt zurück, ob die Musik aktiviert ist
   *
   * @return <tt>true</tt>, wenn die Musik aktiviert ist
   */
  boolean isMusicEnabled();

  /**
   * Gibt zurück, ob der Sound aktiviert ist
   *
   * @return <tt>true</tt>, wenn der Sound aktiviert ist
   */
  boolean isSoundEnabled();

  /**
   * Gibt zurück, ob die FPS-Zahlen angezeigt werden sollen
   *
   * @return <tt>true</tt>, wenn diese angezeigt werden sollen, andernfalls <tt>false</tt>
   */
  boolean isShowFPS();

  /**
   * Gibt zurück, ob VSync aktiviert ist
   *
   * @return <tt>true</tt>, wenn VSync aktiviert ist
   */
  boolean isVSync();
}
