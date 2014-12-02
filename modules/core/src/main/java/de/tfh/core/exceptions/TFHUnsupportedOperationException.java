package de.tfh.core.exceptions;

import de.tfh.core.i18n.ExceptionResources;
import de.tfh.core.utils.ExceptionUtil;
import org.jetbrains.annotations.Nullable;

/**
 * @author W.Glanzer, 28.11.2014
 */
public class TFHUnsupportedOperationException extends UnsupportedOperationException
{
  public TFHUnsupportedOperationException(int pID)
  {
    this(null, pID < 0 ? ExceptionResources.DEFAULT_NO_MESSAGE : pID);
  }

  public TFHUnsupportedOperationException(int pID, Object... pDetails)
  {
    this(null, pID < 0 ? ExceptionResources.DEFAULT_NO_MESSAGE : pID, pDetails);
  }

  public TFHUnsupportedOperationException(@Nullable Throwable pCause, int pID, Object... pDetails)
  {
    super(ExceptionUtil.getErrorString(pID, pDetails), pCause);
  }
}
