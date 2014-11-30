package de.tfh.mapper.gui.containers;

import de.tfh.gamecore.map.ProgressObject;
import de.tfh.mapper.facade.IMapperFacade;

import javax.swing.*;

/**
 * Grund-Container auf dem alle Container des Mappers aufbauen
 *
 * @author W.Glanzer, 19.11.2014
 */
public abstract class AbstractContainer extends JPanel
{

  private final IMapperFacade facade;

  public AbstractContainer(IMapperFacade pFacade)
  {
    facade = pFacade;
    if(facade != null)
      facade.addChangeListener(new IMapperFacade.IChangeListener()
      {
        @Override
        public void facadeChanged()
        {
          reinit();
        }

        @Override
        public void mapSaved(ProgressObject pObject)
        {
        }
      });

    reinit();
  }

  /**
   * Initialisiert den Container (neu)
   */
  protected abstract void reinit();

  /**
   * Liefert die MapperFacade
   *
   * @return MapperFacade, oder <tt>null</tt>
   */
  protected IMapperFacade getFacade()
  {
    return facade;
  }
}
