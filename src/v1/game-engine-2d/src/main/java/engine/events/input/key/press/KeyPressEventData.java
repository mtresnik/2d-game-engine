package main.java.engine.events.input.key.press;

import main.java.engine.events.input.key.KeyEventData;
import main.java.engine.input.keyboard.Keyboard;

import java.util.ArrayList;
import main.java.utilities.chars.strings;

public class KeyPressEventData extends KeyEventData {

    private int key;
    private int scancode;
    private int action;
    private int mods;


    public KeyPressEventData(int key, int scancode, int action, int mods) {

        this.key = key;
        this.scancode = scancode;
        this.action = action;
        this.mods = mods;

    }


    @Override

    public ArrayList getContents() {

        ArrayList retList = new ArrayList();
        retList.add(key);
        retList.add(scancode);
        retList.add(action);
        retList.add(mods);
        return retList;

    }


    public int getKey() {

        return key;

    }


    public int getScancode() {

        return scancode;

    }


    public int getAction() {

        return action;

    }


    public int getMods() {

        return mods;

    }


    @Override
    public String toString() {
        String retString = "";

        retString += "KeyPressEvent: " + "\n";
        retString += "Key:" + (char) this.key + "\n";
        retString += "Action:" + Keyboard.actionMap[this.action] + "\n";
        retString += strings.repeatString("-", " ", 15);


        return retString;
    }


}
