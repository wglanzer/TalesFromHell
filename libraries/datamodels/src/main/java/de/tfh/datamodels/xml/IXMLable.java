package de.tfh.datamodels.xml;

import de.tfh.core.exceptions.TFHException;
import org.jdom2.Element;

/**
 * Gibt an, dass etwas eine XML-Aktion beherrscht
 *
 * @author W.Glanzer, 26.10.2014
 */
public interface IXMLable
{

  /**
   * Liefert das Objekt als XML-Element zurück
   *
   * @return XML-Element des Objektes
   * @throws TFHException Wenn dabei ein Fehler aufgetreten ist
   */
  Element toXMLElement() throws TFHException;

  /**
   * Baut das Objekt aus diesem Element wieder auf
   *
   * @param pElement  Element, aus dem die Werte ausgelesen werden
   * @throws TFHException Wenn dabei ein Fehler aufgetreten ist
   */
  void fromXMLElement(Element pElement) throws TFHException;

}
