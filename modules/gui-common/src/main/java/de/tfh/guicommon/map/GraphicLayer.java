package de.tfh.guicommon.map;

import de.tfh.core.utils.ExceptionUtil;
import de.tfh.gamecore.map.ILayer;
import de.tfh.gamecore.map.TileDescription;
import de.tfh.gamecore.map.tileset.SlickTileset;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Kapselt einen ILayer, damit dieser gerendert werden kann
 *
 * @author W.Glanzer, 09.12.2014
 */
public class GraphicLayer implements Renderable
{
  private static final Logger logger = LoggerFactory.getLogger(GraphicLayer.class);

  private GraphicTile[] tiles;
  private final int tileWidth;
  private final int tileHeight;
  private final int tileCountX;
  private final int tileCountY;

  public GraphicLayer(ILayer pLayer, SlickTileset pTileset, int pTileWidth, int pTileHeight)
  {
    tileWidth = pTileWidth;
    tileHeight = pTileHeight;
    tileCountX = pLayer.getTilesX();
    tileCountY = pLayer.getTilesY();

    tiles = _getTiles(pLayer, pTileset);
  }

  /**
   * Baut das Tile-Array zusammen, das gerendert werden muss
   *
   * @param pLayer    Layer, der repräsentiert werden soll / gerendert werden muss
   * @param pTileset  Dazugehöriges Tileset
   * @return Array aus GraphicTiles
   */
  private GraphicTile[] _getTiles(ILayer pLayer, SlickTileset pTileset)
  {
    GraphicTile[] currTiles = new GraphicTile[tileCountX * tileCountY];
    for(int x = 0; x < tileCountX; x++)
    {
      for(int y = 0; y < tileCountY; y++)
      {
        TileDescription tile = pLayer.getTile(x, y);
        if(tile != null)
          try
          {
            currTiles[y * tileCountX + x] = new GraphicTile(tile.getGraphicID(), pTileset);
          }
          catch(SlickException e)
          {
            ExceptionUtil.logError(logger, 57, e, "x=" + x, "y=" + y);
          }
      }
    }
    return currTiles;
  }

  public void draw(float x, float y)
  {
    for(int tx = 0; tx < tileCountX; tx++)
    {
      for(int ty = 0; ty < tileCountY; ty++)
      {
        GraphicTile currTile = tiles[ty * tileCountX + tx];
        if(currTile != null)
          currTile.draw(tx * tileWidth, ty * tileHeight);
      }
    }
  }
}
