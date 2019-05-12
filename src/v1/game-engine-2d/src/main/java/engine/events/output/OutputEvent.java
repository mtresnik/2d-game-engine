package main.java.engine.events.output;

import main.java.engine.events.program.ProgramEvent;

public abstract class OutputEvent<T extends OutputEventData> extends ProgramEvent<T> {

    public OutputEvent(Object source, T data) {
        super(source, data);
    }


}
