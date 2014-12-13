package de.tfh.core;

import org.jetbrains.annotations.Nullable;

import java.io.File;

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

  /**
   * Liefert das derzeitige File-Object der derzeitigen Map
   *
   * @return das derzeitige File-Object der derzeitigen Map
   */
  @Nullable
  File getCurrentMapFileObject();
}
