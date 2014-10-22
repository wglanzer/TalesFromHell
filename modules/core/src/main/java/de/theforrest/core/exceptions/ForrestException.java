package de.theforrest.core.exceptions;

import de.theforrest.core.i18n.IErrorMessage;

/**
 * W.Glanzer, 22.10.2014.
 */
public class ForrestException extends AbstractException
{

  public ForrestException()
  {
  }

  public ForrestException(String pMessage)
  {
    super(pMessage);
  }

  public ForrestException(Throwable pCause, IErrorMessage pMessage)
  {
    super(pCause, pMessage);
  }

  public ForrestException(Throwable pCause)
  {
    super(pCause);
  }
}
