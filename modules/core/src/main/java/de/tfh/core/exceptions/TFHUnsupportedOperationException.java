package de.tfh.core.exceptions;

import org.jetbrains.annotations.Nullable;

/**
 * @author W.Glanzer, 28.11.2014
 */
public class TFHUnsupportedOperationException extends TFHException
{
  public TFHUnsupportedOperationException(int pID)
  {
    super(pID);
  }

  public TFHUnsupportedOperationException(int pID, Object... pDetails)
  {
    super(pID, pDetails);
  }

  public TFHUnsupportedOperationException(@Nullable Throwable pCause, int pID, Object... pDetails)
  {
    super(pCause, pID, pDetails);
  }
}
