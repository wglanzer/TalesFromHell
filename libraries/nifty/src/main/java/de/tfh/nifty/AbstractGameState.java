package de.tfh.nifty;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.slick2d.NiftyOverlayBasicGameState;
import de.lessvoid.nifty.slick2d.input.PlainSlickInputSystem;
import de.lessvoid.nifty.slick2d.render.SlickRenderDevice;
import de.lessvoid.nifty.slick2d.sound.SlickSoundDevice;
import de.lessvoid.nifty.spi.time.impl.AccurateTimeProvider;
import de.tfh.core.IGameController;
import de.tfh.core.exceptions.TFHException;
import de.tfh.core.utils.ExceptionUtil;
import org.jetbrains.annotations.NotNull;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Abstrakte Klasse für einen State des Games.
 * Kapselt die normale BasicGameState-Klasse, um Fehlermeldungen besser
 * nachvollziehen zu können.
 *
 * @author W.Glanzer, 11.11.2014
 */
public abstract class AbstractGameState extends NiftyOverlayBasicGameState
{
  private final int id;
  private final IGameController controller;
  private static final Logger logger = LoggerFactory.getLogger(AbstractGameState.class);

  public AbstractGameState(int pID, IGameController pController)
  {
    id = pID;
    controller = pController;
  }

  @Override
  protected void enterState(@NotNull GameContainer pGameContainer, @NotNull StateBasedGame pStateBasedGame)
  {
  }

  @Override
  protected void initGameAndGUI(@NotNull GameContainer pGameContainer, @NotNull StateBasedGame pStateBasedGame)
  {
    try
    {
      PlainSlickInputSystem inputSystem = new PlainSlickInputSystem();
      inputSystem.setInput(pGameContainer.getInput());
      initNifty(pGameContainer, pStateBasedGame, new SlickRenderDevice(pGameContainer), new SlickSoundDevice(), inputSystem, new AccurateTimeProvider());

      Nifty nifty = getNifty();
      if(nifty != null)
      {
        // ScreenBuilder
        ScreenBuilder screen = new ScreenBuilder(UUID.randomUUID().toString());
        String id = UUID.randomUUID().toString();

        initGUI(pGameContainer, pStateBasedGame, nifty, screen, id);

        // ScreenBuilder builden
        Screen screenBuilded = screen.build(nifty);
        nifty.addScreen(id, screenBuilded);
        nifty.gotoScreen(id);
      }

      // State initialisieren
      initState(pGameContainer, pStateBasedGame);
    }
    catch(Exception e)
    {
      ExceptionUtil.logError(logger, 12, e, "id", getID(), "container=", pGameContainer, "stateBasedGame=", pStateBasedGame);
    }
  }

  @Override
  protected void leaveState(@NotNull GameContainer pGameContainer, @NotNull StateBasedGame pStateBasedGame)
  {
  }

  @Override
  protected void renderGame(@NotNull GameContainer pGameContainer, @NotNull StateBasedGame pStateBasedGame, @NotNull Graphics pGraphics)
  {
    try
    {
      renderState(pGameContainer, pStateBasedGame, pGraphics);
    }
    catch(TFHException e)
    {
      ExceptionUtil.logError(logger, 13, e, "id", getID(), "container=", pGameContainer, "stateBasedGame=", pStateBasedGame, "graphics=", pGraphics);
    }
  }

  @Override
  protected void updateGame(@NotNull GameContainer pGameContainer, @NotNull StateBasedGame pStateBasedGame, int i)
  {
    try
    {
      updateState(pGameContainer, pStateBasedGame, i);
    }
    catch(TFHException e)
    {
      ExceptionUtil.logError(logger, 14, e, "id", getID(), "container=", pGameContainer, "stateBasedGame=", pStateBasedGame, "delta=", i);
    }
  }

  @Override
  protected void prepareNifty(@NotNull Nifty pNifty, @NotNull StateBasedGame pStateBasedGame)
  {
    pNifty.loadStyleFile("nifty-default-styles.xml");
    pNifty.loadControlFile("nifty-default-controls.xml");
  }

  @Override
  public int getID()
  {
    return id;
  }

  /**
   * Liefert den IGameController, um die Verbindung zw.
   * TFHBasicGame und GameState herstellen zu können
   *
   * @return IGameController
   */
  protected IGameController getController()
  {
    return controller;
  }

  /**
   * Initialisiert den State des Games.
   * Wird einmal aufgerufen, wenn der State initalisiert werden soll.
   *
   * @param pGameContainer  GameContainer, der initialisiert werden soll
   * @param pStateBasedGame StateBasedGame, das initialisiert werden soll
   * @throws TFHException Falls während dem initialisieren ein Fehler aufgetreten ist
   */
  protected abstract void initState(@NotNull GameContainer pGameContainer, @NotNull StateBasedGame pStateBasedGame) throws TFHException;

  /**
   * Rendert den State, sollte nicht allzulang dauern!
   *
   * @param pGameContainer  GameContainer, der gerendert werden soll
   * @param pStateBasedGame StateBasedGame, das gerendert werden soll
   * @param pGraphics       GraphicsObject, auf das gerendert werden soll
   * @throws TFHException Falls während dem Rendern eine Exception auftritt
   */
  protected abstract void renderState(@NotNull GameContainer pGameContainer, @NotNull StateBasedGame pStateBasedGame, @NotNull Graphics pGraphics) throws TFHException;

  /**
   * Updated den State, kein Rendering!
   *
   * @param pGameContainer  GameContainer, der geupdated wird
   * @param pStateBasedGame StateBasedGame, das geupdated werden soll
   * @param pDelta          Zeit seit dem letzen Frame, zur Interpolation der Werte
   * @throws TFHException Falls während dem Updaten ine Exception auftritt
   */
  protected abstract void updateState(@NotNull GameContainer pGameContainer, @NotNull StateBasedGame pStateBasedGame, int pDelta) throws TFHException;

  /**
   * Initialisiert das Nifty-HUD. Zur besseren Kapselung sollte
   * alles nötige hier schon gemacht werden
   *
   * @param pGameContainer  GameContainer, der geupdated wird
   * @param pStateBasedGame StateBasedGame, das geupdated werden soll
   * @param pNifty          Nifty-Objekt für das HUD
   * @param pScreen         ScreenBuilder
   * @param pScreenID       ID des Screens
   * @throws TFHException Falls dabei ein Fehler auftritt
   */
  protected abstract void initGUI(GameContainer pGameContainer, StateBasedGame pStateBasedGame, @NotNull Nifty pNifty, ScreenBuilder pScreen, String pScreenID) throws TFHException;
}
