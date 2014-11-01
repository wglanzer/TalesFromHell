package de.tfh.datamodels;

/**
 * Ein DummyDatenmodell für Unittests
 *
 * @author W.Glanzer, 25.10.2014
 */
public class DummyDataModel extends AbstractDataModel
{

  protected String configName = "DUMMY";

  protected Boolean configBoolean = false;

  @Override
  public String getName()
  {
    return "DummyDataModel";
  }
}
