package de.tfh.mapper.gui.dialog;

import de.tfh.core.i18n.Messages;
import de.tfh.mapper.gui.common.FileChooserPanel;
import de.tfh.mapper.gui.common.ITextable;
import de.tfh.mapper.gui.tablelayout.TableLayout;
import de.tfh.mapper.gui.tablelayout.TableLayoutConstants;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

/**
 * "Neue Karte" - Dialog
 *
 * @author W.Glanzer, 26.11.2014
 */
public class NewMapDialog extends AbstractDialog
{
  private JTextField nameMap = new JTextField();
  private ITextable tilesetPath = new FileChooserPanel(new _TilesetFileFilter(), false);

  public NewMapDialog()
  {
    setTitle(Messages.get(19));
    double[][] layout = new double[][]{{TableLayoutConstants.PREFERRED, TableLayoutConstants.FILL},
        {TableLayoutConstants.PREFERRED, TableLayoutConstants.PREFERRED}};
    TableLayout tl = new TableLayout(layout);
    tl.setHGap(3);
    tl.setVGap(3);

    JPanel textPanel = getTextPanel();
    textPanel.setLayout(tl);

    textPanel.add(new JLabel(Messages.get(16) + ":"), "0,0");
    textPanel.add(nameMap, "1,0");
    textPanel.add(new JLabel(Messages.get(17) + ":"), "0,1");
    textPanel.add(tilesetPath.getComp(), "1,1");

    SwingUtilities.invokeLater(() -> {
      Dimension pref = getPreferredSize();
      setMinimumSize(new Dimension(pref.width * 2, pref.height));
    });
  }

  public String getMapName()
  {
    return nameMap.getText();
  }

  public String getTilesetPath()
  {
    return tilesetPath.getText();
  }

  /**
   * Repräsentiert den FileFilter für Tilesets
   */
  private static class _TilesetFileFilter extends FileFilter
  {
    @Override
    public boolean accept(File f)
    {
      String name = f.getName();
      return name.endsWith(".png") || name.endsWith(".jpg") || f.isDirectory();
    }

    @Override
    public String getDescription()
    {
      return "Tilesets (*.png, *.jpg)";
    }
  }
}
