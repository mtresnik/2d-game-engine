package main.java.engine.program.logic;

import main.java.engine.events.input.InputEventListenerInterface;
import main.java.engine.events.output.OutputEventListenerInterface;
import main.java.engine.events.program.control.ControlEventListenerInterface;
import main.java.engine.input.Input;
import main.java.engine.output.Output;
import main.java.utilities.interfaces.Initializable;

public abstract class Controller
        implements
        Runnable,
        Initializable,
        InputEventListenerInterface,
        OutputEventListenerInterface,
        ControlEventListenerInterface {

    // protected Display display;
    protected Input input;
    protected Output output;
    protected boolean initialized;
    protected String name;


    public Controller(String name, Output output) {
        this.name = name;
        this.output = output;
        output.getRender().getDisplay().setController(this);
        initialized = true;
        this.input = new Input();
        this.input.addListener(this);
        this.output.addListener(this);
    }


    public Output getOutput() {
        return output;
    }


    public Input getInput() {
        return input;
    }


    public String getName() {
        return name;
    }


    public void start() {
        if (initialized == false) {
            throw new RuntimeException("Controller not initialized.");
        }
        this.getOutput().getRender().getDisplay().run();
    }


}
