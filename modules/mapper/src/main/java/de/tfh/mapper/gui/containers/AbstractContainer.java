package de.tfh.mapper.gui.containers;

import javax.swing.*;
import java.awt.*;

/**
 * @author W.Glanzer, 19.11.2014
 */
public class AbstractContainer extends JPanel
{

  private static final int SIZE = 16;
  private static final Color INVALID = new Color(0xFF00FF);

  @Override
  public void paintComponent(Graphics g)
  {
    int width = getWidth() + SIZE;
    int height = getHeight() + SIZE;

    g.setColor(Color.BLACK);
    g.fillRect(0, 0, width, height);

    g.setColor(INVALID);
    for(int i = 0; i < height / SIZE; i++)
      for(int j = i % 2; j < width / SIZE; j += 2)
        g.fillRect(j * SIZE, i * SIZE, SIZE, SIZE);
  }
}
