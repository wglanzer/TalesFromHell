package de.tfh.datamodels;

import de.tfh.datamodels.registry.DefaultDataModelRegistry;
import de.tfh.datamodels.registry.IDataModelRegistry;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tested die Standardfunktionen der Datenmodelle
 *
 * @author W.Glanzer, 25.10.2014
 */
public class Test_DataModel
{

  private static IDataModel model;

  @BeforeClass
  public static void before() throws TFHDataModelException
  {
    IDataModelRegistry reg = DefaultDataModelRegistry.getDefault();
    reg.clearAll();
    reg.registerDataModel(DummyDataModel.class);
    model = reg.newInstance(DummyDataModel.class);
  }

  /**
   * Testet setValue() und getValue()
   */
  @Test
  public void test_setGetValue() throws TFHDataModelException
  {
    Assert.assertNotNull(model);

    Object val = model.getValue("configName");
    Assert.assertEquals("DUMMY", val);

    model.setValue("configName", "invalid");
    val = model.getValue("configName");
    Assert.assertEquals("invalid", val);
  }

  /**
   * Tested das Verhalten, wenn ein boolsches Feld mit einem String
   * belegt werden soll
   */
  @Test
  public void test_setWrongType() throws TFHDataModelException
  {
    Assert.assertNotNull(model);

    Object bool = model.getValue("configBoolean");
    Assert.assertTrue(bool instanceof Boolean);

    try
    {
      model.setValue("configBoolean", "keinBoolean");
    }
    catch(Exception e)
    {
      // Wir wollen, dass die Fehlermeldung aussagekräftig ist!
      Assert.assertTrue(e instanceof TFHDataModelException);
    }
  }
}
