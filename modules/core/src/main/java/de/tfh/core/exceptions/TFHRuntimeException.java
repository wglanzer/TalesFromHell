package de.tfh.core.exceptions;

import de.tfh.core.i18n.ExceptionResources;
import de.tfh.core.utils.ExceptionUtil;
import org.jetbrains.annotations.Nullable;

/**
 * RuntimeException für TFH angepasst
 *
 * @author W.Glanzer, 25.11.2014
 */
public class TFHRuntimeException extends RuntimeException
{
  public TFHRuntimeException(int pID)
  {
    this(null, pID < 0 ? ExceptionResources.DEFAULT_NO_MESSAGE : pID);
  }

  public TFHRuntimeException(int pID, Object... pDetails)
  {
    this(null, pID < 0 ? ExceptionResources.DEFAULT_NO_MESSAGE : pID, pDetails);
  }

  public TFHRuntimeException(@Nullable Throwable pCause, int pID, Object... pDetails)
  {
    super(ExceptionUtil.getErrorString(pID, pDetails), pCause);
  }
}
