package de.talesFromHell.core.exceptions;

import de.talesFromHell.core.i18n.IErrorMessage;
import de.talesFromHell.core.utils.ExceptionUtil;

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
