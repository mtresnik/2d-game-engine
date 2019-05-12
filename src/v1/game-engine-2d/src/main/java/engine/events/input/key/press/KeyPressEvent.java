package main.java.engine.events.input.key.press;

import main.java.engine.events.input.key.KeyEvent;

public class KeyPressEvent extends KeyEvent<KeyPressEventData> {

    public KeyPressEvent(Object source, KeyPressEventData data) {

        super(source, data);

    }


}
