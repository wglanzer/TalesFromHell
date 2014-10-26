package de.tfh.datamodels;

import de.tfh.core.exceptions.TFHException;
import org.jetbrains.annotations.Nullable;

/**
 * Exception, wenn in einem Datenmodell ein Fehler passiert ist
 *
 * @author W.Glanzer, 25.10.2014
 */
public class TFHDataModelException extends TFHException
{
  public TFHDataModelException(int pID)
  {
    super(pID);
  }

  public TFHDataModelException(@Nullable Throwable pCause, int pID)
  {
    super(pCause, pID);
  }
}
