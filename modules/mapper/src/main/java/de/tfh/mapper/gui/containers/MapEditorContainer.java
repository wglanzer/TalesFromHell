package de.tfh.mapper.gui.containers;

import de.tfh.core.i18n.Messages;
import de.tfh.gamecore.map.Layer;
import de.tfh.mapper.facade.IMapperFacade;
import de.tfh.mapper.facade.MapperFacade;
import de.tfh.mapper.gui.GraphicChunk;
import de.tfh.mapper.gui.common.JToggleButtonPanel;
import de.tfh.mapper.gui.tablelayout.TableLayoutConstants;
import de.tfh.mapper.gui.tablelayout.TableLayoutHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author W.Glanzer, 19.11.2014
 */
public class MapEditorContainer extends AbstractContainer
{
  private static JPanel content = new _ScrollablePanel();
  private JToggleButtonPanel panel;

  public MapEditorContainer(IMapperFacade pFacade)
  {
    super(pFacade);
    setLayout(new BorderLayout(0, 0));
    add(new JScrollPane(content), BorderLayout.CENTER);
    add(_getControlPanel(), BorderLayout.NORTH);

    SwingUtilities.invokeLater(this::_registerHotkeys);
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

  /**
   * Registriert die Hotkeys global
   */
  private void _registerHotkeys()
  {
    JRootPane root = getRootPane();
    root.registerKeyboardAction(e -> panel.setSelected(0), KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    root.registerKeyboardAction(e -> panel.setSelected(1), KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    root.registerKeyboardAction(e -> panel.setSelected(2), KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    root.registerKeyboardAction(e -> panel.setSelected(3), KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
  }

  /**
   * Liefert das Control-Panel mit den Control-Buttons
   *
   * @return Control-Panel mit den Control-Buttons
   */
  private JPanel _getControlPanel()
  {
    panel = new JToggleButtonPanel(4, Messages.get(27), Messages.get(28), Messages.get(29), Messages.get(30));
    panel.addSelectionListener(pNewIndex -> {
      IMapperFacade facade = getFacade();
      if(!(facade instanceof MapperFacade))
        return;

      switch(pNewIndex)
      {
        default:
        case 0:
          ((MapperFacade) facade).setSelectedLayer(Layer.BACKGROUND);
          break;

        case 1:
          ((MapperFacade) facade).setSelectedLayer(Layer.MIDGROUND);
          break;

        case 2:
          ((MapperFacade) facade).setSelectedLayer(Layer.FOREGROUND);
          break;

        case 3:
          ((MapperFacade) facade).setSelectedLayer(Layer.SPECIAL_LAYER);
          break;
      }
    });
    panel.setBorder(BorderFactory.createEmptyBorder());
    panel.setSelected(0);
    return panel;
  }

  /**
   * JPanel-Impl fürs Scrollen
   */
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
