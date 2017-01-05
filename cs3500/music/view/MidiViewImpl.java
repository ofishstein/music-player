
package cs3500.music.view;

import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

import cs3500.music.model.ChordModel;
import cs3500.music.model.CompositionModel;
import cs3500.music.model.NoteModel;
import cs3500.music.model.Note;
import cs3500.music.model.Repeat;

/**
 * Implements {@link MidiView}
 */
public class MidiViewImpl implements MidiView {
  private MidiDevice synthesizer;
  private Receiver receiver;
  private CompositionModel comp;
  private int tempo;
  private boolean test;
  private boolean repeat;
  private boolean startPresent;
  private int repeatStart;
  private int repeatEnd;
  private int endingStart;

  /**
   * Default constructor for MidiViewImpl
   */
  public MidiViewImpl() {
    try {
      this.test = false;
      this.repeatStart = 0;
      this.repeat = false;
      this.synthesizer = MidiSystem.getSynthesizer();
      this.receiver = synthesizer.getReceiver();
      this.synthesizer.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }

  /**
   * Constructor for MidiViewImpl using {@link MockReceiver} and {@link MockMidiDevice} for test
   *
   * @param comp the compsition to base it off of
   */
  public MidiViewImpl(CompositionModel comp) {
    try {
      this.synthesizer = new MockMidiDevice();
      this.receiver = synthesizer.getReceiver();
      this.synthesizer.open();
      this.test = true;
      this.comp = comp;
      this.tempo = comp.getTempo();

      ((MockReceiver) this.receiver).setTempo(this.tempo);

      int [] startBeat = {0};
      playSong(startBeat);
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }


  public String returnLog(){
    return ((MockReceiver) this.receiver).getLog();
  }

  public void setComp(CompositionModel comp){
    this.comp = comp;
    this.tempo = comp.getTempo();
  }

  public void render(){
    int [] startBeat = {0};
    playSong(startBeat);
  }

  public void playSong(int[] startBeat){
    int[] currentBeat = startBeat;
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run(){
        try {
          if (test){
            ((MockReceiver) receiver).setCurrentBeat(currentBeat[0]);
          }

          playNotesAtBeat(comp.startAtTime(currentBeat[0]));

          if (repeat && currentBeat[0] == repeatEnd){
            currentBeat[0] = repeatStart;
          } else {
            if (repeat && currentBeat[0] == endingStart){
              currentBeat[0] = repeatEnd;
              repeat = false;
            }
            currentBeat[0]++;
          }

          if (currentBeat[0] > comp.songLength()){
            close();
            timer.cancel();
            timer.purge();
          }

        } catch (InvalidMidiDataException e) {
          e.printStackTrace();
        }
      }
    }, 0, (this.tempo/1000));

  }

  public void playNotesAtBeat(ChordModel notes) throws InvalidMidiDataException{
    for (NoteModel note: notes.getNotes()){
      if (note instanceof Note) {
        int pitch = (note.getOctave() + 1) * 12 + note.getPitch().getValue();
        playNote(note.getInstrument(), note.getLength(), pitch, note.getVolume());
      } else if (note instanceof Repeat) {
        if (note.getInstrument() == 0) {
          this.repeatStart = note.getStartTime();
          this.startPresent = true;
        } else {
          if (repeat) {
            this.repeat = false;

            if (this.startPresent){
              startPresent = false;
              this.repeatStart = 0;
            }

          } else {
            this.repeat = true;
            this.repeatEnd = note.getStartTime();
          }
        }
      } else {
        this.endingStart = note.getStartTime();
      }
    }
  }

  public void playNote(int instrument, int length, int pitch, int volume)
      throws InvalidMidiDataException {
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, instrument - 1, pitch, volume);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, instrument - 1, pitch, volume);
    this.receiver.send(start, 0);
    this.receiver.send(stop,
        this.synthesizer.getMicrosecondPosition() + (tempo * length));

  }

  public void close(){
    this.receiver.close();
  }
}

