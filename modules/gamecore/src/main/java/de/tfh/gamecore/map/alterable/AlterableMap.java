package de.tfh.gamecore.map.alterable;

import de.tfh.core.exceptions.TFHException;
import de.tfh.core.utils.ExceptionUtil;
import de.tfh.datamodels.models.MapDescriptionDataModel;
import de.tfh.datamodels.registry.DefaultDataModelRegistry;
import de.tfh.gamecore.map.IChunk;
import de.tfh.gamecore.map.IMap;
import de.tfh.gamecore.map.Map;
import de.tfh.gamecore.map.TilePreference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Diese Map dient dazu, etwas innerhalb verändern zu können.
 * Wird bspw. beim Mapper gebraucht
 *
 * @author W.Glanzer, 20.11.2014
 */
public class AlterableMap extends Map implements IMap
{
  private static final Logger logger = LoggerFactory.getLogger(AlterableMap.class);

  public AlterableMap()
  {
    super(null);
    try
    {
      _newMap();
    }
    catch(TFHException e)
    {
      // Neue Map konnte nicht erstellt werden
      ExceptionUtil.logError(logger, 32, e);
    }
  }

  /**
   * Setzt ein Tile auf einem Layer
   *
   * @param pX      X-Position des Tiles auf der Map
   * @param pY      Y-Position des Tiles auf der Map
   * @param pLayer  Layer, auf dem das Tile positioniert werden soll
   * @param pTile   Tile das gesetzt werden soll
   */
  public void setTile(int pX, int pY, int pLayer, TilePreference pTile)
  {
    IChunk chunk = getChunkContaining(pX, pY);
    if(chunk != null)
    {
      AlterableLayer layer = (AlterableLayer) chunk.getLayers(false).get(pLayer);
      if(layer != null)
      {
        int x = pX - chunk.getX() * getChunkCountX();
        int y = pY - chunk.getY() * getChunkCountY();
        layer.addTile(x, y, pTile);
      }
    }
  }


  /**
   * Erstellt eine neue Map-Instanz
   *
   * @throws de.tfh.datamodels.TFHDataModelException Wenn beim Erstellen der MapDescription ein Fehler aufgetreten ist
   */
  private void _newMap() throws TFHException
  {
    mapDesc = (MapDescriptionDataModel) DefaultDataModelRegistry.getDefault().newInstance(MapDescriptionDataModel.class);
    if(mapDesc == null)
      throw new TFHException(31);

    chunks = new AlterableChunk[mapDesc.chunksX * mapDesc.chunksY];
    for(int i = 0; i < chunks.length; i++)
    {
      int y = i / mapDesc.chunksX;
      int x = i - y * mapDesc.chunksX;
      chunks[i] = new AlterableChunk(x, y, mapDesc.tilesPerChunkX, mapDesc.tilesPerChunkY, new Long[0]);
    }

  }
}
