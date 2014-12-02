package de.tfh.gamecore.map;

import java.util.BitSet;

/**
 * Repräsentiert ein Tile eines Chunks
 *
 * @author W.Glanzer, 17.11.2014
 */
public class TileDescription
{

  private int graphicID = -1;

  public TileDescription(int pGraphicID)
  {
    graphicID = pGraphicID;
  }

  public TileDescription(BitSet pBits)
  {
    BitSet bitID = pBits.get(0, 7);
    BitSet bitAttr = pBits.get(8, 16);

    if(!bitID.isEmpty())
      graphicID = (int) bitID.toLongArray()[0];

    if(!bitAttr.isEmpty())
      _fromAttributeBits(bitAttr);
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
   * Liefert das repräsentierende BitSet zurück
   *
   * @return BitSet
   */
  public BitSet getBitSet()
  {
    return _toAttributeBits();
  }

  /**
   * Analysiert die Attribute des Tiles / BitSets
   */
  private void _fromAttributeBits(BitSet pAttributes)
  {
  }

  /**
   * Gibt die Attribut-Bits als Bitset zurück
   *
   * @return BitSet
   */
  private BitSet _toAttributeBits()
  {
    return BitSet.valueOf(new long[]{graphicID});
  }
}
