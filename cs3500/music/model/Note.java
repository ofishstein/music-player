package cs3500.music.model;

/**
 * Created by Kofi on 11/1/2015.
 *
 * Representing a musical note
 *
 * Class Invariants: A note's octave is always between -1 and 9 A note's length is always greater
 * than 0 A note's start time is always greater than 0
 */
public final class Note implements NoteModel {
  //Fields of a note
  //Added volume and instrument parameters to note
  private NoteModel.Pitch pitch;
  private int octave;
  private int length;
  private int startTime;
  private int volume;
  private int instrument;

  /**
   * Constructs a note object
   *
   * @param pitch     - desired pitch
   * @param octave    - desired octave (-1 to 9)
   * @param length    - duration of the note
   * @param startTime - time of note attach
   */
  public Note(Pitch pitch, int octave, int length, int startTime, int volume, int instrument) {
    if (octave > 10 || octave < -1 || length < 0 || startTime < 0 || volume < 0 ||
        instrument < 1 || instrument > 128)
      throw new IllegalArgumentException();

    this.pitch = pitch;
    this.octave = octave;
    this.length = length;
    this.startTime = startTime;
    this.volume = volume;
    this.instrument = instrument;
  }

  public Pitch getPitch() {
    return this.pitch;
  }

  public int getOctave() {
    return this.octave;
  }

  public int getLength() {
    return length;
  }

  public int getStartTime() {
    return this.startTime;
  }

  public int getVolume() {
    return this.volume;
  }

  public int getInstrument() {
    return this.instrument;
  }

  public void setPitch(Pitch p) {
    this.pitch = p;
  }

  public void setOctave(int o) {
    if (o < 0)
      throw new IllegalArgumentException();

    this.octave = o;
  }

  public void setLength(int l) {
    if (l < 0)
      throw new IllegalArgumentException();

    this.length = l;
  }

  public void setStartTime(int s) {
    if (s < 0)
      throw new IllegalArgumentException();

    this.startTime = s;
  }

  public void setVolume(int v) {
    if (v < 0)
      throw new IllegalArgumentException();

    this.volume = v;
  }

  public void setInstrument(int i) {
    if (i < 1 || i > 128)
      throw new IllegalArgumentException();

    this.instrument = i;
  }

}
