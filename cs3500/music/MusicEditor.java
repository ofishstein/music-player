
package cs3500.music;

import java.io.FileReader;
import java.io.IOException;

import cs3500.music.controller.Controller;
import cs3500.music.controller.ControllerImpl;
import cs3500.music.model.Composition;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.GuiView.CombinedViewImpl;
import cs3500.music.view.ConsoleViewImpl;
import cs3500.music.view.GuiView.GUIView;
import cs3500.music.view.GuiView.GuiViewImpl;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.View;

public class MusicEditor {

  public static void main(String[] args) throws IOException{
    if (args.length > 2){
      throw new IllegalArgumentException();
    }

    String fileName = args[1];
    String viewName = args[0];

    CompositionBuilder<Composition> piece = new Composition.Builder();
    FileReader file = new FileReader(fileName);
    Composition comp = MusicReader.parseFile(file, piece);

    View view = viewFactory(viewName);
    view.setComp(comp);

    if(viewName.equals("midi") || viewName.equals("console")){
      view.render();
    }
    else{
      Controller controller = new ControllerImpl((GUIView)view);
      controller.render();
    }
  }

  /**
   * Constructs a view based off a given string
   *
   * @param viewName the type of the view to be created, either console, midi, or visual
   * @return a view
   */
  public static View viewFactory(String viewName){
    switch (viewName) {
      case "console":
        return new ConsoleViewImpl();
      case "midi":
        return new MidiViewImpl();
      case "visual":
        return new GuiViewImpl();
      default:
        return new CombinedViewImpl(new MidiViewImpl(), new GuiViewImpl());
    }
  }
}

