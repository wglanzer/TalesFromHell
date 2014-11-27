package de.tfh.mapper.gui.containers;

import de.tfh.mapper.facade.IMapperFacade;
import de.tfh.mapper.gui.GraphicTile;
import de.tfh.mapper.gui.SelectableTile;
import de.tfh.mapper.gui.WrapLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Container für die MapTiles im Mapper
 *
 * @author W.Glanzer, 24.11.2014
 */
public class MapTilesContainer extends AbstractContainer implements Scrollable
{

  public MapTilesContainer(IMapperFacade pFacade)
  {
    super(pFacade);

    setOpaque(true);
    setBackground(Color.BLACK);
    setLayout(new WrapLayout(FlowLayout.CENTER, 1, 1));
  }

  @Override
  protected void reinit()
  {
    IMapperFacade facade = getFacade();
    for(int i = 0; i < facade.getTileCount(); i++)
    {
      GraphicTile tile = facade.getTile(i);
      add(new SelectableTile(facade, tile.getId(), tile.getImage()));   //Alle Tiles hinzufügen
    }

    SwingUtilities.invokeLater(() -> {
      revalidate();
      repaint();
    });
  }

  @Override
  public Dimension getPreferredScrollableViewportSize()
  {
    Dimension pref = getPreferredSize();
    return new Dimension(pref.width + 20, pref.height);
  }

  @Override
  public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction)
  {
    return 32;
  }

  @Override
  public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction)
  {
    return 32;
  }

  @Override
  public boolean getScrollableTracksViewportWidth()
  {
    return true;
  }

  @Override
  public boolean getScrollableTracksViewportHeight()
  {
    return false;
  }
}
