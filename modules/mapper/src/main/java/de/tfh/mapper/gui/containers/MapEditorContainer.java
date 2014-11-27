package de.tfh.mapper.gui.containers;

import de.tfh.mapper.facade.IMapperFacade;
import de.tfh.mapper.gui.GraphicChunk;
import de.tfh.mapper.gui.tablelayout.TableLayoutConstants;
import de.tfh.mapper.gui.tablelayout.TableLayoutHelper;

import javax.swing.*;
import java.awt.*;

/**
 * @author W.Glanzer, 19.11.2014
 */
public class MapEditorContainer extends AbstractContainer implements Scrollable
{
  public MapEditorContainer(IMapperFacade pFacade)
  {
    super(pFacade);
  }

  @Override
  protected void reinit()
  {
    removeAll();

    IMapperFacade facade = getFacade();
    setLayout(TableLayoutHelper.createLayout(facade.getChunkCountX(), facade.getChunkCountY(), TableLayoutConstants.PREFERRED));

    for(int y = 0; y < facade.getChunkCountY(); y++)
      for(int x = 0; x < facade.getChunkCountX(); x++)
        add(new GraphicChunk(x, y, facade), x + ", " + y);

    SwingUtilities.invokeLater(() -> {
      revalidate();
      repaint();
    });
  }

  @Override
  public Dimension getPreferredScrollableViewportSize()
  {
    return getPreferredSize();
  }

  @Override
  public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction)
  {
    return 16;
  }

  @Override
  public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction)
  {
    return 16;
  }

  @Override
  public boolean getScrollableTracksViewportWidth()
  {
    return false;
  }

  @Override
  public boolean getScrollableTracksViewportHeight()
  {
    return false;
  }
}
