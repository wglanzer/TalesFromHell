package de.tfh.gamecore.map;

import java.util.BitSet;

/**
 * Repräsentiert ein Tile eines Chunks
 *
 * @author W.Glanzer, 17.11.2014
 */
public class TilePreference
{

  private final int graphicID;

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
   * Analysiert die Attribute des Tiles / BitSets
   */
  private void _analyzeAttributeBits(BitSet pAttributes)
  {
  }
}
