package cs3500.music.controller;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import cs3500.music.model.CompositionModel;
import cs3500.music.model.NoteModel;

/**
 * Created by Kofi on 11/21/2015.
 *
 * Mouse listener implementation
 */
public class MouseHandler implements java.awt.event.MouseListener, MouseMotionListener {
  //private final GUIView view;
  private final CompositionModel viewComp;
  private final Controller controller;
  private boolean pickedUpp = false;
  private NoteModel pickedUp;

  private Point location;

  private StringBuilder returnLog = new StringBuilder();

  //public MouseHandler(GUIView view) {
  //  this.view = view;
  //  this.viewComp = view.getComp();
  //}

  public MouseHandler(Controller controller, CompositionModel comp) {
    this.controller = controller;
    this.viewComp = comp;
  }

  /**
   * Invoked when the mouse button has been clicked (pressed and released) on a component.
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    this.controller.mutateNote(e.getX(), e.getY(), e.getButton());

    if (e.getButton() == 2) {
      NoteModel n = controller.getNoteFromPosition(e.getX(), e.getY());
      if(n != null) {
        n.setInstrument(10);
        this.viewComp.addNote(n);
      }
    }

    returnLog.append("Clicked");
  }

  /**
   * Invoked when a mouse button has been pressed on a component.
   */
  @Override
  public void mousePressed(MouseEvent e) {
    pickedUp = this.controller.notePickUp(e.getX(), e.getY());
    returnLog.append("Pressed");
  }


  /**
   * Invoked when a mouse button has been released on a component.
   */
  @Override
  public void mouseReleased(MouseEvent e) {
    if ((pickedUp != null) && pickedUpp) {
      this.viewComp.removeNote(pickedUp);

      NoteModel dummy = this.controller.getNoteFromPosition(e.getX(), e.getY());

      if (dummy != null) {
        pickedUp.setStartTime(dummy.getStartTime());
        pickedUp.setOctave(dummy.getOctave());
        pickedUp.setPitch(dummy.getPitch());
        // if (pickedUp)

        this.viewComp.addNote(pickedUp);
        this.controller.getView().Repaint();
        pickedUpp = false;
      }
    }
    returnLog.append("Released");
  }

  /**
   * Invoked when the mouse enters a component.
   */
  @Override
  public void mouseEntered(MouseEvent e) {
  }

  /**
   * Invoked when the mouse exits a component.
   */
  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    pickedUpp = true;
    returnLog.append("Dragged");
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    location = new Point(e.getX(), e.getY());
  }

  public Point getLocation(){
    return location;
  }

  public String log() {
    return returnLog.toString();
  }
}
