package de.tfh.datamodels.registry;

import de.tfh.datamodels.IDataModel;
import de.tfh.datamodels.TFHDataModelException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
  void registerDataModel(@NotNull Class<? extends IDataModel>... pModels) throws TFHDataModelException;

  /**
   * Liefert eine Instanz des Datenmodells mit dem angegebenen String
   *
   * @param pModel String des Datenmodells
   * @return Instanz eines IDataModels, oder <tt>null</tt> wenn kein zugehöriges Datenmodell existiert
   */
  @Nullable
  IDataModel getDataModel(@NotNull String pModel);

  /**
   * Löscht alle Datenmodelle und deren Referenzen
   *
   * @return <tt>true</tt>, wenn sich etwas verändert hat
   */
  boolean clear();
}
