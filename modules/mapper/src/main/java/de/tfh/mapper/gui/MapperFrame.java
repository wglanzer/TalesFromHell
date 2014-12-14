package de.tfh.mapper.gui;

import de.tfh.core.IStaticResources;
import de.tfh.core.i18n.Messages;
import de.tfh.core.utils.ExceptionUtil;
import de.tfh.gamecore.util.ProgressObject;
import de.tfh.mapper.ChangeListenerAdapter;
import de.tfh.mapper.facade.IMapperFacade;
import de.tfh.mapper.gui.common.ComponentGlassPane;
import de.tfh.mapper.gui.containers.MapEditorContainer;
import de.tfh.mapper.gui.containers.MapTilesContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  private static final Logger logger = LoggerFactory.getLogger(MapperFrame.class);

  private final IMapperFacade facade;
  private Container mapEditorContainer;
  private Container maptilesContainer;
  private Container preferencesContainer;
  private Container classEditorContainer;
  private Container classTreeContainer;

  public MapperFrame(boolean pShow, IMapperFacade pFacade)
  {
    facade = pFacade;
    pFacade.addChangeListener(new _MapSaveListener());

    setTitle(MessageFormat.format(IStaticResources.MAPPER_TITLE, IStaticResources.MAIN_TITLE, IStaticResources.VERSION));
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setSize(IStaticResources.MAPPER_SIZE);
    setLocationRelativeTo(null);
    setJMenuBar(new MapperMenuBar(facade));
    setLayout(new BorderLayout());

    if(pShow)
      SwingUtilities.invokeLater(() -> {
        _initGui();
        setVisible(true);
      });
  }

  /**
   * Initialisiert generell die GUI
   */
  private void _initGui()
  {
    try
    {
      mapEditorContainer = new MapEditorContainer(facade);
      maptilesContainer = new MapTilesContainer(facade);
//      preferencesContainer = new DummyContainer(facade);
//      classEditorContainer = new DummyContainer(facade);
//      classTreeContainer = new DummyContainer(facade);

      JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);

      tabbedPane.addTab(Messages.get(12), _createTabMap(mapEditorContainer, maptilesContainer, preferencesContainer));
//      tabbedPane.addTab(Messages.get(13), _createTabClasses(classTreeContainer, classEditorContainer));

      add(tabbedPane, BorderLayout.CENTER);
    }
    catch(Exception e)
    {
      ExceptionUtil.logError(logger, 62, e);
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
//    final JSplitPane verticSplitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
//    verticSplitpane.setTopComponent(new JScrollPane(pMaptilesContainer, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
//    verticSplitpane.setBottomComponent(pPreferencesContainer);
    horizSplitpane.setLeftComponent(new JScrollPane(pMaptilesContainer, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
    horizSplitpane.setRightComponent(pMapEditorContainer);
    panel.add(horizSplitpane, BorderLayout.CENTER);

    SwingUtilities.invokeLater(() -> {
      horizSplitpane.setResizeWeight(0.18);
//      verticSplitpane.setResizeWeight(0.6);
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

    SwingUtilities.invokeLater(() -> horizSplitpane.setDividerLocation(0.18));

    panel.add(horizSplitpane, BorderLayout.CENTER);
    return panel;
  }

  /**
   * ChangeListener, der die GlassPane darstellt,
   * wenn mit dem Map-Speichern begonnen wird
   */
  private class _MapSaveListener extends ChangeListenerAdapter
  {
    final JProgressBar bar = new JProgressBar();
    final ComponentGlassPane pane = new ComponentGlassPane(bar);
    Component oldGlassPane = null;

    @Override
    public void mapSaved(ProgressObject pObject)
    {
      _addBar(pObject);

      if(pObject.isFinished())
        _deactivate();
    }

    @Override
    public void mapLoaded(ProgressObject pObject)
    {
      _addBar(pObject);

      if(pObject.isFinished())
        _deactivate();
    }

    private void _addBar(ProgressObject pObject)
    {
      JRootPane rootpane = SwingUtilities.getRootPane(MapperFrame.this);
      oldGlassPane = rootpane.getGlassPane();
      rootpane.setGlassPane(pane);
      pane.activate();

      SwingUtilities.invokeLater(MapperFrame.this::repaint);

      pObject.addProgressListener(new ProgressObject.IProgressListener()
      {
        @Override
        public void progressChanged(double pNew)
        {
          bar.setValue((int) pNew);
          SwingUtilities.invokeLater(MapperFrame.this::repaint);
        }

        @Override
        public void finished()
        {
          _deactivate();
        }
      });
    }

    private void _deactivate()
    {
      pane.deactivate();
      SwingUtilities.getRootPane(MapperFrame.this).setGlassPane(oldGlassPane);

      SwingUtilities.invokeLater(MapperFrame.this::repaint);
    }
  }
}
