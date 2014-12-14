package de.tfh.gamecore.map;

import de.tfh.core.exceptions.TFHException;
import de.tfh.core.exceptions.TFHUnsupportedOperationException;
import de.tfh.core.utils.ExceptionUtil;
import de.tfh.datamodels.models.MapDescriptionDataModel;
import de.tfh.datamodels.utils.DataModelIOUtil;
import de.tfh.gamecore.map.tileset.ITileset;
import de.tfh.gamecore.util.MapUtil;
import de.tfh.gamecore.util.ProgressObject;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Stellt eine normale Map dar, auf der gespielt werden kann.
 * Die Zugriffe aufs Datenmodell und ähnliche großartige Zugriffe
 * MÜSSEN GECACHED WERDEN, zur Performanceverbesserung
 *
 * @author W.Glanzer, 16.11.2014
 */
public class Map implements IMap
{
  private static final Logger logger = LoggerFactory.getLogger(IMap.class);

  protected ITileset graphicTiles;
  protected IChunk[] chunks = new IChunk[0];
  protected MapDescriptionDataModel mapDesc;

  public Map(File pZipFile, @Nullable ProgressObject pLoadObj, @Nullable Runnable pRunAfterSuccess)
  {
    try
    {
      if(pZipFile != null)
      {
        ZipFile zip = new ZipFile(pZipFile);

        if(_verifyZipFile(zip))
        {
          _loadFromZipFile(zip, pLoadObj);
          if(pRunAfterSuccess != null)
            pRunAfterSuccess.run();
        }
        else
          throw new RuntimeException("Map could not be loaded");
      }
    }
    catch(Exception e)
    {
      // Map konnte nicht erstellt werden
      ExceptionUtil.logError(logger, 26, e, "zip=" + pZipFile);
    }
  }

  public Map(File pZipFile)
  {
    this(pZipFile, null, null);
  }

  @Override
  public IChunk getChunk(int pX, int pY)
  {
    return chunks[pY * getChunkCountX() + pX];
  }

  @Override
  @Nullable
  public IChunk getChunkContaining(int pX, int pY)
  {
    int x = pX / mapDesc.tilesPerChunkX;
    int y = pY / mapDesc.tilesPerChunkY;
    return chunks[y * getChunkCountX() + x];
  }

  @Override
  public int getChunkCountX()
  {
    if(mapDesc != null)
      return mapDesc.chunksX;

    return 0;
  }

  @Override
  public int getChunkCountY()
  {
    if(mapDesc != null)
      return mapDesc.chunksY;

    return 0;
  }

  @Override
  public int getTileWidth()
  {
    return mapDesc.tileWidth;
  }

  @Override
  public int getTileHeight()
  {
    return mapDesc.tileHeight;
  }

  @Override
  public int getTilesPerChunkX()
  {
    return mapDesc.tilesPerChunkX;
  }

  @Override
  public int getTilesPerChunkY()
  {
    return mapDesc.tilesPerChunkY;
  }

  @Override
  public ITileset getTileSet()
  {
    return graphicTiles;
  }

  @Override
  public ProgressObject save(OutputStream pOutputStream, int pThreadCount) throws TFHException
  {
    throw new TFHUnsupportedOperationException(58);
  }

  @Override
  public void setTileSet(ITileset<?> pSet)
  {
    throw new TFHUnsupportedOperationException(59);
  }

  @Override
  public boolean isSavable()
  {
    return false;
  }

  /**
   * Lädt das Tileset anhand eines Entries
   *
   * @param pStream Stream des Zips
   * @return Tileset
   */
  protected ITileset loadTileset(ZipFile pStream) throws TFHException
  {
    try
    {
      return MapUtil.tilesetFromInputStream(pStream.getInputStream(pStream.getEntry(IMapConstants.TILES)), mapDesc);
    }
    catch(IOException e)
    {
      throw new TFHException(e, 52);
    }
  }

  /**
   * Fügt einen Chunk auf die Map ein.
   * Die Positionen sind schon im Chunk gespeichert
   *
   * @param pChunkStream InputStream des Chunks
   * @param pMapDesc     MapDescription
   * @throws TFHException Wenn dabei ein Fehler aufgetreten ist
   */
  protected IChunk getChunk(InputStream pChunkStream, MapDescriptionDataModel pMapDesc) throws TFHException
  {
    IChunk chunkToAdd = MapUtil.chunkFromInputStream(pChunkStream, pMapDesc);
    if(chunkToAdd != null)
      return chunkToAdd;

    return null;
  }

  /**
   * Überprüft ob das Chunk-Array "null"-Werte enthält.
   * Ersetzt diese ggf. mit einer neuen Chunk-Instanz
   *
   * @param pDesc MapDescription
   */
  private void _validateChunkNulls(MapDescriptionDataModel pDesc)
  {
    for(int i = 0; i < chunks.length; i++)
    {
      IChunk currChunk = chunks[i];
      if(currChunk == null)
      {
        int y = i / pDesc.chunksX;
        int x = i - y * pDesc.chunksX;
        chunks[i] = new Chunk(x, y, pDesc.tilesPerChunkX, pDesc.tilesPerChunkY, new Long[pDesc.tilesPerChunkX * pDesc.tilesPerChunkY]);
      }
    }
  }

  /**
   * Lädt die Map aus einer Zip-Datei.
   * Ab hier kann sichergestellt sein, dass die Zip-Datei
   * die richtige Stuktur enthält.
   */
  private void _loadFromZipFile(ZipFile pZip, @Nullable ProgressObject pLoadObj) throws TFHException
  {
    try
    {
      ZipEntry descMap = pZip.getEntry(IMapConstants.DESC_MAP);
      ThreadPoolExecutor exec = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

      mapDesc = (MapDescriptionDataModel) DataModelIOUtil.readDataModelFromXML(pZip.getInputStream(descMap));
      if(mapDesc == null)
        throw new TFHException(30, "descMap=" + descMap.getName());

      graphicTiles = loadTileset(pZip);
      chunks = new IChunk[mapDesc.chunksX * mapDesc.chunksY];

      // Alle Zip-Einträge durchgehen, darauf
      ArrayList<? extends ZipEntry> list = Collections.list(pZip.entries());
      for(int i = 0; i < list.size(); i++)
      {
        ZipEntry currEle = list.get(i);
        final int finalI = i;

        // Runnable zum Laden eines Chunks
        Runnable loadable = () -> {
          try
          {
            _handleZipEntry(currEle, pZip.getInputStream(currEle));
            if(pLoadObj != null)
              pLoadObj.setProgress(100.0D / (double) list.size() * (double) finalI);
          }
          catch(IOException e)
          {
            ExceptionUtil.logError(logger, 50, e);
          }
        };

        // Entscheidung, ob gleich geladen werden soll, oder in einem extra Threadpool
        if(pLoadObj != null)
          exec.execute(loadable);
        else
          loadable.run();
      }

      // Wurde gleich geladen, oder wurde es im ThreadPoolExecutor geladen?
      if(pLoadObj != null)
      {
        new Thread(() -> {
          while(exec.getQueue().size() > 0)
          {
            try
            {
              Thread.sleep(25);
            }
            catch(Exception e)
            {
              ExceptionUtil.logError(logger, 51, e);
            }
          }

          _validateChunkNulls(mapDesc);
          pLoadObj.setFinished();

        }).start();
      }
      else
        _validateChunkNulls(mapDesc);
    }
    catch(Exception e)
    {
      throw new TFHException(e, 27, "file=" + pZip);
    }
  }

  /**
   * Handlet die ZipEntries
   *
   * @param pNext   Nächstes Element
   * @param pStream Stream des Elements
   */
  private void _handleZipEntry(ZipEntry pNext, InputStream pStream)
  {
    try
    {
      String currName = pNext.getName();

      if(currName.startsWith(IMapConstants.CHUNK_FOLDER))
      {
        IChunk chunkToAdd = getChunk(pStream, mapDesc);
        if(chunkToAdd != null)
          chunks[chunkToAdd.getY() * mapDesc.chunksX + chunkToAdd.getX()] = chunkToAdd;
      }
      else if(currName.startsWith(IMapConstants.CLASSES_FOLDER))
        _addClass(pStream);
    }
    catch(Exception e)
    {
      throw new RuntimeException(e); //todo TFHRuntimeException
    }
  }

  /**
   * Fügt eine Klasse zur Map hinzu
   *
   * @param pClassStream InputStream der Klasse, die hinzugefügt werden soll
   */
  private void _addClass(InputStream pClassStream)
  {
    // todo
  }

  /**
   * Verifiziert das ZipFile, ob auch wirklich alle nötigen Einträge vorhanden sind
   *
   * @param pZipFile ZipFile, das verifiziert werden soll
   * @return <tt>true</tt>, wenn alles OK ist, andernfalls <tt>false</tt>
   */
  private boolean _verifyZipFile(ZipFile pZipFile)
  {
    ZipEntry tilesPNG = pZipFile.getEntry(IMapConstants.TILES);
    ZipEntry descMap = pZipFile.getEntry(IMapConstants.DESC_MAP);

    return tilesPNG != null && descMap != null;
  }
}
