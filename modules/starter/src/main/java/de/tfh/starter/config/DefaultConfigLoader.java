package de.tfh.starter.config;

import de.tfh.core.exceptions.TFHException;
import org.jetbrains.annotations.NotNull;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 * Standard-ConfigLoader
 *
 * @author W.Glanzer, 25.10.2014
 */
public class DefaultConfigLoader implements IConfigLoader
{

  private static final IConfigLoader REF = new DefaultConfigLoader();

  @Override
  public void applyConfig(AppGameContainer pContainer) throws TFHException
  {
    try
    {
      pContainer.setDisplayMode(1366, 768, false);
    }
    catch(SlickException e)
    {
      throw new TFHException(e, 9999);
    }
  }

  /**
   * Liefert den Standard-ConfigLoader
   *
   * @return Den Standard-ConfigLoader
   */
  @NotNull
  public static IConfigLoader getDefault()
  {
    return REF;
  }
}
