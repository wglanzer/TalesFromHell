package de.tfh.core;

/**
 * Stellt die Verbindung zwischen dem BasicGame und den einzelnen
 * States bereit
 *
 * @author W.Glanzer, 16.11.2014
 */
public interface IGameController
{

  /**
   * Wechselt den State
   *
   * @param pID ID des States
   */
  void enterState(int pID);

}
