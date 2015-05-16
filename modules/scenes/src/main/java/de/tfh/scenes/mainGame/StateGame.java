package de.tfh.scenes.mainGame;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.tfh.core.IGameController;
import de.tfh.core.exceptions.TFHException;
import de.tfh.gamecore.map.Map;
import de.tfh.gamecore.map.tileset.SlickTileset;
import de.tfh.guicommon.map.GraphicMap;
import de.tfh.guicommon.player.GraphicPlayer;
import de.tfh.nifty.AbstractGameState;
import de.tfh.scenes.States;
import org.jetbrains.annotations.NotNull;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import java.io.File;
import java.io.FileInputStream;

/**
 * Stellt den Haupt-State des Spiels dar.
 *
 * @author W.Glanzer, 16.11.2014
 */
public class StateGame extends AbstractGameState
{
  // Map, auf der gerade gespielt werden soll
  private GraphicMap map;
  private GraphicPlayer player;

  public StateGame(IGameController pController)
  {
    super(States.STATE_GAME, pController);
  }

  @Override
  protected void initState(@NotNull GameContainer pGameContainer, @NotNull StateBasedGame pStateBasedGame) throws TFHException
  {
    try
    {
      map = new GraphicMap(new Map(new File("C:\\Users\\Werner\\Desktop\\sdf.map")));    //todo
      player = new GraphicPlayer(new SlickTileset(new FileInputStream("C:\\Users\\Werner\\Desktop\\tiles.png"), 32, 32));
    }
    catch(Exception e)
    {
      throw new TFHException(e, 99);
    }
  }

  @Override
  protected void renderState(@NotNull GameContainer pGameContainer, @NotNull StateBasedGame pStateBasedGame, @NotNull Graphics pGraphics)
  {
    map.draw(0, 0);
    player.draw(0, 0);
  }

  @Override
  protected void updateState(@NotNull GameContainer pGameContainer, @NotNull StateBasedGame pStateBasedGame, int pDelta)
  {
    Input input = pGameContainer.getInput();
    player.update(pDelta, input);
  }

  @Override
  protected void initGUI(GameContainer pGameContainer, StateBasedGame pStateBasedGame, @NotNull Nifty pNifty, ScreenBuilder pScreen, String pScreenID) throws TFHException
  {
  }
}
