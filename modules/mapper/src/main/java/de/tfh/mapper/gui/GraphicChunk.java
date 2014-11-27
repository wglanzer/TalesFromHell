package de.tfh.mapper.gui;

import de.tfh.core.exceptions.TFHException;
import de.tfh.core.utils.ExceptionUtil;
import de.tfh.gamecore.map.Layer;
import de.tfh.gamecore.map.TilePreference;
import de.tfh.mapper.facade.IMapperFacade;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
  private double zoom = 0.25;

  private double tileWidth;
  private double tileHeight;
  private int tileCountX;
  private int tileCountY;

  public GraphicChunk(int pPosX, int pPosY, IMapperFacade pFacade)
  {
    posX = pPosX;
    posY = pPosY;
    facade = pFacade;
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
          TilePreference pref = facade.getPreference(x, y, posX, posY, Layer.MIDGROUND);
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
          e1.printStackTrace();
        }
      }
    });
  }

  @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    g.setColor(Color.GRAY.brighter());

    try
    {
      for(int x = 0; x < tileCountX; x++)
      {
        for(int y = 0; y < tileCountY; y++)
        {
          g.drawLine((int) (x * tileWidth), (int) (y * tileHeight), (int) ((x + 1) * tileWidth), (int) (y * tileHeight));
          g.drawLine((int) (x * tileWidth), (int) (y * tileHeight), (int) (x * tileWidth), (int) ((y + 1) * tileHeight));

          int id = facade.getTileIDOnMap(posX * tileCountX + x, posY * tileCountY + y, Layer.MIDGROUND);
          if(id > -1)
          {
            Image img = facade.getImageForTile(id);
            if(img != null)
              g.drawImage(img, (int) (x * tileWidth * zoom), (int) (y * tileWidth * zoom), (int) (zoom * tileWidth), (int) (zoom * tileHeight), null);
          }
        }
      }
    }
    catch(Exception e)
    {
      ExceptionUtil.logError(LoggerFactory.getLogger(GraphicChunk.class), 9999, e);
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
}
