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
  IChunk getChunk(int pX, int pY);

  /**
   * Liefert einen bestimmten Chunk, der eine Tileposition enh�lt, oder <tt>null</tt>
   *
   * @param pX  Chunk mit dem spieziellen Tile in X-Richtung
   * @param pY  Chunk mit dem spieziellen Tile in Y-Richtung
   * @return Chunk, oder <tt>null</tt>
   */
  IChunk getChunkContaining(int pX, int pY);

  /**
   * Liefert die Anzahl der Chunks in X-Richtung
   *
   * @return Anzahl der Chunks in X-Richtung
   */
  int getChunkCountX();

  /**
   * Liefert die Anzahl der Chunks in Y-Richtung
   *
   * @return Anzahl der Chunks in Y-Richtung
   */
  int getChunkCountY();

  /**
   * Liefert die Breite der einzelnen Tiles in Pixel
   *
   * @return Breite der Tiles in Pixel
   */
  int getTileWidth();

  /**
   * Liefert die H�he der einzelnen Tiles in Pixel
   *
   * @return H�he der Tiles in Pixel
   */
  int getTileHeight();

  /**
   * Liefert die Anzahl der Tiles innerhalb eines Chunks in X-Richtung
   *
   * @return Anzahl der Tiles innerhalb eines Chunks in X-Richtung
   */
  int getTilesPerChunkX();

  /**
   * Liefert die Anzahl der Tiles innerhalb eines Chunks in Y-Richtung
   *
   * @return Anzahl der Tiles innerhalb eines Chunks in Y-Richtung
   */
  int getTilesPerChunkY();
}
