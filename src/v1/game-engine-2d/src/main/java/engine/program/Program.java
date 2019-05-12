package main.java.engine.program;

import main.java.engine.program.logic.Controller;
import main.java.engine.output.Output;
import main.java.engine.output.render.OpenGL.Display;
import main.java.programs.games.tiled.SIGameController;

public class Program implements Runnable {

    @Override
    public void run() {
        Display display  = new Display(500,500);
        Output output = new Output(display);
        Controller controller = new SIGameController(output);
        controller.start();
    }


}
