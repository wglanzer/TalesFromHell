package de.tfh.gamecore.map.alterable;

import de.tfh.core.exceptions.TFHException;
import de.tfh.core.utils.ExceptionUtil;
import de.tfh.datamodels.models.ChunkDataModel;
import de.tfh.datamodels.models.MapDescriptionDataModel;
import de.tfh.datamodels.registry.DefaultDataModelRegistry;
import de.tfh.gamecore.map.*;
import de.tfh.gamecore.map.tileset.ITileset;
import de.tfh.gamecore.map.tileset.MapperTileset;
import de.tfh.gamecore.util.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.zip.ZipFile;

/**
 * Diese Map dient dazu, etwas innerhalb verändern zu können.
 * Wird bspw. beim Mapper gebraucht
 *
 * @author W.Glanzer, 20.11.2014
 */
public class AlterableMap extends Map implements IMap
{
  private static final Logger logger = LoggerFactory.getLogger(AlterableMap.class);

  public AlterableMap(boolean pGenerateNewMap)
  {
    super(null);
    try
    {
      if(pGenerateNewMap)
        _newMap();
    }
    catch(TFHException e)
    {
      // Neue Map konnte nicht erstellt werden
      ExceptionUtil.logError(logger, 32, e);
    }
  }

  public AlterableMap(File pZipFile, ProgressObject pLoadObj, Runnable pRunAfterSuccess)
  {
    super(pZipFile, pLoadObj, pRunAfterSuccess);
    setSavable(true);
  }

  @Override
  protected ITileset loadTileset(ZipFile pStream) throws TFHException
  {
    try
    {
      InputStream imageStream = pStream.getInputStream(pStream.getEntry(IMapConstants.TILES));
      BufferedImage tilesetImage = ImageIO.read(imageStream);
      return new MapperTileset(tilesetImage, mapDesc.tileWidth, mapDesc.tileHeight);
    }
    catch(Exception e)
    {
      throw new TFHException(e, 53);
    }
  }

  @Override
  protected IChunk getChunk(InputStream pChunkStream, MapDescriptionDataModel pMapDesc) throws TFHException
  {
    IChunk chunk = MapUtil.chunkFromInputStream(pChunkStream, pMapDesc);
    if(chunk != null)
    {
      ChunkDataModel model = chunk.getModel();
      if(model != null)
        return new AlterableChunk(model, mapDesc.tilesPerChunkX, mapDesc.tilesPerChunkY);
    }

    return null;
  }

  /**
   * Setzt, ob die Map gespeichert werden kann
   *
   * @param pIsSavable  <tt>true</tt>, wenn die Map gespeichert werden kann
   */
  public void setSavable(boolean pIsSavable)
  {
    isSavable = pIsSavable;
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
      chunks[i] = new AlterableChunk(x, y, mapDesc.tilesPerChunkX, mapDesc.tilesPerChunkY, new Long[mapDesc.tilesPerChunkX * mapDesc.tilesPerChunkY]);
    }

  }
}
