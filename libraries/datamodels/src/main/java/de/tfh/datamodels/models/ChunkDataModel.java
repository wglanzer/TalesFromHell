package de.tfh.datamodels.models;

import de.tfh.datamodels.AbstractDataModel;

/**
 * Stellt einen einzelnen Chunk dar. Dieser wird Separat auf
 * Platte gespeichert
 *
 * @author W.Glanzer, 16.11.2014
 */
public class ChunkDataModel extends AbstractDataModel
{
  /**
   * X-Wert des Chunks beim Erzeugen des Datenmodells
   */
  public int x = 0;

  /**
   * Y-Wert des Chunks beim Erzeugen des Datenmodells
   */
  public int y = 0;

  /**
   * Stellt ein 64-Bit-Array dar, aus dem die Tiles bestehen.
   * Aufteilung:
   * 64 Bits gesammt, die in 4 Layer unterteilt werden.
   * --> 16Bit pro Layer, die wiederrum in 2 Kolonnen unterteilt werden:
   *  --> 1. Kolonne á 8Bit ("von rechts"): ID des Tile
   *  --> 2. Kolonne á 8Bit ("von rechts"): Attribute des Tile
   */
  public Long[] tiles = new Long[0];

  @Override
  public String getName()
  {
    return "ChunkDataModel";
  }
}
