package de.tfh.datamodels.registry;

import de.tfh.datamodels.IDataModel;
import de.tfh.datamodels.TFHDataModelException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Hier können sich Datenmodelle registrieren
 * und auch wieder geholt werden
 *
 * @author W.Glanzer, 25.10.2014
 */
public interface IDataModelRegistry
{

  /**
   * Registriert ein Datenmodell in der Registry, sodass
   * später darauf zugegriffen werden kann
   *
   * @param pModels Klasse der Datenmodelle, auf die zugegriffen werden können soll
   * @throws TFHDataModelException Wenn die Instanz des Datenmodells nicht erstellt werden konnte
   */
  @SuppressWarnings("unchecked") //IntelliJ
  void registerDataModel(@NotNull Class<? extends IDataModel>... pModels) throws TFHDataModelException;

  /**
   * Liefert eine Neue, leere Instanz eines registrierten Datenmodells
   *
   * @param pModel Name des Datenmodells
   * @return Neue Instanz des Datenmodells
   */
  @Nullable
  IDataModel newInstance(@NotNull String pModel) throws TFHDataModelException;

  /**
   * Liefert eine Neue, leere Instanz eines registrierten Datenmodells
   *
   * @param pModel Klasse des Datenmodells
   * @return Neue Instanz des Datenmodells
   */
  @Nullable
  IDataModel newInstance(@NotNull Class<? extends IDataModel> pModel) throws TFHDataModelException;

  /**
   * Löscht eine Instanz eines registrierten Datenmodells
   *
   * @param pModel Instanz des Datenmodells
   * @return <tt>true</tt>, wenn etwas gelöscht wurde, andernfalls <tt>false</tt>
   */
  boolean removeInstance(@NotNull IDataModel pModel);

  /**
   * Liefert eine Instanz des Datenmodells mit dem angegebenen String
   *
   * @param pModel String des Datenmodells
   * @return Eine Liste aller Instanzen des Datenmodells, oder <tt>null</tt> wenn es keine Instanz gibt
   */
  @Nullable
  Collection<IDataModel> getAllInstances(@NotNull String pModel);

  /**
   * Liefert eine Instanz des Datenmodells mit dem angegebenen String
   *
   * @param pModel Klasse des Datenmodells
   * @return Eine Liste aller Instanzen des Datenmodells, oder <tt>null</tt> wenn es keine Instanz gibt
   */
  @Nullable
  Collection<IDataModel> getAllInstances(@NotNull Class<? extends IDataModel> pModel);

  /**
   * Löscht alle Datenmodelle und deren Referenzen
   *
   * @return <tt>true</tt>, wenn sich etwas veröndert hat
   */
  boolean clearAll();
}
