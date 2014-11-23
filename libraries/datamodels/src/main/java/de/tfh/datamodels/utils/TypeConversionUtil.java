package de.tfh.datamodels.utils;

import com.toddfast.util.convert.TypeConverter;

/**
 * Custom TypeConversions
 *
 * @author W.Glanzer, 23.11.2014
 */
public class TypeConversionUtil
{

  /**
   * Initialisiert alle benutzerdefinierten TypeConverter
   */
  public static void installAdditional()
  {
    // String[] -> Long[]
    TypeConverter.registerTypeConversion(new TypeConverter.Conversion<Long[]>()
    {
      @Override
      public Object[] getTypeKeys()
      {
        return new Object[]{Long[].class, Long[].class.getName()};
      }

      @Override
      public Long[] convert(Object o)
      {
        if(o instanceof String[])
        {
          String[] strings = (String[]) o;
          Long[] longs = new Long[strings.length];

          for(int i = 0; i < strings.length; i++)
          {
            String currString = strings[i];
            longs[i] = Long.valueOf(currString);
          }

          return longs;
        }

        return null;
      }
    });
  }

}
