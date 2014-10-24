package de.tfh.core.exceptions;

import de.tfh.core.i18n.IErrorMessage;

/**
 * W.Glanzer, 22.10.2014.
 */
public class HellException extends AbstractException
{

  public HellException()
  {
  }

  public HellException(String pMessage)
  {
    super(pMessage);
  }

  public HellException(Throwable pCause, IErrorMessage pMessage)
  {
    super(pCause, pMessage);
  }

  public HellException(Throwable pCause)
  {
    super(pCause);
  }
}
