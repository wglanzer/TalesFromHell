package de.tfh.gamecore.map;

import org.jetbrains.annotations.Nullable;

/**
 * Beschreibt eine Map
 *
 * @author W.Glanzer, 16.11.2014
 */
public interface IMap
{

  /**
   * Liefert einen bestimmten Chunk, oder <tt>null</tt>
   *
   * @param pX  n-ter Chunk in X-Richtung
   * @param pY  n-ter Chunk in Y-Richtung
   * @return Chunk, oder <tt>null</tt>
   */
  @Nullable
  Chunk getChunk(int pX, int pY);

  /**
   * Liefert einen bestimmten Chunk, der eine Tileposition enhält, oder <tt>null</tt>
   *
   * @param pX  Chunk mit dem spieziellen Tile in X-Richtung
   * @param pY  Chunk mit dem spieziellen Tile in Y-Richtung
   * @return Chunk, oder <tt>null</tt>
   */
  Chunk getChunkContaining(int pX, int pY);
}
