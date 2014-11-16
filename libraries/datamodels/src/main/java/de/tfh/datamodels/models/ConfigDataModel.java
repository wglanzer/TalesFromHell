package de.tfh.datamodels.models;

import de.tfh.datamodels.AbstractDataModel;

/**
 * Datenmodell f�r die Config
 *
 * @author W.Glanzer, 25.10.2014
 */
public class ConfigDataModel extends AbstractDataModel
{

  /**
   * X-Aufl�sung
   */
  public int screenWidth = 1600;

  /**
   * Y-Aufl�sung
   */
  public int screenHeight = 900;

  /**
   * Multisampling
   */
  public int multisample = 2;

  /**
   * Musik-Lautst�rke, 0 - 1
   */
  public double musicVolume = 0.5;

  /**
   * Sound-Lautst�rke, 0 - 1
   */
  public double soundVolume = 0.5;

  /**
   * <tt>true</tt>, wenn Vollbild
   */
  public boolean fullscreen = false;

  /**
   * <tt>true</tt>, wenn das Spiel nur geupdted werden soll,
   * wenn im Vordergrund
   */
  public boolean onlyUpdateWhenVisible = true;

  /**
   * Musik eingeschaltet?
   */
  public boolean musicEnabled = true;

  /**
   * Sound eingeschaltet?
   */
  public boolean soundEnabled = true;

  /**
   * <tt>true</tt>, wenn die FPS angezeigt werden sollen
   */
  public boolean showFPS = true;

  /**
   * VSync eingeschaltet
   */
  public boolean vSync = false;

  @Override
  public String getName()
  {
    return "ConfigDataModel";
  }
}
