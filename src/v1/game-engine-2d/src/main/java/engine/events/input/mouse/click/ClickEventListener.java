package main.java.engine.events.input.mouse.click;

import main.java.engine.events.input.mouse.MouseEventListener;

public abstract class ClickEventListener extends MouseEventListener<ClickEvent> {

    @Override
    public Class<ClickEvent> getEventType() {
        return ClickEvent.class;
    }


    @Override
    public abstract void messageRecieved(ClickEvent event);

}
