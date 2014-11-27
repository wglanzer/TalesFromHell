package de.tfh.mapper.gui.common;

import javax.swing.*;

/**
 * Gibt an, dass ein Text gesetzt und zur�ckgegeben werden kann
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
   * Gibt den Text zur�ck, der gesetzt wurde
   *
   * @return Text
   */
  String getText();

  /**
   * Gibt die Komponente zur�ck, die in Swing hinzugef�gt werden kann
   *
   * @return JComponent
   */
  JComponent getComp();

}
