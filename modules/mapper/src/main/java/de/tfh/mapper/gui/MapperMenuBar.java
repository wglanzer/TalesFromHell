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
 * Men�leiste des Mappers
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
    menu.add(_createSaveItem());
    menu.add(new JSeparator());
    menu.add(_createExitItem());
    add(menu);
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
   * Liefert das MenuItem f�r die Anzeige im Dropdown-Men�.
   * -> F�r "Neue Map"
   *
   * @return MenuItem f�r die Anzeige im Dropdown-Men�
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
   * MenuItem f�rs Speichern der Map
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
        chooser.setFileFilter(new FileFilter()
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
        });

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
}
