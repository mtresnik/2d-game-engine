package main.java.engine.output.render.objects.buttons;

import main.java.engine.output.render.interfaces.Drawable;
import main.java.engine.output.render.objects._2d.DrawFrame;
import main.java.engine.output.render.objects.DrawObject;
import main.java.engine.output.render.objects._2d.DrawTexture;
import main.java.engine.program.output.screens.Screen;
import main.java.utilities.math.vector.MathVector2D;

public abstract class DrawButtonObject implements ButtonInterface {

    protected DrawObject background;
    protected DrawObject releasedImage;
    protected DrawObject pressedImage;
    protected DrawObject optionalHoverObject;
    protected Screen screen;
    private boolean detectTransparency;


    DrawButtonObject(
            String name,
            MathVector2D<Double> percentCoordinates,
            Double[] percentDimensions,
            Object background,
            Object pressedImage,
            boolean detectTransparency,
            Screen screen,
            DrawObject... optionalHoverObject) {
        this.background = castToDrawTexture(name, background, percentCoordinates, percentDimensions);
        this.pressedImage = castToDrawTexture(name, pressedImage, percentCoordinates, percentDimensions);
        this.detectTransparency = detectTransparency;
        this.screen = screen;
        if (optionalHoverObject.length > 0) {
            this.optionalHoverObject = optionalHoverObject[0];
        } else {
            this.optionalHoverObject = null;
        }
    }


    // TODO : Put in DrawTexture
    private static DrawTexture castToDrawTexture(String name, Object objectToCast, MathVector2D<Double> percentCoordinates, Double[] percentDimensions) {
        if (objectToCast instanceof String) {
            System.out.println("TRYING TO LOAD:" + objectToCast);
            return new DrawTexture(name, (String) objectToCast, percentCoordinates, percentDimensions);
        } else if (objectToCast instanceof DrawFrame) {
            return new DrawTexture(name, (DrawFrame) objectToCast, percentCoordinates, percentDimensions);
        } else if (objectToCast instanceof DrawObject) {
            return new DrawTexture(name, ((DrawObject) objectToCast).getCurrentFrame(), percentCoordinates, percentDimensions);
        } else {
            throw new IllegalArgumentException("Parameter 'objectToCast' must be of type STRING, DRAWFRAME, or DRAWOBJECT.");
        }
    }


    @Override
    public void draw() {
        this.background.draw();
        this.getForeground().draw();
    }


    @Override
    public void hover() {
        if (this.optionalHoverObject != null) {
            this.optionalHoverObject.draw();
        }
    }


    @Override
    public boolean inBounds(MathVector2D<Double> percentCoordinates) {
        if (detectTransparency == true) {
            return this.getForeground().inBounds(percentCoordinates) || this.getBackground().inBounds(percentCoordinates);
        } else {
            // Use square detection
            Double cursor_x = percentCoordinates.getX(), cursor_y = percentCoordinates.getY();

            Double image_x = this.background.getAbsolutePercentCoordinates().getX();
            Double image_y = this.background.getAbsolutePercentCoordinates().getY();
            Integer[] wh = this.background.getDimensionsPixels();
            if (image_x <= cursor_x && cursor_x <= image_x + wh[0]) {
                if (image_y <= cursor_y && cursor_y <= image_y + wh[1]) {
                    return true;
                }
            }
            return false;
        }

    }


    public DrawObject getBackground() {
        return this.background;
    }


    public abstract Drawable getForeground();


    public final void press() {
        if (this.pressedImage != null) {
            this.releasedImage = this.background;
            this.background = this.pressedImage;
        }
        this.performPressFunction();
    }


    public final void release() {
        System.out.println("RELEASED:" + this.getBackground().toString());
        if (this.releasedImage != null) {
            this.background = this.releasedImage;
        }
        this.performReleaseFunction();
    }


    public Screen getScreen() {
        return screen;
    }


    protected abstract void performPressFunction();


    protected abstract void performReleaseFunction();


}
