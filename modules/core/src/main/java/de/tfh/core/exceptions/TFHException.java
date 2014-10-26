package de.tfh.core.exceptions;

import org.jetbrains.annotations.Nullable;

/**
 * Normale Exception, die geworfen werden kann
 *
 * W.Glanzer, 22.10.2014.
 */
public class TFHException extends AbstractException
{
  public TFHException(int pID)
  {
    super(pID);
  }

  public TFHException(@Nullable Throwable pCause, int pID)
  {
    super(pCause, pID);
  }
}
