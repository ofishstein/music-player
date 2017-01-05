package cs3500.music.view;

import cs3500.music.model.CompositionModel;

/**
 * Created by ofishstein on 11/12/15.
 *
 * Interface that all views conform to
 */
public interface View {

  /**
   * Sets the composition used for the view
   *
   * @param comp the composition to view
   */
  void setComp(CompositionModel comp);


  /**
   * Renders the view
   */
  void render();

}
