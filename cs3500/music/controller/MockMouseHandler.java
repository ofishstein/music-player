package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Kofi on 11/25/2015.
 *
 * Mock Mouse handler for testing
 */
public class MockMouseHandler implements MouseListener, MouseMotionListener{
  StringBuilder returnLog = new StringBuilder();

  public void mouseClicked(MouseEvent e) {
    returnLog.append("Clicked");
  }

  public void mousePressed(MouseEvent e) {
    returnLog.append("Pressed");
  }

  public void mouseReleased(MouseEvent e) {
    returnLog.append("Released");
  }

  public void mouseEntered(MouseEvent e) {

  }

  public void mouseExited(MouseEvent e) {

  }

  public void mouseDragged(MouseEvent e) {
    returnLog.append("Dragged");
  }

  public void mouseMoved(MouseEvent e) {

  }
  public String log() {
    return returnLog.toString();
  }
}
