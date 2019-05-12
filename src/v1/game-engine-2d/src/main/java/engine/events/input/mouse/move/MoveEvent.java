package main.java.engine.events.input.mouse.move;

import main.java.engine.events.input.mouse.MouseEvent;
import main.java.engine.events.input.mouse.MouseEventData;

public class MoveEvent extends MouseEvent<MouseEventData> {

    public MoveEvent(Object source, MouseEventData data) {
        super(source, data);
    }


}
