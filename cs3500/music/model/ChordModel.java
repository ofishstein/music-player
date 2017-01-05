package cs3500.music.model;

import java.util.Set;

/**
 * Created by ofishstein on 11/3/15.
 *
 * Re-created by Kofi on 11/9/15.
 */
public interface ChordModel {

  /**
   * Get's the amount of notes in a chord
   *
   * @return amount of notes in a chord
   */
  int numberOfNotes();

  /**
   * Finds the end time of the chord from the maximum duration added to the start time
   *
   * @return the end time of the chord
   */
  int getEndTime();

  /**
   * Gets the max octave of the chord
   *
   * @return max octave of the chord
   */
  int getMaxOctave();

  /**
   * Gets the min octave of the chord
   *
   * @return min octave of the chord
   */
  int getMinOctave();

  /**
   * Get's the start time of the chord
   *
   * @return the start time of the chord
   */
  int getStartTime();

  /**
   * Gets all of the notes of the chord
   *
   * @return all of the notes
   */
  Set<NoteModel> getNotes();

  /**
   * Sets the start time of a chord and all of the notes
   *
   * @param newStartTime new start time
   * @throws  IllegalArgumentException
   */
  void setStartTime(int newStartTime);

  /**
   * Adds a new chord to the existing chord
   *
   * @param newChord chord to be added to existing chord
   * @throws IllegalArgumentException if the start times of the new and old chord don't match
   */
  void append(ChordModel newChord);

  /**
   * Adds a new note to the chord
   *
   * @param newNote note to be added
   * @throws IllegalArgumentException if the start time of the note is not the same as
   *         the start time of the chord
   */
  void addNote(NoteModel newNote);

  /**
   * Deletes a note from a chord
   *
   * @param oldNote note to be removed
   */
  void removeNote(NoteModel oldNote);
}
