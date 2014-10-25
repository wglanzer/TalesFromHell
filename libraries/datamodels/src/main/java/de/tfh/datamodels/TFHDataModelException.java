package de.tfh.datamodels;

import de.tfh.core.exceptions.TFHException;
import de.tfh.core.i18n.IErrorMessage;

/**
 * Exception, wenn in einem Datenmodell ein Fehler passiert ist
 *
 * @author W.Glanzer, 25.10.2014
 */
public class TFHDataModelException extends TFHException
{

  public TFHDataModelException()
  {
  }

  public TFHDataModelException(IErrorMessage pMessage)
  {
    super(pMessage);
  }

  public TFHDataModelException(Throwable pCause, IErrorMessage pMessage)
  {
    super(pCause, pMessage);
  }

  public TFHDataModelException(Throwable pCause)
  {
    super(pCause);
  }
}
