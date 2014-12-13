package de.tfh.gamecore;

import org.newdawn.slick.Input;

/**
 * @author W.Glanzer, 11.12.2014
 */
public interface IUpdateable
{

  /**
   * Führt den "Update"-Befehl aus
   *
   * @param pDelta  Zeit seit dem letzten Frame in ms
   * @param pInput  Input-Objekt für Tastaturabfragen
   */
  void update(int pDelta, Input pInput);

}
