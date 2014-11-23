package de.tfh.mapper;

import de.tfh.core.exceptions.TFHException;
import org.jetbrains.annotations.Nullable;

/**
 * @author W.Glanzer, 20.11.2014
 */
public class TFHMappperException extends TFHException
{
  public TFHMappperException(int pID)
  {
    super(pID);
  }

  public TFHMappperException(int pID, Object... pDetails)
  {
    super(pID, pDetails);
  }

  public TFHMappperException(@Nullable Throwable pCause, int pID, Object... pDetails)
  {
    super(pCause, pID, pDetails);
  }
}
