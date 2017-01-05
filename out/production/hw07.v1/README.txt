Music Model README

Kofi Collins-Sibley
11/3/2015

MODEL interface Files:
NoteModel.java
ChordModel.java
CompositionModel.java

VIEW interface Files:
View.java
GUIView.java
GuiPanel.java
ConsoleView.java
MidiView.java

CONTROLLER interface File:
Controller.java

Our IMPLEMENTATION Files:
ControllerImpl.java
KeyboardHandler.java
MouseHandler.java
Note.java
Chord.java
Composition.java
CombinedViewImpl.java
GuiPanelImpl.java
GuiViewImpl.java
NoteBox.java
ConsoleViewImpl.java
MidiViewImpl.java

TEST File:
MusicTest.java
MockMidiDevice.java
MockReceiver.java

We have represented a piece of music in the following way:

A musical song is comprised of a series of cords. Each cord contains a series of notes that
start at the same time

By definition, all notes in a cord start at the same time therefor they are overlapping, but
they do not share end times.

Cords can over lap within a song. Chord 2 can begin before the end time of the last note in
Chord 1.

A-----------
   E----------
B------
   D-------------
               F----
C----

A,B,C are members of the same chord
D,E are members of the same chord
F is a member of it's own chord

A Note keeps track of the start time, length, pitch, and octave of a note in the piece
of music.

Start times are simple integers, I am choosing to represent the time from start as a single
 increasing value. The first beat of a song is 0, the first beat of measure two of a song is 4
 Measure:      1       2       3
 Beat:         1 2 3 4 1 2 3 4 1 2 3  4
 My startTimes:0 1 2 3 4 5 6 7 8 9 10 11

 MODEL Changes from hw05 to hw06:
 - Changed compisition representation to a HashSet from a ArrayList
 - Interfaces refer to 'Set' as return type / arguments for methods
   that previously took ArrayLists

- Added Tempo to the Composition
  - Includes get and set method in interface
  - tempo int field in implementation

- Added notesDuringTime method
  - Returns all notes being played during given beat, improvement
  from previously only being able to return notes starting on a beat

- Added Volume to Notes (get and set methods)
- Added Instruments to Notes (get and set methods as well)

- Adobted Pitch enum from Ollie's hw05
  - Added AltPitchEnum (get methods)
  - Added Value (get methods)

Midi Output:
- For Midi testing we output a SORTED list as a string from the 
MockReciever, for testing we sort the given input txt file and compare.
- This is so that the lists are identical because they are sorted by the 
same sort method.

Main Arguments:
-Our main method reads the fileName of the piece of number as args[0]
and the view type as args[1]. For testing, edit configuration to send these two strings.

CHANGES FROM HW06 TO HW07:
Refactoring of the GUI view so that the GUI renders based on beat numbers
Refactoring of the MIDI view to work with a timer
New NoteBox class to provide a visual representation of the notes of a song
Controller added
Default view is the combined view

Controls:
-Space Bar:
Play/Pause song
plays from red line

-Left Arrow key:
Scroll the red line to the left

-Right Arrow key:
Scroll the red line to the right

-Home:
Jump to beginning

-End:
Jump to end

-Escape:
Exit program

-Left click on note head:
Delete note

-Left click on blank space:
Add new note

-Right click on note head:
Increase the note length by one,
this covers the edge case of notes of length 1, aka heads only

-Left click on note tail:
Increase the note length by one

-Right click on note tail:
Decrease note length by one

-Left click and drag on head:
Pick up the note and move it to a new location (pitch, time)
NOTE: does not display change until mouse button release

-Middle button click:
Add new percussion note, i.e. instrument #10