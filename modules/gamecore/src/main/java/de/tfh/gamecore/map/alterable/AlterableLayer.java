package de.tfh.gamecore.map.alterable;

import de.tfh.gamecore.map.Layer;
import de.tfh.gamecore.map.TilePreference;

/**
 * Layer, der bearbeitet werden kann
 *
 * @author W.Glanzer, 23.11.2014
 */
public class AlterableLayer extends Layer
{
  public AlterableLayer(int pTilesX, int pTilesY)
  {
    super(pTilesX, pTilesY);
  }

  /**
   * Fügt ein Tile auf diesen Layer hinzu
   *
   * @param pX               X-Position des Tiles
   * @param pY               Y-Position des Tiles
   * @param pTilePreference  Neue TilePreferences
   */
  public void addTile(int pX, int pY, TilePreference pTilePreference)
  {
    tilesOnLayer[getTilesX() * pY + pX] = pTilePreference;
  }
}
