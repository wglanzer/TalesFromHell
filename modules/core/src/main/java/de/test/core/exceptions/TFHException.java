package de.test.core.exceptions;

import de.test.core.i18n.IErrorMessage;

/**
 * Normale Exception, die geworfen werden kann
 *
 * W.Glanzer, 22.10.2014.
 */
public class TFHException extends AbstractException
{

  public TFHException()
  {
  }

  public TFHException(String pMessage)
  {
    super(pMessage);
  }

  public TFHException(Throwable pCause, IErrorMessage pMessage)
  {
    super(pCause, pMessage);
  }

  public TFHException(Throwable pCause)
  {
    super(pCause);
  }
}
