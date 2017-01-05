/**
 * Created by ofishstein on 11/11/15.
 *
 * Console View of a piece of music.
 */
package cs3500.music.view;

import cs3500.music.model.ChordModel;
import cs3500.music.model.CompositionModel;
import cs3500.music.model.Note;
import cs3500.music.model.NoteModel;
import cs3500.music.model.Repeat;

/**
 * The implementation of {@link ConsoleView}
 */
public class ConsoleViewImpl implements ConsoleView {

  private CompositionModel comp;

  public ConsoleViewImpl() {
  }

  public void setComp(CompositionModel comp) {
    this.comp = comp;
  }

  public void render() {
    System.out.print(createView());
  }

  public String testView(CompositionModel comp) {
    this.comp = comp;
    return createView();
  }

  public String createView() {
    String octaveLine = " ";
    StringBuilder builder = new StringBuilder();

    int lineOffSet = String.valueOf(comp.songLength()).length();

    for (int i = 0; i < lineOffSet; i++) {
      octaveLine += " ";
    }

    for (int i = comp.getMinOctave(); i < comp.getMaxOctave() + 1; i++) {
      octaveLine += NoteModel.Pitch.enumToString(i);
    }

    char[][] outputArray = new char[comp.songLength() + 1][octaveLine.length() + 1];

    for (int i = 0; i < octaveLine.length(); i++) {
      outputArray[0][i] = octaveLine.charAt(i);
    }

    outputArray[0][octaveLine.length()] = '\n';


    for (int i = 1; i < comp.songLength() + 1; i++) {
      String num = String.valueOf(i - 1);
      int x = num.length() - 1;

      for (int j = 0; j < octaveLine.length(); j++) {
        outputArray[i][j] = ' ';
      }

      for (int j = (lineOffSet - 1); j >= (lineOffSet - num.length()); j--) {
        if (x >= 0) {
          outputArray[i][j] = num.charAt(x);
        }
        x--;
      }

      outputArray[i][octaveLine.length()] = '\n';
    }

    for (int i = 1; i < comp.songLength() + 1; i++) {
      for (ChordModel c : comp.getComposition()) {
        if (c.getStartTime() == (i - 1)) {
          for (NoteModel n : c.getNotes()) {
            if (n instanceof Note) {
              int printOffset;

              if (n.getOctave() == -1 || n.getOctave() == 10) {
                printOffset = n.getPitch().getAltPrintOffset();
              } else {
                printOffset = n.getPitch().getPrintOffset();
                if (comp.getMinOctave() == -1) {
                  printOffset += 12;
                }
              }

              int index = ((n.getOctave() - comp.getMinOctave()) * 36) + printOffset + lineOffSet;

              outputArray[i][index] = 'X';

              for (int j = 1; j < n.getLength(); j++) {
                if (outputArray[i + j][index] != 'X') {
                  outputArray[i + j][index] = '|';
                }
              }
            } else if (n instanceof Repeat){
              if (n.getInstrument() == 0){
                if (outputArray[i][lineOffSet] == 'R'){
                  outputArray[i][lineOffSet] = 'L';
                } else if (outputArray[i][lineOffSet] == 'E'){
                  outputArray[i][lineOffSet] = 'B';
                } else {
                  outputArray[i][lineOffSet] = 'S';
                }
              } else {
                if (outputArray[i][lineOffSet] == 'S'){
                  outputArray[i][lineOffSet] = 'L';
                } else if (outputArray[i][lineOffSet] == 'E'){
                  outputArray[i][lineOffSet] = 'D';
                } else {
                  outputArray[i][lineOffSet] = 'R';
                }
              }
            } else {
              if (outputArray[i][lineOffSet] == 'S'){
                outputArray[i][lineOffSet] = 'B';
              } else if (outputArray[i][lineOffSet] == 'R'){
                outputArray[i][lineOffSet] = 'D';
              } else {
                outputArray[i][lineOffSet] = 'E';
              }
            }
          }
        }
      }
    }

    for (int i = 0; i < comp.songLength() + 1; i++) {
      builder.append(outputArray[i]);
    }

    return builder.toString();
  }

}