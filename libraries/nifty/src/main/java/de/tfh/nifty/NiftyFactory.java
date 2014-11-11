package de.tfh.nifty;

import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;

import java.util.UUID;

/**
 * Ersteller für Nifty-Komponenten
 *
 * @author W.Glanzer, 11.11.2014
 */
public class NiftyFactory
{

  public static ScreenBuilder buildScreen()
  {
    return new ScreenBuilder(UUID.randomUUID().toString());
  }

  public static LayerBuilder buildLayer()
  {
    return new LayerBuilder();
  }

  public static PanelBuilder buildPanel()
  {
    return new PanelBuilder();
  }

  public static LabelBuilder buildLabel()
  {
    return new LabelBuilder();
  }

}
