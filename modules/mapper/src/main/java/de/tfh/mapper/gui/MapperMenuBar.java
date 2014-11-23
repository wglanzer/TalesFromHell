package de.tfh.mapper.gui;

import de.tfh.core.i18n.Messages;

import java.awt.*;

/**
 * Menüleiste des Mappers
 *
 * @author W.Glanzer, 16.11.2014
 */
public class MapperMenuBar extends MenuBar
{

  public MapperMenuBar()
  {
    Menu menu = new Menu(Messages.get(11));  //Datei
    add(menu);
  }
}
