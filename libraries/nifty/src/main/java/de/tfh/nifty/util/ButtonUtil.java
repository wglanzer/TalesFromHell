package de.tfh.nifty.util;

import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.tools.SizeValue;
import de.tfh.nifty.NiftyFactory;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * @author W.Glanzer, 14.11.2014
 */
public class ButtonUtil
{

  /**
   * F�gt einen Button auf ein Panel unten rechts ein
   *
   * @param pName              Name / Titel des Buttons
   * @param pPanel             Panel auf dem der Button hinzugef�gt werden soll
   * @param pVerticalPosition  Position des Buttons
   * @param pOnClick           Runnable, das bei Klick auf den Button ausgef�hrt werden soll, oder <tt>null</tt>
   */
  public static ButtonBuilder addButtonBottomRight(String pName, PanelBuilder pPanel, int pVerticalPosition, @Nullable Runnable pOnClick)
  {
    String id = UUID.randomUUID().toString();
    ButtonBuilder button = NiftyFactory.createButton(pName, pOnClick);
    button.id(id);
    button.alignRight();
    button.valignBottom();
    button.x(SizeValue.percentWidth(85));
    button.y(SizeValue.percentHeight(100 - ((pVerticalPosition + 1) * 5)));

    button.height(SizeValue.percentHeight(5));
    button.width(SizeValue.percentWidth(15));
    pPanel.control(button);

    return button;
  }

}
