package main.java.engine.events.input.mouse;

import main.java.engine.events.input.InputEvent;
import main.java.engine.events.input.InputEventFireObject;
import main.java.engine.events.EventFireInterface;
import main.java.engine.events.EventListenerInterface;

public class MouseEvent<T extends MouseEventData> extends InputEvent<T> {

    public MouseEvent(Object source, T data) {
        super(source, data);
    }


    @Override
    public Class<? extends EventFireInterface> getFireClass() {
        return InputEventFireObject.class;
    }


    @Override
    public Class<? extends EventListenerInterface> getListenerClass() {
        return MouseEventListener.class;
    }


}
