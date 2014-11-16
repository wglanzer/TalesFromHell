package de.tfh.gamecore.scenes.mainGame;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.tfh.core.IGameController;
import de.tfh.core.exceptions.TFHException;
import de.tfh.gamecore.scenes.States;
import de.tfh.nifty.AbstractGameState;
import org.jetbrains.annotations.NotNull;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Stellt den Haupt-State des Spiels dar.
 *
 * @author W.Glanzer, 16.11.2014
 */
public class StateGame extends AbstractGameState
{
  public StateGame(IGameController pController)
  {
    super(States.STATE_GAME, pController);
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
  protected void initGUI(GameContainer pGameContainer, StateBasedGame pStateBasedGame, @NotNull Nifty pNifty, ScreenBuilder pScreen, String pScreenID) throws TFHException
  {
  }
}