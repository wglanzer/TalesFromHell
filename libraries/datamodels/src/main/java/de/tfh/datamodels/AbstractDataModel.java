package de.tfh.datamodels;

import com.toddfast.util.convert.TypeConverter;
import de.tfh.core.exceptions.TFHException;
import de.tfh.core.i18n.Exceptions;
import org.jdom2.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
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
      throw new TFHDataModelException(e, 4);
    }
  }

  @Override
  public void setValue(@NotNull String pKey, @Nullable Object pValue) throws TFHDataModelException
  {
    try
    {
      Field field = _getField(pKey);
      if(field != null)
        field.set(this, pValue);
    }
    catch(Exception e)
    {
      //Wert des Feldes konnte nicht gesetzt werden
      throw new TFHDataModelException(e, 5);
    }
  }

  @Override
  public Element toXMLElement() throws TFHException
  {
    Element ele = new Element(getName());
    Map<String, Object> allFields = _getAllFields();
    for(Map.Entry<String, Object> currEntry : allFields.entrySet())
    {
      Element childEle = new Element(currEntry.getKey());
      childEle.addContent(currEntry.getValue().toString());
      ele.addContent(childEle);
    }
    return ele;
  }

  @Override
  public void fromXMLElement(Element pElement) throws TFHException
  {
    List<Element> children = pElement.getChildren();
    for(Element currChild : children)
    {
      try
      {
        Field field = _getField(currChild.getName());
        if(field != null)
        {
          Class<?> type = field.getType();
          Object casted = TypeConverter.convert(type, currChild.getValue());
          setValue(currChild.getName(), casted);
        }
      }
      catch(Exception e)
      {
        // XML-Element konnte nicht ins Datenmodell aufgenommen werden
        LoggerFactory.getLogger(AbstractDataModel.class).error(Exceptions.get(20), e, "ele=", pElement);
      }
    }
  }

  /**
   * Liefert alle Felder des Datenmodells und ihren Wert zurück
   *
   * @return Map aus Name - Wert der Felder
   * @throws TFHDataModelException
   */
  private Map<String, Object> _getAllFields() throws TFHDataModelException
  {
    HashMap<String, Object> map = new HashMap<>();
    Field[] fields = getClass().getDeclaredFields();
    for(Field currField : fields)
    {
      String name = currField.getName();
      Object val = _getValue(currField);
      map.put(name, val);
    }
    return map;
  }

  /**
   * Liefert das Feld mit dem angegebenen Namen
   *
   * @param pKey Namen des Feldes
   * @return Das Feld mit dem angegebenen Namen, nicht <tt>null</tt>
   * @throws TFHDataModelException Wenn kein Feld gefunden werden konnte
   */
  @Nullable
  private Field _getField(String pKey) throws TFHDataModelException
  {
    try
    {
      Field field = _getCached(pKey);

      if(field == null)
      {
        field = getClass().getDeclaredField(pKey);
        cachedFields.put(pKey, field);
      }

      return field;
    }
    catch(Exception e)
    {
      throw new TFHDataModelException(e, 3);
    }
  }

  /**
   * Liefert den Wert des übergebenen Feldes
   *
   * @param pField Feld, dessen Wert gewünscht ist
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
      throw new TFHDataModelException(e, 7);
    }
  }

  /**
   * Liefert das gecachte Feld mit dem übergebenen Namen
   *
   * @param pKey Name des Feldes
   * @return Das Feld, oder <tt>null</tt>
   */
  private Field _getCached(String pKey)
  {
    return cachedFields.get(pKey);
  }
}
