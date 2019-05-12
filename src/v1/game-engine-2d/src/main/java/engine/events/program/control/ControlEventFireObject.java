package main.java.engine.events.program.control;

import main.java.engine.events.EventFireObject;
import main.java.engine.events.EventListenerInterface;

public class ControlEventFireObject extends EventFireObject<ControlEvent>{

    @Override
    public void fireEvent(ControlEvent event) {
        for(EventListenerInterface<ControlEvent> listener : this.listeners){
            listener.messageRecieved(event);
        }
    }

}
