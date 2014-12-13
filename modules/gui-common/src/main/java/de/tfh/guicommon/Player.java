package de.tfh.guicommon;

import de.tfh.gamecore.AbstractActor;
import de.tfh.gamecore.map.tileset.ITileset;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import java.util.ArrayList;
import java.util.List;

/**
 * Grafischer Spieler. Dieser wird durch eine Animation dargestellt
 *
 * @author W.Glanzer, 11.12.2014
 */
public class Player extends AbstractActor
{
  private Animation[] animations = new Animation[0];
  private Animation currAnimation = new Animation();

  public Player(@Nullable ITileset<Image> pPlayerTiles, int pAnimationSpeed)
  {
    _applyTileset(pPlayerTiles, pAnimationSpeed);
    _setAnimation(0);
  }

  @Override
  public void update(int pDelta, @NotNull Input pInput)
  {
    super.update(pDelta, pInput);

    if(pInput.isKeyDown(Input.KEY_W))
      _setAnimation(2);

    // Animation updaten
    if(currAnimation != null)
      currAnimation.update(pDelta);

    // Bewegung
    _doMove(pDelta, pInput);
  }

  @Override
  public void draw(float pX, float pY)
  {
    super.draw(pX, pY);
    currAnimation.draw(pX, pY);
  }

  /**
   * Wird aufgerufen, wenn sich der Spieler bewegen soll.
   *
   * @param pDelta Delta-Zeit seit dem letzten Frame in ms
   * @param pInput Input-Object um Tastatureingaben abzufragen
   */
  private void _doMove(int pDelta, Input pInput)
  {
  }

  /**
   * Übernimmt das Tileset in die Animation
   *
   * @param pTileset        Player-Tileset. Jede Reihe stellt eine Animation dar
   * @param pAnimationSpeed Geschwindigkeit der Animation
   */
  private void _applyTileset(ITileset<Image> pTileset, int pAnimationSpeed)
  {
    animations = new Animation[pTileset.getTileCountY()];

    for(int row = 0; row < pTileset.getTileCountY(); row++)
    {
      List<Image> list = new ArrayList<>();
      for(int i = 0; i < pTileset.getTileCountX(); i++)
      {
        Image currImage = pTileset.getTile(i, row);
        if(currImage != null)
          list.add(currImage);
      }

      animations[row] = new Animation(list.toArray(new Image[list.size()]), pAnimationSpeed);
    }
  }

  /**
   * Setzt die gewählte Animation
   *
   * @param pAnimation  Animation
   */
  private void _setAnimation(int pAnimation)
  {
    if(currAnimation != null)
    {
      currAnimation.stop();
      currAnimation.setCurrentFrame(0);
    }

    if(pAnimation < animations.length)
    {
      currAnimation = animations[pAnimation];
      currAnimation.start();
    }
  }
}
