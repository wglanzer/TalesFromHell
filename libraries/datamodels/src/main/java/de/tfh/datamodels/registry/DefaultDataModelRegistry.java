package de.tfh.datamodels.registry;

import de.tfh.datamodels.IDataModel;
import de.tfh.datamodels.TFHDataModelException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * DataModelRegistry-Impl
 *
 * @author W.Glanzer, 25.10.2014
 */
public class DefaultDataModelRegistry implements IDataModelRegistry
{

  private static IDataModelRegistry SINGLETON;

  /**
   * Map der Datenmodelle
   * Key = Klasse des Datenmodells
   * Value = Instanz
   */
  private static final Map<Class<? extends IDataModel>, IDataModel> MODELS = new HashMap<>();

  @SafeVarargs  //für IntelliJ
  @Override
  public final void registerDataModel(@NotNull Class<? extends IDataModel>... pModels) throws TFHDataModelException
  {
    for(Class<? extends IDataModel> currModel : pModels)
    {
      try
      {
        if(MODELS.containsKey(currModel))
          throw new TFHDataModelException(1); //Bereits registriertes Datenmodell kann nicht neu registriert werden

        //Neue Instanz per Reflection erzeugen
        IDataModel instance = currModel.newInstance();

        //Datenmodell zur allg. Map hinzufügen, damit darauf zugegriffen werden kann
        MODELS.put(currModel, instance);
      }
      catch(Exception e)
      {
        throw new TFHDataModelException(e, 2);
      }
    }
  }

  @Nullable
  @Override
  public IDataModel getDataModel(@NotNull Class<? extends IDataModel> pModel)
  {
    return MODELS.get(pModel);
  }

  @Override
  public boolean clear()
  {
    int size = MODELS.size();
    MODELS.clear();
    return size - MODELS.size() > 0;
  }

  /**
   * Liefert die Instanz der DefaultDataModelRegistry
   *
   * @return die Instanz der DefaultDataModelRegistry
   */
  @NotNull
  public static IDataModelRegistry getDefault()
  {
    if(SINGLETON == null)
      SINGLETON = new DefaultDataModelRegistry();

    return SINGLETON;
  }
}
