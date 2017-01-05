package cs3500.music.view;

import cs3500.music.model.CompositionModel;
import cs3500.music.view.View;

/**
 * Created by ofishstein on 11/12/15.
 *
 * Interface ConsoleView adds functionality to {@link View} for the console
 */
public interface ConsoleView extends View{

  /**
   * Prints the view to the console
   */
  void render();

  /**
   * Creates a test view of the console view
   *
   * @param comp the composition to use
   * @return a string of the console view
   */
  String testView(CompositionModel comp);

  /**
   * Creates a the console view
   *
   * @return a string of the console view
   */
  String createView();

  @Override
  void setComp(CompositionModel comp);


}
