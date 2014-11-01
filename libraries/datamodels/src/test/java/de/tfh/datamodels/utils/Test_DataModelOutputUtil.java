package de.tfh.datamodels.utils;

import de.tfh.datamodels.DummyDataModel;
import de.tfh.datamodels.IDataModel;
import de.tfh.datamodels.registry.DefaultDataModelRegistry;
import de.tfh.datamodels.registry.IDataModelRegistry;
import junit.framework.Assert;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileReader;

/**
 * Testet das DataModelOutputUtil
 *
 * @author W.Glanzer, 26.10.2014
 */
public class Test_DataModelOutputUtil
{

  @Test
  public void test_output()
  {
    try
    {
      IDataModelRegistry reg = DefaultDataModelRegistry.getDefault();
      reg.clear();
      reg.registerDataModel(DummyDataModel.class);
      IDataModel ddm = reg.getDataModel(DummyDataModel.class.getSimpleName());

      DataModelIOUtil.writeDataModelXML(ddm, "target/config.xml");
      FileReader reader = new FileReader("target/config.xml");
      String stringRead = IOUtils.toString(reader);

      Assert.assertNotNull(stringRead);
    }
    catch(Exception e)
    {
      Assert.assertFalse(true);
    }
  }

  @Test
  public void test_input()
  {
    try
    {
      IDataModelRegistry reg = DefaultDataModelRegistry.getDefault();
      reg.clear();
      reg.registerDataModel(DummyDataModel.class);

      IDataModel model = DataModelIOUtil.readDataModelFromXML(new FileInputStream("target/config.xml"));
      Assert.assertNotNull(model);

      DummyDataModel ddm = (DummyDataModel) model;
      Assert.assertNotNull(ddm.getValue("configName"));
      Assert.assertNotNull(ddm.getValue("configBoolean"));
    }
    catch(Exception e)
    {
      Assert.assertFalse(true);
    }
  }

}
