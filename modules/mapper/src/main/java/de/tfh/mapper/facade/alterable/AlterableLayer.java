package de.tfh.mapper.facade.alterable;

import de.tfh.gamecore.map.ILayer;
import de.tfh.gamecore.map.Layer;
import de.tfh.gamecore.map.TileDescription;

/**
 * Layer, der bearbeitet werden kann
 *
 * @author W.Glanzer, 23.11.2014
 */
public class AlterableLayer extends Layer implements ILayer
{
  private boolean modified = false;

  public AlterableLayer(int pTilesX, int pTilesY)
  {
    super(pTilesX, pTilesY);
  }

  public AlterableLayer(int pTilesX, int pTilesY, TileDescription[] pTiles)
  {
    super(pTilesX, pTilesY, pTiles);
  }

  /**
   * Fügt ein Tile auf diesen Layer hinzu
   *
   * @param pX               X-Position des Tiles
   * @param pY               Y-Position des Tiles
   * @param pTileDescription  Neue TilePreferences
   */
  public void addTile(int pX, int pY, TileDescription pTileDescription)
  {
    tilesOnLayer[getTilesX() * pY + pX] = pTileDescription;
    modified = true;
  }

  /**
   * Gibt zurück, ob der Layer modifiziert wurde
   *
   * @return <tt>true</tt>, wenn er modifiziert wurde
   */
  public boolean isModified()
  {
    return modified;
  }

  /**
   * Setzt den Layer wieder auf unmodifiziert
   */
  protected void setUnmodified()
  {
    modified = false;
  }
}
