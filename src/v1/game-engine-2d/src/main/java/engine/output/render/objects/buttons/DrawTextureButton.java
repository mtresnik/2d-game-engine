package main.java.engine.output.render.objects.buttons;

import main.java.engine.output.render.interfaces.Drawable;
import main.java.engine.output.render.objects._2d.DrawFrame;
import main.java.engine.output.render.objects.DrawObject;
import main.java.engine.output.render.objects._2d.DrawTexture;
import main.java.engine.program.output.screens.Screen;
import main.java.utilities.math.vector.MathVector2D;

public abstract class DrawTextureButton extends DrawButtonObject {

    protected DrawTexture foreground;


    public DrawTextureButton(String name, MathVector2D percentCoordinates, Double[] percentDimensions, Object background, Object pressedImage, Object foreground, boolean detectTransparency, Screen screen, DrawObject... optionalHoverObject) {
        super(name, percentCoordinates, percentDimensions, background, pressedImage, detectTransparency, screen, optionalHoverObject);
        if (foreground instanceof String) {
            this.foreground = new DrawTexture(name, (String) foreground, percentCoordinates, percentDimensions);
        } else if (foreground instanceof DrawFrame) {
            this.foreground = new DrawTexture(name, (DrawFrame) foreground, percentCoordinates, percentDimensions);
        } else if (foreground instanceof DrawObject) {
            this.foreground = new DrawTexture(name, ((DrawObject) foreground).getCurrentFrame(), percentCoordinates, percentDimensions);
        } else {
            throw new IllegalArgumentException("Fifth parameter 'background' must be of type STRING, DRAWFRAME, or DRAWOBJECT.");
        }
    }


    @Override
    public Drawable getForeground() {
        return this.foreground;
    }


}
