package de.tfh.gamecore.map;

/**
 * Beschreibt einen Layer auf einer Map.
 * Background-, Midground-, Foreground-Layer
 * Read-Only!
 *
 * @see de.tfh.gamecore.map.alterable.AlterableLayer
 * @author W.Glanzer, 17.11.2014
 */
public class Layer
{

  /**
   * Hintergrund-ID
   */
  public static final int BACKGROUND = 0;

  /**
   * Mittelgrund-ID
   */
  public static final int MIDGROUND = 1;

  /**
   * Vordergrund-ID
   */
  public static final int FOREGROUND = 3;


  /**
   * ID des Speziallayers
   */
  public static final int SPECIAL_LAYER = 2;

  /**
   * Anzahl der Tiles in X- und Y-Richtung
   */
  private final int tilesX;
  private final int tilesY;

  /**
   * TilePreferences jedes einzelnen Tiles
   */
  protected final TilePreference[] tilesOnLayer;

  public Layer(int pTilesX, int pTilesY)
  {
    tilesX = pTilesX;
    tilesY = pTilesY;
    tilesOnLayer = new TilePreference[tilesX * tilesY];
  }

  /**
   * Liefert ein Tile auf diesem Layer, oder <tt>null</tt>
   *
   * @param pX  X-Position des Tiles
   * @param pY  Y-Position des Tiles
   * @return Die TilePreferences, oder <tt>null</tt>
   */
  public TilePreference getTile(int pX, int pY)
  {
    return tilesOnLayer[tilesX * pY + pX];
  }

  /**
   * Anzahl der Tiles in X-Richtung
   *
   * @return Anzahl der Tiles in X-Richtung
   */
  public int getTilesX()
  {
    return tilesX;
  }

  /**
   * Anzahl der Tiles in Y-Richtung
   *
   * @return Anzahl der Tiles in Y-Richtung
   */
  public int getTilesY()
  {
    return tilesY;
  }
}
