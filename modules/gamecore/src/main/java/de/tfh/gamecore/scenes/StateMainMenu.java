package de.tfh.gamecore.scenes;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.tools.SizeValue;
import de.tfh.core.exceptions.TFHException;
import de.tfh.nifty.AbstractGameState;
import de.tfh.nifty.RunnableRegistratorScreenController;
import de.tfh.nifty.util.ButtonUtil;
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
  protected void initGUI(GameContainer pGameContainer, final StateBasedGame pStateBasedGame, @NotNull Nifty pNifty, ScreenBuilder pScreen) throws TFHException
  {
    pScreen.controller(new RunnableRegistratorScreenController());

    LayerBuilder layer = new LayerBuilder();
    layer.childLayoutVertical();

    PanelBuilder panel = new PanelBuilder();
    panel.childLayoutAbsoluteInside();
    panel.width(SizeValue.percentWidth(100));
    panel.height(SizeValue.percentHeight(100));

    ButtonUtil.addButtonBottomRight("Kampagne fortsetzen", panel, 5, null);
    ButtonUtil.addButtonBottomRight("Neue Kampagne", panel, 4, null);
    ButtonUtil.addButtonBottomRight("Speicherstand laden", panel, 3, null);
    ButtonUtil.addButtonBottomRight("Zusätzliche Inhalte", panel, 2, null);
    ButtonUtil.addButtonBottomRight("Optionen", panel, 1, null);
    ButtonUtil.addButtonBottomRight("Beenden", panel, 0, new _Exit(pGameContainer));

    layer.panel(panel);
    pScreen.layer(layer);
  }

  /**
   * Beendet das Spiel
   */
  private class _Exit implements Runnable
  {
    private final GameContainer containerToExit;

    public _Exit(GameContainer pContainerToExit)
    {
      containerToExit = pContainerToExit;
    }

    @Override
    public void run()
    {
      containerToExit.exit();  //todo evtl 2sec warten, und dann forcen?
    }
  }
}
