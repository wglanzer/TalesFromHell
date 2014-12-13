package de.tfh.gamecore.map.tileset;

import de.tfh.core.exceptions.TFHException;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;

/**
 * Beschreibt ein Tileset f�r die Map
 *
 * @author W.Glanzer, 24.11.2014
 */
public interface ITileset<T>
{
  /**
   * Liefert das Tile f�r die angegeben ID
   *
   * @param pID  ID, die verwendet werden soll
   * @return Das Tile (Instanz von T)
   */
  T getTile(int pID);

  /**
   * Liefert das Tile an der gew�nschten Position, oder <tt>null</tt>
   *
   * @param pX  X-Position des Tiles
   * @param pY  Y-Position des Tiles
   * @return das Tile, oder <tt>null</tt>
   */
  @Nullable
  T getTile(int pX, int pY);

  /**
   * Liefert alle verf�gbaren Tiles
   *
   * @return alle verf�gbaren Tiles
   */
  T[] getTiles();

  /**
   * Liefert die Breite eines Tiles
   *
   * @return Breite eines Tiles in px
   */
  int getTileWidth();

  /**
   * Liefert die H�he eines Tiles
   *
   * @return H�he eines Tiles in px
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
   * Liefert das zugeh�rige Bild
   *
   * @return dazugeh�riges Bild
   */
  InputStream getImageInputStream() throws TFHException;
}
