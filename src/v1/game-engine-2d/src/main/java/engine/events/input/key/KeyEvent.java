package main.java.engine.events.input.key;

import main.java.engine.events.input.InputEvent;
import main.java.engine.events.input.InputEventFireObject;
import main.java.engine.events.EventFireInterface;
import main.java.engine.events.EventListenerInterface;

public class KeyEvent<T extends KeyEventData> extends InputEvent<T> {

    public KeyEvent(Object source, T data) {
        super(source, data);
    }


    @Override
    public Class<? extends EventFireInterface> getFireClass() {
        return InputEventFireObject.class;
    }


    @Override
    public Class<? extends EventListenerInterface> getListenerClass() {
        return KeyEventListener.class;
    }


}
