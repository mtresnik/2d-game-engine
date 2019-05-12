package main.java.utilities.interfaces;

import main.java.engine.events.EventObject;

public interface State {

    public void execute();


    public State next(EventObject event);


}
