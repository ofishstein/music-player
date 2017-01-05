package cs3500.music.view.GuiView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

import cs3500.music.model.CompositionModel;
import cs3500.music.model.Ending;
import cs3500.music.model.Note;
import cs3500.music.model.NoteModel;
import cs3500.music.model.Repeat;

/**
 * Draws the view panel for the frame
 */
public class GuiPanelImpl extends JComponent implements GuiPanel {
  private final CompositionModel comp;
  private int numOfOctaves;
  private int startBeat;
  private int endBeat;
  private int lineBeat;

  //Dimensions of a measure box
  private final int measureWidth = 80;
  private final int measureHeight = 20;
  private final int xOffset = 30;
  private final int yOffset = 25;
  private final int octaveOffset = 11 * measureHeight;

  private List<String> NoteNames;
  private List<NoteBox> NoteBoxes;

  private Graphics2D g2d;

  public GuiPanelImpl(int startBeat, int endBeat, CompositionModel comp) {
    if (startBeat < 0 || endBeat < startBeat)
      throw new IllegalArgumentException();

    this.comp = Objects.requireNonNull(comp);
    this.numOfOctaves = comp.getMaxOctave() - comp.getMinOctave() + 1;
    this.startBeat = startBeat;
    this.endBeat = endBeat;

    this.makeBlankSheet();

    this.setPreferredSize(new Dimension(this.endBeat * 20 + xOffset + 50,
            this.numOfOctaves * octaveOffset + 200));
    this.setBackground(Color.LIGHT_GRAY);
  }

  public void updateNumOfOctaves() {
    this.numOfOctaves = comp.getMaxOctave() - comp.getMinOctave() + 1;
  }

  /**
   * Used to fill out the sheet of music with empty measures
   */
  private void makeBlankSheet() {
    NoteNames = new ArrayList<>(numOfOctaves * 12);

    int count = 0;
    for (int i = comp.getMaxOctave(); i >= comp.getMinOctave(); i--) {

      for (int noteValue = 11; noteValue >= 0; noteValue--) {
        NoteNames.add(count,
                (NoteModel.Pitch.getPitchFromValue(noteValue).toString() +
                        String.valueOf(i)).replace('s', '#'));
        count++;
      }
    }
  }

  //Added by Ollie
  public int getNumOfOctaves() {
    return this.numOfOctaves;
  }

  public void setStartBeat(int newStart) {
    this.startBeat = newStart;
  }

  public void setEndBeat(int newEnd) {
    this.endBeat = newEnd;
  }

  public void setLineBeat(int newBeat) {
    this.lineBeat = newBeat;
  }

  public int getStartBeat() {
    return this.startBeat;
  }

  public int getEndBeat() {
    return this.endBeat;
  }

  public List<NoteBox> getNotes() {
    return this.NoteBoxes;
  }

  //**************************************PAINT**********************************************
  private void paintLabels() {
    //Draw left and top labels
    for (int i = 0; i <= NoteNames.size() - 1; i++) {
      g2d.drawString(NoteNames.get(i), 0, yOffset + i * measureHeight + 15);
    }
  }

  private void paintRedLine() {
    g2d.setColor(Color.red);
    g2d.setStroke(new BasicStroke(3));
    g2d.drawLine(xOffset + lineBeat * 20,
            yOffset,
            xOffset + lineBeat * 20,
            yOffset + NoteNames.size() * 20);
  }

  private void paintNotesAtBeat(int beat) {
    //First print any note that exists during this beat
    for (NoteModel note : this.comp.notesDuringTime(beat)) {
      if (note instanceof Note) {
        int x = xOffset + beat * 20 - startBeat * 20;
        int y = yOffset + octaveOffset +
                Math.abs(note.getOctave() - comp.getMaxOctave())
                        * (octaveOffset + 20) - note.getPitch().getValue() * measureHeight;

        if (note.getInstrument() <= 8)
          g2d.setColor(new Color(0, 255, 128)); //Piano is green
        else if (note.getInstrument() >= 9 && note.getInstrument() <= 16)
          g2d.setColor(Color.GRAY);             //Percussion in gray
        else
          g2d.setColor(Color.BLUE);             //Default Blue

        g2d.fillRect(x, y, measureWidth / 4, measureHeight);
      }
    }

    //Second print any beat heads that exist during this beat
    for (NoteModel note : this.comp.startAtTime(beat).getNotes()) {
      int x = xOffset + beat * 20 - startBeat * 20;
      g2d.setColor(Color.BLACK);
      if (note instanceof Repeat) {
        //start repeat
        if (note.getInstrument() == 0) {
          g2d.setStroke(new BasicStroke(4));
          g2d.drawLine(x, yOffset, x, yOffset + NoteNames.size() * 20);
          g2d.setStroke(new BasicStroke(2));
          g2d.drawLine(x + 5, yOffset, x + 5, yOffset + NoteNames.size() * 20);

        }
        //end repeat
        else if (note.getInstrument() == 1) {
          g2d.setStroke(new BasicStroke(4));
          g2d.drawLine(x + 20, yOffset, x + 20, yOffset + NoteNames.size() * 20);
          g2d.setStroke(new BasicStroke(2));
          g2d.drawLine(x - 5 + 20, yOffset, x - 5 + 20, yOffset + NoteNames.size() * 20);
        }
      } else if (note instanceof Ending) {
        g2d.setColor(Color.ORANGE);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(x + 3, yOffset, x + 3, yOffset + NoteNames.size() * 20);

      } else if (note instanceof Note) {
        int x1 = xOffset + note.getStartTime() * 20 - startBeat * 20;
        int y = yOffset + octaveOffset +
                Math.abs(note.getOctave() - comp.getMaxOctave())
                        * (octaveOffset + 20) - note.getPitch().getValue() * measureHeight;

        g2d.setColor(Color.BLACK);
        NoteBoxes.add(new NoteBox(note, x1, y));
        g2d.fill(new NoteBox(note, x1, y).getHead());
      }
    }
  }

  private void paintMeasuresAtBeat(int beat) {
    //Print the beat label every fourth beat
    g2d.setColor(Color.BLACK);
    g2d.drawString(String.valueOf(beat), xOffset + (beat * 20) - startBeat * 20 + 5, yOffset - 8);
    g2d.setStroke(new BasicStroke(2));
    for (int i = 0; i <= NoteNames.size() - 1; i++) {
      g2d.drawRect(xOffset + beat * 20 - startBeat * 20,
              yOffset + i * measureHeight, measureWidth, measureHeight);
    }

    for (int i = 0; i < this.numOfOctaves; i++) {
      g2d.setStroke(new BasicStroke(4));
      int y = yOffset + i * (octaveOffset + 20);
      if (y > 25)
        g2d.drawLine(xOffset, y, xOffset + beat * 20 - startBeat * 20 + 80, y);
    }
  }

  //**************************************PAINT**********************************************

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g2d = (Graphics2D) g.create();

    this.makeBlankSheet();

    NoteBoxes = new ArrayList<>();
    paintLabels();

    for (int i = startBeat; i < endBeat; i++) {
      paintNotesAtBeat(i);
    }

    for (int i = startBeat; i < endBeat; i++) {
      if (i % 4 == 0)
        paintMeasuresAtBeat(i);
    }

    paintRedLine();
  }

  @Override
  public void repaint() {
    this.setSize(new Dimension(this.endBeat * 20 + xOffset + 50,
            this.numOfOctaves * octaveOffset + 300));
  }
}
