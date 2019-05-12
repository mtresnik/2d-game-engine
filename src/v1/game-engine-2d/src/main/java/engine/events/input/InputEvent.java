package main.java.engine.events.input;

import main.java.engine.events.program.ProgramEvent;

public abstract class InputEvent<T extends InputEventData> extends ProgramEvent<T> {

    public InputEvent(Object source, T data) {
        super(source, data);
    }


}
