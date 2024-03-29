package de.tfh.gamecore.map.tileset;

import de.tfh.core.exceptions.TFHException;
import de.tfh.core.exceptions.TFHUnsupportedOperationException;
import de.tfh.core.utils.ExceptionUtil;
import org.jetbrains.annotations.Nullable;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

/**
 * TileSet, das zwingend eine OpenGL-Verbindung braucht
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

  public SlickTileset(String pFile, int pTileWidth, int pTileHeight) throws FileNotFoundException
  {
    this(new FileInputStream(pFile), pTileWidth, pTileHeight);
  }

  public SlickTileset(InputStream pStream, int pTileWidth, int pTileHeight)
  {
    stream = pStream;
    tileWidth = pTileWidth;
    tileHeight = pTileHeight;
    spriteSheet = getSpriteSheet();
  }

  /**
   * Baut das SpriteSheet zusammen, und gibt es zur�ck
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
  public Image getTile(int pID)
  {
    int y = pID / getTileCountX();
    int x = pID % getTileCountX();
    return spriteSheet.getSprite(x, y);
  }

  @Nullable
  @Override
  public Image getTile(int pX, int pY)
  {
    return spriteSheet.getSprite(pX, pY);
  }

  @Override
  public Image[] getTiles()
  {
    int tileX = spriteSheet.getHorizontalCount();
    int tileY = spriteSheet.getVerticalCount();

    Image[] images = new Image[tileX * tileY];

    for(int x = 0; x < tileX; x++)
      for(int y = 0; y < tileY; y++)
        images[y * tileX + x] = spriteSheet.getSprite(x, y);

    return images;
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

    return 0;
  }

  @Override
  public int getTileCountY()
  {
    if(spriteSheet != null)
      return spriteSheet.getVerticalCount();

    return 0;
  }

  @Override
  public InputStream getImageInputStream() throws TFHException
  {
    throw new TFHUnsupportedOperationException(44);
  }
}
