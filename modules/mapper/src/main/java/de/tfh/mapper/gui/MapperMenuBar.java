package de.tfh.mapper.gui;

import de.tfh.core.exceptions.TFHException;
import de.tfh.core.i18n.Messages;
import de.tfh.core.utils.ExceptionUtil;
import de.tfh.mapper.facade.IMapperFacade;
import de.tfh.mapper.gui.dialog.NewMapDialog;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 * Menüleiste des Mappers
 *
 * @author W.Glanzer, 16.11.2014
 */
public class MapperMenuBar extends MenuBar
{

  private final IMapperFacade facade;

  public MapperMenuBar(IMapperFacade pFacade)
  {
    facade = pFacade;
    Menu menu = new Menu(Messages.get(11));  //Datei
    menu.add(_createNewMapItem());
    add(menu);
  }

  /**
   * Liefert das MenuItem für die Anzeige im Dropdown-Menü.
   * -> Für "Neue Map"
   *
   * @return MenuItem für die Anzeige im Dropdown-Menü
   */
  private MenuItem _createNewMapItem()
  {
    MenuItem item = new MenuItem(Messages.get(19));
    item.addActionListener(e -> {
      NewMapDialog dialog = new NewMapDialog();
      dialog.addOKActionListener(() -> {
        try
        {
          String mapName = dialog.getMapName();
          String savePath = dialog.getSavePath();
          String tilesetPath = dialog.getTilesetPath();

          if(!mapName.isEmpty() && !savePath.isEmpty() && !tilesetPath.isEmpty())
          {
            facade.generateNewMap(mapName, savePath, tilesetPath);
            return true;
          }
        }
        catch(TFHException e2)
        {
          ExceptionUtil.logError(LoggerFactory.getLogger(MapperMenuBar.class), 38, e2);
        }

        return false;
      });
    });

    return item;
  }
}
