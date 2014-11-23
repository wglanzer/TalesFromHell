package de.tfh.mapper;

import de.tfh.gamecore.map.Chunk;
import de.tfh.gamecore.map.Layer;
import de.tfh.gamecore.map.Map;
import de.tfh.gamecore.map.TilePreference;

import java.io.File;

/**
 * @author W.Glanzer, 20.11.2014
 */
public class AlterableMap extends Map
{

  public AlterableMap(File pZipFile)
  {
    super(pZipFile);
  }

  public void setTile(int pX, int pY, int pLayer, TilePreference pTile)
  {
    Chunk chunk = getChunkContaining(pX, pY);
    if(chunk != null)
    {
      Layer layer = chunk.getLayers(false).get(pLayer);
      if(layer != null)
      {
        int x = pX - chunk.getX() * getChunkCountX();
        int y = pY - chunk.getY() * getChunkCountY();
//        layer.addTile(pX, pY, pTile);
      }
    }
  }

}
