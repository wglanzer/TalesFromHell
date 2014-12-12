package de.tfh.guicommon.map;

import de.tfh.gamecore.IDrawable;
import de.tfh.gamecore.map.tileset.SlickTileset;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author W.Glanzer, 08.12.2014
 */
public class GraphicTile extends Image implements IDrawable
{

  public GraphicTile(int pID, SlickTileset pTileset) throws SlickException
  {
    super(pTileset.getTileForID(pID));
  }

}
