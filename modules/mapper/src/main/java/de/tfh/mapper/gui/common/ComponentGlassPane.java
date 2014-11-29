package de.tfh.mapper.gui.common;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

/*
 *  Simple implementation of a Glass Pane that will capture and ignore all
 *  events as well paint the glass pane to give the frame a "disabled" look.
 *
 *  The background color of the glass pane should use a color with an
 *  alpha value to create the disabled look.
 */
public class ComponentGlassPane extends JComponent implements KeyListener
{
  private final static Border MESSAGE_BORDER = new EmptyBorder(10, 10, 10, 10);
  private JComponent component;

  public ComponentGlassPane(JComponent pComponent)
  {
    component = pComponent;

    //  Set glass pane properties

    setOpaque(false);
    Color base = UIManager.getColor("inactiveCaptionBorder");
    Color background = new Color(base.getRed(), base.getGreen(), base.getBlue(), 128);
    setBackground(background);
    setLayout(new GridBagLayout());

    //  Add a component label to the glass pane

    add(component, new GridBagConstraints());
    component.setOpaque(true);
    component.setBorder(MESSAGE_BORDER);

    //  Disable Mouse, Key and Focus events for the glass pane

    addMouseListener(new MouseAdapter()
    {
    });
    addMouseMotionListener(new MouseMotionAdapter()
    {
    });

    addKeyListener(this);

    setFocusTraversalKeysEnabled(false);
  }

  /*
   *  The component is transparent but we want to paint the background
   *  to give it the disabled look.
   */
  @Override
  protected void paintComponent(Graphics g)
  {
    g.setColor(getBackground());
    g.fillRect(0, 0, getSize().width, getSize().height);
  }

  /*
   *  The	background color of the component label will be the same as the
   *  background of the glass pane without the alpha value
   */
  @Override
  public void setBackground(Color background)
  {
    super.setBackground(background);

    Color messageBackground = new Color(background.getRGB());
    component.setBackground(messageBackground);
  }

  //
  //  Implement the KeyListener to consume events
  //
  public void keyPressed(KeyEvent e)
  {
    e.consume();
  }

  public void keyTyped(KeyEvent e)
  {
  }

  public void keyReleased(KeyEvent e)
  {
    e.consume();
  }

  /*
   *  Make the glass pane visible and change the cursor to the wait cursor
   *
   *  A component can be displayed and it will be centered on the frame.
   */
  public void activate()
  {
    component.setVisible(true);
    component.setForeground(getForeground());

    setVisible(true);
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    requestFocusInWindow();
  }

  /*
   *  Hide the glass pane and restore the cursor
   */
  public void deactivate()
  {
    setCursor(null);
    setVisible(false);
  }
}

