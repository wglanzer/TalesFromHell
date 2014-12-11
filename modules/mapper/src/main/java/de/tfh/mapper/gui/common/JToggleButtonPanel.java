package de.tfh.mapper.gui.common;

import de.tfh.mapper.gui.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Panel, das verschieden JToggleButtons beherbergt, die nur einzeln
 * gedrückt werden können
 *
 * @author W.Glanzer, 11.12.2014
 */
public class JToggleButtonPanel extends JPanel
{
  private final Map<Integer, JToggleButton> buttons = new HashMap<>();
  private final Set<ISelectionListener> listenerList = new HashSet<>();
  private final String[] titles;

  public JToggleButtonPanel(int pButtonCount, String... pTitles)
  {
    titles = pTitles;
    setLayout(new WrapLayout(FlowLayout.LEFT));
    for(int i = 0; i < pButtonCount; i++)
      _addButton(titles.length > i ? titles[i] : "");
  }

  /**
   * Setzt den selektierten Button
   *
   * @param pIndex  Button-Index der selektiert werden soll
   */
  public void setSelected(int pIndex)
  {
    _changeSelection(pIndex);
  }

  /**
   * Fügt einen Selection-Listener hinzu
   *
   * @param pListener  Listener, der hinzugefügt wird
   */
  public void addSelectionListener(ISelectionListener pListener)
  {
    synchronized(listenerList)
    {
      listenerList.add(pListener);
    }
  }

  /**
   * Entfernt einen Selection-Listener hinzu
   *
   * @param pListener  Listener, der entfernt wird
   */
  public void removeSelectionListener(ISelectionListener pListener)
  {
    synchronized(listenerList)
    {
      listenerList.remove(pListener);
    }
  }

  /**
   * Fügt einen Button zum Panel hinzu
   *
   * @param pTitle Titel des Buttons
   */
  private void _addButton(String pTitle)
  {
    JToggleButton button = new JToggleButton(pTitle);
    int index = buttons.size();
    button.addActionListener(e -> _changeSelection(index));
    buttons.put(index, button);
    add(button);
  }

  /**
   * Setzt den selektierten Button
   *
   * @param pIndexToSelect  Button, der selektiert werden soll
   */
  private void _changeSelection(int pIndexToSelect)
  {
    for(Map.Entry<Integer, JToggleButton> currEntry : buttons.entrySet())
    {
      JToggleButton but = currEntry.getValue();
      if(but != null)
        but.setSelected(false);
    }

    JToggleButton button = buttons.get(pIndexToSelect);
    if(button != null)
      button.setSelected(true);

    _fireSelectionChanged(pIndexToSelect);
  }

  /**
   * Feuert, dass sich die Selektion veränderth at
   *
   * @param pNewIndex  Neuer selektierter Index
   */
  private void _fireSelectionChanged(int pNewIndex)
  {
    synchronized(listenerList)
    {
      for(ISelectionListener currListener : listenerList)
        currListener.selectionChanged(pNewIndex);
    }
  }

  /**
   * Listener
   */
  public static interface ISelectionListener
  {
    void selectionChanged(int pNewIndex);
  }
}
