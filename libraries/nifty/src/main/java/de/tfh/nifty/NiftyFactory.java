package de.tfh.nifty;

import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.tools.SizeValue;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Ersteller für Nifty-Komponenten
 *
 * @author W.Glanzer, 11.11.2014
 */
public class NiftyFactory
{

  /**
   * Erstellt einen Layer und fügt gleich eine ID an
   *
   * @return LayerBuilder-Instanz
   */
  public static LayerBuilder createLayer()
  {
    return new LayerBuilder(UUID.randomUUID().toString());
  }

  /**
   * Erstellt ein Panel und fügt gleich eine ID an.
   * Ebenfalls wird das interne Layout bestimmt und die Größe
   * so angepasst, dass diese den ganzen Frame einnimmt
   *
   * @return PanelBuilder-Instanz
   */
  public static PanelBuilder createPanel()
  {
    PanelBuilder panel = new PanelBuilder(UUID.randomUUID().toString());
    panel.childLayoutAbsolute();
    panel.width(SizeValue.percentWidth(100));
    panel.height(SizeValue.percentHeight(100));

    return panel;
  }

  /**
   * Erstellt einen Button und fügt eine Runnable-Action dahinter ein
   *
   * @param pTitle   Titel des Buttons
   * @param pOnClick Runnable, das beim Klick ausgeführt wird.
   *                 Darf keine Referenz auf Dinge ausserhalb haben!! Oder <tt>null</tt>
   * @return ButtonBuilder für den Button
   */
  public static ButtonBuilder createButton(String pTitle, @Nullable Runnable pOnClick)
  {
    ButtonBuilder builder = new ButtonBuilder(UUID.randomUUID().toString(), pTitle);
    if(pOnClick != null)
    {
      String uuid = RunnableRegistrator.register(pOnClick);
      builder.interactOnClick(String.format(RunnableRegistratorScreenController.REGISTERED_RUNNABLE_METHOD_DECLARATION, uuid));
    }

    return builder;
  }

}
