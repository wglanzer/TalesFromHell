package de.tfh.mapper.facade;

import de.tfh.core.exceptions.TFHException;
import de.tfh.gamecore.map.TileDescription;
import de.tfh.gamecore.util.ProgressObject;
import de.tfh.mapper.TFHMappperException;
import de.tfh.mapper.gui.GraphicTile;

import java.awt.*;
import java.io.File;
import java.io.OutputStream;

/**
 * Stellt die Verbindung zw. GUI und Backend her
 *
 * @author W.Glanzer, 20.11.2014
 */
public interface IMapperFacade
{

  /**
   * Erstellt eine neue Map mit den �bergebenen Parametern
   *
   * @param pName        Name der Map
   * @param pTilesetPath Pfad zum Tileset
   * @param pChunkCount  Anzahl der Chunks
   * @param pChunkSize   Gr��e der Chunks (Anzahl der Tiles innerhalb des Chunks)
   * @param pTileSize    Gr��e der Tiles in px
   */
  void generateNewMap(String pName, String pTilesetPath, Dimension pChunkCount, Dimension pChunkSize, Dimension pTileSize) throws TFHException;

  /**
   * Setzt die ID eines Tiles auf einer bestimmten Position
   *
   * @param pX               X-Position des Tiles
   * @param pY               Y-Position des Tiles
   * @param pLayer           Layer-ID des Tiles
   * @param pTileDescription ID des Tiles an sich
   */
  void setTile(int pX, int pY, int pLayer, TileDescription pTileDescription) throws TFHMappperException;

  /**
   * Gibt die ID zur�ck
   *
   * @param pX     X-Position des Tiles
   * @param pY     Y-Position des Tiles
   * @param pLayer Layer, der gezeichnet werden soll
   * @return ID des Tiles
   */
  int getTileIDOnMap(int pX, int pY, int pLayer) throws TFHException;

  /**
   * Liefert das Tile f�r die bestimmte ID
   *
   * @param pTileID Die ID des Tiles
   * @return Graphictile-Instanz
   */
  GraphicTile getTile(int pTileID);

  /**
   * Liefert das Bild des Tiles f�r die angegebene ID
   *
   * @param pTileID ID f�r die das Bild zur�ckgegeben werden soll
   * @return Image
   */
  Image getImageForTile(int pTileID);

  /**
   * Liefert die derzeitigen TilePreferences auf einer besimmten Position
   *
   * @param pXTile  X-Tileposition
   * @param pYTile  Y-Tileposition
   * @param pChunkX Chunk, auf dem das Tile was in X-Richtung
   * @param pChunkY Chunk, auf dem das Tile war in Y-Richtung
   * @return derzeiteigen TilePreferences
   */
  TileDescription getPreference(int pXTile, int pYTile, int pChunkX, int pChunkY, int pLayer) throws TFHException;

  /**
   * Liefert die Tile-Breite
   *
   * @return Tile-Breite
   */
  int getTileWidth();

  /**
   * Liefert die Tile-H�he
   *
   * @return Tile-H�he
   */
  int getTileHeight();

  /**
   * Liefert die Anzahl der Tiles in X-Richtung
   *
   * @return Anzahl der Tiles in X-Richtung
   */
  int getTileCountPerChunkX();

  /**
   * Liefert die Anzahl der Tiles in Y-Richtung
   *
   * @return Anzahl der Tiles in Y-Richtung
   */
  int getTileCountPerChunkY();

  /**
   * Liefert die Anzahl an Tiles
   *
   * @return Anzahl der Tiles
   */
  int getTileCount();

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
   * Setzt die ID des selektierten Tiles
   *
   * @param pID ID des selektierten Tiles, oder <tt>-1</tt>
   */
  void setSelectedMapTileID(int pID);

  /**
   * Liefert die selektierte ID der Map
   *
   * @return die selektierte ID des Tilesets, oder <tt>-1</tt>, wenn kein Tile selektiert ist
   */
  int getSelectedMapTileID();

  /**
   * Speichert die Map auf einem Outputstream
   *
   * @param pStream Stream, auf dem die Map gespeichert werden soll
   */
  ProgressObject save(OutputStream pStream);

  /**
   * Gibt zur�ck, ob die Map gespeichert werden kann
   *
   * @return <tt>true</tt>, wenn die Map gespeichert werden kann
   */
  boolean isSavable();

  /**
   * L�dt die Map aus einem File
   *
   * @param pFile File, das geladen werden kann / soll
   */
  void load(File pFile) throws TFHException;

  /**
   * F�gt einen neuen IChangeListener hinzu
   *
   * @param pListener IChangeListener, der hinzugef�gt werden soll
   */
  void addChangeListener(IChangeListener pListener);

  /**
   * Feuert, dass ich die Facade ge�ndert hat
   */
  void fireFacadeChanged();

  /**
   * Beendet den Mapper
   */
  void shutdown();

  /**
   * ChangeListener f�r die Facade
   */
  public interface IChangeListener
  {
    /**
     * Wird aufgerufen, wenn sich die Facade ge�ndert hat
     */
    void facadeChanged();

    /**
     * Wird aufgerufen, wenn sich die Map speichert
     *
     * @param pObject ProgressObject, aus dem man den Progress herauslesen kann
     */
    void mapSaved(ProgressObject pObject);

    /**
     * Wird aufgerufen, wenn eine Map geladen wird
     *
     * @param pObject ProgressObject, aus dem man den Progress herauslesen kann
     */
    void mapLoaded(ProgressObject pObject);
  }
}
