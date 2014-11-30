package de.tfh.gamecore.map;

import java.util.BitSet;

/**
 * Repräsentiert ein Tile eines Chunks
 *
 * @author W.Glanzer, 17.11.2014
 */
public class TilePreference
{

  private int graphicID = -1;

  public TilePreference(int pGraphicID)
  {
    graphicID = pGraphicID;
  }

  public TilePreference(BitSet pBits)
  {
    BitSet bitID = pBits.get(0, 7);
    BitSet bitAttr = pBits.get(8, 16);

    if(!bitID.isEmpty())
      graphicID = (int) bitID.toLongArray()[0];

    if(!bitAttr.isEmpty())
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

  /**
   * Liefert das repräsentierende BitSet zurück
   *
   * @return BitSet
   */
  public BitSet getBitSet()
  {
    BitSet bitSet = BitSet.valueOf(new long[]{graphicID});
    return bitSet;
  }
}
