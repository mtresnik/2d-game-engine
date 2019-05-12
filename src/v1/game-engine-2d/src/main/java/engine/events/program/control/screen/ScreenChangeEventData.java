package main.java.engine.events.program.control.screen;

import main.java.engine.events.program.control.ControlEventData;
import main.java.engine.program.output.screens.Screen;
import java.util.ArrayList;

public class ScreenChangeEventData extends ControlEventData {

    protected Screen screen;


    public ScreenChangeEventData(Screen screen) {
        this.screen = screen;
    }


    @Override
    public ArrayList getContents() {
        return new ArrayList();
    }


    @Override
    public String toString() {
        String retString = "";
        return retString;
    }


    public Screen getScreen() {
        return screen;
    }


}
