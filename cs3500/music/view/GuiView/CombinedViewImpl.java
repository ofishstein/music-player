package cs3500.music.view.GuiView;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.model.CompositionModel;
import cs3500.music.view.MidiView;

/**
 * Created by Kofi on 11/22/2015.
 *
 * Implementation of combined GUI and Midi view
 */
public class CombinedViewImpl implements GUIView {
  private CompositionModel comp;
  private MidiView midiView;
  private GUIView guiView;

  public CombinedViewImpl(MidiView midiView, GUIView guiView) {
    this.midiView = midiView;
    this.guiView = guiView;
  }

  public void render() {
    this.guiView.render();
  }

  public GUIView getGuiView() {
    return this.guiView;
  }

  /**
   * Sets the composition used for the view and implements it
   *
   * @param comp the composition to view
   */
  public void setComp(CompositionModel comp) {
    this.comp = comp;
    this.midiView.setComp(this.comp);
    this.guiView.setComp(this.comp);
  }

  public void setKeyHandler(KeyListener handler) {
    this.guiView.setKeyHandler(handler);
  }

  public void setMouseHandler(MouseListener handler) {
    this.guiView.setMouseHandler(handler);
  }

  public CompositionModel getComp() {
    return this.comp;
  }

  public int getStartBeat(){
    return guiView.getStartBeat();
  }

  public int getEndBeat(){
    return guiView.getEndBeat();
  }

  public GuiPanelImpl getMusicSheet(){
    return guiView.getMusicSheet();
  }

  public void playNotesAtBeat(int currentBeat) {
    try {
      midiView.playNotesAtBeat(comp.startAtTime(currentBeat));
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
    guiView.playNotesAtBeat(currentBeat);
  }

  public void Repaint() {
    this.guiView.Repaint();
  }

  public int getRepeatStart() {
    return guiView.getRepeatStart();
  }

  public int getRepeatEnd() {
    return guiView.getRepeatEnd();
  }

  public boolean getRepeat() {
    return guiView.getRepeat();
  }

  public void setRepeat(boolean repeat){
    this.guiView.setRepeat(repeat);
  }

  public int getEndingStart(){
    return guiView.getEndingStart();
  }
}

