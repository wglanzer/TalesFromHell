package de.tfh.mapper.gui.dialog;

import com.alee.extended.filechooser.WebFileChooserField;
import de.tfh.core.IStaticResources;
import de.tfh.core.i18n.Messages;
import de.tfh.mapper.gui.tablelayout.TableLayout;
import de.tfh.mapper.gui.tablelayout.TableLayoutConstants;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.util.List;

/**
 * "Neue Karte" - Dialog
 *
 * @author W.Glanzer, 26.11.2014
 */
public class NewMapDialog extends AbstractDialog
{
  private JTextField nameMap = new JTextField();
  private JTextField chunkCountX = new JTextField("8");
  private JTextField chunkCountY = new JTextField("8");
  private JTextField tileCountX = new JTextField("16");
  private JTextField tileCountY = new JTextField("16");
  private JTextField tileWidth = new JTextField("32");
  private JTextField tileHeight = new JTextField("32");
  private WebFileChooserField tilesetPath = new WebFileChooserField(this, true);

  public NewMapDialog()
  {
    setTitle(Messages.get(19));
    double[][] layout = new double[][]{{TableLayoutConstants.PREFERRED, TableLayoutConstants.FILL},
        {TableLayoutConstants.PREFERRED, TableLayoutConstants.PREFERRED, TableLayoutConstants.PREFERRED, TableLayoutConstants.PREFERRED, TableLayoutConstants.PREFERRED}};
    TableLayout tl = new TableLayout(layout);
    tl.setHGap(3);
    tl.setVGap(3);

    JPanel textPanel = getTextPanel();
    textPanel.setLayout(tl);

    tilesetPath.getWebFileChooser().setFileFilter(new _TilesetFileFilter());
    tilesetPath.getWebFileChooser().setCurrentDirectory(IStaticResources.MAP_PATH);

    textPanel.add(new JLabel(Messages.get(16) + ":"), "0,0"); // Mapname
    textPanel.add(nameMap, "1,0");
    textPanel.add(new JLabel(Messages.get(17) + ":"), "0,1"); // Tileset-Pfad
    textPanel.add(tilesetPath, "1,1");
    textPanel.add(new JLabel(Messages.get(24) + ":"), "0,2"); // Größe der Tiles
    textPanel.add(_getTileSizePanel(), "1,2");
    textPanel.add(new JLabel(Messages.get(25) + ":"), "0,3"); // Anzahl der Chunks
    textPanel.add(_getChunkCountPanel(), "1,3");
    textPanel.add(new JLabel(Messages.get(26) + ":"), "0,4"); // Anzahl der Tiles innerhalb eines Chunks
    textPanel.add(_getTileCountPanel(), "1,4");

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
    List<File> files = tilesetPath.getSelectedFiles();
    if(files != null && files.size() >= 1)
      return files.get(0).getAbsolutePath();

    return null;
  }

  public Dimension getChunkSize()
  {
    try
    {
      int x = Integer.parseInt(tileCountX.getText());
      int y = Integer.parseInt(tileCountY.getText());
      return new Dimension(x, y);
    }
    catch(Exception e)
    {
      return new Dimension(0, 0);
    }
  }

  public Dimension getChunkCount()
  {
    try
    {
      int x = Integer.parseInt(chunkCountX.getText());
      int y = Integer.parseInt(chunkCountY.getText());
      return new Dimension(x, y);
    }
    catch(Exception e)
    {
      return new Dimension(0, 0);
    }
  }

  public Dimension getTileSize()
  {
    try
    {
      int x = Integer.parseInt(tileWidth.getText());
      int y = Integer.parseInt(tileHeight.getText());
      return new Dimension(x, y);
    }
    catch(Exception e)
    {
      return new Dimension(0, 0);
    }
  }

  private JPanel _getTileSizePanel()
  {
    JPanel panel = new JPanel();
    TableLayout layout = new TableLayout(new double[][]{{TableLayout.PREFERRED, TableLayout.FILL, TableLayout.PREFERRED, TableLayout.FILL}, {TableLayout.PREFERRED}});
    layout.setHGap(3);
    panel.setLayout(layout);
    panel.add(new JLabel("x:"), "0,0");
    panel.add(tileWidth, "1,0");
    panel.add(new JLabel("y:"), "2,0");
    panel.add(tileHeight, "3,0");
    return panel;
  }

  private JPanel _getChunkCountPanel()
  {
    JPanel panel = new JPanel();
    TableLayout layout = new TableLayout(new double[][]{{TableLayout.PREFERRED, TableLayout.FILL, TableLayout.PREFERRED, TableLayout.FILL}, {TableLayout.PREFERRED}});
    layout.setHGap(3);
    panel.setLayout(layout);    panel.add(new JLabel("x:"), "0,0");
    panel.add(chunkCountX, "1,0");
    panel.add(new JLabel("y:"), "2,0");
    panel.add(chunkCountY, "3,0");
    return panel;
  }

  private JPanel _getTileCountPanel()
  {
    JPanel panel = new JPanel();
    TableLayout layout = new TableLayout(new double[][]{{TableLayout.PREFERRED, TableLayout.FILL, TableLayout.PREFERRED, TableLayout.FILL}, {TableLayout.PREFERRED}});
    layout.setHGap(3);
    panel.setLayout(layout);    panel.add(new JLabel("x:"), "0,0");
    panel.add(tileCountX, "1,0");
    panel.add(new JLabel("y:"), "2,0");
    panel.add(tileCountY, "3,0");
    return panel;
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
      return name.endsWith(".png") || f.isDirectory();
    }

    @Override
    public String getDescription()
    {
      return "Tilesets (*.png)";
    }
  }
}
