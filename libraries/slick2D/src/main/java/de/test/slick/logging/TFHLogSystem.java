package de.test.slick.logging;

import org.newdawn.slick.util.LogSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Leitet alle Ausgaben an Log4J weiter
 *
 * @author W.Glanzer, 24.10.2014
 */
public class TFHLogSystem implements LogSystem
{
  private static final Logger logger = LoggerFactory.getLogger(TFHLogSystem.class);

  @Override
  public void error(String s, Throwable pThrowable)
  {
    logger.error(s, pThrowable);
  }

  @Override
  public void error(Throwable pThrowable)
  {
    logger.error("", pThrowable);
  }

  @Override
  public void error(String s)
  {
    logger.error(s);
  }

  @Override
  public void warn(String s)
  {
    logger.warn(s);
  }

  @Override
  public void warn(String s, Throwable pThrowable)
  {
    logger.warn(s, pThrowable);
  }

  @Override
  public void info(String s)
  {
    logger.info(s);
  }

  @Override
  public void debug(String s)
  {
    logger.debug(s);
  }
}
