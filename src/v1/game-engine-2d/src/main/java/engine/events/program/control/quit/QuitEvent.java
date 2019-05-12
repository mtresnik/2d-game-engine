package main.java.engine.events.program.control.quit;

import main.java.engine.events.program.control.ControlEvent;

public class QuitEvent extends ControlEvent<QuitEventData> {

    public QuitEvent(Object source, QuitEventData data) {
        super(source, data);
    }


}
