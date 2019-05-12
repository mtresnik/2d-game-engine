package main.java.engine.events.input.mouse.scroll;

import main.java.engine.events.input.mouse.MouseEventData;
import java.util.ArrayList;
import main.java.programs.info.global;
import main.java.utilities.chars.strings;
import main.java.utilities.math.vector.MathVector2D;

public class ScrollEventData extends MouseEventData {

    protected MathVector2D<Double> scrollVelocity;


    public ScrollEventData(MathVector2D<Double> scrollVelocity) {
        this.scrollVelocity = scrollVelocity;
    }


    @Override
    public ArrayList getContents() {
        ArrayList retList = new ArrayList();
        retList.add(scrollVelocity);
        return retList;
    }


    public MathVector2D<Double> getScrollVelocity() {
        return scrollVelocity;
    }


    @Override
    public String toString() {
        String retString = "";
        String horizontalScroll = "";
        String verticalScroll = "";

        if (scrollVelocity.getX() != 0) {
            if (scrollVelocity.getX() > 0) {
                horizontalScroll += "Scroll Right";
            } else {
                horizontalScroll += "Scroll Left";
            }
            horizontalScroll += ": " + scrollVelocity.getX() + "\n";
        }

        if (scrollVelocity.getY() != 0) {
            if (scrollVelocity.getY() > 0) {
                verticalScroll += "Scroll Down";
            } else {
                verticalScroll += "Scroll Up";
            }
            verticalScroll += ": " + scrollVelocity.getY() + "\n";
        }

        // retString += global.horizontalLine;
        retString += "ScrollEventData:" + "\n";
        retString += horizontalScroll;
        retString += verticalScroll;
        retString += strings.repeatString("-", " ", 15);

        return retString;
    }


}
