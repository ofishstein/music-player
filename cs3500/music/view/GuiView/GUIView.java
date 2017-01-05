package cs3500.music.view.GuiView;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import cs3500.music.model.CompositionModel;
import cs3500.music.view.View;

/**
 * Created by Kofi on 11/12/2015.
 *
 * GUI View interface
 */
public interface GUIView extends View {

  /**
   * Sets the composition for the view to the given model
   *
   * @param comp - The desired composition
   */
  void setComp(CompositionModel comp);

  /**
   * Sets the keyboard handler
   *
   * @param handler - the new keyboard handler
   */
  void setKeyHandler(KeyListener handler);

  /**
   * Sets the mouse handler
   *
   * @param handler - the new mouse handler
   */
  void setMouseHandler(MouseListener handler);

  /**
   * Returns the views model for mutation
   *
   * @return - view.comp;
   */
  CompositionModel getComp();

  /**
   * Gets the guiview from a guiview
   *
   * @return the gui view
   */
  GUIView getGuiView();

  /**
   * Get the start beat of this frame being displayed
   *
   * @return - the start beat
   */
  int getStartBeat();

  /**
   * Get the last beat being displayed on screen
   *
   * @return - the last beat
   */
  int getEndBeat();

  /**
   * Updates the red line
   *
   * @param currentBeat - the beat the line is at
   */
  void playNotesAtBeat(int currentBeat);

  /**
   * A public repaint method
   */
  void Repaint();

  /**
   * Returns the music sheet.
   *
   * @return musicSheet - the GuiPanel for the Gui
   */
  GuiPanelImpl getMusicSheet();

  /**
   * Returns the repeat's start beat
   *
   * @return the repeat's start beat
   */
  public int getRepeatStart();

  /**
   * Returns the repeat's end beat
   *
   * @return the repeat's end beat
   */
  public int getRepeatEnd();

  /**
   * Returns the boolean for whether or not a piece has been repeated
   *
   * @return the boolean for whether or not a piece has been repeated
   */
  public boolean getRepeat();

  /**
   * Sets the boolean for whether or not a piece has been repeated yet
   *
   * @param repeat - boolean value whether or not a piece has been repeated
   */
  void setRepeat(boolean repeat);

  /**
   * Returns the start beat for an ending
   *
   * @return the ending start beat
   */
  int getEndingStart();
}
