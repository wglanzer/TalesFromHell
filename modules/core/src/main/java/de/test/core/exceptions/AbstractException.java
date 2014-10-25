package de.test.core.exceptions;

import de.test.core.i18n.IErrorMessage;
import de.test.core.utils.ExceptionUtil;

/**
 * W.Glanzer, 22.10.2014.
 */
public class AbstractException extends Exception
{
  public AbstractException()
  {
  }

  public AbstractException(String pMessage)
  {
    super(pMessage);
  }

  public AbstractException(Throwable pCause, IErrorMessage pMessage)
  {
    super(ExceptionUtil.parseErrorMessage(pMessage), pCause);
  }

  public AbstractException(Throwable pCause)
  {
    super(pCause);
  }
}
