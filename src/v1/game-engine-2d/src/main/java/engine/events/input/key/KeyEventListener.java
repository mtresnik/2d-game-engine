package main.java.engine.events.input.key;

import main.java.engine.events.input.InputEventListenerInterface;

public class KeyEventListener<T extends KeyEvent> implements InputEventListenerInterface<KeyEvent> {

    @Override
    public Class<KeyEvent> getEventType() {
        return KeyEvent.class;
    }


    @Override
    public void messageRecieved(KeyEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
