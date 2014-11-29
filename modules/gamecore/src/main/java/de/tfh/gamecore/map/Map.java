package de.tfh.gamecore.map;

import de.tfh.core.exceptions.TFHException;
import de.tfh.core.utils.ExceptionUtil;
import de.tfh.datamodels.models.MapDescriptionDataModel;
import de.tfh.datamodels.utils.DataModelIOUtil;
import de.tfh.gamecore.map.tileset.ITileset;
import de.tfh.gamecore.util.MapUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Stellt eine normale Map dar, auf der gespielt werden kann.
 * Die Zugriffe aufs Datenmodell und �hnliche gro�artige Zugriffe
 * M�SSEN GECACHED WERDEN, zur Performanceverbesserung
 *
 * @author W.Glanzer, 16.11.2014
 */
public class Map implements IMap
{
  protected ITileset<Image> graphicTiles = null;
  protected IChunk[] chunks = new IChunk[0];
  protected MapDescriptionDataModel mapDesc;
  protected boolean isSavable = false;

  private final Object SAVE_LOCK = new Object();
  private static final Logger logger = LoggerFactory.getLogger(IMap.class);

  public Map(File pZipFile)
  {
    try
    {
      if(pZipFile != null)
      {
        ZipFile zip = new ZipFile(pZipFile);

        if(_verifyZipFile(zip))
          _loadFromZipFile(zip);
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

  @Override
  public IChunk getChunk(int pX, int pY)
  {
    return chunks[pY * getChunkCountX() + pX];
  }

  @Override
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
  public void setTileSet(ITileset<?> pSet)
  {
    graphicTiles = (ITileset<Image>) pSet;
  }

  @Override
  public ITileset getTileSet()
  {
    return graphicTiles;
  }

  @Override
  public MapSaveObject save(OutputStream pOutputStream, int pThreadCount) throws TFHException
  {
    try
    {
      final ZipOutputStream zip = new ZipOutputStream(pOutputStream);
      MapSaveObject obj = new MapSaveObject();
      ExecutorService pool = Executors.newFixedThreadPool(pThreadCount);

      //chunks speichern
      for(int i = 0; i < chunks.length; i++)
      {
        final int finalI = i;
        pool.submit(() -> new _SaveRunnable(finalI, zip, (ThreadPoolExecutor) pool, obj));
      }

      return obj;
    }
    catch(Exception e)
    {
      //Map konnte nicht gespeichert werden
      throw new TFHException(e, 42);
    }
  }

  @Override
  public boolean isSavable()
  {
    return isSavable;
  }

  /**
   * Speichert den zus�tzlichen Inhalt
   *
   * @param pStream  Stream, auf den geschrieben werden soll
   * @throws TFHException wenn dabei ein Fehler aufgetreten ist
   */
  private void _saveOthers(ZipOutputStream pStream) throws TFHException
  {
    try
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
    catch(Exception e)
    {
      throw new TFHException(e, 40);
    }
  }

  /**
   * �berpr�ft ob das Chunk-Array "null"-Werte enth�lt.
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
   * L�dt die Map aus einer Zip-Datei.
   * Ab hier kann sichergestellt sein, dass die Zip-Datei
   * die richtige Stuktur enth�lt.
   */
  private void _loadFromZipFile(ZipFile pZip) throws TFHException
  {
    try
    {
      ZipEntry tilesPNG = pZip.getEntry(IMapConstants.TILES);
      ZipEntry descMap = pZip.getEntry(IMapConstants.DESC_MAP);

      mapDesc = (MapDescriptionDataModel) DataModelIOUtil.readDataModelFromXML(pZip.getInputStream(descMap));
      if(mapDesc == null)
        throw new TFHException(30, "descMap=" + descMap.getName());

      graphicTiles = MapUtil.tilesetFromInputStream(pZip.getInputStream(tilesPNG), mapDesc);
      chunks = new IChunk[mapDesc.chunksX * mapDesc.chunksY];

      // Alle Zip-Eintr�ge durchgehen, darauf
      Enumeration<? extends ZipEntry> allZipEntries = pZip.entries();
      while(allZipEntries.hasMoreElements())
      {
        try
        {
          ZipEntry currEntry = allZipEntries.nextElement();
          InputStream is = pZip.getInputStream(currEntry);
          String currName = currEntry.getName();

          if(currName.startsWith(IMapConstants.CHUNK_FOLDER))
            _addChunk(is, mapDesc);
          else if(currName.startsWith(IMapConstants.CLASSES_FOLDER))
            _addClass(is);
        }
        catch(Exception e)
        {
          throw new RuntimeException(e); //todo TFHRuntimeException
        }
      }

      _validateChunkNulls(mapDesc);
    }
    catch(Exception e)
    {
      throw new TFHException(e, 27, "file=" + pZip);
    }
  }

  /**
   * F�gt eine Klasse zur Map hinzu
   *
   * @param pClassStream InputStream der Klasse, die hinzugef�gt werden soll
   */
  private void _addClass(InputStream pClassStream)
  {
    // todo
  }

  /**
   * F�gt einen Chunk auf die Map ein.
   * Die Positionen sind schon im Chunk gespeichert
   *
   * @param pChunkStream InputStream des Chunks
   * @param pMapDesc     MapDescription
   * @throws TFHException Wenn dabei ein Fehler aufgetreten ist
   */
  private void _addChunk(InputStream pChunkStream, MapDescriptionDataModel pMapDesc) throws TFHException
  {
    IChunk chunkToAdd = MapUtil.chunkFromInputStream(pChunkStream, pMapDesc);
    if(chunkToAdd != null)
      chunks[chunkToAdd.getY() * pMapDesc.chunksX + chunkToAdd.getX()] = chunkToAdd;
  }

  /**
   * Verifiziert das ZipFile, ob auch wirklich alle n�tigen Eintr�ge vorhanden sind
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

  /**
   * Runnable zum Speichern eines Chunks
   */
  private class _SaveRunnable implements Runnable
  {
    private final int chunkNr;
    private final ZipOutputStream stream;
    private final ThreadPoolExecutor pool;
    private final MapSaveObject object;

    public _SaveRunnable(int pChunkNr, ZipOutputStream pStream, ThreadPoolExecutor pPool, MapSaveObject pObject)
    {
      chunkNr = pChunkNr;
      stream = pStream;
      pool = pPool;
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
          currChunk.synchronizeModel(); //Damit Chunk und Datenmodell synchron sind

          synchronized(SAVE_LOCK)
          {
            ZipEntry entry = new ZipEntry(IMapConstants.CHUNK_FOLDER + "chunk" + chunkNr + ".chunk");
            stream.putNextEntry(entry);
            DataModelIOUtil.writeDataModelXML(currChunk.getModel(), stream);
            stream.closeEntry();
          }
        }

        if(pool.getActiveCount() == 1 && chunkNr > 0)
        {
          _saveOthers(stream);
          object.setFinished();
          stream.close();
        }
        else
          object.setProgress((100.0D / (double) chunks.length) * (double) chunkNr);
      }
      catch(Exception e)
      {
        ExceptionUtil.logError(logger, 41, e);
      }
    }
  }
}
