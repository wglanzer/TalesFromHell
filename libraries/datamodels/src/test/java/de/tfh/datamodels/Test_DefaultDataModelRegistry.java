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

    reg.clear();
    reg.registerDataModel(DummyDataModel.class);
    IDataModel model = reg.getDataModel(DummyDataModel.class);
    Assert.assertNotNull(model);
  }

}
