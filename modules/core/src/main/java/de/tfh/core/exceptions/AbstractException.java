package de.tfh.core.exceptions;

import de.tfh.core.i18n.Errors;
import de.tfh.core.i18n.IErrorMessage;
import de.tfh.core.utils.ExceptionUtil;
import org.jetbrains.annotations.Nullable;

/**
 * W.Glanzer, 22.10.2014.
 */
public class AbstractException extends Exception
{
  public AbstractException()
  {
    this(Errors.EINVALID);
  }

  public AbstractException(@Nullable IErrorMessage pMessage)
  {
    this(null, pMessage);
  }

  public AbstractException(@Nullable Throwable pCause, @Nullable IErrorMessage pMessage)
  {
    super(ExceptionUtil.parseErrorMessage(pMessage == null ? Errors.EINVALID : pMessage), pCause);
  }

  public AbstractException(@Nullable Throwable pCause)
  {
    super(pCause);
  }
}
