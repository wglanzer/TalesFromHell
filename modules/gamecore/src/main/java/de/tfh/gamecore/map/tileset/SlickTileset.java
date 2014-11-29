package de.tfh.gamecore.map.tileset;

import de.tfh.core.exceptions.TFHException;
import de.tfh.core.exceptions.TFHUnsupportedOperationException;
import de.tfh.core.utils.ExceptionUtil;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.UUID;

/**
 * TileSet, das nicht zwingend eine OpenGL-Verbindung erzwingt
 *
 * @author W.Glanzer, 22.11.2014
 */
public class SlickTileset implements ITileset<Image>
{
  private static final Logger logger = LoggerFactory.getLogger(SlickTileset.class);
  private final InputStream stream;
  private final int tileWidth;
  private final int tileHeight;
  private SpriteSheet spriteSheet;

  public SlickTileset(InputStream pStream, int pTileWidth, int pTileHeight) throws SlickException
  {
    stream = pStream;
    tileWidth = pTileWidth;
    tileHeight = pTileHeight;
  }

  /**
   * Baut das SpriteSheet zusammen, und gibt es zurück
   *
   * @return Spritesheet
   */
  public SpriteSheet getSpriteSheet()
  {
    if(spriteSheet == null)
      try
      {
        spriteSheet = new SpriteSheet(UUID.randomUUID().toString(), stream, tileWidth, tileHeight);
      }
      catch(SlickException e)
      {
        // Spritesheet konnte nicht erstellt werden
        ExceptionUtil.logError(logger, 33, e, "stream=" + stream);
      }

    return spriteSheet;
  }

  @Override
  public Image getTileForID(int pLayer)
  {
    int y = pLayer - pLayer / getTileCountX();
    int x = pLayer - y * getTileCountX();
    return spriteSheet.getSprite(x, y);
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
    if(spriteSheet != null)
      return spriteSheet.getHorizontalCount();

    return -1;
  }

  @Override
  public int getTileCountY()
  {
    if(spriteSheet != null)
      return spriteSheet.getVerticalCount();

    return -1;
  }

  @Override
  public InputStream getImageInputStream() throws TFHException
  {
    throw new TFHUnsupportedOperationException(9999);
  }
}
