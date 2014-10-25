package de.tfh.core.exceptions;

import de.tfh.core.i18n.IErrorMessage;

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

  public TFHException(IErrorMessage pMessage)
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
