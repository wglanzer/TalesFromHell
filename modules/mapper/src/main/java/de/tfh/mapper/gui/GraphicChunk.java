package de.tfh.mapper.gui;

import com.google.common.base.Supplier;
import de.tfh.core.exceptions.TFHException;
import de.tfh.core.utils.ExceptionUtil;
import de.tfh.gamecore.map.Layer;
import de.tfh.gamecore.map.TileDescription;
import de.tfh.mapper.facade.IMapperFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Beschreibt einen Grafischen Chunk. Hier
 * werden die Tiles nur gezeichnet...
 *
 * @author W.Glanzer, 27.11.2014
 */
public class GraphicChunk extends JPanel
{

  private final int posX;
  private final int posY;
  private final IMapperFacade facade;
  private final Supplier<Boolean> isPaintChunkSeparators;
  private final Supplier<Boolean> isPaintTileSeparators;
  private double zoom = 0.25;

  private double tileWidth;
  private double tileHeight;
  private int tileCountX;
  private int tileCountY;

  private static final BasicStroke DASHED = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{10.0f}, 0.0f);
  private static final Logger logger = LoggerFactory.getLogger(GraphicChunk.class);

  public GraphicChunk(int pPosX, int pPosY, IMapperFacade pFacade, Supplier<Boolean> pIsPaintChunkSeparators, Supplier<Boolean> pIsPaintTileSeparators)
  {
    posX = pPosX;
    posY = pPosY;
    facade = pFacade;
    isPaintChunkSeparators = pIsPaintChunkSeparators;
    isPaintTileSeparators = pIsPaintTileSeparators;
    tileCountX = pFacade.getTileCountPerChunkX();
    tileCountY = pFacade.getTileCountPerChunkY();
    tileWidth = pFacade.getTileWidth();
    tileHeight = pFacade.getTileHeight();
    setZoom(1);

    addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseReleased(MouseEvent e)
      {
        int x = (int) ((e.getX() / tileWidth) % tileCountX);
        int y = (int) ((e.getY() / tileHeight) % tileCountY);

        try
        {
          int selectedLayer = facade.getSelectedLayer();
          TileDescription pref = facade.getPreference(x, y, posX, posY, selectedLayer >= 0 ? selectedLayer : 0);
          if(pref != null)
          {
            pref.setGraphicID(facade.getSelectedMapTileID());

            SwingUtilities.invokeLater(() -> {
              revalidate();
              repaint();
            });
          }
        }
        catch(TFHException e1)
        {
          ExceptionUtil.logError(logger, 60, e1);
        }
      }
    });
  }

  @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    int width = getWidth();
    int height = getHeight();
    boolean isPaintTileSeparator = isPaintTileSeparators.get();
    boolean isPaintChunkSeparator = isPaintChunkSeparators.get();

    g.setColor(Color.GRAY.brighter());

    try
    {
      for(int x = 0; x < tileCountX; x++)
      {
        for(int y = 0; y < tileCountY; y++)
        {
          if(isPaintTileSeparator)
          {
            g.drawLine((int) (x * tileWidth), (int) (y * tileHeight), (int) ((x + 1) * tileWidth), (int) (y * tileHeight));
            g.drawLine((int) (x * tileWidth), (int) (y * tileHeight), (int) (x * tileWidth), (int) ((y + 1) * tileHeight));
          }

          for(Image currImage : _getImagesWithoutCollision(posX * tileCountX + x, posY * tileCountY + y))
            g.drawImage(currImage, (int) (x * tileWidth * zoom), (int) (y * tileWidth * zoom), (int) (zoom * tileWidth), (int) (zoom * tileHeight), null);
        }
      }
    }
    catch(TFHException e1)
    {
      ExceptionUtil.logError(logger, 61, e1);
    }

    if(isPaintChunkSeparator)
    {
      g.setColor(Color.BLACK);
      ((Graphics2D) g).setStroke(DASHED);
      g.drawLine(0, 0, width, 0);
      g.drawLine(0, 0, 0, height);
    }
  }

  /**
   * Setzt den Zoom-Faktor
   *
   * @param pZoom  Zoom-Faktor als double
   */
  public void setZoom(double pZoom)
  {
    zoom = pZoom;
    setPreferredSize(new Dimension((int) (tileCountX * tileWidth * zoom), (int) (tileCountY * tileHeight * zoom)));
  }

  private Image[] _getImagesWithoutCollision(int pX, int pY) throws TFHException
  {
    ArrayList<Image> images = new ArrayList<>();
    for(int i = 0; i < 4; i++)
    {
      if(i == Layer.SPECIAL_LAYER)
        continue;

      int id = facade.getTileIDOnMap(pX, pY, i);
      if(id > -1)
      {
        Image img = facade.getImageForTile(id);
        if(img != null)
          images.add(img);
      }
    }
    return images.toArray(new Image[images.size()]);
  }
}
