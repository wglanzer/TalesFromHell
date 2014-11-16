package de.tfh.datamodels.registry;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import de.tfh.datamodels.IDataModel;
import de.tfh.datamodels.TFHDataModelException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
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

  private static final Multimap<String, IDataModel> INSTANCES = ArrayListMultimap.create();
  private static final Map<String, Class<? extends IDataModel>> REGISTRY = new HashMap<>();

  @SafeVarargs  //für IntelliJ
  @Override
  public final void registerDataModel(@NotNull Class<? extends IDataModel>... pModels) throws TFHDataModelException
  {
    for(Class<? extends IDataModel> currModel : pModels)
    {
      try
      {
        if(REGISTRY.containsValue(currModel))
          throw new TFHDataModelException(1); //Bereits registriertes Datenmodell kann nicht neu registriert werden

        //Datenmodell zur allg. Map hinzufügen, damit darauf zugegriffen werden kann
        REGISTRY.put(getString(currModel), currModel);
      }
      catch(Exception e)
      {
        throw new TFHDataModelException(e, 2);
      }
    }
  }

  @Nullable
  @Override
  public Collection<IDataModel> getAllInstances(@NotNull Class<? extends IDataModel> pModel)
  {
    return getAllInstances(getString(pModel));
  }

  @Nullable
  @Override
  public Collection<IDataModel> getAllInstances(@NotNull String pModel)
  {
    return INSTANCES.get(pModel);
  }

  @Override
  public boolean clearAll()
  {
    int size1 = INSTANCES.size();
    INSTANCES.clear();
    boolean changed1 = size1 - INSTANCES.size() > 0;

    int size2 = REGISTRY.size();
    REGISTRY.clear();
    boolean changed2 = size2 - REGISTRY.size() > 0;

    return changed1 || changed2;
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

  @Nullable
  @Override
  public IDataModel newInstance(@NotNull Class<? extends IDataModel> pModel) throws TFHDataModelException
  {
    return newInstance(getString(pModel));
  }

  @Nullable
  @Override
  public IDataModel newInstance(@NotNull String pModel) throws TFHDataModelException
  {
    try
    {
      Class<? extends IDataModel> model = REGISTRY.get(pModel);
      if(model != null)
      {
        IDataModel instance = model.newInstance();
        if(instance != null)
        {
          INSTANCES.put(pModel, instance);
          return instance;
        }
      }
      else
        throw new TFHDataModelException(null, 18, "model=", pModel); //Datenmodell nicht registriert

      return null;
    }
    catch(Exception e)
    {
      // Fehler beim Instantiieren eines Datenmodells
      throw new TFHDataModelException(e, 19, "model=", pModel);
    }
  }

  @Override
  public boolean removeInstance(@NotNull IDataModel pModel)
  {
    return INSTANCES.remove(getString(pModel.getClass()), pModel);
  }

  /**
   * Wandelt eine Klasse in einen String um
   *
   * @param pClass  Klasse die umgewandelt werden soll
   * @return Name der Klasse
   */
  public static <T> String getString(Class<T> pClass)
  {
    return pClass.getSimpleName();
  }
}
