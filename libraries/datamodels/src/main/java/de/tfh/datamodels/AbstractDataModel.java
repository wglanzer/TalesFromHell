package de.tfh.datamodels;

import de.tfh.core.i18n.Errors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementiert die Grundfunktionen
 * eines Datenmodells
 *
 * @author W.Glanzer, 25.10.2014
 */
public abstract class AbstractDataModel implements IDataModel
{

  private final Map<String, Field> cachedFields = new HashMap<>();

  @Nullable
  @Override
  public Object getValue(@NotNull String pKey) throws TFHDataModelException
  {
    try
    {
      return _getValue(_getField(pKey));
    }
    catch(Exception e)
    {
      //Wert des Feldes konnte nicht bestimmt werden
      throw new TFHDataModelException(e, Errors.E4);
    }
  }

  @Override
  public void setValue(@NotNull String pKey, @Nullable Object pValue) throws TFHDataModelException
  {
    try
    {
      Field field = _getField(pKey);
      field.set(this, pValue);
    }
    catch(Exception e)
    {
      //Wert des Feldes konnte nicht gesetzt werden
      throw new TFHDataModelException(e, Errors.E5);
    }
  }

  /**
   * Liefert das Feld mit dem angegebenen Namen
   *
   * @param pKey Namen des Feldes
   * @return Das Feld mit dem angegebenen Namen, nicht <tt>null</tt>
   * @throws TFHDataModelException Wenn kein Feld gefunden werden konnte
   */
  @NotNull
  private Field _getField(String pKey) throws TFHDataModelException
  {
    try
    {
      Field field = _getCached(pKey);

      if (field == null)
      {
        field = getClass().getDeclaredField(pKey);
        cachedFields.put(pKey, field);
      }

      return field;
    }
    catch(Exception e)
    {
      throw new TFHDataModelException(e, Errors.E3);
    }
  }

  /**
   * Liefert den Wert des übergebenen Feldes
   *
   * @param pField  Feld, dessen Wert gewünscht ist
   * @return Den Wert des Feldes, kann daher also <tt>null</tt> sein,
   * wenn das Feld den Wert <tt>null</tt> besitzt
   * @throws TFHDataModelException Wenn der Wert des Feldes nicht ausgelesen werden konnte
   */
  private Object _getValue(Field pField) throws TFHDataModelException
  {
    try
    {
      pField.setAccessible(true);
      Object value = pField.get(this);
      pField.setAccessible(false);
      return value;
    }
    catch(Exception e)
    {
      throw new TFHDataModelException(e, null);
    }
  }

  /**
   * Liefert das gecachte Feld mit dem übergebenen Namen
   *
   * @param pKey  Name des Feldes
   * @return Das Feld, oder <tt>null</tt>
   */
  private Field _getCached(String pKey)
  {
    return cachedFields.get(pKey);
  }
}
