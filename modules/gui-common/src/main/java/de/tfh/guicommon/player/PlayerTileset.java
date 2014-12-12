package de.tfh.guicommon.player;

import de.tfh.core.exceptions.TFHException;
import de.tfh.core.exceptions.TFHUnsupportedOperationException;
import de.tfh.gamecore.map.tileset.ITileset;
import org.newdawn.slick.Image;

import java.io.InputStream;

/**
 * @author W.Glanzer, 11.12.2014
 */
public class PlayerTileset implements ITileset<Image>
{
  @Override
  public Image getTileForID(int pLayer)
  {
    return null;
  }

  @Override
  public int getTileWidth()
  {
    throw new TFHUnsupportedOperationException(9999);
  }

  @Override
  public int getTileHeight()
  {
    throw new TFHUnsupportedOperationException(9999);
  }

  @Override
  public int getTileCountX()
  {
    throw new TFHUnsupportedOperationException(9999);
  }

  @Override
  public int getTileCountY()
  {
    throw new TFHUnsupportedOperationException(9999);
  }

  @Override
  public InputStream getImageInputStream() throws TFHException
  {
    throw new TFHUnsupportedOperationException(9999);
  }
}
