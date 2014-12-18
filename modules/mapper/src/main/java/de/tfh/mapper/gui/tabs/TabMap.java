package de.tfh.mapper.gui.tabs;

import com.alee.laf.splitpane.WebSplitPane;
import de.tfh.mapper.facade.IMapperFacade;
import de.tfh.mapper.gui.containers.MapEditorContainer;
import de.tfh.mapper.gui.containers.MapTilesContainer;

import javax.swing.*;
import java.awt.*;

/**
 * @author W.Glanzer, 14.12.2014
 */
public class TabMap extends JPanel
{

  public TabMap(IMapperFacade pFacade)
  {
    Container mapEditorContainer = new MapEditorContainer(pFacade);
    Container maptilesContainer = new MapTilesContainer(pFacade);

    setLayout(new BorderLayout());

    final WebSplitPane horizSplitpane = new WebSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    horizSplitpane.setLeftComponent(new JScrollPane(maptilesContainer, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
    horizSplitpane.setRightComponent(mapEditorContainer);
    horizSplitpane.setOneTouchExpandable(true);
    horizSplitpane.setContinuousLayout(true);
    add(horizSplitpane, BorderLayout.CENTER);

    SwingUtilities.invokeLater(() -> {
      horizSplitpane.setDividerLocation(0.18D);
    });
  }
}
