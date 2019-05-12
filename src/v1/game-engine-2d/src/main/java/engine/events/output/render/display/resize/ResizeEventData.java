package main.java.engine.events.output.render.display.resize;

import main.java.engine.events.output.render.display.DisplayEventData;
import java.util.ArrayList;
import java.util.Arrays;
import main.java.utilities.chars.strings;

public class ResizeEventData extends DisplayEventData {

    private int window_width, window_height;


    public ResizeEventData(int window_width, int window_height) {
        this.window_width = window_width;
        this.window_height = window_height;
    }


    public int getWindow_width() {
        return window_width;
    }


    public int getWindow_height() {
        return window_height;
    }


    @Override
    public ArrayList getContents() {
        return new ArrayList(Arrays.asList(window_width, window_height));
    }


    @Override
    public String toString() {
        String retString = "";

        retString += "ResizeEvent: " + "\n";
        retString += "Window Width:" + this.window_width + "\n";
        retString += "Window Height:" + this.window_height + "\n";
        retString += strings.repeatString("-", " ", 15);


        return retString;
    }


}
