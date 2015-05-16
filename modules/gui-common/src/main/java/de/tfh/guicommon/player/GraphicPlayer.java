package de.tfh.guicommon.player;

import de.tfh.gamecore.AbstractActor;
import de.tfh.gamecore.IKeyConfig;
import de.tfh.gamecore.map.tileset.ITileset;
import org.jetbrains.annotations.NotNull;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

/**
 * Grafischer Spieler
 *
 * @author W.Glanzer, 11.12.2014
 */
public class GraphicPlayer extends AbstractActor
{
  public static final int ANIM_IDLE = 0;

  /**
   * Animationen
   */
  private Animation[] animation;
  private int currAnimation = ANIM_IDLE;
  private ITileset<Image> tileset;

  /**
   * Position des Spielers
   */
  private float x;
  private float y;

  /**
   * Zeit in ms die der Spieler zum nächsten Block benötigt
   */
  private float movespeed = 250;
  private int deltaMovespeed = 0;
  private boolean isMoving = false;
  private int moveX = 0, moveY = 0;
  private float xBeforeMove, yBeforeMove;

  /**
   * Delta-Time
   */
  private int delta = 0;

  public GraphicPlayer(ITileset<Image> pTileset)
  {
    tileset = pTileset;
    _initAnimations();
  }

  @Override
  public void draw(float pX, float pY)
  {
    animation[currAnimation].draw(pX + x, pY + y);
  }

  @Override
  public void update(int pDelta, @NotNull Input pInput)
  {
    delta = pDelta;
    animation[currAnimation].update(pDelta);

    if(!isMoving)
    {
      if(pInput.isKeyDown(IKeyConfig.KEY_UP))
        _move(0, -1);
      else if(pInput.isKeyDown(IKeyConfig.KEY_DOWN))
        _move(0, 1);
      else if(pInput.isKeyDown(IKeyConfig.KEY_RIGHT))
        _move(1, 0);
      else if(pInput.isKeyDown(IKeyConfig.KEY_LEFT))
        _move(-1, 0);
    }

    if(isMoving)
      _updateMoving();
  }

  private void _updateMoving()
  {
    if(!isMoving)
      return;

    deltaMovespeed += delta;

    float toMoveNow = (Math.min(deltaMovespeed / movespeed, 1f)) * tileset.getTileWidth();
    x = xBeforeMove + (toMoveNow * moveX);
    y = yBeforeMove + (toMoveNow * moveY);

    if(deltaMovespeed >= movespeed)
    {
      isMoving = false;

      x = _getNearest(xBeforeMove, xBeforeMove + (tileset.getTileWidth() * Math.min(0, moveX)), xBeforeMove + (tileset.getTileWidth() * Math.max(0, moveX)));
      y = _getNearest(yBeforeMove, yBeforeMove + (tileset.getTileHeight() * Math.min(0, moveY)), yBeforeMove + (tileset.getTileHeight() * Math.max(0, moveY)));

      deltaMovespeed = 0;
      xBeforeMove = 0;
      yBeforeMove = 0;
    }
  }

  private float _getNearest(float pFrom, float pLeft, float pRight)
  {
    return pFrom - pLeft > pRight - pFrom ? pLeft : pRight;
  }

  private void _move(int pX, int pY)
  {
    isMoving = true;
    moveX = pX;
    moveY = pY;
    xBeforeMove = x;
    yBeforeMove = y;
  }

  /**
   * Initialisiert die Animationen
   */
  private void _initAnimations()
  {
    animation = new Animation[1];
    Image[] tiles = _getImages(tileset);
    animation[0] = new Animation(new Image[]{tiles[0], tiles[1], tiles[3], tiles[5]}, 500);
  }

  /**
   * Liefert alle Images des Tilesets
   *
   * @param pTileset  Tileset, das verwendet werden soll
   * @return Image-Array, nicht <tt>null</tt>
   */
  private Image[] _getImages(ITileset<Image> pTileset)
  {
    int tileCountX = pTileset.getTileCountX();
    int tileCountY = pTileset.getTileCountY();

    Image[] image = new Image[tileCountX * tileCountY];
    for(int x = 0; x < tileCountX; x++)
      for(int y = 0; y < tileCountY; y++)
        image[y * tileCountX + x] = pTileset.getTile(x, y);

    return image;
  }
}
