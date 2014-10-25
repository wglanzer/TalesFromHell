package de.test.core.i18n;

import de.test.core.utils.ExceptionUtil;

/**
 * W.Glanzer, 22.10.2014.
 */
public class AbstractErrorMessage implements IErrorMessage
{
  private final String message;
  private final int id;

  public AbstractErrorMessage(String pMessage, int pId)
  {
    message = pMessage;
    id = pId;
  }

  @Override
  public String getMessage()
  {
    return message;
  }

  @Override
  public int getID()
  {
    return id;
  }

  @Override
  public String toString()
  {
    String msg = ExceptionUtil.parseErrorMessage(this);
    if(msg != null)
      return msg;
    else
      return super.toString();
  }
}
