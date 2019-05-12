package main.java.engine.events.input.mouse.scroll;

import main.java.engine.events.input.mouse.MouseEvent;

public class ScrollEvent extends MouseEvent<ScrollEventData>{

    public ScrollEvent(Object source, ScrollEventData data) {
        super(source, data);
    }

}
