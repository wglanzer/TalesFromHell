package de.tfh.mapper;

import de.tfh.gamecore.map.ProgressObject;
import de.tfh.mapper.facade.IMapperFacade;

/**
 * Adapter für den ChangeListener
 *
 * @author W.Glanzer, 30.11.2014
 */
public class ChangeListenerAdapter implements IMapperFacade.IChangeListener
{
  @Override
  public void facadeChanged()
  {
  }

  @Override
  public void mapSaved(ProgressObject pObject)
  {
  }

  @Override
  public void mapLoaded(ProgressObject pObject)
  {
  }
}
