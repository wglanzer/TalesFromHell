package de.tfh.gamecore.map.tileset;

import de.tfh.core.exceptions.TFHException;

import java.io.InputStream;

/**
 * Beschreibt ein Tileset für die Map
 *
 * @author W.Glanzer, 24.11.2014
 */
public interface ITileset<T>
{
  /**
   * Liefert das Tile für den angegebenen Layer
   *
   * @param pLayer  Layer, der verwendet werden soll
   * @return Das Tile (Instanz von T)
   */
  public T getTileForID(int pLayer);

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
