package de.tfh.starter;

import de.lessvoid.nifty.slick2d.NiftyStateBasedGame;
import de.tfh.core.IGameController;
import de.tfh.core.IStaticResources;
import de.tfh.scenes.DefaultGameController;
import de.tfh.scenes.StateMainMenu;
import de.tfh.scenes.States;
import de.tfh.scenes.mainGame.StateGame;
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
    IGameController controller = new DefaultGameController(this);

    addState(new StateMainMenu(controller));
    addState(new StateGame(controller));

    // Hauptmenü aktivieren
    enterState(States.STATE_MAINMENU);
  }
}
