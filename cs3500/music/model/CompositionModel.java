package cs3500.music.model;

import java.util.Set;

/**
 * Music Editor Model Interface
 *
 * Created by ofishstein on 11/2/15.
 *
 * Re-created by Kofi on 11/9/15.
 *
 * An interface for a music player/editor.
 * Represents notes C-1 through G9 (128 notes).
 *
 * A musical song is comprised of a series of cords. Each cord contains a series of notes that
 * start at the same time
 *
 * By definition, all notes in a cord start at the same time therefor they are overlapping, but
 * they do not share end times.
 *
 * Cords can over lap within a song. Chord 2 can begin before the end time of the last note in
 * Chord 1.
 */
public interface CompositionModel {
  //--------------------------------OBSERVING------------------------------
  /**
   * Allows the user to get the tempo
   *
   * @return the current tempo
   */
  int getTempo();

    /**
   * Gets the maximum octave of a song
   *
   * @return maximum octave
   */
  int getMaxOctave();

  /**
   * Gets the minimum octave in the song
   *
   * @return minimum octave
   */
  int getMinOctave();

  /**
   *  Adds an octave to the maximum octave of a song
   */
  void addMaxOctave();

  /**
   * Subtracts an octave from the minimum octave in the song
   */
  void subMinOctave();

  /**
   * Recalculates the maximum octave of the song
   */
  void reCalcMaxOctave();

  /**
   * Recalculates the minimum octave of the song
   */
  void reCalcMinOctave();

  /**
   * Gets the length of the song - number of measures
   * @return the song length
   */
  int songLength();

  /**
   * Adds a measure to the song length
   */
  void addSongLength();

  /**
   * Resets song length to only the necessary measures
   */
  void subSongLength();

  /**
   * Allows user to get the composition
   *
   * @return the composition/song
   */
  Set<ChordModel> getComposition();

  /**
   * Returns the number of notes in a song
   *
   * @return - number of notes
   */
  int notesInSong();

  /**
   * Returns the number of chords in a song
   * @return - chords in a song
   */
  int chordsInSong();

  /**
   * View the notes at a specified time If there is no cord at that time, returns an empty cord
   *
   * @param time - specified time
   * @return - the chord at said time
   */
  ChordModel startAtTime(int time);

  /**
   * Returns the set of all note being played during given beat,
   * whether its beginning or sustained
   * Added based off of the self-eval for HW05
   *
   * @param time - specified time
   * @return - set of notes at beat
   */
  Set<NoteModel> notesDuringTime(int time);

  //--------------------------------MUTATING-------------------------------

  /**
   * Allows the user to set the tempo
   *
   * @param newTempo the new tempo
   * @throws IllegalArgumentException if tempo is less than or equal to 0
   */
  void setTempo(int newTempo);

  /**
   * Adds a new note to the song
   *
   * @param newNote note to add
   * @throws IllegalArgumentException if start time and newNote start time don't match
   */
  void addNote(NoteModel newNote);

  /**
   * Adds a new chord to the song
   *
   * @param newChord chord to add
   */
  void addChord(ChordModel newChord);

  /**
   * Adds a song to the current song
   *
   * @param newSong song to add
   */
  void addSongToSong(Set<ChordModel> newSong);

  /**
   * Adds a song to the end of the current song
   *
   * @param newSong song to add to the end
   */
  void addSongToEnd(Set<ChordModel> newSong);

    /**
   * Deletes a note from the song
   *
   * @param oldNote note to delete
   */
  void removeNote(NoteModel oldNote);

  /**
   * Edits an existing note's pitch
   *
   * @param currentNote note to change
   * @param newPitch pitch to change to
   */
  void editNotePitch(NoteModel currentNote, NoteModel.Pitch newPitch);

  /**
   * Edits an existing note's duration
   *
   * @param currentNote note to change
   * @param newLength position of note to change
   */
  void editNoteLength(NoteModel currentNote, int newLength);

  /**
   * Edits an existing note's start time
   *
   * @param currentNote note to change
   * @param newStartTime new start time for note
   */
  void editNoteStartTime(NoteModel currentNote, int newStartTime);

  @Override
  // Updated to reflect right justified line numbers and support for octave -1 and 10
  String toString();
}
