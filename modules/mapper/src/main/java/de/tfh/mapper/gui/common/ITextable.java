package de.tfh.mapper.gui.common;

import javax.swing.*;

/**
 * Gibt an, dass ein Text gesetzt und zurückgegeben werden kann
 *
 * @author W.Glanzer, 27.11.2014
 */
public interface ITextable
{

  /**
   * Setzt den Text
   *
   * @param pText Text, der gesetzt werden soll
   */
  void setText(String pText);

  /**
   * Gibt den Text zurück, der gesetzt wurde
   *
   * @return Text
   */
  String getText();

  /**
   * Gibt die Komponente zurück, die in Swing hinzugefügt werden kann
   *
   * @return JComponent
   */
  JComponent getComp();

}
