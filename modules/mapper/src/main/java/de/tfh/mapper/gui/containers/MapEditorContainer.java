package de.tfh.mapper.gui.containers;

import de.tfh.core.i18n.Messages;
import de.tfh.mapper.facade.IMapperFacade;
import de.tfh.mapper.gui.GraphicChunk;
import de.tfh.mapper.gui.common.JToggleButtonPanel;
import de.tfh.mapper.gui.tablelayout.TableLayoutConstants;
import de.tfh.mapper.gui.tablelayout.TableLayoutHelper;

import javax.swing.*;
import java.awt.*;

/**
 * @author W.Glanzer, 19.11.2014
 */
public class MapEditorContainer extends AbstractContainer
{
  private static JPanel content = new _ScrollablePanel();

  public MapEditorContainer(IMapperFacade pFacade)
  {
    super(pFacade);
    setLayout(new BorderLayout(0, 0));
    add(new JScrollPane(content), BorderLayout.CENTER);
    add(_getControlPanel(), BorderLayout.NORTH);
  }

  @Override
  protected void reinit()
  {
    content.removeAll();

    IMapperFacade facade = getFacade();
    content.setLayout(TableLayoutHelper.createLayout(facade.getChunkCountX(), facade.getChunkCountY(), TableLayoutConstants.PREFERRED));

    for(int y = 0; y < facade.getChunkCountY(); y++)
      for(int x = 0; x < facade.getChunkCountX(); x++)
        content.add(new GraphicChunk(x, y, facade), x + ", " + y);

    SwingUtilities.invokeLater(() -> {
      revalidate();
      repaint();
    });
  }

  private JPanel _getControlPanel()
  {
    JToggleButtonPanel panel = new JToggleButtonPanel(4, Messages.get(27), Messages.get(28), Messages.get(29), Messages.get(30));
    panel.setBorder(BorderFactory.createEmptyBorder());
    panel.setSelected(0);
    return panel;
  }

  private static class _ScrollablePanel extends JPanel implements Scrollable
  {
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
}
