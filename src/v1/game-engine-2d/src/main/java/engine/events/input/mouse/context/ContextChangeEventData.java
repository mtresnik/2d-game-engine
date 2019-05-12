package main.java.engine.events.input.mouse.context;

import main.java.engine.events.input.mouse.MouseEventData;
import java.util.ArrayList;
import java.util.Arrays;
import main.java.programs.info.global;
import main.java.utilities.chars.strings;

public class ContextChangeEventData extends MouseEventData {

    protected boolean hasEntered;


    public ContextChangeEventData(boolean hasEntered) {
        this.hasEntered = hasEntered;
    }


    @Override
    public ArrayList getContents() {
        return new ArrayList(Arrays.asList(hasEntered));
    }


    public boolean hasEntered() {
        return hasEntered;
    }


    @Override
    public String toString() {
        String retString = "";

        retString += "ContextChangeEvent: " + "\n";
        if (hasEntered == true) {
            retString += "Mouse has entered the window.";
        } else {
            retString += "Mouse has exited the window.";
        }
        retString += "\n";
        retString += strings.repeatString("-", " ", 15);

        return retString;
    }


}
