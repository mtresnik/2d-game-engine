package main.java.engine.events.program.control.play;

import main.java.engine.events.program.control.ControlEvent;

public class PlayEvent extends ControlEvent<PlayEventData> {

    public PlayEvent(Object source, PlayEventData data) {
        super(source, data);
    }

}
