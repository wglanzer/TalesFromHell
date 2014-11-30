package de.tfh.mapper.facade;

import de.tfh.core.exceptions.TFHException;
import de.tfh.core.utils.ExceptionUtil;
import de.tfh.gamecore.map.IChunk;
import de.tfh.gamecore.map.ILayer;
import de.tfh.gamecore.map.ProgressObject;
import de.tfh.gamecore.map.TilePreference;
import de.tfh.gamecore.map.alterable.AlterableChunk;
import de.tfh.gamecore.map.alterable.AlterableLayer;
import de.tfh.gamecore.map.alterable.AlterableMap;
import de.tfh.gamecore.map.tileset.ITileset;
import de.tfh.gamecore.map.tileset.MapperTileset;
import de.tfh.mapper.TFHMappperException;
import de.tfh.mapper.gui.GraphicTile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Beschreibt die Facade des Mappers.
 * Hier wird der Zugriff der GUI auf das Backend geregelt
 *
 * @author W.Glanzer, 20.11.2014
 */
public class MapperFacade implements IMapperFacade
{
  private static final Logger logger = LoggerFactory.getLogger(MapperFacade.class);

  private AlterableMap map;
  private int selectedID = -1;
  private final Set<IChangeListener> changeListeners = new HashSet<>();

  public MapperFacade()
  {
    map = new AlterableMap(false);
    map.setSavable(false);
  }

  @Override
  public void generateNewMap(String pName, String pTilesetPath) throws TFHException
  {
    try
    {
      map = new AlterableMap(true);
      map.setSavable(true);
      BufferedImage image = ImageIO.read(new File(pTilesetPath));
      map.setTileSet(new MapperTileset(image, image.getWidth() / 16, image.getHeight() / 16));

      fireFacadeChanged();
    }
    catch(IOException e)
    {
      // Map konnte nicht neu generiert werden
      throw new TFHException(e, 37, "name=" + pName, "tilesetPath=" + pTilesetPath);
    }
  }

  @Override
  public void setTile(int pX, int pY, int pLayer, TilePreference pTilePreference) throws TFHMappperException
  {
    if(pLayer < 0)
      throw new TFHMappperException(36, "layer=" + pLayer);

    map.setTile(pX, pY, pLayer, pTilePreference);
  }

  public int getTileIDOnMap(int pX, int pY, int pLayer) throws TFHException
  {
    try
    {
      int x = pX % map.getTilesPerChunkX();
      int y = pY % map.getTilesPerChunkY();
      TilePreference tilePref = map.getChunkContaining(pX, pY).getTilesOn(x, y)[pLayer];
      if(tilePref != null)
        return tilePref.getGraphicID();

      return -1;
    }
    catch(Exception e)
    {
      // Tile konnte nicht erstellt werden
      throw new TFHException(e, 35, "x=" + pX, "y=" + pY, "layer=" + pLayer);
    }
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
    ITileset tileset = map.getTileSet();
    if(tileset != null)
      return tileset.getTileCountX() * tileset.getTileCountY();

    return 0;
  }

  @Override
  public int getChunkCountX()
  {
    return map.getChunkCountX();
  }

  @Override
  public int getChunkCountY()
  {
    return map.getChunkCountY();
  }

  @Override
  public GraphicTile getTile(int pTileID)
  {
    ITileset tileSet = map.getTileSet();
    if(tileSet != null)
      return new GraphicTile(pTileID, (Image) tileSet.getTileForID(pTileID));

    return null;
  }

  @Override
  public Image getImageForTile(int pTileID)
  {
    ITileset tileSet = map.getTileSet();
    if(tileSet != null)
      return (Image) tileSet.getTileForID(pTileID);

    return null;
  }

  @Override
  public TilePreference getPreference(int pXTile, int pYTile, int pChunkX, int pChunkY, int pLayer) throws TFHException
  {
    try
    {
      IChunk chunk = map.getChunk(pChunkX, pChunkY);
      if(chunk != null)
      {
        AlterableChunk alterable = (AlterableChunk) chunk;
        TilePreference[] tiles = alterable.getTilesOn(pXTile, pYTile);

        if(tiles != null && tiles.length == 4)
        {
          TilePreference pref = tiles[pLayer];

          if(tiles[pLayer] == null)
          {
            ILayer layer = alterable.getLayers(false).get(pLayer);
            AlterableLayer alterableLayer = (AlterableLayer) layer;

            pref = new TilePreference(-1);
            alterableLayer.addTile(pXTile, pYTile, pref);
          }

          return pref;
        }
      }
    }
    catch(Exception e)
    {
      throw new TFHException(e, 45, "xTile=" + pXTile, "yTile=" + pYTile, "chunkx=" + pChunkX, "chunky=" + pChunkY);
    }

    return null;
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

  @Override
  public ProgressObject save(OutputStream pStream)
  {
    try
    {
      if(map != null)
      {
        ProgressObject object = map.save(pStream, 4);
        _fireMapSaved(object);
      }
    }
    catch(TFHException e)
    {
      ExceptionUtil.logError(logger, 46, e);
    }

    return null;
  }

  @Override
  public boolean isSavable()
  {
    return map != null && map.isSavable();
  }

  @Override
  public ProgressObject load(File pFile) throws TFHException
  {
    try
    {
      ProgressObject loadObj = new ProgressObject();
      map = new AlterableMap(pFile, loadObj, (Runnable) () -> fireFacadeChanged());
      return loadObj;
    }
    catch(Exception e)
    {
      throw new TFHException(e, 9999, "file=" + pFile);
    }
  }

  @Override
  public void shutdown()
  {
    System.exit(0);
  }

  @Override
  public void addChangeListener(IChangeListener pListener)
  {
    synchronized(changeListeners)
    {
      changeListeners.add(pListener);
    }
  }

  @Override
  public void fireFacadeChanged()
  {
    synchronized(changeListeners)
    {
      for(IChangeListener currListener : changeListeners)
        currListener.facadeChanged();
    }
  }

  private void _fireMapSaved(ProgressObject pObject)
  {
    synchronized(changeListeners)
    {
      for(IChangeListener currListener : changeListeners)
        currListener.mapSaved(pObject);
    }
  }
}
