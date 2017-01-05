package cs3500.music.model;

/**
 * Created by ofishstein on 11/3/15.
 *
 * Re-created by Kofi on 11/9/15
 */
public interface NoteModel {
  /**
   * Gets the pitch of the note
   *
   * @return the pitch of the note
   */
  Pitch getPitch();

  /**
   * Gets the octave of the note
   *
   * @return the octave of the note
   */
  int getOctave();

  /**
   * Gets the duration of the note
   *
   * @return the duration of the note
   */
  int getLength();

  /**
   * Gets the start time of the note
   *
   * @return the start time of the note
   */
  int getStartTime();

  /**
   * Gets the start time of the note
   *
   * @return the start time of the note
   */
  int getVolume();

  /**
   * Gets the start time of the note
   *
   * @return the instrument of the note
   */
  int getInstrument();

  /**
   * Changes the pitch of a Note
   *
   * @param newPitch new pitch
   */
  void setPitch(Pitch newPitch);

  /**
   * Changes the octave of a Note
   *
   * @param newOctave new octave
   * @throws IllegalArgumentException if octave is out of range
   */
  void setOctave(int newOctave);

  /**
   * Changes the duration of a Note
   *
   * @param newDuration new duration
   * @throws IllegalArgumentException if duration is out of range
   */
  void setLength(int newDuration);

  /**
   * Changes the start time of a Note
   *
   * @param newStartTime new start time
   * @throws IllegalArgumentException if start time is out of range
   */
  void setStartTime(int newStartTime);

  /**
   * Changes the volume of a Note
   *
   * @param newVolume new volume
   * @throws IllegalArgumentException if volume is out of range
   */
  void setVolume(int newVolume);

  /**
   * Changes the instrument of a Note
   *
   * @param newInstrument new instrument
   * @throws IllegalArgumentException if instrument is out of range
   */
  void setInstrument(int newInstrument);


  /**
   * Created by ofishstein on 11/2/15.
   *
   * A representation for {@link Note} for {@link Composition}
   *
   * A sharp symbol is represented as a 's'
   */
  public enum Pitch {
    C(0, 1, 1), Cs(1, 3, 4), D(2, 7, 9), Ds(3, 9, 12), E(4, 13, 17), F(5, 16, 21), Fs(6, 18, 24),
    G(7, 22, 29), Gs(8, 24, 32), A(9, 28, 37), As(10, 30, 40), B(11, 34, 45);

    private int value;
    private int printOffset;
    private int altPrintOffset;

    /**
     * Constructs a pitch
     *
     * @param printOffset the offset to create a console view
     */
    Pitch(int value, int printOffset, int altPrintOffset){
      this.value = value;
      this.printOffset = printOffset;
      this.altPrintOffset = altPrintOffset;
    }

    /**
     * Gets the print space offset for a pitch
     *
     * @return gets the print space offset
     */
    public int getPrintOffset(){
      return this.printOffset;
    }

    /**
     * Gets the print space offset for a pitch if the octave is -1 or 10
     *
     * @return gets the print space offset
     */
    public int getAltPrintOffset(){
      return this.altPrintOffset;
    }

    public int getValue(){
      return this.value;
    }

    /**
     * Gets the pitch corresponding to a given value
     *
     * @param value the pitch value
     * @throws IllegalArgumentException if the pitch value doesn't exist
     * @return the Pitch enum corresponding to the given value
     */
    public static Pitch getPitchFromValue(int value){
      for (Pitch p: Pitch.values()){
        if ( p.value == value){
          return p;
        }
      }
      throw new IllegalArgumentException();
    }

    /**
     * Returns the enum as a string with octave number
     *
     * @param octaveNumber the octave to print
     * @return the enum as a string
     */
    public static String enumToString(int octaveNumber){
      String result;

      result = "C" + octaveNumber +"C#" + octaveNumber + " D" + octaveNumber + "D#" + octaveNumber +
          " E" + octaveNumber + " F" + octaveNumber + "F#" + octaveNumber + " G" + octaveNumber +
          "G#" + octaveNumber + " A" + octaveNumber + "A#" + octaveNumber + " B" + octaveNumber
          + " ";

      return result;
    }
  }
}
