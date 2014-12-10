package de.tfh.datamodels.models;

import de.tfh.datamodels.AbstractDataModel;

/**
 * Beschreibt eine Map auf XML-Ebene.
 * Hier werden wichtige Informationen über die Map gespeichert
 *
 * @author W.Glanzer, 22.11.2014
 */
public class MapDescriptionDataModel extends AbstractDataModel
{

  /**
   * Anzahl der Chunks in X-Richtung
   */
  public int chunksX = 0;

  /**
   * Anzahl der Chunks in Y-Richtung
   */
  public int chunksY = 0;

  /**
   * Anzahl der Tiels pro Chunk in X-Richtung
   */
  public int tilesPerChunkX = 0;

  /**
   * Anzahl der Tiels pro Chunk in Y-Richtung
   */
  public int tilesPerChunkY = 0;

  /**
   * Breite (in Pixel) der Tiles
   */
  public int tileWidth = 0;

  /**
   * Höhe (in Pixel) der Tiles
   */
  public int tileHeight = 0;

  @Override
  public String getName()
  {
    return "MapDescriptionDataModel";
  }
}
