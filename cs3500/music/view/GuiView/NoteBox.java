package cs3500.music.view.GuiView;

import java.awt.*;

import cs3500.music.model.NoteModel;

/**
 * Created by Kofi on 11/24/2015.
 *
 * Visual representation of a note
 */
public class NoteBox {
  private NoteModel note;
  private int x;
  private int y;
  private int length;

  public NoteBox(NoteModel note, int x, int y) {
    this.note = note;
    this.x = x;
    this.y = y;
    this.length = note.getLength() * 20;
  }

  public NoteModel getNote() {
    return this.note;
  }

  public Rectangle getHead() {
    return new Rectangle(x, y, 20, 20);
  }

  public Rectangle getTail() {
    return new Rectangle(x+20, y, length-20, 20);
  }

  public boolean headContains(int x, int y) {
    return this.getHead().contains(x, y);
  }

  public boolean tailContains(int x, int y) {
    return this.getTail().contains(x, y);
  }
}
