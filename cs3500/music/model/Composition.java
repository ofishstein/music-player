package cs3500.music.model;

import java.util.HashSet;
import java.util.Set;

import cs3500.music.util.*;

/**
 * An implementation of a Composition.
 * Created by ofishstein and Kofi on 11/12/15.
 */
public class Composition implements CompositionModel {
  private Set<ChordModel> composition;
  private int tempo;
  private int maxOctave;
  private int minOctave;
  private int songLength;

  /**
   * Constructs a music editor from a composition
   *
   * INVARIANTS - tempo is greater than 0
   *
   * @param composition song to edit
   * @throws IllegalArgumentException if tempo is 0 or smaller
   */
  public Composition(Set<ChordModel> composition, int tempo) {
    if (tempo <= 0) {
      throw new IllegalArgumentException();
    }

    this.composition = composition;
    this.tempo = tempo;
    this.maxOctave = calcMaxOctave();
    this.minOctave = calcMinOctave();
    this.songLength = calcSongLength();
  }

  public static final class Builder implements CompositionBuilder<Composition>{
    private int tempo;
    private Set<ChordModel> comp;

    public Builder(){
      this.tempo = 0;
      this.comp = new HashSet<>();
    }

    @Override
    public Composition build() {
      return new Composition(this.comp, this.tempo);
    }

    @Override
    public CompositionBuilder<Composition> setTempo(int tempo) {
      this.tempo = tempo;

      return this;
    }

    @Override
    public CompositionBuilder<Composition> addNote(int start, int end, int instrument,
                                                   int pitch, int volume) {
      NoteModel.Pitch pitchEnum = NoteModel.Pitch.getPitchFromValue(pitch % 12);
      boolean added = false;
      int length = end - start;
      int octave = (pitch / 12) - 1;

      NoteModel newNote = new Note(pitchEnum, octave, length, start, volume, instrument);


      for (ChordModel c : this.comp) {
        if (newNote.getStartTime() == c.getStartTime()) {
          c.addNote(newNote);
          added = true;
        }
      }

      if (!added) {
        Set<NoteModel> newNotes = new HashSet<>();
        newNotes.add(newNote);
        ChordModel newChord = new Chord(newNote.getStartTime(), newNotes);
        this.comp.add(newChord);
      }

      return this;
    }

    public CompositionBuilder<Composition> addRepeat(int start, int type){

      NoteModel newRepeat = new Repeat(start, type);
      boolean added = false;

      for (ChordModel c : this.comp) {
        if (newRepeat.getStartTime() == c.getStartTime()){
          c.addNote(newRepeat);
          added = true;
        }
      }

      if (!added){
        Set<NoteModel> newNotes = new HashSet<>();
        newNotes.add(newRepeat);
        ChordModel newChord = new Chord(newRepeat.getStartTime(), newNotes);
        this.comp.add(newChord);
      }

      return this;
    }

    public  CompositionBuilder<Composition> addEnding(int start){
      NoteModel newEnding = new Ending(start);
      boolean added = false;

      for (ChordModel c : this.comp) {
        if (newEnding.getStartTime() == c.getStartTime()){
          c.addNote(newEnding);
          added = true;
        }
      }

      if (!added){
        Set<NoteModel> newNotes = new HashSet<>();
        newNotes.add(newEnding);
        ChordModel newChord = new Chord(newEnding.getStartTime(), newNotes);
        this.comp.add(newChord);
      }

      return this;
    }
  }

  public int getTempo() {
    return this.tempo;
  }

  private int calcMaxOctave(){
    int maxOctave = -1;

    for (ChordModel c : this.composition) {
      if (c.getMaxOctave() > maxOctave)
        maxOctave = c.getMaxOctave();
    }

    return maxOctave;
  }

  public void reCalcMaxOctave(){
    this.maxOctave = calcMaxOctave();
  }

  public int getMaxOctave() {
    return this.maxOctave;
  }

  public void addMaxOctave(){
    if (this.maxOctave < 9 ){
      this.maxOctave++;
    }
  }

  private int calcMinOctave(){
    int minOctave = 9;

    for (ChordModel c : this.composition) {
      if (c.getMinOctave() < minOctave)
        minOctave = c.getMinOctave();
    }

    return minOctave;
  }

  public void reCalcMinOctave(){
    this.minOctave = calcMinOctave();
  }

  public int getMinOctave() {
   return this.minOctave;
  }

  public void subMinOctave(){
    if (this.minOctave > -1){
      this.minOctave--;
    }
  }

  private int calcSongLength(){
    int songLength = 0;

    if (this.composition.size() > 0) {
      for (ChordModel c : this.composition) {
        if (c.getEndTime() > songLength) {
          songLength = c.getEndTime();
        }
      }
    }

    return songLength;
  }

  public void addSongLength(){
    this.songLength = this.songLength() + 4;
  }

  public void subSongLength(){
    this.songLength = calcSongLength();
  }

  public int songLength() {
    return this.songLength;
  }

  public Set<ChordModel> getComposition() {
    return this.composition;
  }

  public int notesInSong() {
    int count = 0;
    for (ChordModel c : this.composition) {
      count += c.numberOfNotes();
    }
    return count;
  }

  public int chordsInSong() {
    int count = 0;
    for (ChordModel c : this.composition) {
      count++;
    }
    return count;
  }

  public ChordModel startAtTime(int time) {
    if (time > this.songLength())
      throw new IllegalArgumentException();

    for (ChordModel c : this.composition) {
      if (c.getStartTime() == time) {
        return c;
      }
    }

    return new Chord(time, new HashSet<>());
  }

  // Added method to return all notes playing at certain time based on the self-eval from HW05
  public Set<NoteModel> notesDuringTime(int time) {
    Set<NoteModel> returnValue = new HashSet<>();
    for (ChordModel c : this.composition){
      for (NoteModel n : c.getNotes()){
        if ((n.getStartTime() <= time) && (n.getLength() + n.getStartTime()) > time){
          returnValue.add(n);
        }
      }
    }
    return returnValue;
  }

  public void setTempo(int newTempo) {
    if (tempo <= 0) {
      throw new IllegalArgumentException();
    }

    this.tempo = newTempo;
  }

  public void addNote(NoteModel newNote) {
    boolean added = false;
    for (ChordModel c : this.composition) {
      if (newNote.getStartTime() == c.getStartTime()) {
        c.addNote(newNote);
        added = true;
      }
    }

    if (!added) {
      Set<NoteModel> newNotes = new HashSet<>();
      newNotes.add(newNote);
      ChordModel newChord = new Chord(newNote.getStartTime(), newNotes);
      this.composition.add(newChord);
    }
  }

  public void addChord(ChordModel newChord) {
    boolean added = false;
    for (ChordModel c : this.composition) {
      if (c.getStartTime() == newChord.getStartTime()) {
        c.append(newChord);
        added = true;
      }
    }

    if (!added)
      this.composition.add(newChord);
  }

  public void addSongToSong(Set<ChordModel> newSong) {
    for (ChordModel c : newSong) {
      this.addChord(c);
    }
  }

  public void addSongToEnd(Set<ChordModel> newSong) {
    int length = this.songLength();
    for (ChordModel c : newSong) {
      c.setStartTime(c.getStartTime() + length);
      this.addChord(c);
    }
  }

  public void removeNote(NoteModel oldNote) {
    for (ChordModel c : this.composition) {
      if (c.getNotes().contains(oldNote))
        c.removeNote(oldNote);
    }
  }

  public void editNotePitch(NoteModel currentNote, NoteModel.Pitch newPitch) {
    currentNote.setPitch(newPitch);
  }

  public void editNoteLength(NoteModel currentNote, int newLength) {
    currentNote.setLength(newLength);
  }

  public void editNoteStartTime(NoteModel currentNote, int newStartTime) {
    this.removeNote(currentNote);
    currentNote.setStartTime(newStartTime);
    this.addNote(currentNote);
  }

  @Override
  // Has been changed to reflect needing support for -1 and 10 as octaves
  // And supports right justified line numbers with varied sizes
  public String toString() {
    String octaveLine = " ";
    String result = "";
    int lineOffSet = String.valueOf(songLength()).length();

    for (int i = 0; i < lineOffSet; i++){
      octaveLine += " ";
    }

    for (int i = getMinOctave(); i < getMaxOctave() + 1; i++) {
      octaveLine += NoteModel.Pitch.enumToString(i);
    }

    char[][] outputArray = new char[songLength() + 1][octaveLine.length() + 1];

    for (int i = 0; i < octaveLine.length(); i++) {
      outputArray[0][i] = octaveLine.charAt(i);
    }

    outputArray[0][octaveLine.length()] = '\n';


    for (int i = 1; i < songLength() + 1; i++) {
      String num = String.valueOf(i - 1);
      int x = num.length() - 1;

      for (int j = 0; j < octaveLine.length(); j++) {
        outputArray[i][j] = ' ';
      }

      for (int j = (lineOffSet - 1); j >= (lineOffSet - num.length()); j--) {
        if (x >= 0){
          outputArray[i][j] = num.charAt(x);
        }
        x--;
      }

      outputArray[i][octaveLine.length()] = '\n';
    }

    for (int i = 0; i < songLength() + 1; i++) {
      for (ChordModel c : getComposition()) {
        if (c.getStartTime() == (i - 1)) {
          for (NoteModel n : c.getNotes()) {
            int printOffset;

            if (n.getOctave() == -1 || n.getOctave() == 10) {
              printOffset = n.getPitch().getAltPrintOffset();
            } else {
              printOffset = n.getPitch().getPrintOffset();
              if (getMinOctave() == -1) {
                printOffset += 12;
              }
            }

            int index = ((n.getOctave() - getMinOctave()) * 36) + printOffset + lineOffSet;

            outputArray[i][index] = 'X';

            for (int j = 1; j < n.getLength(); j++) {
              if (outputArray[i + j][index] != 'X') {
                outputArray[i + j][index] = '|';
              }
            }
          }
        }
      }
    }

    for (int i = 0; i < songLength() + 1; i++) {
      for (int j = 0; j < octaveLine.length() + 1; j++) {
        result += outputArray[i][j];
      }
    }

    return result;
  }
}
