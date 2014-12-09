package de.tfh.guicommon.map;

import de.tfh.gamecore.map.tileset.SlickTileset;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;

/**
 * @author W.Glanzer, 08.12.2014
 */
public class GraphicTile extends Image implements Renderable
{

  public GraphicTile(int pID, SlickTileset pTileset) throws SlickException
  {
    super(pTileset.getTileForID(pID));
  }

}
