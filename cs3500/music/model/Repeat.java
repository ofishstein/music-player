package cs3500.music.model;

/**
 * Created by ofishstein on 12/10/15.
 *
 *
 */
public class Repeat implements NoteModel {

  private int startTime;
  private int type;

  public Repeat(int startTime, int type){
    this.startTime = startTime;
    this.type = type;
  }

  @Override
  public Pitch getPitch() {
    return null;
  }

  @Override
  public int getOctave() {
    return 0;
  }

  @Override
  public int getLength() {
    return 0;
  }

  @Override
  public int getStartTime() {
    return this.startTime;
  }

  @Override
  public int getVolume() {
    return 0;
  }

  @Override
  public int getInstrument() {
    return this.type;
  }

  @Override
  public void setPitch(Pitch newPitch) {

  }

  @Override
  public void setOctave(int newOctave) {

  }

  @Override
  public void setLength(int newDuration) {

  }

  @Override
  public void setStartTime(int newStartTime) {
    this.startTime = newStartTime;
  }

  @Override
  public void setVolume(int newVolume) {

  }

  @Override
  public void setInstrument(int newInstrument) {
    this.type = newInstrument;
  }
}
