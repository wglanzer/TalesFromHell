package de.tfh.gamecore.map;

import de.tfh.core.exceptions.TFHException;
import de.tfh.gamecore.map.tileset.ITileset;
import org.jetbrains.annotations.Nullable;

import java.io.OutputStream;

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
   * Liefert einen bestimmten Chunk, der eine Tileposition enhält, oder <tt>null</tt>
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
   * Liefert die Höhe der einzelnen Tiles in Pixel
   *
   * @return Höhe der Tiles in Pixel
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

  /**
   * Setzt das Tileset für die Map
   *
   * @param pSet  Tileset, das gesetzt werden soll
   */
  void setTileSet(ITileset<?> pSet);

  /**
   * Liefert das Tileset
   *
   * @return Tileset
   */
  ITileset getTileSet();

  /**
   * Schreibt die Map auf den Outputstream
   *
   * @param pOutputStream  Stream, auf den geschrieben werden soll
   * @param pThreadCount   Anzahl der Threads zum Speichern
   */
  MapSaveObject save(OutputStream pOutputStream, int pThreadCount) throws TFHException;

  /**
   * Gibt zurück, ob die Map gespeichert werden kann, oder nicht
   *
   * @return <tt>true</tt>, wenn die Map gespeichert werden kann
   */
  boolean isSavable();
}
