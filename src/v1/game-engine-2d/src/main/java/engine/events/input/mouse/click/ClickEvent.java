package main.java.engine.events.input.mouse.click;

import main.java.engine.events.input.mouse.MouseEvent;

public class ClickEvent extends MouseEvent<ClickEventData>{

    public ClickEvent(Object source, ClickEventData data) {
        super(source, data);
    }
}
