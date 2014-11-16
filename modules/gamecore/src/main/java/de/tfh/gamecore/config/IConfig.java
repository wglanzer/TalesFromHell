package de.tfh.gamecore.config;

/**
 * Beschreibt eine Configuration
 *
 * @author W.Glanzer, 02.11.2014
 */
public interface IConfig
{

  /**
   * Liefert die Breite der Aufl�sung zur?ck
   *
   * @return Breite der Aufl�sung
   */
  int getScreenWidth();

  /**
   * Liefert die H�he der Aufl�sung zur?ck
   *
   * @return H�he der Aufl�sung
   */
  int getScreenHeight();

  /**
   * Gibt zur�ck, wie oft gemultisamplet wird
   *
   * @return Anzahl der Multisamples
   */
  int getMultisamples();

  /**
   * Liefert die Lautst�rke der Musik in Prozent, sprich von 0 - 1
   *
   * @return Lautst�rke von 0 - 1
   */
  double getMusicVolume();

  /**
   * Liefert die Lautst�rke des Sounds in Prozent, sprich von 0 - 1
   *
   * @return Lautst�rke von 0 - 1
   */
  double getSoundVolume();

  /**
   * Liefert zur�ck, ob der Vollbildmodus verwendet wird
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
   * Gibt zur�ck, ob die Musik aktiviert ist
   *
   * @return <tt>true</tt>, wenn die Musik aktiviert ist
   */
  boolean isMusicEnabled();

  /**
   * Gibt zur�ck, ob der Sound aktiviert ist
   *
   * @return <tt>true</tt>, wenn der Sound aktiviert ist
   */
  boolean isSoundEnabled();

  /**
   * Gibt zur�ck, ob die FPS-Zahlen angezeigt werden sollen
   *
   * @return <tt>true</tt>, wenn diese angezeigt werden sollen, andernfalls <tt>false</tt>
   */
  boolean isShowFPS();

  /**
   * Gibt zur�ck, ob VSync aktiviert ist
   *
   * @return <tt>true</tt>, wenn VSync aktiviert ist
   */
  boolean isVSync();
}
