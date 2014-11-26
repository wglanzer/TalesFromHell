package de.tfh.mapper.facade;

import de.tfh.core.exceptions.TFHException;
import de.tfh.gamecore.map.TilePreference;
import de.tfh.mapper.TFHMappperException;
import de.tfh.mapper.gui.GraphicTile;

/**
 * Stellt die Verbindung zw. GUI und Backend her
 *
 * @author W.Glanzer, 20.11.2014
 */
public interface IMapperFacade
{

  /**
   * Erstellt eine neue Map mit den übergebenen Parametern
   *
   * @param pName           Name der Map
   * @param pMapSavingPath  Speicher-Pfad der Map
   * @param pTilesetPath    Pfad zum Tileset
   */
  void generateNewMap(String pName, String pMapSavingPath, String pTilesetPath) throws TFHException;

  /**
   * Setzt die ID eines Tiles auf einer bestimmten Position
   *
   * @param pX              X-Position des Tiles
   * @param pY              Y-Position des Tiles
   * @param pLayer          Layer-ID des Tiles
   * @param pTilePreference ID des Tiles an sich
   */
  void setTile(int pX, int pY, int pLayer, TilePreference pTilePreference) throws TFHMappperException;

  /**
   * Gibt das GraphicTile zurück, das gezeichnet werden kann
   *
   * @param pX      X-Position des Tiles
   * @param pY      Y-Position des Tiles
   * @param pLayer  Layer, der gezeichnet werden soll
   * @return GraphicTile-Instanz
   */
  GraphicTile getTilePaintableOnMap(int pX, int pY, int pLayer) throws TFHException;

  /**
   * Liefert das Tile für die bestimmte ID
   *
   * @param pTileID  Die ID des Tiles
   * @return Graphictile-Instanz
   */
  GraphicTile getTile(int pTileID);

  /**
   * Liefert die Tile-Breite
   *
   * @return Tile-Breite
   */
  int getTileWidth();

  /**
   * Liefert die Tile-Höhe
   *
   * @return Tile-Höhe
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
   * Setzt die ID des selektierten Tiles
   *
   * @param pID  ID des selektierten Tiles, oder <tt>-1</tt>
   */
  void setSelectedMapTileID(int pID);

  /**
   * Liefert die selektierte ID der Map
   *
   * @return die selektierte ID des Tilesets, oder <tt>-1</tt>, wenn kein Tile selektiert ist
   */
  int getSelectedMapTileID();

  /**
   * Fügt einen neuen IChangeListener hinzu
   *
   * @param pListener  IChangeListener, der hinzugefügt werden soll
   */
  void addChangeListener(IChangeListener pListener);

  /**
   * Feuert, dass ich die Facade geändert hat
   */
  void fireFacadeChanged();

  /**
   * ChangeListener für die Facade
   */
  public interface IChangeListener
  {
    /**
     * Wird aufgerufen, wenn sich die Facade geändert hat
     */
    void facadeChanged();
  }
}
