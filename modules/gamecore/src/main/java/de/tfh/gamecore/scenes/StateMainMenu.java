package de.tfh.gamecore.scenes;

import de.lessvoid.nifty.Nifty;
import de.tfh.core.exceptions.TFHException;
import de.tfh.nifty.AbstractGameState;
import org.jetbrains.annotations.NotNull;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 * State: Hauptmenü
 *
 * @author W.Glanzer, 12.11.2014
 */
public class StateMainMenu extends AbstractGameState
{
  public StateMainMenu()
  {
    super(States.STATE_MAINMENU);
  }

  @Override
  protected void initState(@NotNull GameContainer pGameContainer, @NotNull StateBasedGame pStateBasedGame) throws TFHException
  {
  }

  @Override
  protected void renderState(@NotNull GameContainer pGameContainer, @NotNull StateBasedGame pStateBasedGame, @NotNull Graphics pGraphics) throws TFHException
  {
  }

  @Override
  protected void updateState(@NotNull GameContainer pGameContainer, @NotNull StateBasedGame pStateBasedGame, int pDelta) throws TFHException
  {
  }

  @Override
  protected void initGUI(@NotNull Nifty pNifty) throws TFHException
  {
  }
}
