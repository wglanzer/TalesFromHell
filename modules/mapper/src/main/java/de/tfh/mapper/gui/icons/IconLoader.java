package de.tfh.mapper.gui.icons;

import de.tfh.core.utils.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * Hilfsklasse zum Laden der Icons
 *
 * @author W.Glanzer, 14.12.2014
 */
public class IconLoader
{

  private static final Logger logger = LoggerFactory.getLogger(IconLoader.class);

  /**
   * Lädt ein Icon als BufferedImage, oder gibt <tt>null</tt> zurück
   *
   * @param pPath Pfad des Bildes
   * @return BufferedImage, oder <tt>null</tt>
   */
  public static BufferedImage loadAsImage(String pPath)
  {
    try
    {
      InputStream stream = IIcons.class.getResourceAsStream(pPath);
      if(stream != null)
        return ImageIO.read(stream);
    }
    catch(Exception e)
    {
      ExceptionUtil.logError(logger, 9999, e, "pPath=" + pPath);
    }

    return null;
  }

  /**
   * Lädt ein Icon als ImageIcon, oder gibt <tt>null</tt> zurück
   *
   * @param pPath Pfad des Bildes
   * @return ImageIcon, oder <tt>null</tt>
   */
  public static ImageIcon loadAsIcon(String pPath)
  {
    return loadAsIcon(pPath, -1);
  }

  /**
   * Lädt ein Icon als ImageIcon, oder gibt <tt>null</tt> zurück.
   * Resized das Image sofort
   *
   * @param pPath  Pfad des Bildes
   * @param pWidth Breite des Bildes nach dem resizen, oder -1 um es nicht zu resizen
   * @return ImageIcon, oder <tt>null</tt>
   */
  public static ImageIcon loadAsIcon(String pPath, int pWidth)
  {
    try
    {
      BufferedImage image = loadAsImage(pPath);
      if(pWidth > -1)
      {
        Image instance = image.getScaledInstance(pWidth, (int) ((float) image.getHeight() * ((float) pWidth / (float)image.getWidth())), Image.SCALE_SMOOTH);
        return new ImageIcon(instance);
      }
      else
        return new ImageIcon(image);
    }
    catch(Exception e)
    {
      ExceptionUtil.logError(logger, 9999, e, "pPath=" + pPath);
    }

    return null;
  }
}
