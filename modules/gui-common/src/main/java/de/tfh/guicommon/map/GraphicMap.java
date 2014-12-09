package de.tfh.guicommon.map;

import de.tfh.core.exceptions.TFHUnsupportedOperationException;
import de.tfh.gamecore.map.IChunk;
import de.tfh.gamecore.map.IMap;
import de.tfh.gamecore.map.tileset.SlickTileset;
import org.newdawn.slick.Renderable;

/**
 * Kapselt eine Map, um diese zu rendern
 *
 * @author W.Glanzer, 08.12.2014
 */
public class GraphicMap implements Renderable
{

  private final IMap map;
  private GraphicChunk[] chunks;
  private SlickTileset tileset;

  public GraphicMap(IMap pMap)
  {
    map = pMap;
    if(!(pMap.getTileSet() instanceof SlickTileset))
      throw new TFHUnsupportedOperationException(56);

    tileset = (SlickTileset) pMap.getTileSet();
    chunks = _getChunks();
  }

  /**
   * Chunks die gerendert werden sollen
   *
   * @return Chunks
   */
  private GraphicChunk[] _getChunks()
  {
    GraphicChunk[] chunks = new GraphicChunk[map.getChunkCountX() * map.getChunkCountY()];
    for(int x = 0; x < map.getChunkCountX(); x++)
    {
      for(int y = 0; y < map.getChunkCountY(); y++)
      {
        IChunk currChunk = map.getChunk(x, y);
        if(currChunk != null)
          chunks[y * map.getChunkCountX() + x] = new GraphicChunk(currChunk, tileset, map.getTilesPerChunkX(), map.getTilesPerChunkY(), map.getTileWidth(), map.getTileHeight());
      }
    }
    return chunks;
  }

  public void draw(float x, float y)
  {
    int chunkWidth = map.getTilesPerChunkX() * map.getTileWidth();
    int chunkHeight = map.getTilesPerChunkY() * map.getTileHeight();

    for(GraphicChunk currChunk : chunks)
      currChunk.draw(currChunk.getX() * chunkWidth, currChunk.getY() * chunkHeight);
  }
}
