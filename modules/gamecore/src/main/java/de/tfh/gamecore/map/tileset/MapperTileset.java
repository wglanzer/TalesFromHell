package de.tfh.gamecore.map.tileset;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Beschreibt ein Tileset für den Mapper.
 * Hier wird nicht mit Slick-Images gearbeitet, sondern mit normalen,
 * für Swing zeichenbaren BufferedImages
 *
 * @author W.Glanzer, 24.11.2014
 */
public class MapperTileset implements ITileset<Image>
{

  private final BufferedImage image;
  private final int tileWidth;
  private final int tileHeight;
  private final int tilesX;
  private final int tilesY;

  public MapperTileset(BufferedImage pImage, int pTileWidth, int pTileHeight)
  {
    image = pImage;
    tileWidth = pTileWidth;
    tileHeight = pTileHeight;
    tilesX = image.getWidth() / tileWidth;
    tilesY = image.getHeight() / tileHeight;
  }

  @Override
  public Image getTileForID(int pLayer)
  {
    int y = pLayer / getTileCountX();
    int x = pLayer - y * getTileCountX();
    return image.getSubimage(x * getTileWidth(), y * getTileHeight(), getTileWidth(), getTileHeight());
  }

  @Override
  public int getTileWidth()
  {
    return tileWidth;
  }

  @Override
  public int getTileHeight()
  {
    return tileHeight;
  }

  @Override
  public int getTileCountX()
  {
    return tilesX;
  }

  @Override
  public int getTileCountY()
  {
    return tilesY;
  }
}
