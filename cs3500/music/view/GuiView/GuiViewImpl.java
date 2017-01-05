package cs3500.music.view.GuiView;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

import cs3500.music.model.CompositionModel;
import cs3500.music.model.Ending;
import cs3500.music.model.NoteModel;
import cs3500.music.model.Repeat;

/**
 * Main GUI Frame that displays the GuiPanelImpl sub-view
 */
public class GuiViewImpl extends JFrame implements GUIView {
  private CompositionModel comp;
  private GuiPanelImpl musicSheet;

  //for repeating and endings
  private boolean repeat;
  private int repeatStart;
  private int repeatEnd;
  private int endingStart;
  private boolean startRepeat;

  private final int windowWidth = 11 * 4;

  public GuiViewImpl() throws HeadlessException {
    this.setTitle("Music Editor");

    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setLocation(150, 0);
    this.setLayout(new BorderLayout());
    this.setPreferredSize(new Dimension(1000, 750));
    this.setResizable(false);
  }

  public void setComp(CompositionModel comp) {
    this.comp = comp;
    this.musicSheet = new GuiPanelImpl(0, windowWidth, this.comp);
  }

  //Added by Ollie
  public GuiPanelImpl getMusicSheet() {
    return this.musicSheet;
  }

  public void setKeyHandler(KeyListener handler) {
    this.addKeyListener(handler);
  }

  public void setMouseHandler(MouseListener handler) {
    if (this.musicSheet != null) {
      this.musicSheet.addMouseListener(handler);
      this.musicSheet.addMouseMotionListener((MouseMotionListener) handler);
    }
  }

  public GUIView getGuiView() {
    return this;
  }

  public CompositionModel getComp() {
    return this.comp;
  }

  public int getStartBeat() {
    return musicSheet.getStartBeat();
  }

  public int getEndBeat() {
    return musicSheet.getEndBeat();
  }

  //(red line)
  public void playNotesAtBeat(int currentBeat) {
    if (currentBeat <= musicSheet.getEndBeat() && currentBeat >= musicSheet.getStartBeat()) {
      musicSheet.setLineBeat(currentBeat % windowWidth);
    } else if (currentBeat < musicSheet.getStartBeat()) {
      int newStart = musicSheet.getStartBeat() - windowWidth;
      int newEnd = newStart + windowWidth;
      musicSheet.setStartBeat(newStart);
      musicSheet.setEndBeat(newEnd);
    } else if ((currentBeat + windowWidth) < this.comp.songLength()) {
      int newStart = musicSheet.getEndBeat();
      int newEnd = newStart + windowWidth;
      musicSheet.setStartBeat(newStart);
      musicSheet.setEndBeat(newEnd);
    } else {
      int newStart = musicSheet.getEndBeat();
      musicSheet.setEndBeat(this.comp.songLength());
      musicSheet.setStartBeat(newStart);
      musicSheet.setLineBeat(0);
    }

    if (currentBeat != musicSheet.getStartBeat() && currentBeat % windowWidth == 0) {
      musicSheet.setLineBeat(windowWidth);
    } else
      musicSheet.setLineBeat(currentBeat % windowWidth);

    for (NoteModel n : comp.startAtTime(currentBeat).getNotes()){
      if (n instanceof Repeat){
        if (n.getInstrument() == 0) {
          this.repeatStart = n.getStartTime();
          this.startRepeat = true;

        } else {
          if (repeat) {
            this.repeat = false;
            if (startRepeat){
              this.startRepeat = false;
              this.repeatStart = 0;
            }
          } else {
            this.repeat = true;
            this.repeatEnd = n.getStartTime();
          }
        }
      } else if (n instanceof Ending){
        this.endingStart = n.getStartTime();
      }
    }

    this.repaint();
  }

  public void render() {
    JScrollPane scrollPane = new JScrollPane(musicSheet,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setLocation(50, 50);

    scrollPane.setPreferredSize(new Dimension(1000, 750));

    this.add(scrollPane, BorderLayout.CENTER);
    this.setVisible(true);
    this.setSize(1000, 750);
  }

  public void Repaint() {
    this.setPreferredSize(musicSheet.getSize());
    musicSheet.setPreferredSize(musicSheet.getSize());
    musicSheet.revalidate();
    musicSheet.repaint();
    this.revalidate();
    this.repaint();
  }

  public int getRepeatStart() {
    return this.repeatStart;
  }

  public int getRepeatEnd() {
    return this.repeatEnd;
  }

  public boolean getRepeat() {
    return this.repeat;
  }

  public void setRepeat(boolean repeat){
    this.repeat = repeat;
  }

  public int getEndingStart(){
    return this.endingStart;
  }

}
