package main.java.engine.events.program.serial.save;

import main.java.engine.events.program.serial.SerialEvent;

public class SaveEvent extends SerialEvent<SaveEventData>{

    public SaveEvent(Object source, SaveEventData data) {
        super(source, data);
    }

}
