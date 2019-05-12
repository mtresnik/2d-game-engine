package main.java.engine.events.input.mouse.click;

import main.java.engine.events.input.mouse.MouseEventData;
import main.java.engine.input.mouse.Mouse;
import java.util.ArrayList;
import java.util.Arrays;
import main.java.programs.info.global;
import main.java.utilities.chars.strings;

public class ClickEventData extends MouseEventData {

    protected int button;
    protected int action;
    protected int mods;


    public ClickEventData(int button, int action, int mods) {
        this.button = button;
        this.action = action;
        this.mods = mods;
    }


    @Override
    public ArrayList getContents() {
        ArrayList retList = new ArrayList(Arrays.asList(button, action, mods));
        return retList;
    }


    public int getButton() {
        return button;
    }


    public int getAction() {
        return action;
    }


    public int getMods() {
        return mods;
    }


    public String toString() {
        String retString = "";
        String whichButton = Mouse.buttonMap[this.button];
        String whichAction = Mouse.actionMap[this.action];
        // String whichMods = Mouse.modsMap[this.mods];
        retString += "ClickEvent: " + "\n";
        retString += "Button:" + whichButton + "\n";
        retString += "Action:" + whichAction + "\n";
        // retString += "Mods:" + whichMods + "\n";
        retString += strings.repeatString("-", " ", 15);
        return retString;
    }


}
