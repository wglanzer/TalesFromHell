package de.tfh.mapper.gui.containers;

import de.tfh.core.guicommon.WrapLayout;
import de.tfh.mapper.facade.IMapperFacade;

import javax.swing.*;
import java.awt.*;

/**
 * Container für die MapTiles im Mapper
 *
 * @author W.Glanzer, 24.11.2014
 */
public class MapTilesContainer extends AbstractContainer implements Scrollable
{

  private final IMapperFacade facade;

  public MapTilesContainer(IMapperFacade pFacade)
  {
    facade = pFacade;
    setOpaque(true);
    setBackground(Color.BLACK);
    setLayout(new WrapLayout(FlowLayout.CENTER, 1, 1));

    for(int i = 0; i < facade.getTileCount(); i++)
      add(facade.getTile(i));   //Alle Tiles hinzufügen
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
