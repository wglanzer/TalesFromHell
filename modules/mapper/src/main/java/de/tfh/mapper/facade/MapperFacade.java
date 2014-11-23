package de.tfh.mapper.facade;

import de.tfh.gamecore.map.TilePreference;
import de.tfh.mapper.AlterableMap;
import de.tfh.mapper.TFHMappperException;
import org.jetbrains.annotations.NotNull;

/**
 * @author W.Glanzer, 20.11.2014
 */
public class MapperFacade implements IMapperFacade
{
  private final AlterableMap map;

  public MapperFacade(@NotNull AlterableMap pMap)
  {
    map = pMap;
  }

  @Override
  public void setTile(int pX, int pY, int pLayer, TilePreference pTilePreference) throws TFHMappperException
  {
    if(pLayer < 0)
      throw new TFHMappperException(9999, "layer=" + pLayer);

    map.setTile(pX, pY, pLayer, pTilePreference);
  }
}
