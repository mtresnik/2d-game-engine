package main.java.engine.events;

import java.util.ArrayList;

public abstract class EventData {

    protected EventData() {
    }

    /**
     * Returns an ArrayList containing all of the objects of the container.
     * @return A new ArrayList based on an EventData's specifications.
     */
    public abstract ArrayList getContents();


}
