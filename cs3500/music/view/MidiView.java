package cs3500.music.view;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.model.ChordModel;
import cs3500.music.model.CompositionModel;

/**
 * Created by ofishstein on 11/12/15.
 */
public interface MidiView extends View {

  @Override
  void setComp(CompositionModel comp);

  /**
   * Plays a note for the midi
   *
   * @param instrument the instrument for the note
   * @param tickLength the length of the note
   * @param pitch the pitch of the note
   * @param volume the volume of the note
   * @throws InvalidMidiDataException
   */
  void playNote(int instrument, int tickLength, int pitch, int volume)
      throws InvalidMidiDataException;

  /**
   * Plays the song by going through and playing all notes
   *
   * @param startBeat the beat to start at
   * @throws InvalidMidiDataException
   */
  void playSong(int[] startBeat);

  /**
   * Play all notes at the current beat
   * @param notes
   * @throws InvalidMidiDataException
   */
  void playNotesAtBeat(ChordModel notes) throws InvalidMidiDataException;

  /**
   * Returns the log from the {@link MockReceiver} for test
   *
   * @return the log
   */
  String returnLog();

  @Override
  void render();

  /**
   * Closes the receiver
   */
  void close();
}
