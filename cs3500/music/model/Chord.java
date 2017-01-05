package cs3500.music.model;

import java.util.Objects;
import java.util.Set;

/**
 * Created by Kofi on 11/1/2015.
 *
 * Representing a musical cord
 *
 * Class Invariants: - A chord's start time is always greater than 0 - A chord's notes can be
 * empty, but never null. - The start times of all notes in a chord must match the chord's start
 * time
 */
public final class Chord implements ChordModel {
  //Fields of a chord
  private Set<NoteModel> Notes;
  private int chordStartTime;

  /**
   * Constructs a Chord object
   *
   * @param startTime     - start of the chord All notes within a chord share a start time
   * @param startingNotes - Array list of notes in the cord
   */
  public Chord(int startTime, Set<NoteModel> startingNotes) {
    if (startTime < 0)
      throw new IllegalArgumentException();
    for (NoteModel n : startingNotes) {
      if (n.getStartTime() != startTime)
        throw new IllegalArgumentException();
    }

    this.chordStartTime = startTime;
    this.Notes = Objects.requireNonNull(startingNotes);
  }

  public int numberOfNotes() {
    return Notes.size();
  }

  public int getEndTime() {
    int maxLength = 0;
    for (NoteModel n : this.getNotes()) {
      if (n.getLength() > maxLength) {
        maxLength = n.getLength();
      }
    }
    return maxLength + this.chordStartTime;
  }

  public int getMaxOctave() {
    int maxOctave = -1;

    for (NoteModel n : this.getNotes()) {
      if (n instanceof Note && n.getOctave() > maxOctave)
        maxOctave = n.getOctave();
    }

    return maxOctave;
  }

  public int getMinOctave() {
    int minOctave = 9;

    for (NoteModel n : this.getNotes()) {
      if (n instanceof Note && n.getOctave() < minOctave)
        minOctave = n.getOctave();
    }

    return minOctave;
  }

  public int getStartTime() {
    return this.chordStartTime;
  }

  public Set<NoteModel> getNotes() {
    return this.Notes;
  }

  public void setStartTime(int newStartTime) {
    if (newStartTime < 0)
      throw new IllegalArgumentException();

    this.chordStartTime = newStartTime;

    for (NoteModel n : this.Notes) {
      n.setStartTime(newStartTime);
    }
  }

  public void append(ChordModel newChord) {
    if (newChord.getStartTime() != this.chordStartTime)
      throw new IllegalArgumentException();

    this.Notes.addAll(newChord.getNotes());
  }

  public void addNote(NoteModel note) {
    if (note.getStartTime() != this.chordStartTime)
      throw new IllegalArgumentException();

    this.Notes.add(note);
  }

  public void removeNote(NoteModel note) {
    if (Notes.contains(note))
      Notes.remove(note);
  }
}
