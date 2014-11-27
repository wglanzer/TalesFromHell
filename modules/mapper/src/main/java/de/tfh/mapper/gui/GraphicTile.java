package de.tfh.mapper.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Grafisches Tile
 *
 * @author W.Glanzer, 27.11.2014
 */
public class GraphicTile extends JPanel
{

  private final int id;
  private final Image image;

  public GraphicTile(int pId, Image pImage)
  {
    id = pId;
    image = pImage;
  }

  @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
  }

  public int getId()
  {
    return id;
  }

  public Image getImage()
  {
    return image;
  }
}
