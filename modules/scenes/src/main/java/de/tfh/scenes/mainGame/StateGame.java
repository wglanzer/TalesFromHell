package de.tfh.scenes.mainGame;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.tfh.core.IGameController;
import de.tfh.core.exceptions.TFHException;
import de.tfh.gamecore.map.Map;
import de.tfh.guicommon.map.GraphicMap;
import de.tfh.nifty.AbstractGameState;
import de.tfh.nifty.DefaultScreenController;
import de.tfh.scenes.States;
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
  // Map, auf der gerade gespielt werden soll
  private GraphicMap map;

//  private Player player;

  public StateGame(IGameController pController)
  {
    super(States.STATE_GAME, pController);
  }

  @Override
  protected void initState(@NotNull GameContainer pGameContainer, @NotNull StateBasedGame pStateBasedGame) throws TFHException
  {
//    try
//    {
//      player = new Player(new SlickTileset("C:\\Users\\werne_000\\Desktop\\player.png", 64, 128), 250);
//    }
//    catch(Exception e)
//    {
//      throw new TFHException(e, 9999);
//    }
  }

  @Override
  protected void enterState(@NotNull GameContainer pGameContainer, @NotNull StateBasedGame pStateBasedGame)
  {
    map = new GraphicMap(new Map(getController().getCurrentMapFileObject()));
  }

  @Override
  protected void renderState(@NotNull GameContainer pGameContainer, @NotNull StateBasedGame pStateBasedGame, @NotNull Graphics pGraphics)
  {
    map.draw(0, 0);
//    player.draw(100, 100);
  }

  @Override
  protected void updateState(@NotNull GameContainer pGameContainer, @NotNull StateBasedGame pStateBasedGame, int pDelta)
  {
//    player.update(pDelta, pGameContainer.getInput());
  }

  @Override
  protected void initGUI(GameContainer pGameContainer, StateBasedGame pStateBasedGame, @NotNull Nifty pNifty, ScreenBuilder pScreen, String pScreenID) throws TFHException
  {
    pScreen.controller(new DefaultScreenController());
  }
}
