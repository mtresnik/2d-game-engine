package main.java.engine.events.output.render.display.resize;

import main.java.engine.events.output.render.display.DisplayEvent;

public class ResizeEvent extends DisplayEvent<ResizeEventData>{

    public ResizeEvent(Object source, ResizeEventData data) {
        super(source, data);
    }

}
