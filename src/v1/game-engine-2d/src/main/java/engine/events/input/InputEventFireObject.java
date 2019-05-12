package main.java.engine.events.input;

import main.java.engine.events.EventFireObject;
import main.java.engine.events.EventListenerInterface;

public class InputEventFireObject extends EventFireObject<InputEvent> {

    @Override
    public void fireEvent(InputEvent event) {
        for (EventListenerInterface<InputEvent> listener : this.listeners) {
            listener.messageRecieved(event);
        }
    }


}
