package de.tfh.mapper.gui.containers;

import de.tfh.mapper.facade.IMapperFacade;

import java.awt.*;

/**
 * Ein DummyContainer, der schwarz-rosa K�stchen anzeigt
 *
 * @author W.Glanzer, 19.11.2014
 */
public class DummyContainer extends AbstractContainer
{
  private static final int SIZE = 16;
  private static final Color INVALID = new Color(0xFF00FF);

  public DummyContainer(IMapperFacade pFacade)
  {
    super(pFacade);
  }

  @Override
  public void paintComponent(Graphics g)
  {
    int width = getWidth() + SIZE;
    int height = getHeight() + SIZE;

    g.setColor(Color.BLACK);
    g.fillRect(0, 0, width, height);

    g.setColor(INVALID);
    for(int i = 0; i < height / SIZE; i++)
      for(int j = i % 2; j < width / SIZE; j += 2)
        g.fillRect(j * SIZE, i * SIZE, SIZE, SIZE);
  }

  @Override
  protected void reinit()
  {
  }
}
