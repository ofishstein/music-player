package cs3500.music.controller;

import cs3500.music.model.NoteModel;
import cs3500.music.view.GuiView.GUIView;

/**
 * Created by Kofi on 11/23/2015.
 *
 * controller interface
 */
public interface Controller {

  /**
   * Render the view
   */
  void render();

  /**
   * Returns the current beat being played by the controller
   *
   * @return - the current beat
   */
  int getCurrentBeat();


  //*******************************GUIView***********************************************

  //Added by Ollie
  GUIView getView();

  /**
   * Scroll left/right by given distance
   *
   * @param scrollDistance - Distance to scroll
   */
  void scroll(int scrollDistance);

  /**
   * Reset the view to beginning
   */
  void scrollToBeginning();

  /**
   * Scroll to the end of the song
   */
  void scrollToEnd();

  /**
   * Get the noteModel of the note being visualized at point x,y
   *
   * @param x - x location
   * @param y - y location
   * @return - the NoteModel of the note at x,y
   */
  NoteModel getNoteFromPosition(int x, int y);

  /**
   * Change aspects of a note based on the source
   *
   * @param x      - x location of source
   * @param y      - y location of source
   * @param source - who did it?
   */
  void mutateNote(int x, int y, int source);

  /**
   * Pick up a note to be moved
   *
   * @param x - x location of source click
   * @param y - y location of source click
   * @return - the picked up note
   */
  NoteModel notePickUp(int x, int y);

  /**
   * Place a note back down after a move
   *
   * @param x - new x location of the note
   * @param y - new y location of the moved note
   */
  void notePutDown(int x, int y);
  //*******************************GUIViewPanel***********************************************

  NoteModel getNoteFromPositionSheet(int x, int y);
}
