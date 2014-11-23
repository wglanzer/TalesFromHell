package de.tfh.datamodels;

import de.tfh.core.i18n.Exceptions;
import de.tfh.datamodels.models.ChunkDataModel;
import de.tfh.datamodels.models.ConfigDataModel;
import de.tfh.datamodels.models.MapDescriptionDataModel;
import de.tfh.datamodels.registry.DefaultDataModelRegistry;
import de.tfh.datamodels.registry.IDataModelRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Registriert alle verfügbaren Datenmodelle
 *
 * @author W.Glanzer, 25.10.2014
 */
public class StaticDataModelRegistrator
{

  /**
   * Registry, bei der die Datenmodelle registriert werden
   */
  private static final IDataModelRegistry reg = DefaultDataModelRegistry.getDefault();
  private static final Logger logger = LoggerFactory.getLogger(StaticDataModelRegistrator.class);
  private static boolean logging = true;

  /**
   * Registriert alle verfügbaren Datenmodelle bei der Registry
   *
   * @param pLogErrors <tt>true</tt>, wenn evtl. auftretende Fehler geloggt werden sollen
   */
  public static void registerAll(boolean pLogErrors)
  {
    logging = pLogErrors;

    _register(ConfigDataModel.class);
    _register(ChunkDataModel.class);
    _register(MapDescriptionDataModel.class);
  }

  /**
   * Registriert ein bestimmtes Model bei der Registry und loggt evtl. auftretende Fehler
   *
   * @param pModelToRegister Model, das registriert werden soll
   */
  private static void _register(Class<? extends IDataModel> pModelToRegister)
  {
    try
    {
      reg.registerDataModel(pModelToRegister);
    }
    catch(Exception e)
    {
      if(logging)
        logger.warn(Exceptions.get(6), e);
    }
  }
}
