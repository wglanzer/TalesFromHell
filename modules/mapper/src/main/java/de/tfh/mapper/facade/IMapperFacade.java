package de.tfh.mapper.facade;

import de.tfh.gamecore.map.TilePreference;
import de.tfh.mapper.TFHMappperException;

/**
 * Stellt die Verbindung zw. GUI und Backend her
 *
 * @author W.Glanzer, 20.11.2014
 */
public interface IMapperFacade
{

  /**
   * Setzt die ID eines Tiles auf einer bestimmten Position
   *
   * @param pX              X-Position des Tiles
   * @param pY              Y-Position des Tiles
   * @param pLayer          Layer-ID des Tiles
   * @param pTilePreference ID des Tiles an sich
   */
  void setTile(int pX, int pY, int pLayer, TilePreference pTilePreference) throws TFHMappperException;

}
