package de.tfh.gamecore.map;

/**
 * Beschreibt einen Layer auf einer Map.
 * Background-, Midground-, Foreground-Layer
 * Read-Only!
 *
 * @see de.tfh.mapper.facade.alterable.AlterableLayer
 * @author W.Glanzer, 17.11.2014
 */
public class Layer implements ILayer
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
  protected final TileDescription[] tilesOnLayer;

  public Layer(int pTilesX, int pTilesY)
  {
    this(pTilesX, pTilesY, new TileDescription[pTilesX * pTilesY]);
  }

  public Layer(int pTilesX, int pTilesY, TileDescription[] pTiles)
  {
    tilesX = pTilesX;
    tilesY = pTilesY;
    tilesOnLayer = pTiles;
  }

  @Override
  public TileDescription getTile(int pX, int pY)
  {
    return tilesOnLayer[tilesX * pY + pX];
  }

  @Override
  public int getTilesX()
  {
    return tilesX;
  }

  @Override
  public int getTilesY()
  {
    return tilesY;
  }
}
