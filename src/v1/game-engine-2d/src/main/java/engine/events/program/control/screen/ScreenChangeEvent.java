package main.java.engine.events.program.control.screen;

import main.java.engine.events.program.control.ControlEvent;

public class ScreenChangeEvent extends ControlEvent<ScreenChangeEventData> {

    public ScreenChangeEvent(Object source, ScreenChangeEventData data) {
        super(source, data);
    }


}
