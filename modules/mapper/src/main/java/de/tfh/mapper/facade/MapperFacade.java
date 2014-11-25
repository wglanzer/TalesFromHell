package de.tfh.mapper.facade;

import de.tfh.core.exceptions.TFHException;
import de.tfh.gamecore.map.TilePreference;
import de.tfh.gamecore.map.alterable.AlterableMap;
import de.tfh.gamecore.map.tileset.ITileset;
import de.tfh.gamecore.map.tileset.MapperTileset;
import de.tfh.mapper.TFHMappperException;
import de.tfh.mapper.gui.GraphicTile;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * Beschreibt die Facade des Mappers.
 * Hier wird der Zugriff der GUI auf das Backend geregelt
 *
 * @author W.Glanzer, 20.11.2014
 */
public class MapperFacade implements IMapperFacade
{
  private AlterableMap map;
  private int selectedID = -1;

  public MapperFacade()
  {
    generateNewMap();
  }

  @Override
  public void generateNewMap()
  {
    map = new AlterableMap();
    try
    {
      map.setTileSet(new MapperTileset(ImageIO.read(MapperFacade.class.getResource("tiles.png")), 32, 32));  //todo
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }
  }

  @Override
  public void setTile(int pX, int pY, int pLayer, TilePreference pTilePreference) throws TFHMappperException
  {
    if(pLayer < 0)
      throw new TFHMappperException(9999, "layer=" + pLayer);

    map.setTile(pX, pY, pLayer, pTilePreference);
  }

  public GraphicTile getTilePaintableOnMap(int pX, int pY, int pLayer) throws TFHException
  {
    try
    {
      int x = pX - pX / map.getTilesPerChunkX();
      int y = pY - pY / map.getTilesPerChunkY();
      TilePreference tilePref = map.getChunkContaining(pX, pY).getTilesOn(x, y)[pLayer];
      return new GraphicTile(this, tilePref.getGraphicID(), getImageForID(tilePref.getGraphicID()));
    }
    catch(Exception e)
    {
      throw new TFHException(e, 9999, "x=" + pX, "y=" + pY, "layer=" + pLayer);
    }
  }

  @Nullable
  private Image getImageForID(int pTileID)
  {
    ITileset tileset = map.getTileSet();
    if(tileset != null)
      return (Image) tileset.getTileForID(pTileID);

    return null;
  }

  @Override
  public int getTileWidth()
  {
    return map.getTileWidth();
  }

  @Override
  public int getTileHeight()
  {
    return map.getTileHeight();
  }

  @Override
  public int getTileCountPerChunkX()
  {
    return map.getTilesPerChunkX();
  }

  @Override
  public int getTileCountPerChunkY()
  {
    return map.getTilesPerChunkY();
  }

  @Override
  public int getTileCount()
  {
    return map.getTileSet().getTileCountX() * map.getTileSet().getTileCountY();
  }

  @Override
  public GraphicTile getTile(int pTileID)
  {
    return new GraphicTile(this, pTileID, (Image) map.getTileSet().getTileForID(pTileID));
  }

  @Override
  public void setSelectedMapTileID(int pID)
  {
    selectedID = pID;
  }

  @Override
  public int getSelectedMapTileID()
  {
    return selectedID;
  }
}
