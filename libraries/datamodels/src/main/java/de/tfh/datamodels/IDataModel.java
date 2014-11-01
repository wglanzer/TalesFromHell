package de.tfh.datamodels;

import de.tfh.datamodels.xml.IXMLable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Beschreibt ein allgemeines Datenmodell
 *
 * @author W.Glanzer, 25.10.2014
 */
public interface IDataModel extends IXMLable
{

  /**
   * Liefert den Wert eines Eintrages im Datenmodell
   *
   * @param pKey Name des Feldes im Datenmodell
   * @return den Wert des Feldes als Object, kann <tt>null</tt>
   * sein, wenn das Feld als Wert <tt>null</tt> hat
   * @throws TFHDataModelException Wenn beim Auslesen ein Fehler aufgetreten ist
   */
  @Nullable
  Object getValue(@NotNull String pKey) throws TFHDataModelException;

  /**
   * Setzt den Wert eines Feldes im Datenmodell
   *
   * @param pKey   Name des Feldes im Datenmodell
   * @param pValue Wert, den das Feld danach haben soll
   * @throws TFHDataModelException Wenn der Wert nicht gesetzt werden kann oder ein anderweitiger Fehler aufgetreten ist
   */
  void setValue(@NotNull String pKey, @Nullable Object pValue) throws TFHDataModelException;

  /**
   * Liefert den Namen eines Datenmodells
   *
   * @return Name des Datenmodells als String
   */
  String getName();
}
