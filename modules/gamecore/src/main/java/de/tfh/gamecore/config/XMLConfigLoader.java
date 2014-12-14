package de.tfh.gamecore.config;

import de.tfh.core.IStaticResources;
import de.tfh.core.exceptions.TFHException;
import org.jetbrains.annotations.NotNull;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 * Standard-ConfigLoader
 *
 * @author W.Glanzer, 25.10.2014
 */
public class XMLConfigLoader implements IConfigLoader
{

  private static final IConfigLoader REF = new XMLConfigLoader();

  private final IConfig config;

  public XMLConfigLoader()
  {
    config = new Configuration(IStaticResources.CONFIG_PATH);
  }

  @Override
  public void applyConfig(AppGameContainer pContainer) throws TFHException
  {
    try
    {
      pContainer.setDisplayMode(config.getScreenWidth(), config.getScreenHeight(), config.isFullscreen());
      pContainer.setMusicVolume((float) config.getMusicVolume());
      pContainer.setMusicOn(config.isMusicEnabled());
      pContainer.setSoundVolume((float) config.getSoundVolume());
      pContainer.setSoundOn(config.isSoundEnabled());
      pContainer.setShowFPS(config.isShowFPS());
      pContainer.setUpdateOnlyWhenVisible(config.isOnlyUpdateWhenVisible());
      pContainer.setMultiSample(config.getMultisamples());
      pContainer.setVSync(config.isVSync());
    }
    catch(SlickException e)
    {
      throw new TFHException(e, 11);
    }
  }

  @NotNull
  @Override
  public IConfig getConfig()
  {
    return config;
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
