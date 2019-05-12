package main.java.engine.events.program.control.start;

import main.java.engine.events.program.control.ControlEvent;

public class StartEvent extends ControlEvent<StartEventData> {

    public StartEvent(Object source, StartEventData data) {
        super(source, data);
    }


}
