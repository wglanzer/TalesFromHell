package de.tfh.gamecore.config;

import org.jetbrains.annotations.Nullable;

/**
 * @author W.Glanzer, 14.11.2014
 */
class Configuration extends AbstractConfiguration
{
  public Configuration(@Nullable String pPath)
  {
    super(pPath);
  }

  @Override
  public int getScreenWidth()
  {
    return model.screenWidth;
  }

  @Override
  public int getScreenHeight()
  {
    return model.screenHeight;
  }

  @Override
  public int getMultisamples()
  {
    return model.multisample > 0 ? model.multisample : 0;
  }

  @Override
  public double getMusicVolume()
  {
    if(model.musicVolume > 1)
      return 1.0;

    if(model.musicVolume < 0)
      return 0;

    return model.musicVolume;
  }

  @Override
  public double getSoundVolume()
  {
    if(model.soundVolume > 1)
      return 1.0;

    if(model.soundVolume < 0)
      return 0;

    return model.soundVolume;
  }

  @Override
  public boolean isFullscreen()
  {
    return model.fullscreen;
  }

  @Override
  public boolean isOnlyUpdateWhenVisible()
  {
    return model.onlyUpdateWhenVisible;
  }

  @Override
  public boolean isMusicEnabled()
  {
    return model.musicEnabled;
  }

  @Override
  public boolean isSoundEnabled()
  {
    return model.soundEnabled;
  }

  @Override
  public boolean isShowFPS()
  {
    return model.showFPS;
  }

  @Override
  public boolean isVSync()
  {
    return model.vSync;
  }
}
