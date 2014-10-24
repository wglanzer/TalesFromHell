package de.tfh.starter.config;

import de.tfh.core.exceptions.TFHException;
import org.newdawn.slick.AppGameContainer;

/**
 * Beschreibt einen allgemeinen ConfigLoader,
 * der Config-Dateien auf einen AppGameContainer anwenden kann
 *
 * @author W.Glanzer, 25.10.2014
 */
public interface IConfigLoader
{

  /**
   * Wendet eine Config auf einen GameContainer an
   *
   * @param pContainer Container, auf den die Config angewandt werden soll
   * @throws TFHException Wenn dabei ein Fehler aufgetreten ist und
   *                      die Config nicht übernommen werden kann
   */
  void applyConfig(AppGameContainer pContainer) throws TFHException;

}
