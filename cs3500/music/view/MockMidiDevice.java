package cs3500.music.view;

import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

/**
 * Created by ofishstein on 11/13/15.
 */
public class MockMidiDevice implements MidiDevice {

  /**
   * Creates an instance of MockMidiDevice
   */
  public MockMidiDevice(){

  }

  @Override
  public Receiver getReceiver() throws MidiUnavailableException {
    return new MockReceiver();
  }


  @Override
  public Info getDeviceInfo() {
    return null;
  }

  @Override
  public void open() throws MidiUnavailableException {

  }

  @Override
  public void close() {

  }

  @Override
  public boolean isOpen() {
    return false;
  }

  @Override
  public long getMicrosecondPosition() {
    return 0;
  }

  @Override
  public int getMaxReceivers() {
    return 0;
  }

  @Override
  public int getMaxTransmitters() {
    return 0;
  }

  @Override
  public List<Receiver> getReceivers() {
    return null;
  }

  @Override
  public Transmitter getTransmitter() throws MidiUnavailableException {
    return null;
  }

  @Override
  public List<Transmitter> getTransmitters() {
    return null;
  }
}
