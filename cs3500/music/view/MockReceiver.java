package cs3500.music.view;

import java.util.ArrayList;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/**
 * Mock Receiver class to implement {@link Receiver} and {@MidiView} in test
 * Created by ofishstein on 11/13/15.
 */
public class MockReceiver implements Receiver {

  private StringBuilder log;
  private ArrayList<int[]> on;
  private ArrayList<int[]> off;
  private int tempo;
  private int currentBeat;
  private int[] prev;
  /**
   * Creates an instance of MockReceiver
   */
  public MockReceiver(){
    this.log = new StringBuilder();
  }

  /**
   * Returns the log for testing purposes
   *
   * @return the log of activity within the receiver
   */
  public String getLog(){
    return this.log.toString();
  }

  /**
   * Sets the tempo for the receiver
   *
   * @param tempo tempo of the composition
   */
  public void setTempo(int tempo){
    this.tempo = tempo;
    log.append("tempo " + tempo + "\n");
  }

  public void setCurrentBeat(int currentBeat) {
    this.currentBeat = currentBeat;
  }


  @Override
  public void send(MidiMessage message, long timeStamp) {
    ShortMessage t = ((ShortMessage) message);

    int[] info = new int[4];


    info[0] = (((int) timeStamp)/tempo) + currentBeat;
    info[1] = t.getChannel() + 1;
    info[2] = t.getData1();
    info[3] = t.getData2();

    switch (t.getCommand()){
      case 144:
        this.prev = info;
        break;
      case 128:
        log.append("note " + prev[0] + " " + info[0] + " " + info[1] + " " + info[2] +
            " " + info[3] + "\n");
        break;
      default:
        throw new IllegalStateException();
    }
  }

  @Override
  public void close() {
  }
}
