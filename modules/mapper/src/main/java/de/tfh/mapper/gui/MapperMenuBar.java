package de.tfh.mapper.gui;

import de.tfh.core.IStaticResources;
import de.tfh.core.exceptions.TFHException;
import de.tfh.core.i18n.Messages;
import de.tfh.core.utils.ExceptionUtil;
import de.tfh.mapper.facade.IMapperFacade;
import de.tfh.mapper.gui.dialog.NewMapDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Menüleiste des Mappers
 *
 * @author W.Glanzer, 16.11.2014
 */
public class MapperMenuBar extends JMenuBar
{
  private static final Logger logger = LoggerFactory.getLogger(MapperMenuBar.class);
  private final IMapperFacade facade;

  public MapperMenuBar(IMapperFacade pFacade)
  {
    facade = pFacade;
    JMenu menu = new JMenu(Messages.get(11));  //Datei
    menu.add(_createNewMapItem());
    menu.add(_createLoadItem());
    menu.add(_createSaveItem());
    menu.add(new JSeparator());
    menu.add(_createExitItem());
    add(menu);
  }

  /**
   * Erstellt das "Laden..."-Item
   *
   * @return "Laden..."-Item
   */
  private JMenuItem _createLoadItem()
  {
    JMenuItem item = new JMenuItem("LOAD...");
    item.addActionListener((e) -> {
      JFileChooser chooser = new JFileChooser();
      chooser.setFileFilter(new _MapFileFilter());
      int result = chooser.showDialog(SwingUtilities.getRoot(this), "Load");
      File file = chooser.getSelectedFile();
      if(result == JFileChooser.APPROVE_OPTION && file != null)
        try
        {
          facade.load(file);
        }
        catch(TFHException e1)
        {
          ExceptionUtil.logError(logger, 9999, e1, "selectedFile=" + file);
        }
    });
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
    item.addActionListener(e -> {
      NewMapDialog dialog = new NewMapDialog();
      dialog.addOKActionListener(() -> {
        try
        {
          String mapName = dialog.getMapName();
          String tilesetPath = dialog.getTilesetPath();

          if(!mapName.isEmpty() && !tilesetPath.isEmpty())
          {
            facade.generateNewMap(mapName, tilesetPath);
            return true;
          }
        }
        catch(TFHException e2)
        {
          ExceptionUtil.logError(logger, 38, e2);
        }

        return false;
      });
    });

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
    item.addActionListener(e -> {
      try
      {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new _MapFileFilter());

        int result = chooser.showSaveDialog(SwingUtilities.getRoot(this));
        if(result == JFileChooser.APPROVE_OPTION)
        {
          String file = chooser.getSelectedFile().getAbsolutePath();
          if(!file.endsWith("." + IStaticResources.MAP_FILEENDING))
            file += "." + IStaticResources.MAP_FILEENDING;

          FileOutputStream stream = new FileOutputStream(file);
          facade.save(stream);
        }
      }
      catch(Exception ex)
      {
        ExceptionUtil.logError(logger, 48, ex);
      }
    });

    return item;
  }

  private static class _MapFileFilter extends FileFilter
  {
    @Override
    public boolean accept(File f)
    {
      if(f != null)
      {
        if(f.isDirectory() || f.getName().endsWith("." + IStaticResources.MAP_FILEENDING))
          return true;
      }

      return false;
    }

    @Override
    public String getDescription()
    {
      return "Map (*." + IStaticResources.MAP_FILEENDING + ")";
    }
  }
}
