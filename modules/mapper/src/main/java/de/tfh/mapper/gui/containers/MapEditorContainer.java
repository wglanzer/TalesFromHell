package de.tfh.mapper.gui.containers;

import com.alee.extended.button.WebSwitch;
import com.alee.extended.panel.WebButtonGroup;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.toolbar.ToolbarStyle;
import com.alee.laf.toolbar.WebToolBar;
import com.google.common.base.Supplier;
import de.tfh.core.i18n.Messages;
import de.tfh.gamecore.map.Layer;
import de.tfh.mapper.facade.IMapperFacade;
import de.tfh.mapper.facade.MapperFacade;
import de.tfh.mapper.gui.GraphicChunk;
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
  private Supplier<Boolean> isPaintChunkSeparators;
  private Supplier<Boolean> isPaintTileSeparators;

  public MapEditorContainer(IMapperFacade pFacade)
  {
    super(pFacade);
    setLayout(new BorderLayout(0, 0));

    WebToolBar bar = new WebToolBar(WebToolBar.HORIZONTAL);
    bar.add(_getControlPanel());
    bar.addSeparator();
    bar.add(new WebLabel(Messages.get(32)));
    bar.add(_createIsPaintChunkSeparatorSwitch());
    bar.add(new WebLabel(Messages.get(33)));
    bar.add(_createIsPaintTileSeparatorSwitch());
    bar.setFloatable(false);
    bar.setToolbarStyle(ToolbarStyle.attached);
    bar.setUndecorated(true);

    add(new JScrollPane(content), BorderLayout.CENTER);
    add(bar, BorderLayout.NORTH);
  }

  @Override
  protected void reinit()
  {
    content.removeAll();

    IMapperFacade facade = getFacade();
    content.setLayout(TableLayoutHelper.createLayout(facade.getChunkCountX(), facade.getChunkCountY(), TableLayoutConstants.PREFERRED));

    for(int y = 0; y < facade.getChunkCountY(); y++)
      for(int x = 0; x < facade.getChunkCountX(); x++)
        content.add(new GraphicChunk(x, y, facade, isPaintChunkSeparators, isPaintTileSeparators), x + ", " + y);

    SwingUtilities.invokeLater(() -> {
      revalidate();
      repaint();
    });
  }

  /**
   * Erstellt den Switch fürs PaintChunkSeparator-Supplier und gibt ihn zurück
   *
   * @return Switch
   */
  private WebSwitch _createIsPaintChunkSeparatorSwitch()
  {
    WebSwitch switch1 = new WebSwitch(true);
    isPaintChunkSeparators = () -> switch1.isSelected();

    switch1.addActionListener((e) -> {
      revalidate();
      repaint();
    });

    return switch1;
  }

  /**
   * Erstellt den Switch fürs PaintTileSeparator-Supplier und gibt ihn zurück
   *
   * @return Switch
   */
  private WebSwitch _createIsPaintTileSeparatorSwitch()
  {
    WebSwitch switch1 = new WebSwitch(true);
    isPaintTileSeparators = () -> switch1.isSelected();

    switch1.addActionListener((e) -> {
      revalidate();
      repaint();
    });

    return switch1;
  }

  /**
   * Liefert das Control-Panel mit den Control-Buttons
   *
   * @return Control-Panel mit den Control-Buttons
   */
  private JPanel _getControlPanel()
  {
    WebToggleButton bg = new WebToggleButton(Messages.get(27));
    bg.setActionCommand("BG");
    bg.addHotkey(KeyEvent.VK_Q);
    WebToggleButton mg = new WebToggleButton(Messages.get(28));
    mg.setActionCommand("MG");
    mg.addHotkey(KeyEvent.VK_W);
    WebToggleButton fg = new WebToggleButton(Messages.get(29));
    fg.setActionCommand("FG");
    fg.addHotkey(KeyEvent.VK_E);
    WebToggleButton sl = new WebToggleButton(Messages.get(30));
    sl.setActionCommand("SL");
    sl.addHotkey(KeyEvent.VK_R);
    sl.setEnabled(false);

    WebButtonGroup group = new WebButtonGroup(true, bg, mg, fg, sl);
    group.setButtonsDrawFocus(false);
    group.getButtonGroup().addButtonGroupListener(() -> {
      String actionCommand = group.getButtonGroup().getSelection().getActionCommand();

      IMapperFacade facade = getFacade();
      if(!(facade instanceof MapperFacade))
        return;

      switch(actionCommand)
      {
        default:
        case "BG":
          ((MapperFacade) facade).setSelectedLayer(Layer.BACKGROUND);
          break;

        case "MG":
          ((MapperFacade) facade).setSelectedLayer(Layer.MIDGROUND);
          break;

        case "FG":
          ((MapperFacade) facade).setSelectedLayer(Layer.FOREGROUND);
          break;

        case "SL":
          ((MapperFacade) facade).setSelectedLayer(Layer.SPECIAL_LAYER);
          break;
      }
    });

    group.setBorder(BorderFactory.createEmptyBorder());
    group.getButtonGroup().setSelected(group.getButtonGroup().getButtons().get(0).getModel(), true);
    return group;
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
