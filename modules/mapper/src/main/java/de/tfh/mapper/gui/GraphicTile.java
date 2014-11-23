package de.tfh.mapper.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Repräsentiert ein Tile der Map
 *
 * @author W.Glanzer, 16.11.2014
 */
public class GraphicTile extends JLabel
{

  private static final Color SELECTION_COLOR = new Color(0, 0, 255, 64);

  private final Image image;

  public GraphicTile(Image pImage)
  {
    image = pImage;

    setLayout(new OverlayLayout(this));
    setFocusable(true);
    setPreferredSize(new Dimension(32, 32));
    addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseReleased(MouseEvent e)
      {
        requestFocusInWindow();
      }
    });

    addFocusListener(new FocusAdapter()
    {
      @Override
      public void focusGained(FocusEvent e)
      {
        _repaint();
      }

      @Override
      public void focusLost(FocusEvent e)
      {
        _repaint();
      }

      private void _repaint()
      {
        SwingUtilities.invokeLater(new Runnable()
        {
          @Override
          public void run()
          {
            revalidate();
            repaint();
          }
        });
      }
    });
  }

  @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    int width = getPreferredSize().width;
    int height = getPreferredSize().height;

    g.drawImage(image, 0, 0, width, height, null);

    if(isFocusOwner())
    {
      g.setColor(SELECTION_COLOR);
      g.fillRect(0, 0, width, height);
    }
  }
}
