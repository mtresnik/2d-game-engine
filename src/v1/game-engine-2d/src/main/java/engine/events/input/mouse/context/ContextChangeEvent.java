package main.java.engine.events.input.mouse.context;

import main.java.engine.events.input.mouse.MouseEvent;

public class ContextChangeEvent extends MouseEvent<ContextChangeEventData> {

    public ContextChangeEvent(Object source, ContextChangeEventData data) {
        super(source, data);
    }


}
