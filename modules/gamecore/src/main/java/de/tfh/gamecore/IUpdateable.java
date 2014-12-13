package de.tfh.gamecore;

import org.newdawn.slick.Input;

/**
 * @author W.Glanzer, 11.12.2014
 */
public interface IUpdateable
{

  /**
   * F�hrt den "Update"-Befehl aus
   *
   * @param pDelta  Zeit seit dem letzten Frame in ms
   * @param pInput  Input-Objekt f�r Tastaturabfragen
   */
  void update(int pDelta, Input pInput);

}
