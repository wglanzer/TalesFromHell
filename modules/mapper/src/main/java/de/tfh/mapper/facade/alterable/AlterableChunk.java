package de.tfh.mapper.facade.alterable;

import de.tfh.datamodels.models.ChunkDataModel;
import de.tfh.gamecore.map.Chunk;
import de.tfh.gamecore.map.IChunk;
import de.tfh.gamecore.map.ILayer;
import de.tfh.gamecore.map.TileDescription;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Chunk, den man verändern kann
 *
 * @author W.Glanzer, 23.11.2014
 */
public class AlterableChunk extends Chunk implements IChunk
{
  public AlterableChunk(int pX, int pY, int pXTileCount, int pYTileCount, Long[] pTiles)
  {
    super(pX, pY, pXTileCount, pYTileCount, pTiles);
  }

  public AlterableChunk(@NotNull ChunkDataModel pDataModel, int pXTileCount, int pYTileCount)
  {
    super(pDataModel, pXTileCount, pYTileCount);
  }

  @Override
  public void synchronizeModel()
  {
    super.synchronizeModel();
    for(ILayer currLayer : getLayers(false))
      if(currLayer instanceof AlterableLayer)
        ((AlterableLayer) currLayer).setUnmodified();
  }

  /**
   * Gibt zurück, ob der Layer modifiziert wurde
   *
   * @return <tt>true</tt>, wenn er modifiziert wurde
   */
  public boolean isModified()
  {
    List<ILayer> layers = getLayers(false);
    for(ILayer layer : layers)
    {
      if(layer instanceof AlterableLayer && ((AlterableLayer) layer).isModified())
        return true;
    }

    return false;
  }

  @Override
  protected ILayer createLayer(int pXTileCount, int pYTileCount, TileDescription[] pPreferences)
  {
    return new AlterableLayer(pXTileCount, pYTileCount, pPreferences);
  }
}
