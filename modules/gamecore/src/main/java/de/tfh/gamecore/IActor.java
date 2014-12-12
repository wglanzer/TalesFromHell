package de.tfh.gamecore;

import org.jetbrains.annotations.NotNull;
import org.newdawn.slick.Input;

/**
 * Actor, dieser wird auf der Map dargestellt, und kann selbst etwas machen
 *
 * @author W.Glanzer, 12.12.2014
 */
public interface IActor extends IDrawable, IUpdateable
{

  @Override
  void draw(float pX, float pY);

  @Override
  void update(float pDelta, @NotNull Input pInput);

}
