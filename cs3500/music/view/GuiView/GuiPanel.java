package cs3500.music.view.GuiView;

import java.util.List;

/**
 * Created by Kofi on 11/25/2015.
 *
 * Gui Panel interface. this is where we will draw all of our GUI components
 */
public interface GuiPanel {
  /**
   * Set the start beat of the panel
   *
   * @param newStart - first visible beat of this view
   */
  void setStartBeat(int newStart);

  /**
   * Set the send beat of the panel
   *
   * @param newEnd - last visible beat of this view
   */
  void setEndBeat(int newEnd);

  /**
   * Set the beat of the red line, a.k.a. current beat NOTE: the red line "beat number" raps
   * around to 0 on each new page, does not represent current beat
   *
   * @param newBead - beat location of the red line
   */
  void setLineBeat(int newBead);

  /**
   * Returns the starting beat of this page view
   *
   * @return - the starting beat
   */
  int getStartBeat();

  /**
   * Returns the ending beat of this page view
   *
   * @return - the ending beat
   */
  int getEndBeat();

  /**
   * Returns the VISUAL representation of notes in this page view NOTE: this is the visual
   * representation, not the model representation, this data relates to the note's locations on
   * the screen and is refreshed on each "page turn"
   *
   * @return - visual representation of notes in view
   */
  List<NoteBox> getNotes();


  //Added by Ollie
  int getNumOfOctaves();
}

