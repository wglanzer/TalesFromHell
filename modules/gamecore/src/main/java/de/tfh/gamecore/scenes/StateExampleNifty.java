package de.tfh.gamecore.scenes;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.screen.Screen;
import de.tfh.core.exceptions.TFHException;
import de.tfh.nifty.AbstractGameState;
import de.tfh.nifty.DefaultScreenController;
import de.tfh.nifty.NiftyFactory;
import org.jetbrains.annotations.NotNull;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 * State: Beispiel für die NiftyImplementierung
 *
 * @author W.Glanzer, 11.11.2014
 */
public class StateExampleNifty extends AbstractGameState
{

  public StateExampleNifty()
  {
    super(States.STATE_NIFTYEXAMPLE);
  }

  @Override
  public void initState(@NotNull GameContainer pGameContainer, @NotNull StateBasedGame pStateBasedGame)
  {
  }

  @Override
  public void renderState(@NotNull GameContainer pGameContainer, @NotNull StateBasedGame pStateBasedGame, @NotNull Graphics pGraphics)
  {
  }

  @Override
  public void updateState(@NotNull GameContainer pGameContainer, @NotNull StateBasedGame pStateBasedGame, int i)
  {
  }

  @Override
  protected void initGUI(@NotNull Nifty pNifty) throws TFHException
  {
    ScreenBuilder screen = NiftyFactory.buildScreen();
    screen.controller(new DefaultScreenController());

    LayerBuilder layer = NiftyFactory.buildLayer();
    layer.childLayoutVertical();

    PanelBuilder panel = NiftyFactory.buildPanel();
    panel.childLayoutCenter();

    ButtonBuilder button = new ButtonBuilder("test", "Hello Nifty");
    button.alignCenter();
    button.valignCenter();
    button.height("5%");
    button.width("15%");
    button.visibleToMouse(true);
    button.interactOnClick("test()");

    panel.control(button);
    layer.panel(panel);
    screen.layer(layer);

    Screen screenBuilded = screen.build(pNifty);
    pNifty.addScreen("screen", screenBuilded);
    pNifty.gotoScreen("screen");
  }
}
