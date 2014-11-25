package de.tfh.gamecore.util;

import de.tfh.core.exceptions.TFHException;
import de.tfh.datamodels.IDataModel;
import de.tfh.datamodels.models.ChunkDataModel;
import de.tfh.datamodels.models.MapDescriptionDataModel;
import de.tfh.datamodels.utils.DataModelIOUtil;
import de.tfh.gamecore.map.Chunk;
import de.tfh.gamecore.map.IChunk;
import de.tfh.gamecore.map.tileset.SlickTileset;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;

/**
 * Allgemeine Utilityklasse für die Map
 *
 * @author W.Glanzer, 22.11.2014
 */
public class MapUtil
{

  /**
   * Erstellt einen Chunk anhand eines InputStreams, kann <tt>null</tt> sein,
   * wenn das Datenmodell nicht erstellt werden kann, oder pStream null ist
   *
   * @param pStream          Stream des Chunks
   * @param pMapDescription  MapDescription
   * @return Den Chunk, oder <tt>null</tt>
   */
  @Nullable
  public static IChunk chunkFromInputStream(InputStream pStream, MapDescriptionDataModel pMapDescription) throws TFHException
  {
    try
    {
      if(pStream == null)
        return null;

      IDataModel chunkDataModel = DataModelIOUtil.readDataModelFromXML(pStream);
      if(chunkDataModel instanceof ChunkDataModel)
        return new Chunk((ChunkDataModel) chunkDataModel, pMapDescription.tilesPerChunkX, pMapDescription.tilesPerChunkY);

      return null;
    }
    catch(Exception e)
    {
      // Chunk konnte nicht aus InputStream geladen werden
      throw new TFHException(e, 28);
    }
  }

  /**
   * Erstellt das TileSet anhand eines InputStreams
   *
   * @param pPNGStream       InputStream des PNGs des TileSets
   * @param pMapDescription  MapDescription
   * @return TileSet
   */
  public static SlickTileset tilesetFromInputStream(InputStream pPNGStream, MapDescriptionDataModel pMapDescription) throws TFHException
  {
    try
    {
      return new SlickTileset(pPNGStream, pMapDescription.tileWidth, pMapDescription.tileHeight);
    }
    catch(Exception e)
    {
      // TileSet konnte nicht aus InputStream geladen werden
      throw new TFHException(e, 29);
    }
  }
}
