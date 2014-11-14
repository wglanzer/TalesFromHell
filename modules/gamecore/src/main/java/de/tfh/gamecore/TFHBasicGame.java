package de.tfh.gamecore;

import de.lessvoid.nifty.slick2d.NiftyStateBasedGame;
import de.tfh.core.IStaticResources;
import de.tfh.gamecore.scenes.StateMainMenu;
import de.tfh.gamecore.scenes.States;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import java.text.MessageFormat;

/**
 * Anfang der Einbindung in Slick2D.
 * Entspricht sozusagen dem Kern
 *
 * @author W.Glanzer, 25.10.2014
 */
public class TFHBasicGame extends NiftyStateBasedGame
{
  public TFHBasicGame()
  {
    super(MessageFormat.format(IStaticResources.WINDOW_TITLE, IStaticResources.MAIN_TITLE, IStaticResources.VERSION));
  }

  @Override
  public void initStatesList(GameContainer pGameContainer) throws SlickException
  {
    addState(new StateMainMenu());

    // Hauptmenü aktivieren
    enterState(States.STATE_MAINMENU);
  }
}
