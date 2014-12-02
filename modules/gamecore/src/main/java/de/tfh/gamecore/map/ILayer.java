package de.tfh.gamecore.map;

/**
 * Beschreibt einen Layer der Map
 *
 * @author W.Glanzer, 23.11.2014
 */
public interface ILayer
{
  /**
   * Liefert ein Tile auf diesem Layer, oder <tt>null</tt>
   *
   * @param pX  X-Position des Tiles
   * @param pY  Y-Position des Tiles
   * @return Die TilePreferences, oder <tt>null</tt>
   */
  TileDescription getTile(int pX, int pY);

  /**
   * Anzahl der Tiles in X-Richtung
   *
   * @return Anzahl der Tiles in X-Richtung
   */
  int getTilesX();

  /**
   * Anzahl der Tiles in Y-Richtung
   *
   * @return Anzahl der Tiles in Y-Richtung
   */
  int getTilesY();
}
