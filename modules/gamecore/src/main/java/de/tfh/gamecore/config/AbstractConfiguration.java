package de.tfh.gamecore.config;

import de.tfh.core.exceptions.TFHException;
import de.tfh.core.i18n.Messages;
import de.tfh.core.utils.ExceptionUtil;
import de.tfh.datamodels.IDataModel;
import de.tfh.datamodels.TFHDataModelException;
import de.tfh.datamodels.models.ConfigDataModel;
import de.tfh.datamodels.registry.DefaultDataModelRegistry;
import de.tfh.datamodels.utils.DataModelIOUtil;
import org.apache.commons.io.input.ReaderInputStream;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.Reader;

/**
 * Bereitet das ConfigDatenmodell zur Verwendung auf
 *
 * @author W.Glanzer, 02.11.2014
 */
abstract class AbstractConfiguration implements IConfig
{

  protected ConfigDataModel model;
  private static final Logger logger = LoggerFactory.getLogger(AbstractConfiguration.class);

  public AbstractConfiguration(@Nullable String pPath)
  {
    try
    {
      if(pPath != null)
      {
        FileReader filereader = new FileReader(pPath);
        model = _load(filereader);
      }
    }
    catch(Exception e)
    {
      // Config konnte nicht gelesen werden
      ExceptionUtil.logError(logger, 22, e);
    }

    try
    {
      if(model == null)
      {
        logger.info(Messages.get(10)); //Creating config...
        model = _newWithDefaults(pPath);
      }
    }
    catch(Exception e)
    {
      // Config konnte nicht angelegt werden
      ExceptionUtil.logError(logger, 21, e, "path=", pPath);
    }
  }

  /**
   * Lädt das ConfigDatenModell von einem Reader und
   * gibt es zurück
   *
   * @param pReader  Reader f?r das Datenmodell
   * @return ConfigDataModel, oder <tt>null</tt>
   * @throws TFHException Wenn dabei ein Fehler aufgetreten ist
   */
  private ConfigDataModel _load(Reader pReader) throws TFHException
  {
    try
    {
      IDataModel tempModel = DataModelIOUtil.readDataModelFromXML(new ReaderInputStream(pReader));
      if(tempModel != null && tempModel instanceof ConfigDataModel)
        return (ConfigDataModel) tempModel;

      return null;
    }
    catch(Exception e)
    {
      // ConfigDatenModell konnte nicht geladen werden
      throw new TFHException(e, 23);
    }
  }

  /**
   * Erstellt ein neues ConfigDatenModell
   *
   * @param pPath Pfad, an dem das neue Datenmodell abgelegt werden soll
   * @return Neues ConfigDataModel
   * @throws TFHException Wenn die Instanz nicht erstellt werden konnte
   */
  private ConfigDataModel _newWithDefaults(String pPath) throws TFHException
  {
    try
    {
      ConfigDataModel model = (ConfigDataModel) DefaultDataModelRegistry.getDefault().newInstance(ConfigDataModel.class);
      DataModelIOUtil.writeDataModelXML(model, pPath);
      return model;
    }
    catch(TFHDataModelException e)
    {
      // Neues Config-Datenmodell konnte nicht angelegt werden
      throw new TFHException(e, 24, "path=" + pPath);
    }
  }
}
