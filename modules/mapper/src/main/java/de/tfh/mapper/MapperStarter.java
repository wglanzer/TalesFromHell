package de.tfh.mapper;

import de.tfh.datamodels.StaticDataModelRegistrator;
import de.tfh.mapper.facade.MapperFacade;
import de.tfh.mapper.gui.MapperFrame;

/**
 * Haupteinstiegspunkt des Mappers
 *
 * @author W.Glanzer, 16.11.2014
 */
public class MapperStarter
{
  public static void main(String[] args)
  {
    // Alle Datenmodelle registrieren, damit man bspw. Zugriff auf die MapDescription hat
    StaticDataModelRegistrator.registerAll(true);

    // MapperFacade erstellen, für Zugriff GUI -> Backend
    MapperFacade facade = new MapperFacade();

    // Eigentlicher Frame
    MapperFrame frame = new MapperFrame(true, facade);
  }

}
