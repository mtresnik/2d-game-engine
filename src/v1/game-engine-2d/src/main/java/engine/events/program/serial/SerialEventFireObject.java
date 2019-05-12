package main.java.engine.events.program.serial;

import main.java.engine.events.EventFireObject;
import main.java.engine.events.EventListenerInterface;

public class SerialEventFireObject extends EventFireObject<SerialEvent>{

    @Override
    public void fireEvent(SerialEvent event) {
        for(EventListenerInterface<SerialEvent> listener : this.listeners){
            listener.messageRecieved(event);
        }
    }

}
