package de.tfh.gamecore.map;

import java.util.BitSet;

/**
 * Repräsentiert ein Tile eines Chunks
 *
 * @author W.Glanzer, 17.11.2014
 */
public class TilePreference
{

  private int graphicID;

  public TilePreference(int pGraphicID)
  {
    graphicID = pGraphicID;
  }

  public TilePreference(BitSet pBits)
  {
    BitSet bitID = pBits.get(0, 8);
    BitSet bitAttr = pBits.get(9, 16);

    graphicID = (int) bitID.toLongArray()[0];
    _analyzeAttributeBits(bitAttr);
  }

  /**
   * Liefert die grafische ID des Tiles
   *
   * @return Grafische ID des Tiles
   */
  public int getGraphicID()
  {
    return graphicID;
  }

  /**
   * Setzt die grafische ID
   *
   * @param pGraphicID   ID, die gesetzt werden soll
   */
  public void setGraphicID(int pGraphicID)
  {
    graphicID = pGraphicID;
  }

  /**
   * Analysiert die Attribute des Tiles / BitSets
   */
  private void _analyzeAttributeBits(BitSet pAttributes)
  {
  }
}
