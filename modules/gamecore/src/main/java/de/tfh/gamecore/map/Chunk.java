package de.tfh.gamecore.map;

import de.tfh.core.exceptions.TFHException;
import de.tfh.core.utils.ExceptionUtil;
import de.tfh.datamodels.models.ChunkDataModel;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Stellt einen Chunk der Map dar.
 * Dieser hat ein ChunkDataModel dahinterliegen
 *
 * @author W.Glanzer, 16.11.2014
 */
public class Chunk implements IChunk
{
  // Das dahinterliegende Datenmodell
  private final ChunkDataModel dataModel;

  // TileCount in X- und Y-Richtung
  private final int xTileCount;
  private final int yTileCount;

  // Gecachte Layer, damit diese nicht neu aufgebaut werden müssen
  private List<ILayer> cachedLayers;
  private static final Logger logger = LoggerFactory.getLogger(Chunk.class);

  /**
   * Konstruktor
   *
   * @param pX          X-Position des Chunks
   * @param pY          Y-Position des Chunks
   * @param pXTileCount Anzahl der Tiles in X-Richtung
   * @param pYTileCount Anzahl der Tiles in Y-Richtung
   * @param pTiles      Bit-Speichermasken-Array für die Tiles, genauer beschrieben im ChunkDataModel
   */
  public Chunk(int pX, int pY, int pXTileCount, int pYTileCount, Long[] pTiles)
  {
    this(new ChunkDataModel(), pXTileCount, pYTileCount);
    dataModel.x = pX;
    dataModel.y = pY;
    dataModel.tiles = pTiles;

    _verifiyTileNulls();
    // Neu berechnen
    getLayers(true);
  }

  /**
   * Konstruktor
   *
   * @param pDataModel  Das dahinterliegende Datenmodell
   * @param pXTileCount Anzahl der Tiles in X-Richtung
   * @param pYTileCount Anzahl der Tiles in Y-Richtung
   */
  public Chunk(@NotNull ChunkDataModel pDataModel, int pXTileCount, int pYTileCount)
  {
    dataModel = pDataModel;
    xTileCount = pXTileCount;
    yTileCount = pYTileCount;

    _verifiyTileNulls();
    getLayers(true);
  }

  @Override
  public int getX()
  {
    return dataModel.x;
  }

  @Override
  public int getY()
  {
    return dataModel.y;
  }

  @Override
  public List<ILayer> getLayers(boolean pForceRecalculate)
  {
    if(cachedLayers == null || pForceRecalculate)
    {
      cachedLayers = new ArrayList<>(4);

      TilePreference[] background = new TilePreference[xTileCount * yTileCount];
      TilePreference[] midground = new TilePreference[xTileCount * yTileCount];
      TilePreference[] foreground = new TilePreference[xTileCount * yTileCount];
      TilePreference[] special = new TilePreference[xTileCount * yTileCount];

      Long[] dmTiles = dataModel.tiles;

      for(int i = 0; i < dmTiles.length; i++)
      {
        long currDMTile = dmTiles[i] != null ? dmTiles[i] : 0L;

        BitSet currBits = BitSet.valueOf(new long[]{currDMTile});

        BitSet bitBG = currBits.get(0, 15);
        BitSet bitMG = currBits.get(16, 31);
        BitSet bitSL = currBits.get(32, 47);
        BitSet bitFG = currBits.get(48, 63);

        if(!bitBG.isEmpty())
          background[i] = new TilePreference(bitBG);
        if(!bitMG.isEmpty())
          midground[i] = new TilePreference(bitMG);
        if(!bitSL.isEmpty())
          special[i] = new TilePreference(bitSL);
        if(!bitFG.isEmpty())
          foreground[i] = new TilePreference(bitFG);
      }

      cachedLayers.add(Layer.BACKGROUND, createLayer(xTileCount, yTileCount, background));
      cachedLayers.add(Layer.MIDGROUND, createLayer(xTileCount, yTileCount, midground));
      cachedLayers.add(Layer.SPECIAL_LAYER, createLayer(xTileCount, yTileCount, special));
      cachedLayers.add(Layer.FOREGROUND, createLayer(xTileCount, yTileCount, foreground));
    }

    return cachedLayers;
  }

  @Override
  public TilePreference[] getTilesOn(int pX, int pY) throws TFHException
  {
    try
    {
      List<ILayer> layers = getLayers(false);
      TilePreference[] tiles = new TilePreference[layers.size()];
      for(int i = 0; i < layers.size(); i++)
      {
        ILayer currLayer = layers.get(i);
        tiles[i] = currLayer.getTile(pX, pY);
      }

      return tiles;
    }
    catch(Exception e)
    {
      // Tile konnte nicht bestimmt werden
      throw new TFHException(e, 25, "x=" + pX, "y=" + pY);
    }
  }

  @Override
  public ChunkDataModel getModel()
  {
    return dataModel;
  }

  @Override
  public void synchronizeModel()
  {
    Long[] tileArr = new Long[dataModel.tiles.length];
    for(int x = 0; x < xTileCount; x++)
    {
      for(int y = 0; y < yTileCount; y++)
      {
        try
        {
          TilePreference[] tiles = getTilesOn(x, y);
          tileArr[y * xTileCount + x] = _toLong(tiles);
        }
        catch(TFHException e)
        {
          ExceptionUtil.logError(logger, 39, e, "x=" + x, "y=" + y);
        }
      }
    }

    dataModel.tiles = tileArr;
    dataModel.x = getX();
    dataModel.y = getY();
  }

  protected ILayer createLayer(int pXTileCount, int pYTileCount, TilePreference[] pPreferences)
  {
    return new Layer(pXTileCount, pYTileCount, pPreferences);
  }

  private Long _toLong(TilePreference[] pPreferences)
  {
    BitSet currBitSet = new BitSet();

    for(int i = 0; i < pPreferences.length; i++)
    {
      int offset = 16 * i;
      TilePreference currTile = pPreferences[i];
      if(currTile != null)
      {
        BitSet bitSet = currTile.getBitSet();
        for(int j = 0; j < 16; j++)
          currBitSet.set(j + offset, bitSet.get(j));
      }
    }

    long[] longs = currBitSet.toLongArray();
    if(longs.length > 0)
      return longs[0];

    return 0L;
  }

  private void _verifiyTileNulls()
  {
    Long[] tiles = dataModel.tiles;
    for(int i = 0; i < tiles.length; i++)
    {
      Long currLong = tiles[i];
      if(currLong == null)
        dataModel.tiles[i] = 0L;
    }

  }
}
