package de.tfh.mapper.gui;

import com.alee.extended.layout.ToolbarLayout;
import com.alee.extended.statusbar.WebMemoryBar;
import com.alee.extended.statusbar.WebStatusBar;
import com.alee.laf.tabbedpane.TabbedPaneStyle;
import com.alee.laf.tabbedpane.WebTabbedPane;
import de.tfh.core.IStaticResources;
import de.tfh.core.i18n.Messages;
import de.tfh.core.utils.ExceptionUtil;
import de.tfh.gamecore.util.ProgressObject;
import de.tfh.mapper.ChangeListenerAdapter;
import de.tfh.mapper.facade.IMapperFacade;
import de.tfh.mapper.gui.common.ComponentGlassPane;
import de.tfh.mapper.gui.tabs.TabMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
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
  private final JMenuBar menuBar;

  public MapperFrame(boolean pShow, IMapperFacade pFacade)
  {
    facade = pFacade;
    menuBar = new MapperMenuBar(facade);

    pFacade.addChangeListener(new _MapSaveListener());

    setJMenuBar(menuBar);
    setTitle(MessageFormat.format(IStaticResources.MAPPER_TITLE, IStaticResources.MAIN_TITLE, IStaticResources.VERSION));
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setSize(IStaticResources.MAPPER_SIZE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    if(pShow)
      SwingUtilities.invokeLater(() -> {
        _initGui();
        setVisible(true);
      });

    SwingUtilities.invokeLater(this::_registerHotkeys);
  }

  /**
   * Registriert die Hotkeys
   */
  private void _registerHotkeys()
  {
    SwingUtilities.invokeLater(() -> {
      JRootPane root = SwingUtilities.getRootPane(this);
      root.registerKeyboardAction((e) -> {
        setJMenuBar(getJMenuBar() == null ? menuBar : null);
        revalidate();
        repaint();
      }, KeyStroke.getKeyStroke(KeyEvent.VK_ALT, KeyEvent.ALT_DOWN_MASK), JComponent.WHEN_IN_FOCUSED_WINDOW);
    });
  }

  /**
   * Initialisiert generell die GUI.
   * Hier werden bspw. die Tabs zusammengebaut, oder
   * die Statusbar hinzugefügt
   */
  private void _initGui()
  {
    try
    {
      // Tabs initialisieren
      WebTabbedPane tabbedPane = new WebTabbedPane(WebTabbedPane.TOP);
      tabbedPane.addTab(Messages.get(12), new TabMap(facade));
      tabbedPane.setTabbedPaneStyle(TabbedPaneStyle.attached);
      add(tabbedPane, BorderLayout.CENTER);

      // Initialisiert die StatusBar
      add(_createStatusBar(), BorderLayout.SOUTH);

      // Initialisiert die Main-ToolBar
      add(_createMainToolBar(), BorderLayout.NORTH);
    }
    catch(Exception e)
    {
      ExceptionUtil.logError(logger, 62, e);
    }
  }

  /**
   * Initialisiert die Toolbar und gibt diese zurück
   *
   * @return die Toolbar
   */
  private JComponent _createMainToolBar()
  {
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.add(new MapperToolbar(facade));
    panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));
    return panel;
  }

  /**
   * Initialisiert die Statusbar und gibt diese zurück
   *
   * @return die Statusbar
   */
  private JComponent _createStatusBar()
  {
    WebStatusBar statusBar = new WebStatusBar();
    WebMemoryBar memoryBar = new WebMemoryBar();
    memoryBar.setPreferredWidth(memoryBar.getPreferredSize().width + 20);
    memoryBar.setDrawBorder(false);
    memoryBar.setFillBackground(false);
    memoryBar.setShowMaximumMemory(false);

    statusBar.addSeparatorToEnd();
    statusBar.add(memoryBar, ToolbarLayout.END);
    return statusBar;
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
