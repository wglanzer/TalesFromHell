package de.tfh.gamecore.map.tileset;

import de.tfh.core.exceptions.TFHException;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;

/**
 * Beschreibt ein Tileset für die Map
 *
 * @author W.Glanzer, 24.11.2014
 */
public interface ITileset<T>
{
  /**
   * Liefert das Tile für die angegeben ID
   *
   * @param pID  ID, die verwendet werden soll
   * @return Das Tile (Instanz von T)
   */
  T getTile(int pID);

  /**
   * Liefert das Tile an der gewünschten Position, oder <tt>null</tt>
   *
   * @param pX  X-Position des Tiles
   * @param pY  Y-Position des Tiles
   * @return das Tile, oder <tt>null</tt>
   */
  @Nullable
  T getTile(int pX, int pY);

  /**
   * Liefert alle verfügbaren Tiles
   *
   * @return alle verfügbaren Tiles
   */
  T[] getTiles();

  /**
   * Liefert die Breite eines Tiles
   *
   * @return Breite eines Tiles in px
   */
  int getTileWidth();

  /**
   * Liefert die Höhe eines Tiles
   *
   * @return Höhe eines Tiles in px
   */
  int getTileHeight();

  /**
   * Liefert die Anzahl der Tiles auf dem Tileset (!, nicht auf der Map)
   * in X-Richtung
   *
   * @return Anzahl in X-Richtung
   */
  int getTileCountX();

  /**
   * Liefert die Anzahl der Tiles auf dem Tileset (!, nicht auf der Map)
   * in Y-Richtung
   *
   * @return Anzahl in Y-Richtung
   */
  int getTileCountY();

  /**
   * Liefert das zugehörige Bild
   *
   * @return dazugehöriges Bild
   */
  InputStream getImageInputStream() throws TFHException;
}
