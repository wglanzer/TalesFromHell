package de.tfh.mapper.gui;

import de.tfh.core.IStaticResources;
import de.tfh.core.exceptions.TFHException;
import de.tfh.core.i18n.Messages;
import de.tfh.core.utils.ExceptionUtil;
import de.tfh.mapper.facade.IMapperFacade;
import de.tfh.mapper.gui.dialog.AboutDialog;
import de.tfh.mapper.gui.dialog.NewMapDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author W.Glanzer, 17.12.2014
 */
public class GuiAction
{
  private static String saveAndLoadLastFile; //Muss STATIC sein!
  private static final Logger logger = LoggerFactory.getLogger(GuiAction.class);

  /**
   * Zeigt den AboutDialog an
   */
  public static class ShowAboutDialogActionListener implements ActionListener
  {
    @Override
    public void actionPerformed(ActionEvent e)
    {
       new AboutDialog();
    }
  }

  /**
   * ActionListener zum Laden der Map
   */
  public static class LoadMapActionListener implements ActionListener
  {
    private final IMapperFacade facade;

    public LoadMapActionListener(IMapperFacade pFacade)
    {
      facade = pFacade;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
      JFileChooser chooser = new JFileChooser(IStaticResources.MAP_PATH);
      chooser.setFileFilter(new _MapFileFilter());
      int result = chooser.showDialog(null, Messages.get(23));
      File file = chooser.getSelectedFile();
      if(result == JFileChooser.APPROVE_OPTION && file != null)
        try
        {
          facade.load(file);
          saveAndLoadLastFile = file.getAbsolutePath();
        }
        catch(TFHException e1)
        {
          ExceptionUtil.logError(logger, 55, e1, "selectedFile=" + file);
        }
    }
  }

  /**
   * ActionListener für "Speichern unter"
   */
  public static class SaveAsActionListener implements ActionListener
  {
    private static ActionListener saveMapListener;

    public SaveAsActionListener(IMapperFacade pFacade)
    {
      saveMapListener = new SaveMapActionListener(pFacade);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
      saveAndLoadLastFile = null;
      saveMapListener.actionPerformed(e);
    }
  }

  /**
   * ActionListener zum Speichern der Map, NICHT "SPEICHERN UNTER"!
   */
  public static class SaveMapActionListener implements ActionListener
  {
    private final IMapperFacade facade;

    public SaveMapActionListener(IMapperFacade pFacade)
    {
      facade = pFacade;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
      try
      {
        String file = saveAndLoadLastFile;

        if(file == null)
        {
          JFileChooser chooser = new JFileChooser(IStaticResources.MAP_PATH);
          chooser.setFileFilter(new _MapFileFilter());

          int result = chooser.showSaveDialog(null);
          if(result == JFileChooser.APPROVE_OPTION)
          {
            file = chooser.getSelectedFile().getAbsolutePath();
            saveAndLoadLastFile = file;
          }
        }

        if(file != null)
          _save(file);
      }
      catch(Exception ex)
      {
        ExceptionUtil.logError(logger, 48, ex);
      }
    }

    private void _save(String pFile) throws FileNotFoundException
    {
      if(!pFile.endsWith("." + IStaticResources.MAP_FILEENDING))
        pFile += "." + IStaticResources.MAP_FILEENDING;

      FileOutputStream stream = new FileOutputStream(pFile);
      facade.save(stream);
    }
  }

  /**
   * ActionListener um eine neue Map zu erstellen
   */
  public static class NewMapActionListener implements ActionListener
  {
    private final IMapperFacade facade;

    public NewMapActionListener(IMapperFacade pFacade)
    {
      facade = pFacade;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
      NewMapDialog dialog = new NewMapDialog();
      dialog.addOKActionListener(() -> {
        try
        {
          String mapName = dialog.getMapName();
          String tilesetPath = dialog.getTilesetPath();
          Dimension chunkCount = dialog.getChunkCount();
          Dimension chunkSize = dialog.getChunkSize();
          Dimension tileSize = dialog.getTileSize();

          if(!mapName.isEmpty() && !tilesetPath.isEmpty())
          {
            facade.generateNewMap(mapName, tilesetPath, chunkCount, chunkSize, tileSize);
            return true;
          }
        }
        catch(TFHException e2)
        {
          ExceptionUtil.logError(logger, 38, e2);
        }

        return false;
      });
    }
  }

  /**
   * FileFilter-Impl
   */
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
