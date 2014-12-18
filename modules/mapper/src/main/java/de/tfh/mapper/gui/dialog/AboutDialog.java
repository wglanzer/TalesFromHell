package de.tfh.mapper.gui.dialog;

import de.tfh.core.IStaticResources;

import javax.swing.*;
import java.awt.*;

/**
 * About-Dialog-Impl
 *
 * @author W.Glanzer, 17.12.2014
 */
public class AboutDialog extends AbstractDialog
{

  public AboutDialog()
  {
    setTitle(IStaticResources.MAIN_TITLE);

    JPanel panel = getTextPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.add(_getTitle());
    panel.add(_getVersion());
    panel.add(_getDesc());
    panel.add(_getCopyright());

    pack();
    addOKActionListener(() -> true);
  }

  private JLabel _getTitle()
  {
    JLabel label = new JLabel(IStaticResources.MAIN_TITLE + " - MapCreator");
    label.setFont(new Font(label.getFont().getName(), Font.BOLD, 20));
    return label;
  }

  private JLabel _getVersion()
  {
    return new JLabel("Version: " + IStaticResources.VERSION);
  }

  private JLabel _getDesc()
  {
    return new JLabel("<html>Creating maps for \"Tales from Hell\" is so easy...</html>");
  }

  private JLabel _getCopyright()
  {
    JLabel label = new JLabel("Copyright (c) 2014 Werner Glanzer, All rights reserved!");
    label.setForeground(Color.GRAY);
    return label;
  }
}
