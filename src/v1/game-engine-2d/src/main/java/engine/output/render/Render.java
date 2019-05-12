package main.java.engine.output.render;

import main.java.engine.output.render.OpenGL.Display;
import main.java.engine.output.render.interfaces.Drawable;
import main.java.engine.events.output.OutputEvent;
import main.java.engine.events.output.OutputEventFireObject;
import main.java.engine.events.output.render.RenderEvent;
import main.java.engine.events.output.render.RenderEventListenerInterface;
import main.java.engine.events.output.render.display.DisplayEvent;
import static org.lwjgl.opengl.GL11.*;
import main.java.engine.output.Output;
import main.java.engine.output.render.objects._2d.DrawFrame;
import main.java.utilities.math.vector.MathVector2D;
import main.java.engine.events.EventListenerInterface;
import main.java.engine.output.render.info.global;
import main.java.engine.output.render.objects.DrawObject;
import main.java.utilities.chars.strings;

/**
 * A class which represents an object which controls Drawable objects.
 *
 * @author Mike Resnik
 * @since 2017-06-13
 * @see output.render.Drawable
 */
public class Render
        extends OutputEventFireObject<OutputEvent>
        implements RenderEventListenerInterface<RenderEvent> {

    private Display display;
    private Output output;


    public Render(Display display, Output output) {
        this.display = display;
        this.output = output;
        this.addListener(output);
        this.display.addListener(this);
    }


    public void draw(Drawable element) {

    }


    // TODO : Change xy to MathVector4D
    // TODO : Change wh to NumericalMatrix
    public static void drawQuad(DrawFrame frame, MathVector2D<Integer> position, Integer[] dimensions) {
        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, frame.textureID);

        glPushMatrix();
        glBegin(GL_QUADS);
        {
            glTexCoord2d(0.0, 0.0);
            glVertex2d(position.getX(),
                    position.getY());

            glTexCoord2d(1.0, 0.0);
            glVertex2d(position.getX() + dimensions[0], position.getY());

            glTexCoord2d(1.0, 1.0);
            glVertex2d(position.getX() + dimensions[0],
                    position.getY()
                    + dimensions[1]);

            glTexCoord2d(0.0, 1.0);
            glVertex2d(position.getX(), position.getY() + dimensions[1]);
        }
        glEnd();

        glPopMatrix();
        glDisable(GL_TEXTURE_2D);
    }


    public Display getDisplay() {
        return display;
    }


    public static void resizeScreen(double window_width, double window_height) {

        // <editor-fold desc="Set Variables">
        Integer[] screen_dimensions_pixels = DrawObject.screen.getDimensionsPixels();
        double screen_width = 0.0, screen_height = 0.0;
        double screen_ratio = global.screen_ratio;
        double window_ratio = window_width / window_height;
        System.out.println("screen_ratio:" + screen_ratio);
        System.out.println("window_ratio:" + window_ratio);
        // </editor-fold>

        // <editor-fold desc="Resize screen dimensions based on ratio">
        if (screen_ratio > window_ratio) {
            screen_height = window_width / screen_ratio;
            screen_width = window_width;
        } else if (screen_ratio < window_ratio) {
            screen_width = window_height * screen_ratio;
            screen_height = window_height;
            System.out.println("screen_width:" + screen_width);
        } else {
            screen_width = window_width;
            screen_height = window_height;
        }
        // </editor-fold>

        // <editor-fold desc="Set Dimensions">
        MathVector2D<Double> screen_dimensions
                = Render.getPercentages(
                        new MathVector2D<Integer>(
                                (int) screen_width,
                                (int) screen_height
                        )
                );
        DrawObject.screen.setAbsolutePercentDimensions(screen_dimensions.toArray());
        // </editor-fold>

        // <editor-fold desc="Set Coordinates">
        double space_x = Math.abs((window_width - screen_width) / 2);
        double space_y = Math.abs((window_height - screen_height) / 2);
        MathVector2D<Double> screen_coordinates
                = Render.getPercentages(
                        new MathVector2D<Integer>(
                                (int) space_x,
                                (int) space_y
                        )
                );
        DrawObject.screen.setAbsolutePercentCoordinates(screen_coordinates);
        //</editor-fold>
    }


    //<editor-fold desc="Conversion Methods">
    // TODO : Change into Matrix
    public static MathVector2D<Double> getPercentages(MathVector2D<Integer> pixelVector) {
        return new MathVector2D<Double>(
                pixelVector.getTime(),
                (100.0 * pixelVector.getX()) / Display.instance.getWidthHeight()[0],
                (100.0 * pixelVector.getY()) / Display.instance.getWidthHeight()[1]
        );
    }


    // TODO : Change into Matrix
    /**
     * Returns a pixel representation of a passed percentage array.
     *
     * @param percentVector
     * @return
     */
    public static MathVector2D<Integer> getPixels(MathVector2D<Double> percentVector) {
        return new MathVector2D<Integer>(
                percentVector.getTime(),
                (int) (percentVector.getX() * Display.instance.getWidthHeight()[0] / 100.0),
                (int) (percentVector.getY() * Display.instance.getWidthHeight()[1] / 100.0)
        );

    }

    //</editor-fold>

    // <editor-fold desc="Event Handling">
    @Override
    public void fireEvent(OutputEvent event) {
        if (event.getSource() instanceof Display) {
            for (EventListenerInterface<OutputEvent> L : listeners) {
                L.messageRecieved(event);
            }
        } else if (event instanceof DisplayEvent) {
            this.display.messageRecieved((DisplayEvent) event);
        }
    }


    @Override
    public Class<RenderEvent> getEventType() {
        return RenderEvent.class;
    }


    @Override
    public void messageRecieved(RenderEvent event) {
        this.fireEvent(event);
    }

    // </editor-fold>

}
