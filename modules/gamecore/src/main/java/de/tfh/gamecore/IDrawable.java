package de.tfh.gamecore;

import org.newdawn.slick.Renderable;

/**
 * @author W.Glanzer, 11.12.2014
 */
public interface IDrawable extends Renderable
{

  @Override
  void draw(float pX, float pY);
}
