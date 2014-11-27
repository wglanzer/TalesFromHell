package de.tfh.gamecore.map;

import de.tfh.core.exceptions.TFHException;
import de.tfh.datamodels.models.ChunkDataModel;
import de.tfh.gamecore.map.alterable.AlterableLayer;
import org.jetbrains.annotations.NotNull;

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

  /**
   * Konstruktor
   *
   * @param pX           X-Position des Chunks
   * @param pY           Y-Position des Chunks
   * @param pXTileCount  Anzahl der Tiles in X-Richtung
   * @param pYTileCount  Anzahl der Tiles in Y-Richtung
   * @param pTiles       Bit-Speichermasken-Array für die Tiles, genauer beschrieben im ChunkDataModel
   */
  public Chunk(int pX, int pY, int pXTileCount, int pYTileCount, Long[] pTiles)
  {
    this(new ChunkDataModel(), pXTileCount, pYTileCount);
    dataModel.x = pX;
    dataModel.y = pY;
    dataModel.tiles = pTiles;

    // Neu berechnen
    getLayers(true);
  }

  /**
   * Konstruktor
   *
   * @param pDataModel   Das dahinterliegende Datenmodell
   * @param pXTileCount  Anzahl der Tiles in X-Richtung
   * @param pYTileCount  Anzahl der Tiles in Y-Richtung
   */
  public Chunk(@NotNull ChunkDataModel pDataModel, int pXTileCount, int pYTileCount)
  {
    dataModel = pDataModel;
    xTileCount = pXTileCount;
    yTileCount = pYTileCount;
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

      AlterableLayer background = new AlterableLayer(xTileCount, yTileCount);
      AlterableLayer midground = new AlterableLayer(xTileCount, yTileCount);
      AlterableLayer specialLayer = new AlterableLayer(xTileCount, yTileCount);
      AlterableLayer foreground = new AlterableLayer(xTileCount, yTileCount);

      Long[] dmTiles = dataModel.tiles;

      for(int i = 0; i < dmTiles.length; i++)
      {
        long currDMTile = dmTiles[i] != null ? dmTiles[i] : 0L;
        int y = i / xTileCount;
        int x = i - y * xTileCount;

        BitSet currBits = BitSet.valueOf(new long[]{currDMTile});

        BitSet bitBG = currBits.get(0, 16);
        BitSet bitMG = currBits.get(17, 32);
        BitSet bitSL = currBits.get(33, 48);
        BitSet bitFG = currBits.get(49, 64);

        background.addTile(x, y, new TilePreference(bitBG));
        midground.addTile(x, y, new TilePreference(bitMG));
        specialLayer.addTile(x, y, new TilePreference(bitSL));
        foreground.addTile(x, y, new TilePreference(bitFG));
      }

      cachedLayers.add(Layer.BACKGROUND, background);
      cachedLayers.add(Layer.MIDGROUND, midground);
      cachedLayers.add(Layer.SPECIAL_LAYER, specialLayer);
      cachedLayers.add(Layer.FOREGROUND, foreground);
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
}
