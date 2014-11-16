package de.tfh.gamecore.scenes;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.tfh.core.IGameController;
import de.tfh.core.exceptions.TFHException;
import de.tfh.core.i18n.Messages;
import de.tfh.core.utils.ExceptionUtil;
import de.tfh.nifty.AbstractGameState;
import de.tfh.nifty.NiftyFactory;
import de.tfh.nifty.RunnableRegistratorScreenController;
import de.tfh.nifty.util.ButtonUtil;
import org.jetbrains.annotations.NotNull;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * State: Hauptmenü
 *
 * @author W.Glanzer, 12.11.2014
 */
public class StateMainMenu extends AbstractGameState
{
  public StateMainMenu(IGameController pController)
  {
    super(States.STATE_MAINMENU, pController);
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
    pScreen.controller(new RunnableRegistratorScreenController());

    // Layer des kompletten Hauptmenüs
    LayerBuilder layer = NiftyFactory.createLayer();
    layer.childLayoutAbsolute();

    // Panels, die angezeigt werden können
    PanelBuilder defaultPanel = NiftyFactory.createPanel();
    PanelBuilder settingsPanel = NiftyFactory.createPanel();
    PanelBuilder loadPanel = NiftyFactory.createPanel();

    // Panels unsichtbar schalten
    settingsPanel.visible(false);
    loadPanel.visible(false);

    // DefaultPanel, das am Anfang angezeigt wird
    ButtonBuilder btnContinue = ButtonUtil.addButtonBottomRight(Messages.get(0), defaultPanel, 5, null);// Fortsetzen
    ButtonBuilder btnNewCampaign = ButtonUtil.addButtonBottomRight(Messages.get(1), defaultPanel, 4, new _LoadOtherState(States.STATE_GAME));// Neue Kampagne //todo
    ButtonBuilder btnLoad = ButtonUtil.addButtonBottomRight(Messages.get(2), defaultPanel, 3, new _SwitchPanel(pScreenID, defaultPanel.getId(), loadPanel.getId()));// Laden
    ButtonBuilder btnAdditionalContent = ButtonUtil.addButtonBottomRight(Messages.get(3), defaultPanel, 2, null);// Zustäzliche Inhalte
    ButtonBuilder btnOptions = ButtonUtil.addButtonBottomRight(Messages.get(4), defaultPanel, 1, new _SwitchPanel(pScreenID, defaultPanel.getId(), settingsPanel.getId()));// Optionen
    ButtonBuilder btnExit = ButtonUtil.addButtonBottomRight(Messages.get(5), defaultPanel, 0, new _Exit(pGameContainer));// Beenden
    layer.panel(defaultPanel);

    // Speicherstand laden
    ButtonBuilder btnAutosave = ButtonUtil.addButtonBottomRight(Messages.get(9), loadPanel, 8, null);//autosave
    ButtonBuilder btnQuicksave = ButtonUtil.addButtonBottomRight(Messages.get(8), loadPanel, 7, null);//quicksave
    ButtonBuilder btnLoad1 = ButtonUtil.addButtonBottomRight("- " + Messages.get(7) + " -", loadPanel, 5, null);
    ButtonBuilder btnLoad2 = ButtonUtil.addButtonBottomRight("- " + Messages.get(7) + " -", loadPanel, 4, null);
    ButtonBuilder btnLoad3 = ButtonUtil.addButtonBottomRight("- " + Messages.get(7) + " -", loadPanel, 3, null);
    ButtonBuilder btnLoad5 = ButtonUtil.addButtonBottomRight("- " + Messages.get(7) + " -", loadPanel, 2, null);
    ButtonBuilder btnLoad6 = ButtonUtil.addButtonBottomRight("- " + Messages.get(7) + " -", loadPanel, 1, null);
    ButtonBuilder btnBack2 = ButtonUtil.addButtonBottomRight(Messages.get(6), loadPanel, 0, new _SwitchPanel(pScreenID, loadPanel.getId(), defaultPanel.getId()));
    layer.panel(loadPanel);

    // SettingsPanel, für Optionen
    ButtonBuilder btnDummy = ButtonUtil.addButtonBottomRight("_DUMMY_", settingsPanel, 1, null);
    ButtonBuilder btnBack1 = ButtonUtil.addButtonBottomRight(Messages.get(6), settingsPanel, 0, new _SwitchPanel(pScreenID, settingsPanel.getId(), defaultPanel.getId()));
    layer.panel(settingsPanel);

    // Layer zum Screen hinzufügen
    pScreen.layer(layer);
  }

  /**
   * Wechselt den State
   */
  private class _LoadOtherState implements Runnable
  {

    private final int switchTo;

    public _LoadOtherState(int pSwitchTo)
    {
      switchTo = pSwitchTo;
    }

    @Override
    public void run()
    {
      getController().enterState(switchTo);
    }
  }

  /**
   * Aufruf zum Switchen des Panels
   */
  private class _SwitchPanel implements Runnable
  {
    private final String screenID;
    private final String from;
    private final String to;
    private final Logger logger = LoggerFactory.getLogger(_SwitchPanel.class);

    public _SwitchPanel(String pScreenID, String pFrom, String pSwitchTo)
    {
      screenID = pScreenID;
      from = pFrom;
      to = pSwitchTo;
    }

    @Override
    public void run()
    {
      Nifty nifty = getNifty();
      if(nifty != null)
      {
        Screen screen = nifty.getScreen(screenID);
        if(screen != null)
        {
          Element eleFrom = screen.findElementById(from);
          Element eleTo = screen.findElementById(to);
          if(eleFrom != null && eleTo != null)
          {
            eleFrom.setVisible(false);
            eleTo.setVisible(true);
            return;
          }
        }
      }

      ExceptionUtil.logError(logger, 9999, null, "to=" + to, "from=" + from, "screenID=" + screenID);
    }
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
