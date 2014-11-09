package de.tfh.core.exceptions;

import de.tfh.core.i18n.ExceptionResources;
import de.tfh.core.utils.ExceptionUtil;
import org.jetbrains.annotations.Nullable;

/**
 * W.Glanzer, 22.10.2014.
 */
public class AbstractException extends Exception
{

  public AbstractException(int pID)
  {
    this(null, pID < 0 ? ExceptionResources.DEFAULT_NO_MESSAGE : pID);
  }

  public AbstractException(@Nullable Throwable pCause, int pID, Object... pDetails)
  {
    super(ExceptionUtil.getErrorString(pID, pDetails), pCause);
  }
}
