package de.tfh.mapper.gui;

import com.alee.laf.menu.MenuBarStyle;
import com.alee.laf.menu.WebMenuBar;
import de.tfh.core.i18n.Messages;
import de.tfh.mapper.facade.IMapperFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;

/**
 * Menüleiste des Mappers
 *
 * @author W.Glanzer, 16.11.2014
 */
public class MapperMenuBar extends WebMenuBar
{
  private static final Logger logger = LoggerFactory.getLogger(MapperMenuBar.class);
  private final IMapperFacade facade;

  private ActionListener saveActionListener;

  public MapperMenuBar(IMapperFacade pFacade)
  {
    facade = pFacade;
    saveActionListener = new GuiAction.SaveMapActionListener(facade);

    setPreferredSize(new Dimension(getPreferredSize().width, 24));
    setMenuBarStyle(MenuBarStyle.attached);

    JMenu menu = new JMenu(Messages.get(11));  //Datei
    menu.add(_createNewMapItem());
    menu.add(_createLoadItem());
    menu.add(_createSaveItem());
    menu.add(_createSaveAsItem());
    menu.add(new JSeparator());
    menu.add(_createExitItem());
    add(menu);

    addComponentListener(new ComponentAdapter()
    {
      @Override
      public void componentShown(ComponentEvent e)
      {
        removeComponentListener(this);
        SwingUtilities.invokeLater(MapperMenuBar.this::_registerHotkeys);
      }
    });
  }

  /**
   * Registriert die Hotkeys
   */
  private void _registerHotkeys()
  {
    SwingUtilities.invokeLater(() -> {
      getRootPane().registerKeyboardAction((e) -> {
        if(facade.isSavable())
          saveActionListener.actionPerformed(e);
      }, KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), JComponent.WHEN_IN_FOCUSED_WINDOW);
    });
  }

  /**
   * Erstellt das "Laden..."-Item
   *
   * @return "Laden..."-Item
   */
  private JMenuItem _createLoadItem()
  {
    JMenuItem item = new JMenuItem(Messages.get(23));
    item.addActionListener(new GuiAction.LoadMapActionListener(facade));
    return item;
  }

  /**
   * Beenden-Item
   *
   * @return Beenden-Item
   */
  private JMenuItem _createExitItem()
  {
    JMenuItem exit = new JMenuItem(Messages.get(22));
    exit.addActionListener((e) -> facade.shutdown());
    return exit;
  }

  /**
   * Liefert das MenuItem für die Anzeige im Dropdown-Menü.
   * -> Für "Neue Map"
   *
   * @return MenuItem für die Anzeige im Dropdown-Menü
   */
  private JMenuItem _createNewMapItem()
  {
    JMenuItem item = new JMenuItem(Messages.get(19));
    item.addActionListener(new GuiAction.NewMapActionListener(facade));

    return item;
  }

  /**
   * MenuItem fürs Speichern der Map
   *
   * @return MenuItem
   */
  private JMenuItem _createSaveItem()
  {
    JMenuItem item = new JMenuItem(Messages.get(21));
    item.addPropertyChangeListener(evt -> item.setEnabled(facade.isSavable()));
    item.addActionListener(saveActionListener);

    return item;
  }

  /**
   * MenuItem fürs Speichern der Map
   *
   * @return MenuItem
   */
  private JMenuItem _createSaveAsItem()
  {
    JMenuItem item = new JMenuItem(Messages.get(31));
    item.addPropertyChangeListener(evt -> item.setEnabled(facade.isSavable()));
    item.addActionListener(new GuiAction.SaveAsActionListener(facade));
    return item;
  }
}
