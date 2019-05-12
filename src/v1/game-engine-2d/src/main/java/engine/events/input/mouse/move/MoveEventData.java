package main.java.engine.events.input.mouse.move;

import main.java.engine.events.input.mouse.MouseEventData;
import java.util.ArrayList;
import main.java.programs.info.global;
import main.java.utilities.chars.strings;
import main.java.utilities.math.vector.MathVector2D;

public class MoveEventData extends MouseEventData {

    protected MathVector2D<Double> mouseVelocity;


    public MoveEventData(MathVector2D<Double> mouseVelocity) {
        this.mouseVelocity = mouseVelocity;
    }


    @Override
    public ArrayList getContents() {
        ArrayList retList = new ArrayList();
        retList.add(mouseVelocity);
        return retList;
    }


    public MathVector2D<Double> getMouseVelocity() {
        return mouseVelocity;
    }


    public String toString() {
        String retString = "";
        String horizontalMove = "";
        String verticalMove = "";

        if (mouseVelocity.getX() != 0) {
            if (mouseVelocity.getX() > 0) {
                horizontalMove += "Move Right";
            } else {
                horizontalMove += "Move Left";
            }
            horizontalMove += strings.genericToStringSeparated(" ", ": \t", mouseVelocity.getX(), mouseVelocity.getUnits()) + "\n";
        }

        if (mouseVelocity.getY() != 0) {
            if (mouseVelocity.getY() > 0) {
                verticalMove += "Move Down";
            } else {
                verticalMove += "Move Up";
            }
            verticalMove += strings.genericToStringSeparated(" ", ": \t", mouseVelocity.getY(), mouseVelocity.getUnits()) + "\n";
        }

        // retString += global.horizontalLine;
        retString += "MoveEventData:" + "\n";
        retString += horizontalMove;
        retString += verticalMove;
        retString += strings.repeatString("-", " ", 20);

        return retString;

    }


}
