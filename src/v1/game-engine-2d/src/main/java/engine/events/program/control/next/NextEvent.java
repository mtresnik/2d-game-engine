package main.java.engine.events.program.control.next;

import main.java.engine.events.program.control.ControlEvent;

public class NextEvent extends ControlEvent<NextEventData>{

    public NextEvent(Object source, NextEventData data) {
        super(source, data);
    }

}
