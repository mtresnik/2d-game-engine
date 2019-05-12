package main.java.engine.input.mouse;

import main.java.engine.events.input.InputEventFireObject;
import main.java.engine.events.input.mouse.MouseEvent;
import main.java.engine.events.input.mouse.click.ClickEvent;
import main.java.engine.events.input.mouse.click.ClickEventData;
import main.java.engine.events.input.mouse.move.MoveEvent;
import main.java.engine.events.input.mouse.move.MoveEventData;
import main.java.engine.events.input.mouse.scroll.ScrollEvent;
import main.java.engine.events.input.mouse.scroll.ScrollEventData;
import main.java.engine.input.Input;
import main.java.engine.output.render.Render;
import main.java.engine.events.input.mouse.context.ContextChangeEvent;
import main.java.engine.events.input.mouse.context.ContextChangeEventData;
import main.java.utilities.math.vector.MathVector2D;
import main.java.utilities.math.vector.MathVector4D;

public class Mouse extends InputEventFireObject {

    protected MathVector2D<Integer> pixelPosition;

    public final static String[] buttonMap = new String[]{"left", "right", "middle"};
    public final static String[] actionMap = new String[]{"release", "click"};

    public Mouse(Input input) {
        this.pixelPosition = new MathVector2D<Integer>(System.currentTimeMillis(), 0, 0);
        this.addListener(input);
    }


    public void positionHandle(MathVector2D<Integer> position) {
        MathVector2D<Integer> deltaPosition = position.subtract(this.pixelPosition);
        Long deltaTime = position.getTime() - this.pixelPosition.getTime();
        this.pixelPosition = position;
        Double deltaX = deltaPosition.getX().doubleValue();
        Double deltaY = deltaPosition.getY().doubleValue();
        if (deltaTime == 0) {
            return;
        }
        MathVector2D<Double> velocity
                = new MathVector2D<Double>(
                        position.getTime(),
                        Math.ceil(1000 * deltaX / deltaTime) / 1000,
                        Math.ceil(1000 * deltaY / deltaTime) / 1000
                );
        velocity.setUnits("(pixel/ms)");
        MoveEventData dataToFire = new MoveEventData(velocity);
        MouseEvent eventToFire = new MoveEvent(this, dataToFire);
        this.fireEvent(eventToFire);
    }


    public void clickHandle(int button, int action, int mods) {
        ClickEventData dataToFire = new ClickEventData(button, action, mods);
        MouseEvent eventToFire = new ClickEvent(this, dataToFire);
        this.fireEvent(eventToFire);
    }


    public void scrollHandle(MathVector4D<Double> velocity) {
        ScrollEventData dataToFire = new ScrollEventData(velocity.stepDown().stepDown());
        MouseEvent eventToFire = new ScrollEvent(this, dataToFire);
        this.fireEvent(eventToFire);
    }


    public void enterScreenHandle(boolean entered) {
        ContextChangeEventData dataToFire = new ContextChangeEventData(entered);
        MouseEvent eventToFire = new ContextChangeEvent(this, dataToFire);
        this.fireEvent(eventToFire);
    }


    public MathVector2D<Integer> getCursorPositionPixels() {
        return this.pixelPosition;
    }


    public MathVector2D<Double> getCursorPositionPercentages() {
        return Render.getPercentages(this.pixelPosition);
    }


}
