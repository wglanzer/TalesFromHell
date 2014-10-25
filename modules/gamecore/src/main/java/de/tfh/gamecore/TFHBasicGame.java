package de.tfh.gamecore;

import de.test.core.IStaticResources;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.text.MessageFormat;

/**
 * Anfang der Einbindung in Slick2D.
 * Entspricht sozusagen dem Kern
 *
 * @author W.Glanzer, 25.10.2014
 */
public class TFHBasicGame extends BasicGame
{
  public TFHBasicGame()
  {
    super(MessageFormat.format(IStaticResources.WINDOW_TITLE, IStaticResources.MAIN_TITLE, IStaticResources.VERSION));
  }

  @Override
  public void init(GameContainer pGameContainer) throws SlickException
  {
  }

  @Override
  public void update(GameContainer pGameContainer, int i) throws SlickException
  {
  }

  @Override
  public void render(GameContainer pGameContainer, Graphics pGraphics) throws SlickException
  {
  }
}
