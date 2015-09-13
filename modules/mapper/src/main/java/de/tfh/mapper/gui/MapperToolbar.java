package de.tfh.mapper.gui;

import com.alee.laf.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.toolbar.ToolbarStyle;
import com.alee.laf.toolbar.WebToolBar;
import de.tfh.core.i18n.Messages;
import de.tfh.mapper.facade.IMapperFacade;
import de.tfh.mapper.gui.icons.IIcons;
import de.tfh.mapper.gui.icons.IconLoader;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


/**
 * @author W.Glanzer, 14.12.2014
 */
public class MapperToolbar extends WebToolBar
{

  private static final Color BG = new Color(235, 235, 235);
  private static final int SIZE = 16;

  private final IMapperFacade facade;

  public MapperToolbar(IMapperFacade pFacade)
  {
    super(WebToolBar.HORIZONTAL);
    facade = pFacade;
    setToolbarStyle(ToolbarStyle.attached);
    setTopBgColor(BG);
    setBottomBgColor(BG);

    add(_createButton(IconLoader.loadAsIcon(IIcons.NEW, SIZE), Messages.get(19), new GuiAction.NewMapActionListener(facade)));
    add(_createButton(IconLoader.loadAsIcon(IIcons.LOAD, SIZE), Messages.get(23), new GuiAction.LoadMapActionListener(facade)));
    add(_createButton(IconLoader.loadAsIcon(IIcons.SAVE, SIZE), Messages.get(21), new GuiAction.SaveMapActionListener(facade)));
    addToEnd(_createButton(IconLoader.loadAsIcon(IIcons.ABOUT, SIZE), Messages.get(34), new GuiAction.ShowAboutDialogActionListener()));
  }

  private WebButton _createButton(ImageIcon pIcon, @Nullable String pTooltip, ActionListener pListener)
  {
    WebButton button = WebButton.createIconWebButton(pIcon, StyleConstants.smallRound, true);
    if(pTooltip != null)
      button.setToolTipText(pTooltip);
    button.addActionListener(pListener);
    return button;
  }
}
