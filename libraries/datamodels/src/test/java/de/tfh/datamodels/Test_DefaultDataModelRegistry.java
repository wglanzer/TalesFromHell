package de.tfh.datamodels;

import de.tfh.datamodels.registry.DefaultDataModelRegistry;
import de.tfh.datamodels.registry.IDataModelRegistry;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tested die DefaultDataModelRegistry
 *
 * @author W.Glanzer, 25.10.2014
 */
public class Test_DefaultDataModelRegistry
{

  @Test
  public void test_DefaultDataModelRegistry() throws TFHDataModelException
  {
    IDataModelRegistry reg = DefaultDataModelRegistry.getDefault();
    Assert.assertNotNull(reg);

    reg.clearAll();
    reg.registerDataModel(DummyDataModel.class);
    IDataModel model = reg.newInstance(DummyDataModel.class);
    Assert.assertNotNull(model);
  }

  @Test
  public void test_multipleModels() throws TFHDataModelException
  {
    IDataModelRegistry reg = DefaultDataModelRegistry.getDefault();
    reg.clearAll();
    reg.registerDataModel(DummyDataModel.class);
    IDataModel model1 = reg.newInstance(DummyDataModel.class);
    IDataModel model2 = reg.newInstance(DummyDataModel.class);
    IDataModel model3 = reg.newInstance(DummyDataModel.class);
    IDataModel model4 = reg.newInstance(DummyDataModel.class);

    Assert.assertTrue(reg.getAllInstances(DummyDataModel.class).size() == 4);

    reg.removeInstance(model1);
    reg.removeInstance(model2);

    Assert.assertTrue(reg.getAllInstances(DummyDataModel.class).size() == 2);

    reg.clearAll();

    Assert.assertTrue(reg.getAllInstances(DummyDataModel.class).size() == 0);
  }
}
