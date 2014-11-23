package de.tfh.gamecore.map;

import de.tfh.core.utils.ExceptionUtil;
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
public class Tileset
{
  private static final Logger logger = LoggerFactory.getLogger(Tileset.class);
  private final InputStream stream;
  private final int tileWidth;
  private final int tileHeight;
  private SpriteSheet spriteSheet;

  public Tileset(InputStream pStream, int pTileWidth, int pTileHeight) throws SlickException
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
        ExceptionUtil.logError(logger, 9999, e, "stream=" + stream);
      }

    return spriteSheet;
  }
}
