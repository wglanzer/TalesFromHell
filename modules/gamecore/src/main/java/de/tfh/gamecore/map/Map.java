package de.tfh.gamecore.map;

import de.tfh.core.exceptions.TFHException;
import de.tfh.core.utils.ExceptionUtil;
import de.tfh.datamodels.models.MapDescriptionDataModel;
import de.tfh.datamodels.utils.DataModelIOUtil;
import de.tfh.gamecore.map.tileset.ITileset;
import de.tfh.gamecore.util.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.Enumeration;
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
  protected ITileset graphicTiles = null;
  protected IChunk[] chunks = new IChunk[0];
  protected MapDescriptionDataModel mapDesc;

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
    int x = pX - pX / mapDesc.tilesPerChunkX;
    int y = pY - pY / mapDesc.tilesPerChunkY;
    return chunks[y * getChunkCountX() + x];
  }

  @Override
  public int getChunkCountX()
  {
    return mapDesc.chunksX;
  }

  @Override
  public int getChunkCountY()
  {
    return mapDesc.chunksY;
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
    graphicTiles = pSet;
  }

  @Override
  public ITileset getTileSet()
  {
    return graphicTiles;
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

      // Alle Zip-Einträge durchgehen, darauf
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
   * Fügt eine Klasse zur Map hinzu
   *
   * @param pClassStream InputStream der Klasse, die hinzugefügt werden soll
   */
  private void _addClass(InputStream pClassStream)
  {
    // todo
  }

  /**
   * Fügt einen Chunk auf die Map ein.
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
