package de.tfh.mapper.gui;

import de.tfh.mapper.facade.IMapperFacade;

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
public class SelectableTile extends GraphicTile
{

  private static final Color SELECTION_COLOR = new Color(0, 0, 255, 64);  //todo raus hier!

  private final IMapperFacade facade;
  private final int id;

  public SelectableTile(IMapperFacade pFacade, int pId, Image pImage)
  {
    super(pId, pImage);
    facade = pFacade;
    id = pId;

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
        facade.setSelectedMapTileID(id);
      }

      @Override
      public void focusLost(FocusEvent e)
      {
        _repaint();
      }

      private void _repaint()
      {
        SwingUtilities.invokeLater(() -> {
          revalidate();
          repaint();
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

    if(isFocusOwner())
    {
      g.setColor(SELECTION_COLOR);
      g.fillRect(0, 0, width, height);
    }
  }
}
