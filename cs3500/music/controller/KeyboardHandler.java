package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kofi on 11/21/2015.
 *
 * Class to handle the keyboard
 */
public class KeyboardHandler implements KeyListener {
  Map<Integer, Runnable> keyMap = new HashMap<>();

  public KeyboardHandler(Map<Integer, Runnable> keyMap){
    this.keyMap = keyMap;
  }
  /**
   * Invoked when a key has been typed. See the class description for {@link KeyEvent} for a
   * definition of a key typed event.
   */
  @Override
  public void keyTyped(KeyEvent e) {
    //System.out.println(e.getKeyCode());
  }

  /**
   * Invoked when a key has been pressed. See the class description for {@link KeyEvent} for a
   * definition of a key pressed event.
   */
  @Override
  public void keyPressed(KeyEvent e) {
    //System.out.println(e.getKeyCode());
    if(keyMap.containsKey(e.getKeyCode())){
      keyMap.get(e.getKeyCode()).run();
    }
  }

  /**
   * Invoked when a key has been released. See the class description for {@link KeyEvent} for a
   * definition of a key released event.
   */
  @Override
  public void keyReleased(KeyEvent e) {
    //System.out.println(e.getKeyCode());
  }
}
