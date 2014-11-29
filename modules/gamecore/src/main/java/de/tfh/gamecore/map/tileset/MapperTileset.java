package de.tfh.gamecore.map.tileset;

import de.tfh.core.exceptions.TFHException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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
  private Map<Integer, Image> imageCache = new HashMap<>();
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
  public Image getTileForID(int pID)
  {
    if(!imageCache.containsKey(pID))
    {
      int y = pID / getTileCountX();
      int x = pID - y * getTileCountX();
      imageCache.put(pID, image.getSubimage(x * getTileWidth(), y * getTileHeight(), getTileWidth(), getTileHeight()));
    }

    return imageCache.get(pID);
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

  @Override
  public InputStream getImageInputStream() throws TFHException
  {
    try
    {
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      ImageIO.write(image, "png", os);
      return new ByteArrayInputStream(os.toByteArray());
    }
    catch(Exception e)
    {
      throw new TFHException(e, 9999);
    }
  }
}
