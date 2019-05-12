package main.java.engine.events.output.render.display;

import main.java.engine.events.output.render.RenderEvent;

public class DisplayEvent<T extends DisplayEventData> extends RenderEvent<T>{

    public DisplayEvent(Object source, T data) {
        super(source, data);
    }

}
