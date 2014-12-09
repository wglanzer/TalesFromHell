package de.tfh.guicommon.map;

import de.tfh.gamecore.map.IChunk;
import de.tfh.gamecore.map.ILayer;
import de.tfh.gamecore.map.tileset.SlickTileset;
import org.newdawn.slick.Renderable;

import java.util.List;

/**
 * Kapselt einen Chunk, um diesen zu rendern
 *
 * @author W.Glanzer, 09.12.2014
 */
public class GraphicChunk implements Renderable
{

  private final SlickTileset tileset;
  private final int tilesPerChunkX;
  private final int tilesPerChunkY;
  private final int tileWidth;
  private final int tileHeight;
  private final int x;
  private final int y;
  private final GraphicLayer[] layers;

  public GraphicChunk(IChunk pChunk, SlickTileset pTileset, int pTilesPerChunkX, int pTilesPerChunkY, int pTileWidth, int pTileHeight)
  {
    tileset = pTileset;

    tilesPerChunkX = pTilesPerChunkX;
    tilesPerChunkY = pTilesPerChunkY;
    tileWidth = pTileWidth;
    tileHeight = pTileHeight;
    layers = _getLayers(pChunk);

    x = pChunk.getX();
    y = pChunk.getY();
  }

  /**
   * Baut das Array aus den Layern zusammen
   *
   * @param pChunk  Chunk, der gerendert bzw. repräsentiert werden soll
   * @return GraphicLayer-Array, die gerendert werden müssen
   */
  private GraphicLayer[] _getLayers(IChunk pChunk)
  {
    List<ILayer> layers = pChunk.getLayers(false);
    GraphicLayer[] layersArr = new GraphicLayer[layers.size()];
    for(int i = 0; i < layers.size(); i++)
    {
      ILayer currLayer = layers.get(i);
      layersArr[i] = new GraphicLayer(currLayer, tileset, tileWidth, tileHeight);
    }
    return layersArr;
  }

  public void draw(float x, float y)
  {
    for(GraphicLayer currLayer : layers)
      if(currLayer != null)
        currLayer.draw(x, y);
  }

  /**
   * Liefert die X-Koordinate des Chunks auf der Map
   *
   * @return X-Koordinate des Chunks auf der Map
   */
  public int getX()
  {
    return x;
  }

  /**
   * Liefert die Y-Koordinate des Chunks auf der Map
   *
   * @return Y-Koordinate des Chunks auf der Map
   */
  public int getY()
  {
    return y;
  }
}
