package de.tfh.slick.logging;

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
    if(s != null && !s.isEmpty())
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
    if(s != null && !s.isEmpty())
      logger.error(s);
  }

  @Override
  public void warn(String s)
  {
    if(s != null && !s.isEmpty())
      logger.warn(s);
  }

  @Override
  public void warn(String s, Throwable pThrowable)
  {
    if(s != null && !s.isEmpty())
      logger.warn(s, pThrowable);
  }

  @Override
  public void info(String s)
  {
    if(s != null && !s.isEmpty())
      logger.info(s);
  }

  @Override
  public void debug(String s)
  {
    if(s != null && !s.isEmpty())
      logger.debug(s);
  }
}
