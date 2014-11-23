package de.tfh.gamecore.map.alterable;

import de.tfh.datamodels.models.ChunkDataModel;
import de.tfh.gamecore.map.Chunk;
import de.tfh.gamecore.map.IChunk;
import org.jetbrains.annotations.NotNull;

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
}
