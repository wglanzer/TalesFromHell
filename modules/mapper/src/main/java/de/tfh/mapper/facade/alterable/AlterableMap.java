package de.tfh.mapper.facade.alterable;

import de.tfh.core.exceptions.TFHException;
import de.tfh.core.utils.ExceptionUtil;
import de.tfh.datamodels.models.ChunkDataModel;
import de.tfh.datamodels.models.MapDescriptionDataModel;
import de.tfh.datamodels.registry.DefaultDataModelRegistry;
import de.tfh.datamodels.utils.DataModelIOUtil;
import de.tfh.gamecore.map.*;
import de.tfh.gamecore.map.tileset.ITileset;
import de.tfh.gamecore.map.tileset.MapperTileset;
import de.tfh.gamecore.util.MapUtil;
import de.tfh.gamecore.util.ProgressObject;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Diese Map dient dazu, etwas innerhalb verändern zu können.
 * Wird bspw. beim Mapper gebraucht
 *
 * @author W.Glanzer, 20.11.2014
 */
public class AlterableMap extends Map implements IMap
{
  private static final Logger logger = LoggerFactory.getLogger(AlterableMap.class);
  private boolean isSavable = false;
  private final Object SAVE_LOCK = new Object();

  public AlterableMap(boolean pGenerateNewMap, Dimension pChunkCount, Dimension pChunkSize, Dimension pTileSize)
  {
    super(null);
    try
    {
      if(pGenerateNewMap)
        _newMap(pChunkCount, pChunkSize, pTileSize);
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
  public void setTileSet(ITileset<?> pSet)
  {
    graphicTiles = pSet;
  }

  @Override
  public boolean isSavable()
  {
    return isSavable;
  }

  @Override
  public ProgressObject save(OutputStream pOutputStream, int pThreadCount) throws TFHException
  {
    try
    {
      final ZipOutputStream zip = new ZipOutputStream(pOutputStream);
      ProgressObject obj = new ProgressObject();
      ExecutorCompletionService<Object> pool = new ExecutorCompletionService<>(Executors.newFixedThreadPool(pThreadCount));

      //chunks speichern
      for(int i = 0; i < chunks.length; i++)
        pool.submit(new _SaveRunnable(i, zip, obj), new Object());

      // Thread der läuft, wenn der pool fertig ist
      new Thread(() -> {
        try
        {
          for(IChunk chunk : chunks)
            pool.take();

          _saveOthers(zip);
          obj.setFinished();
          zip.close();
        }
        catch(Exception e)
        {
          ExceptionUtil.logError(logger, 49, e);
        }
      }).start();

      return obj;
    }
    catch(Exception e)
    {
      //Map konnte nicht gespeichert werden
      throw new TFHException(e, 42);
    }
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
  public void setTile(int pX, int pY, int pLayer, TileDescription pTile)
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
   * Speichert den zusätzlichen Inhalt
   *
   * @param pStream Stream, auf den geschrieben werden soll
   * @throws TFHException wenn dabei ein Fehler aufgetreten ist
   */
  private void _saveOthers(ZipOutputStream pStream) throws TFHException
  {
    try
    {
      synchronized(SAVE_LOCK)
      {
        ZipEntry entry = new ZipEntry(IMapConstants.DESC_MAP);
        pStream.putNextEntry(entry);
        DataModelIOUtil.writeDataModelXML(mapDesc, pStream);
        pStream.closeEntry();

        //Tiles.png speichern
        ZipEntry entryTiles = new ZipEntry(IMapConstants.TILES);
        pStream.putNextEntry(entryTiles);
        IOUtils.copy(graphicTiles.getImageInputStream(), pStream);
        pStream.closeEntry();
      }
    }
    catch(Exception e)
    {
      throw new TFHException(e, 40);
    }
  }

  /**
   * Erstellt eine neue Map-Instanz
   *
   * @param pChunkCount  Anzahl der Chunks
   * @param pChunkSize   Anzahl der Tiles innerhalb des Chunks
   * @param pTileSize    Größe der Tiles in px
   * @throws de.tfh.datamodels.TFHDataModelException Wenn beim Erstellen der MapDescription ein Fehler aufgetreten ist
   */
  private void _newMap(Dimension pChunkCount, Dimension pChunkSize, Dimension pTileSize) throws TFHException
  {
    mapDesc = (MapDescriptionDataModel) DefaultDataModelRegistry.getDefault().newInstance(MapDescriptionDataModel.class);
    if(mapDesc == null)
      throw new TFHException(31);

    if(pChunkCount != null)
    {
      mapDesc.chunksX = pChunkCount.width;
      mapDesc.chunksY = pChunkCount.height;
    }

    if(pChunkSize != null)
    {
      mapDesc.tilesPerChunkX = pChunkSize.width;
      mapDesc.tilesPerChunkY = pChunkSize.height;
    }

    if(pTileSize != null)
    {
      mapDesc.tileWidth = pTileSize.width;
      mapDesc.tileHeight = pTileSize.height;
    }

    chunks = new AlterableChunk[mapDesc.chunksX * mapDesc.chunksY];
    for(int i = 0; i < chunks.length; i++)
    {
      int y = i / mapDesc.chunksX;
      int x = i - y * mapDesc.chunksX;
      chunks[i] = new AlterableChunk(x, y, mapDesc.tilesPerChunkX, mapDesc.tilesPerChunkY, new Long[mapDesc.tilesPerChunkX * mapDesc.tilesPerChunkY]);
    }
  }


  /**
   * Runnable zum Speichern eines Chunks
   */
  private class _SaveRunnable implements Runnable
  {
    private final int chunkNr;
    private final ZipOutputStream stream;
    private final ProgressObject object;

    public _SaveRunnable(int pChunkNr, ZipOutputStream pStream, ProgressObject pObject)
    {
      chunkNr = pChunkNr;
      stream = pStream;
      object = pObject;
    }

    @Override
    public void run()
    {
      try
      {
        IChunk currChunk = chunks[chunkNr];
        if(currChunk != null)
        {
          if(currChunk instanceof AlterableChunk && ((AlterableChunk) currChunk).isModified())
            currChunk.synchronizeModel(); //Damit Chunk und Datenmodell synchron sind

          synchronized(SAVE_LOCK)
          {
            ZipEntry entry = new ZipEntry(IMapConstants.CHUNK_FOLDER + "chunk" + chunkNr + ".chunk");
            stream.putNextEntry(entry);
            DataModelIOUtil.writeDataModelXML(currChunk.getModel(), stream);
            object.setProgress((100.0D / (double) chunks.length) * (double) chunkNr);
            stream.closeEntry();
          }
        }
      }
      catch(Exception e)
      {
        ExceptionUtil.logError(logger, 41, e);
      }
    }
  }
}
