package main.java.engine.events.program.serial.load;

import main.java.engine.events.program.serial.SerialEvent;

public class LoadEvent extends SerialEvent<LoadEventData> {

    public LoadEvent(Object source, LoadEventData data) {
        super(source, data);
    }


}
