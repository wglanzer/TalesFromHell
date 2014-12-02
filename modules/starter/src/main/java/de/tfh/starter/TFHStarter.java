package de.tfh.starter;

import de.tfh.core.utils.ExceptionUtil;
import de.tfh.datamodels.StaticDataModelRegistrator;
import de.tfh.gamecore.config.IConfigLoader;
import de.tfh.gamecore.config.XMLConfigLoader;
import de.tfh.slick.SlickInit;
import org.newdawn.slick.AppGameContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Starter
 *
 * @author W.Glanzer, 25.10.2014
 */
public class TFHStarter
{
  private static final Logger logger = LoggerFactory.getLogger(TFHStarter.class);

  public static void main(String[] args)
  {
    try
    {
      StaticDataModelRegistrator.registerAll(true);
      SlickInit.installSlick();
      IConfigLoader configLoader =  XMLConfigLoader.getDefault();
      TFHBasicGame game = new TFHBasicGame();
      AppGameContainer container = new AppGameContainer(game);
      configLoader.applyConfig(container);
      container.start();
    }
    catch(Exception e)
    {
      // Eine unerwartete Fehlermeldung ist aufgetreten
      ExceptionUtil.logError(logger, 10, e, "args=", args);
    }
  }

}
