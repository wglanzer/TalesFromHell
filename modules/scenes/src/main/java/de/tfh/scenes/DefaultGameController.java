package de.tfh.scenes;

import de.tfh.core.IGameController;
import org.jetbrains.annotations.Nullable;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.io.File;

/**
 * IGameController-Impl
 *
 * @author W.Glanzer, 13.12.2014
 */
public class DefaultGameController implements IGameController
{

  private final StateBasedGame game;
  private File currentMapFileObject;

  public DefaultGameController(StateBasedGame pGame)
  {
    game = pGame;
  }

  @Override
  public void enterState(int pID)
  {
    game.enterState(pID, new FadeOutTransition(), new FadeInTransition());
  }

  @Nullable
  @Override
  public File getCurrentMapFileObject()
  {
    return currentMapFileObject;
  }

  /**
   * Setzt das MapFileObject
   *
   * @param pCurrentMapFileObject MapFileObject, oder <tt>null</tt>
   */
  public void setCurrentMapFileObject(File pCurrentMapFileObject)
  {
    currentMapFileObject = pCurrentMapFileObject;
  }
}
