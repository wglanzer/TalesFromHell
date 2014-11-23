package de.tfh.gamecore.map.alterable;

import de.tfh.core.utils.ExceptionUtil;
import de.tfh.datamodels.TFHDataModelException;
import de.tfh.datamodels.models.MapDescriptionDataModel;
import de.tfh.datamodels.registry.DefaultDataModelRegistry;
import de.tfh.gamecore.map.*;
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
    catch(TFHDataModelException e)
    {
      ExceptionUtil.logError(logger, 9999, e);
    }
  }

  /**
   * Setzt ein Tile auf einem Layer
   *
   * @param pX
   * @param pY
   * @param pLayer
   * @param pTile
   */
  public void setTile(int pX, int pY, int pLayer, TilePreference pTile)
  {
    IChunk chunk = getChunkContaining(pX, pY);
    if(chunk != null)
    {
      ILayer layer = chunk.getLayers(false).get(pLayer);
      if(layer != null)
      {
        int x = pX - chunk.getX() * getChunkCountX();
        int y = pY - chunk.getY() * getChunkCountY();
        //        layer.addTile(pX, pY, pTile);
      }
    }
  }


  /**
   * Erstellt eine neue Map-Instanz
   *
   * @throws de.tfh.datamodels.TFHDataModelException Wenn beim Erstellen der MapDescription ein Fehler aufgetreten ist
   */
  private void _newMap() throws TFHDataModelException
  {
    mapDesc = (MapDescriptionDataModel) DefaultDataModelRegistry.getDefault().newInstance(MapDescriptionDataModel.class);
    validateChunkNulls(mapDesc);
  }
}
