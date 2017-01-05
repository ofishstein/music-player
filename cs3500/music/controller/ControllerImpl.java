package cs3500.music.controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import cs3500.music.model.CompositionModel;
import cs3500.music.model.Ending;
import cs3500.music.model.Note;
import cs3500.music.model.NoteModel;
import cs3500.music.model.Repeat;
import cs3500.music.view.GuiView.GUIView;
import cs3500.music.view.GuiView.GuiPanel;
import cs3500.music.view.GuiView.NoteBox;

/**
 * Created by Kofi on 11/22/2015.
 *
 * ControllerImpl class
 */
public class ControllerImpl implements Controller {
  private final GUIView view;
  private int currentBeat;
  private boolean spacePressed;
  private boolean playingp;
  private Timer timer;
  private final int windowWidth = 11 * 4;
  private final CompositionModel comp;

  public ControllerImpl(GUIView view) {
    this.view = view;
    this.currentBeat = 0;
    this.spacePressed = false;
    this.comp = view.getComp();

    final MouseHandler mouseListener = new MouseHandler(this, this.comp);

    Map<Integer, Runnable> keyMap = new HashMap<>();
    //Nothing
    keyMap.put(KeyEvent.VK_K, () -> System.out.println("HELLO"));
    //Play/Pause
    keyMap.put(KeyEvent.VK_SPACE, this::playSongOnKey);
    //Scroll the the beginning
    keyMap.put(KeyEvent.VK_HOME, () -> {
      if (!this.playingp) {
        scrollToBeginning();
        this.currentBeat = 0;
      }
    });
    keyMap.put(KeyEvent.VK_O, () -> {
      if (!this.playingp) {
        scrollToBeginning();
        this.currentBeat = 0;
      }
    });

    //Scroll to end
    keyMap.put(KeyEvent.VK_END, () -> {
      if (!this.playingp) {
        this.currentBeat = this.comp.songLength() - this.comp.songLength() % this.windowWidth;
        scrollToEnd();

      }
    });
    keyMap.put(KeyEvent.VK_P, () -> {
      if (!this.playingp) {
        this.currentBeat = this.comp.songLength() - this.comp.songLength() % this.windowWidth;
        scrollToEnd();

      }
    });

    //Scroll with Red Line to left
    keyMap.put(KeyEvent.VK_LEFT, () -> {
      if (!this.playingp) {
        if (this.currentBeat > 0) {
          this.currentBeat--;
          scroll(this.currentBeat);
        }
      }
    });

    //Scroll with Red Line to right
    keyMap.put(KeyEvent.VK_RIGHT, () -> {
      if (!playingp) {
        if (this.currentBeat <= this.comp.songLength()) {
          this.currentBeat++;
          scroll(this.currentBeat);
        }
      }
    });

    //Exit
    keyMap.put(KeyEvent.VK_ESCAPE, () -> {
      System.out.println("Goodbye!");
      System.exit(0);
    });

    //Add a high octave
    keyMap.put(KeyEvent.VK_UP, () -> {
      if (!playingp) {
        this.comp.addMaxOctave();
        this.view.getMusicSheet().updateNumOfOctaves();
        this.view.getMusicSheet().revalidate();
        this.view.getMusicSheet().repaint();
        this.view.Repaint();
      }
    });

    //Add a low octave
    keyMap.put(KeyEvent.VK_DOWN, () -> {
      if (!playingp) {
        this.comp.subMinOctave();
        this.view.getMusicSheet().updateNumOfOctaves();
        this.view.getMusicSheet().revalidate();
        this.view.getMusicSheet().repaint();
        this.view.Repaint();
      }
    });

    //Add a measure to the end of the song
    keyMap.put(KeyEvent.VK_A, () -> {
      if (!playingp &&
              currentBeat >=
                      (this.comp.songLength() - this.comp.songLength() % this.windowWidth)) {

        this.comp.addSongLength();
        scrollToEnd();
      }
    });

    //Remove extra measures at end
    keyMap.put(KeyEvent.VK_S, () -> {
      if (!playingp) {
        this.comp.subSongLength();
        scrollToEnd();
      }
    });

    //Remove extra octaves
    keyMap.put(KeyEvent.VK_R, () -> {
      if (!playingp) {
        this.comp.reCalcMaxOctave();
        this.comp.reCalcMinOctave();
        this.view.getMusicSheet().updateNumOfOctaves();
        this.view.getMusicSheet().revalidate();
        this.view.Repaint();
      }
    });

    //Add/remove start-repeat
    keyMap.put(KeyEvent.VK_Q, () -> {
      boolean remove = false;
      if (!playingp) {
        Point mouse = mouseListener.getLocation();
        NoteModel dummy = getNoteFromPosition((int) mouse.getX(), (int) mouse.getY());
        int beat = dummy.getStartTime();

        try {
          for (NoteModel note : this.comp.startAtTime(beat).getNotes()) {
            if (note instanceof Repeat && note.getInstrument() == 0) {
              this.comp.removeNote(note);
              remove = true;
            }
          }
          if (!remove) {
            NoteModel addStartRepeat = new Repeat(beat, 0);
            this.comp.addNote(addStartRepeat);
          }
        } catch (Exception ignored) {
        }
        this.view.Repaint();
      }
    });

    //Add/remove end repeat
    keyMap.put(KeyEvent.VK_W, () -> {
      boolean remove = false;
      if (!playingp) {
        Point mouse = mouseListener.getLocation();
        NoteModel dummy = getNoteFromPosition((int) mouse.getX(), (int) mouse.getY());
        int beat = dummy.getStartTime();

        try {
          for (NoteModel note : this.comp.startAtTime(beat).getNotes()) {
            if (note instanceof Repeat && note.getInstrument() == 1) {
              this.comp.removeNote(note);
              remove = true;
            }
          }
          if (!remove) {
            NoteModel addStartRepeat = new Repeat(beat, 1);
            this.comp.addNote(addStartRepeat);
          }
        } catch (Exception ignored) {
        }
        this.view.Repaint();
      }
    });

    //Add/remove Ending marker
    keyMap.put(KeyEvent.VK_E, () -> {
      boolean remove = false;
      if (!playingp) {
        Point mouse = mouseListener.getLocation();
        NoteModel dummy = getNoteFromPosition((int) mouse.getX(), (int) mouse.getY());
        int beat = dummy.getStartTime();

        try {
          for (NoteModel note : this.comp.startAtTime(beat).getNotes()) {
            if (note instanceof Ending) {
              this.comp.removeNote(note);
              remove = true;
            }
          }
          if (!remove) {
            NoteModel addEnd = new Ending(beat);
            this.comp.addNote(addEnd);
          }
        } catch (Exception ignored) {
        }
        this.view.Repaint();
      }
    });

    final KeyListener keyHandler = new KeyboardHandler(keyMap);

    this.view.setKeyHandler(keyHandler);
    this.view.setMouseHandler(mouseListener);
  }

  public void render() {
    this.view.render();
    this.timer = new Timer(this.comp.getTempo() / 1000, new PlaySong());
  }

  public int getCurrentBeat() {
    return this.currentBeat;
  }

  //Added by Ollie
  public GUIView getView() {
    return this.view;
  }

  //*******************************SPACE BAR***********************************************

  public void playSongOnKey() {
    if (!this.spacePressed) {
      if (this.currentBeat > this.comp.songLength()) {
        this.currentBeat = 0;
        scrollToBeginning();
        this.playingp = false;
      } else {
        this.spacePressed = true;
        this.playingp = true;
        this.timer.start();
      }
    } else {
      this.spacePressed = false;
      this.playingp = false;
      this.timer.stop();
    }
  }

  public class PlaySong implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      view.playNotesAtBeat(currentBeat);

      if (view.getRepeat() && currentBeat == view.getRepeatEnd()) {
        currentBeat = view.getRepeatStart();
      } else {
        if (view.getRepeat() && currentBeat == view.getEndingStart()) {
          currentBeat = view.getRepeatEnd();
          view.setRepeat(false);
        }
        currentBeat++;
      }

      if (currentBeat > comp.songLength()) {
        spacePressed = false;
        playingp = false;
        timer.stop();
      }
    }
  }

  //*******************************GUIView***********************************************

  public NoteModel getNoteFromPosition(int x, int y) {
    return getNoteFromPositionSheet(x, y);
  }

  public void mutateNote(int x, int y, int source) {
    boolean deleteNote = false;
    GuiPanel musicSheet = this.view.getMusicSheet();
    for (NoteBox note : musicSheet.getNotes()) {
      if (source == 1) {
        if (note.headContains(x, y)) {
          this.comp.removeNote(note.getNote());
          deleteNote = true;
        } else if (note.tailContains(x, y)) {
          this.comp.editNoteLength(note.getNote(), note.getNote().getLength() + 1);
          deleteNote = true;
        }
      } else if (source == 3) {
        if (note.headContains(x, y)) {
          note.getNote().setLength(note.getNote().getLength() + 1);
        } else if (note.tailContains(x, y)) {
          this.comp.editNoteLength(note.getNote(), note.getNote().getLength() - 1);
        }
      }
    }

    if (!deleteNote && source == 1) {
      if (getNoteFromPositionSheet(x, y) != null) {
        this.comp.addNote(getNoteFromPositionSheet(x, y));
      }
    }

    this.view.Repaint();
  }

  public NoteModel notePickUp(int x, int y) {
    for (NoteBox note : this.view.getMusicSheet().getNotes()) {
      if (note.headContains(x, y)) {
        return note.getNote();
      }
    }
    return null;
  }

  public void notePutDown(int x, int y) {
    NoteModel dummyNote = getNoteFromPositionSheet(x, y);
    this.comp.addNote(dummyNote);
    this.view.Repaint();
  }

  public void scroll(int scrollPosition) {
    if (scrollPosition >= 0 && scrollPosition <= this.comp.songLength())
      this.view.getGuiView().playNotesAtBeat(scrollPosition);
  }

  public void scrollToBeginning() {
    this.currentBeat = 0;
    GuiPanel musicSheet = this.view.getMusicSheet();
    musicSheet.setStartBeat(0);
    musicSheet.setEndBeat(windowWidth);
    musicSheet.setLineBeat(0);
    this.view.Repaint();
  }

  public void scrollToEnd() {
    GuiPanel musicSheet = this.view.getMusicSheet();
    int newStart = this.comp.songLength() - this.comp.songLength() % windowWidth;
    this.currentBeat = newStart;
    musicSheet.setEndBeat(this.view.getComp().songLength());
    musicSheet.setStartBeat(newStart);
    musicSheet.setLineBeat(0);
    this.view.Repaint();
  }

  //*******************************GUIViewPanel***********************************************

  public NoteModel getNoteFromPositionSheet(int x, int y) {
    GuiPanel musicSheet = this.view.getMusicSheet();
    int xOffset = 30;
    int yOffset = 25;
    if ((y - yOffset) > 0 && (y - yOffset) < (12 * 20 * musicSheet.getNumOfOctaves())
            && (x - xOffset) > 0 && (x - xOffset) < musicSheet.getEndBeat() * 20) {

      int pitchValue = 11 - (((y - yOffset) / 20) % 12);
      NoteModel.Pitch pitch = NoteModel.Pitch.getPitchFromValue(pitchValue);

      int octave = this.comp.getMaxOctave() -
              (((y - yOffset) / 240) % musicSheet.getNumOfOctaves());

      int beat = (x - xOffset + musicSheet.getStartBeat() * 20) / 20;

      return new Note(pitch, octave, 2, beat, 64, 1);
    }

    return null;
  }

}
