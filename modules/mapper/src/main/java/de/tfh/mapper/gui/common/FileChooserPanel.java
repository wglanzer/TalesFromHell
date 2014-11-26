package de.tfh.mapper.gui.common;

import de.tfh.core.IStaticResources;
import de.tfh.core.i18n.Messages;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Repräsentiert einen FileChooser
 *
 * @author W.Glanzer, 27.11.2014
 */
public class FileChooserPanel extends JPanel implements ITextable
{
  private final JButton button = new JButton(Messages.get(20));
  private final JTextField field = new JTextField();
  private final FileFilter fileFilter;
  private final boolean isSaveDialog;

  public FileChooserPanel(FileFilter pFileFilter, boolean pIsSaveDialog)
  {
    fileFilter = pFileFilter;
    isSaveDialog = pIsSaveDialog;
    setLayout(new BorderLayout());

    field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(1, 0, 0, 0), field.getBorder()));
    button.setPreferredSize(new Dimension(button.getPreferredSize().width, field.getPreferredSize().height));

    add(field, BorderLayout.CENTER);
    add(button, BorderLayout.EAST);

    button.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        JFileChooser chooser = new JFileChooser();
        chooser.setPreferredSize(chooser.getPreferredSize());
        if(fileFilter != null)
          chooser.setFileFilter(fileFilter);

        int ret;
        if(isSaveDialog)
          ret = chooser.showSaveDialog(FileChooserPanel.this);
        else
          ret = chooser.showOpenDialog(FileChooserPanel.this);

        if(ret == JFileChooser.APPROVE_OPTION)
        {
          File file = chooser.getSelectedFile();
          if(file != null)
          {
            String textToSet = file.getAbsolutePath();
            if(isSaveDialog && !textToSet.endsWith("." + IStaticResources.MAP_FILEENDING))
              textToSet += "." + IStaticResources.MAP_FILEENDING;

            field.setText(textToSet);
          }
        }
      }
    });
  }

  @Override
  public void setText(String pText)
  {
    field.setText(pText != null ? pText : "");
  }

  public String getText()
  {
    return field.getText();
  }

  @Override
  public JComponent getComp()
  {
    return this;
  }
}
