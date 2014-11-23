package de.tfh.mapper.gui;

import de.tfh.core.IStaticResources;
import de.tfh.core.i18n.Messages;
import de.tfh.mapper.gui.containers.DummyContainer;
import de.tfh.mapper.gui.containers.MapEditorContainer;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;

/**
 * JFrame des Mappers, GUI
 *
 * @author W.Glanzer, 16.11.2014
 */
public class MapperFrame extends JFrame
{

  private Container mapEditorContainer;
  private Container maptilesContainer;
  private Container preferencesContainer;
  private Container classEditorContainer;
  private Container classTreeContainer;

  public MapperFrame(boolean pShow)
  {
    setTitle(MessageFormat.format(IStaticResources.MAPPER_TITLE, IStaticResources.MAIN_TITLE, IStaticResources.VERSION));
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setSize(IStaticResources.MAPPER_SIZE);
    setLocationRelativeTo(null);
    setMenuBar(new MapperMenuBar());
    setLayout(new BorderLayout());

    if(pShow)
      SwingUtilities.invokeLater(new Runnable()
      {
        @Override
        public void run()
        {
          _initGui();
          setVisible(true);
        }
      });
  }

  /**
   * Initialisiert generell die GUI
   */
  private void _initGui()
  {
    try
    {
      mapEditorContainer = new MapEditorContainer();
      maptilesContainer = new DummyContainer();
      preferencesContainer = new DummyContainer();
      classEditorContainer = new DummyContainer();
      classTreeContainer = new DummyContainer();

      JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);

      tabbedPane.addTab(Messages.get(12), _createTabMap(mapEditorContainer, maptilesContainer, preferencesContainer));
      tabbedPane.addTab(Messages.get(13), _createTabClasses(classTreeContainer, classEditorContainer));

      add(tabbedPane, BorderLayout.CENTER);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Initialisiert den Tab "Map"
   */
  private JPanel _createTabMap(Container pMapEditorContainer, Container pMaptilesContainer, Container pPreferencesContainer)
  {
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());

    final JSplitPane horizSplitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    final JSplitPane verticSplitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    verticSplitpane.setTopComponent(pMaptilesContainer);
    verticSplitpane.setBottomComponent(pPreferencesContainer);
    horizSplitpane.setLeftComponent(verticSplitpane);
    horizSplitpane.setRightComponent(pMapEditorContainer);
    panel.add(horizSplitpane, BorderLayout.CENTER);

    SwingUtilities.invokeLater(new Runnable()
    {
      @Override
      public void run()
      {
        horizSplitpane.setDividerLocation(0.18);
        verticSplitpane.setDividerLocation(0.6);
      }
    });

    return panel;
  }

  /**
   * Initialisiert den Tab "Klassen"
   */
  private JPanel _createTabClasses(Container pClassTreeContainer, Container pClassEditorContainer)
  {
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());

    final JSplitPane horizSplitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

    horizSplitpane.setLeftComponent(pClassTreeContainer);
    horizSplitpane.setRightComponent(pClassEditorContainer);

    SwingUtilities.invokeLater(new Runnable()
    {
      @Override
      public void run()
      {
        horizSplitpane.setDividerLocation(0.18);
      }
    });

    panel.add(horizSplitpane, BorderLayout.CENTER);
    return panel;
  }

}
