package main.java.engine.output.render.objects.buttons;

import static main.java.engine.info.global.ui_objects.template_font.*;
import main.java.engine.output.render.interfaces.Drawable;
import main.java.engine.output.render.objects.DrawObject;
import main.java.engine.output.render.objects.font.DrawString;
import main.java.engine.program.output.screens.Screen;
import main.java.utilities.math.vector.MathVector2D;

public abstract class DrawTextButton extends DrawButtonObject {

    protected DrawString foreground;


    public DrawTextButton(String name, MathVector2D<Double> percentCoordinates, Double[] percentDimensions, Object unpressedImage, Object pressedImage, Object textToDraw, boolean detectTransparency, Screen screen, DrawObject... optionalHoverObject) {
        super(name, percentCoordinates, percentDimensions, unpressedImage, pressedImage, detectTransparency, screen, optionalHoverObject);
        if (textToDraw instanceof String) {
            this.foreground = new DrawString(times_atlas, (String) textToDraw, percentCoordinates, percentDimensions);
        } else if (textToDraw instanceof DrawString) {
            this.foreground = (DrawString) textToDraw;
        }else{
            throw new IllegalArgumentException("Parameter 'textToDraw' must be of type STRING or DRAWSTRING.");
        }
    }


    @Override
    public Drawable getForeground() {
        return this.foreground;
    }


}
