package de.tfh.core.exceptions;

import de.tfh.core.i18n.ExceptionResources;
import de.tfh.core.utils.ExceptionUtil;
import org.jetbrains.annotations.Nullable;

/**
 * Normale Exception, die geworfen werden kann
 *
 * W.Glanzer, 22.10.2014.
 */
public class TFHException extends Exception
{
  public TFHException(int pID)
  {
    this(null, pID < 0 ? ExceptionResources.DEFAULT_NO_MESSAGE : pID);
  }

  public TFHException(int pID, Object... pDetails)
  {
    this(null, pID < 0 ? ExceptionResources.DEFAULT_NO_MESSAGE : pID, pDetails);
  }

  public TFHException(@Nullable Throwable pCause, int pID, Object... pDetails)
  {
    super(ExceptionUtil.getErrorString(pID, pDetails), pCause);
  }
}
