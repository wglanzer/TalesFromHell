package de.tfh.mapper.gui.dialog;

import de.tfh.core.i18n.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Repr�sentiert einen Dialog
 *
 * @author W.Glanzer, 26.11.2014
 */
public abstract class AbstractDialog extends JDialog
{

  private final JPanel textPanel = new JPanel();
  private final JButton buttonOK = new JButton(Messages.get(14));
  private final JButton buttonCancel = new JButton(Messages.get(15));

  public AbstractDialog()
  {
    setLayout(new BorderLayout());

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    buttonPanel.add(buttonOK);
    buttonPanel.add(buttonCancel);
    add(buttonPanel, BorderLayout.SOUTH);

    textPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(3, 3, 0, 3), textPanel.getBorder()));
    add(textPanel, BorderLayout.CENTER);

    ActionListener escapeListener = e -> dispose();
    buttonCancel.addActionListener(escapeListener);
    getRootPane().registerKeyboardAction(escapeListener, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

    SwingUtilities.invokeLater(() -> {
      setLocationRelativeTo(getOwner());
      setModal(true);
      setVisible(true);
    });
  }

  /**
   * F�gt einen OK-Listener zum OK-Button hinzu.
   * Wird automatisch wieder entfernt, sobald actionPerformed() <tt>true</tt>
   * zur�ckliefert
   *
   * @param pListener  Listener, der hinzugef�gt werden soll
   */
  public void addOKActionListener(IOKListener pListener)
  {
    buttonOK.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        if(pListener.actionPerformed())
        {
          buttonOK.removeActionListener(this);
          dispose();
        }
      }
    });
  }

  @Override
  public void dispose()
  {
    // Alle bekannten Listener entfernen, damit kein Speicherleck ensteht
    for(ActionListener currListener : buttonOK.getActionListeners())
      buttonOK.removeActionListener(currListener);

    super.dispose();
  }

  /**
   * TextPanel, auf dem etwaige Felder angebracht werden k�nnen
   *
   * @return JPanel, auf dem Componenten platziert werden k�nnen
   */
  protected JPanel getTextPanel()
  {
    return textPanel;
  }

  /**
   * OK-Listener, der ausl�st, wenn man auf den OK-Button gedr�ckt hat
   */
  public interface IOKListener
  {
    /**
     * Wird aufgerufen, wenn auf den OK-Button geklickt wird
     *
     * @return <tt>true</tt>, wenn der Dialog geschlossen werden kann, andernfalls <tt>false</tt>
     */
    boolean actionPerformed();
  }
}
