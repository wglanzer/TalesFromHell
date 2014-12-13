package de.tfh.gamecore;

import org.jetbrains.annotations.NotNull;
import org.newdawn.slick.Input;

/**
 * Abstrakte Implementierung des Actors
 *
 * @author W.Glanzer, 12.12.2014
 */
public abstract class AbstractActor implements IActor
{

  @Override
  public void draw(float pX, float pY)
  {
  }

  @Override
  public void update(int pDelta, @NotNull Input pInput)
  {
  }

}
