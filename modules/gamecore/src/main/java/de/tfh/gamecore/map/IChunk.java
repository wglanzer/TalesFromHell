package de.tfh.gamecore.map;

import de.tfh.core.exceptions.TFHException;
import de.tfh.datamodels.models.ChunkDataModel;

import java.util.List;

/**
 * Beschreibt einen Chunk
 *
 * @author W.Glanzer, 23.11.2014
 */
public interface IChunk
{
  /**
   * Liefert die X-Position des Chunks
   *
   * @return X-Position des Chunks
   */
  int getX();

  /**
   * Liefert die Y-Position des Chunks
   *
   * @return Y-Position des Chunks
   */
  int getY();

  /**
   * Liefert die Layer des Chunks zurück.
   *
   * @param pForceRecalculate <tt>true</tt>, wenn die Layer neu berechnet werden sollen
   * @return Eine Liste aus Layern, Index = Layer-Index
   */
  List<ILayer> getLayers(boolean pForceRecalculate);

  /**
   * Liefert alle Tiles, die auf einem bestimmten Punkt sind
   *
   * @param pX  X-Position
   * @param pY  Y-Position
   * @return Tile-Array mit den Tiles, die auf diesem Punkt sind
   * @throws de.tfh.core.exceptions.TFHException Falls dabei ein Fehler aufgetreten ist
   */
  TilePreference[] getTilesOn(int pX, int pY) throws TFHException;

  /**
   * Liefert das ChunkDataModel, das dahinter liegt
   *
   * @return ChunkDataModel
   */
  ChunkDataModel getModel();

  /**
   * Synchronisiert das Model mit dem dahinterliegenden Datenmodell.
   * Alle Änderungen im Chunk werden ins Datenmodell geschrieben
   */
  void synchronizeModel();
}
